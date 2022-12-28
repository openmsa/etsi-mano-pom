/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.utils;

import java.io.IOException;
import java.lang.reflect.Type;

import org.hibernate.HibernateException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ManoObjectMapper {
	private static final ManoObjectMapper INSTANCE = new ManoObjectMapper();
	private final ObjectMapper mapper;

	private ManoObjectMapper() {
		this.mapper = new ObjectMapper()
				.findAndRegisterModules();
	}

	public static ManoObjectMapper getInstance() {
		return INSTANCE;
	}

	public Object clone(final Object value) {
		return null;
	}

	public String toString(final Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (final JsonProcessingException e) {
			throw new HibernateException(
					new IllegalArgumentException("The given Json object value: " + value + " cannot be transformed to a String", e));
		}
	}

	public Object toJsonNode(final String value) {
		try {
			return mapper.readTree(value);
		} catch (final IOException e) {
			throw new HibernateException(
					new IllegalArgumentException(e));
		}
	}

	public Object fromString(final String string, final Type type) {
		try {
			return mapper.readValue(string, mapper.getTypeFactory().constructType(type));
		} catch (final IOException e) {
			throw new HibernateException(
					new IllegalArgumentException("The given string value: " + string + " cannot be transformed to Json object", e));
		}
	}

}
