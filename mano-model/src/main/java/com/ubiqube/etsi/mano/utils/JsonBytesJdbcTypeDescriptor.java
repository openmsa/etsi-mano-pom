package com.ubiqube.etsi.mano.utils;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;

public class JsonBytesJdbcTypeDescriptor extends AbstractJsonJdbcTypeDescriptor {

	private static final long serialVersionUID = 1L;

	public static final JsonBytesJdbcTypeDescriptor INSTANCE = new JsonBytesJdbcTypeDescriptor();

	public static final String CHARSET = "UTF8";

	private final int jdbcType;

	public JsonBytesJdbcTypeDescriptor() {
		this.jdbcType = Types.BINARY;
	}

	public JsonBytesJdbcTypeDescriptor(final int jdbcType) {
		this.jdbcType = jdbcType;
	}

	@Override
	public int getJdbcTypeCode() {
		return jdbcType;
	}

	@Override
	public <X> ValueBinder<X> getBinder(final JavaType<X> JavaType) {
		return new BasicBinder<>(JavaType, this) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void doBind(final PreparedStatement st, final X value, final int index, final WrapperOptions options) throws SQLException {
				st.setBytes(index, toJsonBytes(JavaType.unwrap(value, String.class, options)));
			}

			@Override
			protected void doBind(final CallableStatement st, final X value, final String name, final WrapperOptions options)
					throws SQLException {
				st.setBytes(name, toJsonBytes(JavaType.unwrap(value, String.class, options)));
			}
		};
	}

	// @Override
	@Override
	protected Object extractJson(final ResultSet rs, final int paramIndex) throws SQLException {
		return fromJsonBytes(rs.getBytes(paramIndex));
	}

	// @Override
	@Override
	protected Object extractJson(final CallableStatement statement, final int index) throws SQLException {
		return fromJsonBytes(statement.getBytes(index));
	}

	// @Override
	@Override
	protected Object extractJson(final CallableStatement statement, final String name) throws SQLException {
		return fromJsonBytes(statement.getBytes(name));
	}

	protected static byte[] toJsonBytes(final String jsonValue) {
		return jsonValue.getBytes(StandardCharsets.UTF_8);
	}

	protected static String fromJsonBytes(final byte[] jsonBytes) {
		if (jsonBytes == null) {
			return null;
		}
		return new String(jsonBytes, StandardCharsets.UTF_8);
	}
}
