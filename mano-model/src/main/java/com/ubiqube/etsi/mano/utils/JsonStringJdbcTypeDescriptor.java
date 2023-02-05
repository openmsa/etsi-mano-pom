package com.ubiqube.etsi.mano.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;

public class JsonStringJdbcTypeDescriptor extends AbstractJsonJdbcTypeDescriptor {

	private static final long serialVersionUID = 1L;
	public static final JsonStringJdbcTypeDescriptor INSTANCE = new JsonStringJdbcTypeDescriptor();

	@Override
	public int getJdbcTypeCode() {
		return Types.VARCHAR;
	}

	@Override
	public <X> ValueBinder<X> getBinder(final JavaType<X> javaType) {
		return new BasicBinder<>(javaType, this) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void doBind(final PreparedStatement st, final X value, final int index, final WrapperOptions options) throws SQLException {
				st.setString(index, javaType.unwrap(value, String.class, options));
			}

			@Override
			protected void doBind(final CallableStatement st, final X value, final String name, final WrapperOptions options)
					throws SQLException {
				st.setString(name, javaType.unwrap(value, String.class, options));
			}
		};
	}

	@Override
	protected Object extractJson(final ResultSet rs, final int paramIndex) throws SQLException {
		return rs.getString(paramIndex);
	}

	@Override
	protected Object extractJson(final CallableStatement statement, final int index) throws SQLException {
		return statement.getString(index);
	}

	@Override
	protected Object extractJson(final CallableStatement statement, final String name) throws SQLException {
		return statement.getString(name);
	}
}
