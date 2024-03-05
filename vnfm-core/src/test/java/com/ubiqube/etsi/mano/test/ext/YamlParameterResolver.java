/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.test.ext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.exception.GenericException;

public class YamlParameterResolver implements ParameterResolver {

	private final ObjectMapper mapper;

	public YamlParameterResolver() {
		mapper = YamlFactory.createMapper();
	}

	@Override
	public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
		final Optional<YamlTestData> ann = parameterContext.findAnnotation(YamlTestData.class);
		return ann.isPresent();
	}

	@Override
	public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
		final YamlTestData ann = parameterContext.findAnnotation(YamlTestData.class).orElseThrow();
		return loadFile(ann.value(), parameterContext.getParameter().getType());
	}

	private Object loadFile(final String value, final Class<?> clazz) {
		try (InputStream is = getClass().getResourceAsStream(makeFilePath(value))) {
			Objects.requireNonNull(is, "Unable to find file: " + value);
			return mapper.readValue(is, clazz);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private static String makeFilePath(final String value) {
		return "/mano-data/" + value;
	}

}
