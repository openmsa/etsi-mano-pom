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
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.internal.util.SerializationHelper;
import org.hibernate.type.SerializationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

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

	public Object clone(final Object object) {
		if (object instanceof String) {
			return object;
		}
		if (object instanceof final Collection<?> c) {
			final Object firstElement = findFirstNonNullElement(c);
			if ((firstElement != null) && !(firstElement instanceof Serializable)) {
				final JavaType type = TypeFactory.defaultInstance().constructParametricType(object.getClass(), firstElement.getClass());
				return fromBytes(toBytes(object), type);
			}
		} else if (object instanceof final Map<?, ?> m) {
			final Map.Entry<?, ?> firstEntry = findFirstNonNullEntry(m);
			if (firstEntry != null) {
				final Object key = firstEntry.getKey();
				final Object value = firstEntry.getValue();
				if (!(key instanceof Serializable) || !(value instanceof Serializable)) {
					final JavaType type = TypeFactory.defaultInstance().constructParametricType(object.getClass(), key.getClass(), value.getClass());
					return fromBytes(toBytes(object), type);
				}
			}
		} else if (object instanceof final JsonNode jn) {
			return jn.deepCopy();
		}

		if (object instanceof final Serializable s) {
			try {
				return SerializationHelper.clone(s);
			} catch (final SerializationException e) {
				// it is possible that object itself implements java.io.Serializable, but
				// underlying structure does not
				// in this case we switch to the other JSON marshaling strategy which doesn't
				// use the Java serialization
			}
		}

		return jsonClone(object);
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

	public byte[] toBytes(final Object value) {
		try {
			return mapper.writeValueAsBytes(value);
		} catch (final JsonProcessingException e) {
			throw new HibernateException(
					new IllegalArgumentException("The given Json object value: " + value + " cannot be transformed to a byte array", e));
		}
	}

	public <T> T fromBytes(final byte[] value, final Type type) {
		try {
			return mapper.readValue(value, mapper.getTypeFactory().constructType(type));
		} catch (final IOException e) {
			throw new HibernateException(
					new IllegalArgumentException("The given byte array cannot be transformed to Json object", e));
		}
	}

	private static Object findFirstNonNullElement(final Collection<?> collection) {
		for (final java.lang.Object element : collection) {
			if (element != null) {
				return element;
			}
		}
		return null;
	}

	private static Map.Entry<?, ?> findFirstNonNullEntry(final Map<?, ?> map) {
		for (final Map.Entry<?, ?> entry : map.entrySet()) {
			if ((entry.getKey() != null) && (entry.getValue() != null)) {
				return entry;
			}
		}
		return null;
	}

	private <T> T jsonClone(final T object) {
		return fromBytes(toBytes(object), (Class<T>) object.getClass());
	}
}
