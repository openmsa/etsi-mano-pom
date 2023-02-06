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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaXMember;
import org.hibernate.engine.jdbc.BinaryStream;
import org.hibernate.engine.jdbc.internal.BinaryStreamImpl;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractClassJavaType;
import org.hibernate.type.descriptor.java.BlobJavaType;
import org.hibernate.type.descriptor.java.DataHelper;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import org.hibernate.usertype.DynamicParameterizedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManoJsonJavaTypeDescriptor extends AbstractClassJavaType<Object> implements DynamicParameterizedType {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(ManoJsonJavaTypeDescriptor.class);

	public static final ManoJsonJavaTypeDescriptor INSTANCE = new ManoJsonJavaTypeDescriptor();

	private static List<Class<?>> validatedTypes = new ArrayList<>();
	private transient Type propertyType;

	private Class<?> propertyClass;

	private transient ManoObjectMapper objectMapperWrapper = ManoObjectMapper.getInstance();

	private JdbcType jdbcType;

	public ManoJsonJavaTypeDescriptor() {
		super(Object.class, new MutableMutabilityPlan<>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected Object deepCopyNotNull(final Object value) {
				return ManoObjectMapper.getInstance().clone(value);
			}
		});
		setPropertyClass(Object.class);
	}

	@Override
	public void setParameterValues(final Properties parameters) {
		final XProperty xProperty = (XProperty) parameters.get(DynamicParameterizedType.XPROPERTY);
		final Type type = (xProperty instanceof final JavaXMember jm) ? jm.getJavaType() : ((ParameterType) parameters.get(PARAMETER_TYPE)).getReturnedClass();
		setPropertyClass(type);
	}

	@Override
	public boolean areEqual(final Object one, final Object another) {
		if (one == another) {
			return true;
		}
		if ((one == null) || (another == null)) {
			return false;
		}
		if ((one instanceof String) && (another instanceof String)) {
			return one.equals(another);
		}
		if (((one instanceof Collection) && (another instanceof Collection)) ||
				((one instanceof Map) && (another instanceof Map))) {
			return Objects.equals(one, another);
		}
		if (one.getClass().equals(another.getClass()) &&
				(ReflectionUtils.getDeclaredMethodOrNull(one.getClass(), "equals", Object.class) != null)) {
			return one.equals(another);
		}
		return objectMapperWrapper.toJsonNode(objectMapperWrapper.toString(one)).equals(
				objectMapperWrapper.toJsonNode(objectMapperWrapper.toString(another)));
	}

	@Override
	public String toString(final Object value) {
		return objectMapperWrapper.toString(value);
	}

	@Override
	public Object fromString(final CharSequence string) {
		if (propertyClass == null) {
			throw new HibernateException(
					"The propertyClass in JsonTypeDescriptor is null, " +
							"hence it doesn't know to what Java Object type " +
							"to map the JSON column value that was read from the database!");
		}
		if (String.class.isAssignableFrom(propertyClass)) {
			return string;
		}
		return objectMapperWrapper.fromString((String) string, propertyType);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public <X> X unwrap(final Object value, final Class<X> type, final WrapperOptions options) {
		if (value == null) {
			return null;
		}

		if (String.class.isAssignableFrom(type)) {
			return value instanceof String ? (X) value : (X) toString(value);
		}
		if (BinaryStream.class.isAssignableFrom(type) ||
				byte[].class.isAssignableFrom(type)) {
			final String stringValue = (value instanceof final String s) ? s : toString(value);

			return (X) new BinaryStreamImpl(DataHelper.extractBytes(new ByteArrayInputStream(stringValue.getBytes())));
		}
		if (Blob.class.isAssignableFrom(type)) {
			final String stringValue = (value instanceof final String s) ? s : toString(value);

			final Blob blob = BlobJavaType.INSTANCE.fromString(stringValue);
			return (X) blob;
		}
		if (Object.class.isAssignableFrom(type)) {
			final String stringValue = (value instanceof final String s) ? s : toString(value);
			return (X) objectMapperWrapper.toJsonNode(stringValue);
		}

		throw unknownUnwrap(type);
	}

	@Override
	public <X> Object wrap(final X value, final WrapperOptions options) {
		if (value == null) {
			return null;
		}

		Blob blob = null;

		if (Blob.class.isAssignableFrom(value.getClass())) {
			blob = options.getLobCreator().wrap((Blob) value);
		} else if (byte[].class.isAssignableFrom(value.getClass())) {
			blob = options.getLobCreator().createBlob((byte[]) value);
		} else if (InputStream.class.isAssignableFrom(value.getClass())) {
			final InputStream inputStream = (InputStream) value;
			try {
				blob = options.getLobCreator().createBlob(inputStream, inputStream.available());
			} catch (final IOException e) {
				throw unknownWrap(value.getClass());
			}
		}

		String stringValue;
		try {
			stringValue = (blob != null) ? new String(DataHelper.extractBytes(blob.getBinaryStream())) : value.toString();
		} catch (final SQLException e) {
			throw new HibernateException("Unable to extract binary stream from Blob", e);
		}

		return fromString(stringValue);
	}

	private void setPropertyClass(final Type type) {
		this.propertyType = type;
		if (type instanceof ParameterizedType) {
			this.propertyClass = (Class<?>) ((ParameterizedType) type).getRawType();
		} else if (type instanceof final TypeVariable<?> t) {
			this.propertyClass = t.getGenericDeclaration().getClass();
		} else {
			this.propertyClass = (Class<?>) type;
		}
		validatePropertyType();
	}

	private void validatePropertyType() {
		if (Collection.class.isAssignableFrom(propertyClass) && (propertyType instanceof final ParameterizedType parameterizedType)) {
			for (final Class<?> genericType : ReflectionUtils.getGenericTypes(parameterizedType)) {
				if (validatedTypes.contains(genericType)) {
					continue;
				}
				validatedTypes.add(genericType);
				final Method equalsMethod = ReflectionUtils.getMethodOrNull(genericType, "equals", Object.class);
				final Method hashCodeMethod = ReflectionUtils.getMethodOrNull(genericType, "hashCode");

				if ((equalsMethod == null) ||
						(hashCodeMethod == null) ||
						Object.class.equals(equalsMethod.getDeclaringClass()) ||
						Object.class.equals(hashCodeMethod.getDeclaringClass())) {
					LOG.warn("The {} class should override both the equals and hashCode methods based on the JSON object value it represents!", genericType);
				}
			}
		}
	}

	@Override
	public JdbcType getRecommendedJdbcType(final JdbcTypeIndicators indicators) {
		return jdbcType;
	}

	public void setJdbcType(final JdbcType jdbcType) {
		this.jdbcType = jdbcType;
	}

}
