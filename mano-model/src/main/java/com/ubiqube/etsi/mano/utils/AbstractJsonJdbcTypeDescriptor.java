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

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.BasicExtractor;
import org.hibernate.type.descriptor.jdbc.JdbcType;

public abstract class AbstractJsonJdbcTypeDescriptor implements JdbcType {

	private static final long serialVersionUID = 1L;

	@Override
	public int getJdbcTypeCode() {
		return Types.OTHER;
	}

	@Override
	public <X> ValueExtractor<X> getExtractor(final JavaType<X> javaType) {
		return new BasicExtractor<>(javaType, this) {
			private static final long serialVersionUID = 1L;

			protected X doExtract(final ResultSet rs, final int paramIndex, final WrapperOptions options) throws SQLException {
				return javaType.wrap(extractJson(rs, paramIndex), options);
			}

			@Override
			protected X doExtract(final CallableStatement statement, final int index, final WrapperOptions options) throws SQLException {
				return javaType.wrap(extractJson(statement, index), options);
			}

			@Override
			protected X doExtract(final CallableStatement statement, final String name, final WrapperOptions options) throws SQLException {
				return javaType.wrap(extractJson(statement, name), options);
			}
		};
	}

	@SuppressWarnings("static-method")
	protected Object extractJson(final ResultSet rs, final int paramIndex) throws SQLException {
		return rs.getObject(paramIndex);
	}

	@SuppressWarnings("static-method")
	protected Object extractJson(final CallableStatement statement, final int index) throws SQLException {
		return statement.getObject(index);
	}

	@SuppressWarnings("static-method")
	protected Object extractJson(final CallableStatement statement, final String name) throws SQLException {
		return statement.getObject(name);
	}
}
