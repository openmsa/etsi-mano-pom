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
	public <X> ValueBinder<X> getBinder(final JavaType<X> javaType) {
		return new BasicBinder<>(javaType, this) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void doBind(final PreparedStatement st, final X value, final int index, final WrapperOptions options) throws SQLException {
				st.setBytes(index, toJsonBytes(javaType.unwrap(value, String.class, options)));
			}

			@Override
			protected void doBind(final CallableStatement st, final X value, final String name, final WrapperOptions options)
					throws SQLException {
				st.setBytes(name, toJsonBytes(javaType.unwrap(value, String.class, options)));
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
