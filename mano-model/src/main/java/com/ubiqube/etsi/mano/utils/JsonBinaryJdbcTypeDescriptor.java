package com.ubiqube.etsi.mano.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;

public class JsonBinaryJdbcTypeDescriptor extends AbstractJsonJdbcTypeDescriptor {

	private static final long serialVersionUID = 1L;
	public static final JsonBinaryJdbcTypeDescriptor INSTANCE = new JsonBinaryJdbcTypeDescriptor();

	@Override
	public <X> ValueBinder<X> getBinder(final JavaType<X> javaType) {
		return new BasicBinder<>(javaType, this) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void doBind(final PreparedStatement st, final X value, final int index, final WrapperOptions options) throws SQLException {
				st.setObject(index, javaType.unwrap(value, String.class, options), getJdbcTypeCode());
			}

			@Override
			protected void doBind(final CallableStatement st, final X value, final String name, final WrapperOptions options)
					throws SQLException {
				st.setObject(name, javaType.unwrap(value, String.class, options), getJdbcTypeCode());
			}
		};
	}
}
