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
package com.ubiqube.parser.tosca.sol006.javapoet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import com.ubiqube.parser.tosca.generator.YangException;
import com.ubiqube.parser.tosca.walker.ClassUtils;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;

/**
 *
 * @author Olivier Vignaud
 *
 */
public class JavaPoetHelper {

	private JavaPoetHelper() {
		// Nothing.
	}

	public static FieldSpec createField(final String field, final Class<?> clazz, final String descString, final String mandatory) {
		final String javaFieldName = ClassUtils.toJavaVariableName(field);
		final TypeName clz = ClassName.get(clazz);
		final com.squareup.javapoet.FieldSpec.Builder fieldBuilder = FieldSpec.builder(clz, javaFieldName, Modifier.PRIVATE);
		return createField(field, descString, mandatory, fieldBuilder);
	}

	public static FieldSpec createField(final String name, final ClassName clazz, final String description, final String mandatory) {
		final String javaFieldName = ClassUtils.toJavaVariableName(name);
		final com.squareup.javapoet.FieldSpec.Builder builder = FieldSpec.builder(clazz, javaFieldName, Modifier.PRIVATE);
		return createField(name, description, mandatory, builder);
	}

	public static FieldSpec createListField(final String name, final ClassName clazz, final String description, final Integer min, final Integer max) {
		final ClassName list = ClassName.get(List.class);
		final ParameterizedTypeName ptn = ParameterizedTypeName.get(list, clazz);
		final String javaFieldName = ClassUtils.toJavaVariableName(name);
		final com.squareup.javapoet.FieldSpec.Builder fieldBuilder = FieldSpec.builder(ptn, javaFieldName, Modifier.PRIVATE)
				.addAnnotation(Valid.class);
		if (null != min) {
			final AnnotationSpec spec = AnnotationSpec.builder(Min.class).addMember("value", "$L", min).build();
			fieldBuilder.addAnnotation(spec);
		}
		if (null != max) {
			final AnnotationSpec spec = AnnotationSpec.builder(Max.class).addMember("value", "$L", max).build();
			fieldBuilder.addAnnotation(spec);
		}
		if (description != null) {
			fieldBuilder.addJavadoc(description);
		}
		if (!javaFieldName.equals(name)) {
			final AnnotationSpec spec = AnnotationSpec.builder(XmlElement.class).addMember("name", "$L", "\"" + name + "\"").build();
			fieldBuilder.addAnnotation(spec);
		}
		return fieldBuilder.build();
	}

	public static FieldSpec createField(final String field, final String description, final String mandatory, final com.squareup.javapoet.FieldSpec.Builder fieldBuilder) {
		final String javaFieldName = ClassUtils.toJavaVariableName(field);
		fieldBuilder.addAnnotation(Valid.class);
		if (!javaFieldName.equals(field)) {
			final AnnotationSpec spec = AnnotationSpec.builder(XmlElement.class).addMember("name", "$L", "\"" + field + "\"").build();
			fieldBuilder.addAnnotation(spec);
		}
		if (mandatory != null) {
			fieldBuilder.addAnnotation(NotNull.class);
		}
		if (null != description) {
			fieldBuilder.addJavadoc(description);
		}
		return fieldBuilder.build();
	}

	public static void serializeClass(final Path path, final String pkg, final TypeSpec cls) {
		checkPackageName(pkg);
		final JavaFile javaFile = JavaFile
				.builder(pkg, cls)
				.indent("\t")
				.build();
		try {
			javaFile.writeTo(path);
		} catch (final IOException | RuntimeException e) {
			throw new YangException("Exception during serialization of : " + cls, e);
		}

	}

	private static void checkPackageName(final String pkg) {
		if (pkg.contains(".int.") || pkg.contains(".interface.")) {
			throw new YangException("");
		}
	}

	public static void createGetterSetter(final Builder parent, final FieldSpec cf, final boolean nonnull) {
		final String fieldName = cf.name;
		final TypeName fieldType = cf.type;
		final String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		final MethodSpec.Builder mb = MethodSpec
				.methodBuilder("get" + methodName)
				.addModifiers(Modifier.PUBLIC)
				.returns(fieldType)
				.addStatement("return this." + fieldName);
		if (nonnull) {
			mb.addAnnotation(NotNull.class);
		}
		parent.addMethod(mb.build());
		// Setter
		final MethodSpec.Builder methodBuilder = MethodSpec
				.methodBuilder("set" + methodName)
				.addModifiers(Modifier.PUBLIC);
		methodBuilder.addStatement("this." + fieldName + " = " + fieldName);
		if (nonnull) {
			final ParameterSpec param = ParameterSpec.builder(fieldType, fieldName, Modifier.FINAL).addAnnotation(NotNull.class).build();
			methodBuilder.addParameter(param);
		} else {
			methodBuilder.addParameter(fieldType, fieldName, Modifier.FINAL);
		}
		parent.addMethod(methodBuilder.build());
	}

}
