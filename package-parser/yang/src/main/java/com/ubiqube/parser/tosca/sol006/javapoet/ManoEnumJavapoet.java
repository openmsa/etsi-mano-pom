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

import java.util.List;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import jakarta.annotation.Nullable;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author Olivier Vignaud
 *
 */
public class ManoEnumJavapoet {
	private static final String VALUE = "value";

	private ManoEnumJavapoet() {
		//
	}

	public static TypeSpec createEnum(final String pkg, final String name, final List<String> lst) {
		final Builder enumBuiler = TypeSpec.enumBuilder(name)
				.addModifiers(Modifier.PUBLIC)
				.addAnnotation(XmlEnum.class);
		final TypeName clazz = ClassName.get(String.class);
		enumBuiler.addField(clazz, VALUE, Modifier.PRIVATE);
		lst.forEach(x -> {
			final String javaName = toJAvaname(x);
			final AnnotationSpec annoSpec = AnnotationSpec.builder(XmlEnumValue.class)
					.addMember(VALUE, "$L", "\"" + x + "\"")
					.build();
			final TypeSpec spec = TypeSpec.anonymousClassBuilder("$L", "\"" + x + "\"")
					.addAnnotation(annoSpec)
					.build();
			enumBuiler.addEnumConstant(javaName, spec);
		});
		// Constructor
		final MethodSpec ctor = MethodSpec.constructorBuilder()
				.addParameter(clazz, VALUE, Modifier.FINAL)
				.addStatement("this.value = value")
				.build();
		enumBuiler.addMethod(ctor);
		// toString
		final TypeName retType = ClassName.get(String.class);
		final MethodSpec toStringMethod = MethodSpec
				.methodBuilder("toString")
				.addAnnotation(Override.class)
				.returns(retType)
				.addModifiers(Modifier.PUBLIC)
				.addStatement("return this.value")
				.build();
		enumBuiler.addMethod(toStringMethod);
		// fromValue
		final ClassName ret = ClassName.get(pkg, name);
		final MethodSpec fromValue = MethodSpec
				.methodBuilder("fromValue")
				.addModifiers(Modifier.PUBLIC)
				.addParameter(clazz, "text")
				.addAnnotation(Nullable.class)
				.returns(ret)
				.addCode("""
						for (final $N b : $N.values()) {
							if (String.valueOf(b.value).equalsIgnoreCase(text)) {
								return b;
							}
						}
						return null;
						""", name, name)
				.build();
		enumBuiler.addMethod(fromValue);
		return enumBuiler.build();
	}

	private static String toJAvaname(final String name) {
		return name.toUpperCase().replace("-", "_");
	}
}
