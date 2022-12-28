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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.PostgreSQL81Dialect;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.engine.jdbc.dialect.spi.DatabaseMetaDataDialectResolutionInfoAdapter;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.usertype.ParameterizedType;

public class ManoJsonSqlTypeDescriptor extends AbstractJsonSqlTypeDescriptor implements ParameterizedType {

	private static final long serialVersionUID = 1L;
	private volatile Dialect dialect;
	private volatile AbstractJsonSqlTypeDescriptor sqlTypeDescriptor;

	private volatile Properties properties;

	public ManoJsonSqlTypeDescriptor() {
		// Nothing.
	}

//	public ManoJsonSqlTypeDescriptor(final Properties properties) {
//		this.properties = properties;
//	}

	@Override
	public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
		return new BasicBinder<>(javaTypeDescriptor, this) {
			@Override
			protected void doBind(final PreparedStatement st, final X value, final int index, final WrapperOptions options) throws SQLException {
				sqlTypeDescriptor(st.getConnection()).getBinder(javaTypeDescriptor).bind(
						st, value, index, options);
			}

			@Override
			protected void doBind(final CallableStatement st, final X value, final String name, final WrapperOptions options)
					throws SQLException {
				sqlTypeDescriptor(st.getConnection()).getBinder(javaTypeDescriptor).bind(
						st, value, name, options);
			}
		};
	}

	@Override
	protected Object extractJson(final ResultSet rs, final String name) throws SQLException {
		return sqlTypeDescriptor(rs.getStatement().getConnection()).extractJson(rs, name);
	}

	@Override
	protected Object extractJson(final CallableStatement statement, final int index) throws SQLException {
		return sqlTypeDescriptor(statement.getConnection()).extractJson(statement, index);
	}

	@Override
	protected Object extractJson(final CallableStatement statement, final String name) throws SQLException {
		return sqlTypeDescriptor(statement.getConnection()).extractJson(statement, name);
	}

	private AbstractJsonSqlTypeDescriptor sqlTypeDescriptor(final Connection connection) {
		if (sqlTypeDescriptor == null) {
			sqlTypeDescriptor = resolveSqlTypeDescriptor(connection);
		}
		return sqlTypeDescriptor;
	}

	private AbstractJsonSqlTypeDescriptor resolveSqlTypeDescriptor(final Connection connection) {
		try {
			final StandardDialectResolver dialectResolver = new StandardDialectResolver();
			final DatabaseMetaDataDialectResolutionInfoAdapter metaDataInfo = new DatabaseMetaDataDialectResolutionInfoAdapter(connection.getMetaData());
			dialect = dialectResolver.resolveDialect(metaDataInfo);
			if (dialect instanceof PostgreSQL81Dialect) {
				return ManoJsonBinarySqlTypeDescriptor.INSTANCE;
			}
			if (dialect instanceof H2Dialect) {
				return ManoJsonBytesSqlTypeDescriptor.INSTANCE;
			}
			return ManoJsonStringSqlTypeDescriptor.INSTANCE;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public int getSqlType() {
		return sqlTypeDescriptor != null ? sqlTypeDescriptor.getSqlType() : super.getSqlType();
	}

	@Override
	public void setParameterValues(final Properties parameters) {
		if (properties == null) {
			properties = parameters;
		} else {
			properties.putAll(parameters);
		}
	}
}
