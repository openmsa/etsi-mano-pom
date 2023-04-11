/**
 *     Copyright (C) 2019-2023 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.parser.tosca.walker;

import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import com.ubiqube.parser.tosca.sol006.javapoet.JavaPoetHelper;
import com.ubiqube.parser.tosca.sol006.javapoet.ManoEnumJavapoet;
import com.ubiqube.parser.tosca.sol006.statement.ContainerStatement;
import com.ubiqube.parser.tosca.sol006.statement.LeafListStatement;
import com.ubiqube.parser.tosca.sol006.statement.LeafStatement;
import com.ubiqube.parser.tosca.sol006.statement.ListStatement;
import com.ubiqube.parser.tosca.sol006.statement.TypeStatement;
import com.ubiqube.parser.tosca.sol006.statement.UsesStatement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.xml.bind.annotation.XmlElement;

public class JavaPoetListener implements WalkerListener {
	private static final Logger LOG = LoggerFactory.getLogger(JavaPoetListener.class);
	private final Path targetFolder;

	private final Map<String, TypeSpec> cache = new HashMap<>();
	private Context currentContext;
	private final LinkedList<Context> stack = new LinkedList<>();
	private final Map<String, Class<?>> types = new HashMap<>();

	public JavaPoetListener(final String path) {
		targetFolder = Paths.get(path);
		types.put("string", String.class);
		types.put("int16", Integer.class);
		types.put("uint16", Integer.class);
		types.put("int32", Long.class);
		types.put("uint32", Long.class);
		types.put("int64", BigInteger.class);
		types.put("uint64", BigInteger.class);
		types.put("boolean", Boolean.class);
	}

	@Override
	public void startContainer(final ContainerStatement container) {
		final String pkg = ClassUtils.getPackage(container);
		final String className = ClassUtils.toJavaClassName(container.getName());
		createClass(pkg, className, container.getName());
	}

	private void createClass(final String pkg, final String className, final String fieldName) {
		if (currentContext != null) {
			stack.push(currentContext);
		}
		final Builder builder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC);
		currentContext = new Context(pkg, className, fieldName, builder);
	}

	@Override
	public void endContainer(final ContainerStatement x) {
		serializeClass();
	}

	private TypeSpec serializeClass() {
		final TypeSpec cls = currentContext.builder.build();
		cache.put(currentContext.pkg + "." + currentContext.className, cls);
		JavaPoetHelper.serializeClass(targetFolder, currentContext.pkg, cls);
		if (!stack.isEmpty()) {
			currentContext = stack.pop();
		}
		return cls;
	}

	@Override
	public void listStart(final ListStatement x) {
		final String pkg = ClassUtils.getPackage(x);
		final String className = ClassUtils.toJavaClassName(x.getName());
		createClass(pkg, className, x.getName());
	}

	@Override
	public void listEnd(final ListStatement x) {
		final Context tmp = currentContext;
		serializeClass();
		final ClassName list = ClassName.get(List.class);
		final ClassName typ = ClassName.get(tmp.pkg, tmp.className);
		final ParameterizedTypeName ptn = ParameterizedTypeName.get(list, typ);
		final String javaFieldName = ClassUtils.toJavaVariableName(tmp.fieldName);
		final com.squareup.javapoet.FieldSpec.Builder fieldBuilder = FieldSpec.builder(ptn, javaFieldName, Modifier.PRIVATE)
				.addAnnotation(Valid.class);
		if (null != x.getMinElements()) {
			final AnnotationSpec spec = AnnotationSpec.builder(Min.class).addMember("value", "$L", x.getMinElements()).build();
			fieldBuilder.addAnnotation(spec);
		}
		if (!javaFieldName.equals(tmp.fieldName)) {
			final AnnotationSpec spec = AnnotationSpec.builder(XmlElement.class).addMember("name", "$L", "\"" + tmp.fieldName + "\"").build();
			fieldBuilder.addAnnotation(spec);
		}
		final FieldSpec field = fieldBuilder.build();
		currentContext.builder.addField(field);
		createGetterSetter(field, false);
	}

	@Override
	public void leafStart(final LeafStatement x) {
		// Nothing.
	}

	@Override
	public void leafEnd(final LeafStatement x) {
		final Class<?> type = types.get(x.getType().getName());
		if (null != type) {
			final FieldSpec field = JavaPoetHelper.createField(x.getName(), type, x.getDescription(), x.getMandatory());
			currentContext.builder.addField(field);
			JavaPoetHelper.createGetterSetter(currentContext.builder, field, false);
		} else if ("enumeration".equals(x.getType().getName())) {
			final String className = ClassUtils.toJavaClassName(x.getName() + "Type");
			final TypeSpec ts = createEnumeration(className, x.getType());
			JavaPoetHelper.serializeClass(targetFolder, currentContext.pkg, ts);
			final ClassName clazz = ClassName.get(currentContext.pkg, className);
			final FieldSpec field = JavaPoetHelper.createField(x.getName(), clazz, x.getDescription(), x.getMandatory());
			currentContext.builder.addField(field);
			JavaPoetHelper.createGetterSetter(currentContext.builder, field, false);
		} else {
			LOG.warn("Ignoring {}/{}", x.getName(), x.getType().getName());
		}
	}

	private TypeSpec createEnumeration(final String name, final TypeStatement type) {
		final List<String> values = type.getEnu().stream().map(x -> x.getName()).toList();
		return ManoEnumJavapoet.createEnum(currentContext.pkg, name, values);
	}

	@Override
	public void leafListStart(final LeafListStatement x) {
		System.out.println("leaf-ref: " + x.getName() + " " + x.getType().getName());
		// System.out.println(" " + pkg + " " + x.getName());
	}

	@Override
	public void leafListEnd(final LeafListStatement x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void usesStart(final UsesStatement x) {
		// Nothing.

	}

	@Override
	public void usesEnd(final UsesStatement x) {
		// Nothing.

	}

	void createGetterSetter(final FieldSpec cf, final boolean nonnull) {
		JavaPoetHelper.createGetterSetter(currentContext.builder, cf, nonnull);
	}

	record Context(String pkg, String className, String fieldName, Builder builder) {
		//
	}
}
