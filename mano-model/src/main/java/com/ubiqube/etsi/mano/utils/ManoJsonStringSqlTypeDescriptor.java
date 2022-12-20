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
import java.sql.Types;
import java.util.Properties;

import org.hibernate.dialect.Database;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.hibernate.dialect.PostgreSQL81Dialect;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.engine.jdbc.dialect.spi.DatabaseMetaDataDialectResolutionInfoAdapter;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.usertype.DynamicParameterizedType;

import com.vladmihalcea.hibernate.type.json.internal.AbstractJsonSqlTypeDescriptor;
import com.vladmihalcea.hibernate.type.json.internal.JsonBinarySqlTypeDescriptor;
import com.vladmihalcea.hibernate.type.json.internal.JsonBlobSqlTypeDescriptor;
import com.vladmihalcea.hibernate.type.json.internal.JsonBytesSqlTypeDescriptor;
import com.vladmihalcea.hibernate.type.json.internal.JsonStringSqlTypeDescriptor;
import com.vladmihalcea.hibernate.type.util.ParameterTypeUtils;
import com.vladmihalcea.hibernate.util.StringUtils;

public class ManoJsonStringSqlTypeDescriptor implements SqlTypeDescriptor {

	private static final long serialVersionUID = 1L;

	public static final ManoJsonStringSqlTypeDescriptor INSTANCE = new ManoJsonStringSqlTypeDescriptor();

	private AbstractJsonSqlTypeDescriptor sqlTypeDescriptor;

	private Properties properties;

	@Override
	public int getSqlType() {
		return Types.JAVA_OBJECT;
	}

	@Override
	public boolean canBeRemapped() {
		return true;
	}

	@Override
	public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
		return new BasicBinder<>(javaTypeDescriptor, this) {
			@Override
			protected void doBind(final PreparedStatement st, final X value, final int index, final WrapperOptions options) throws SQLException {
				try (Connection conn = st.getConnection()) {
					sqlTypeDescriptor(conn).getBinder(javaTypeDescriptor).bind(st, value, index, options);
				}
			}

			@Override
			protected void doBind(final CallableStatement st, final X value, final String name, final WrapperOptions options)
					throws SQLException {
				try (Connection conn = st.getConnection()) {
					sqlTypeDescriptor(conn).getBinder(javaTypeDescriptor).bind(st, value, name, options);
				}
			}
		};
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
			final Dialect dialect = dialectResolver.resolveDialect(metaDataInfo);
			if (dialect instanceof PostgreSQL81Dialect) {
				return JsonBinarySqlTypeDescriptor.INSTANCE;
			}
			if (dialect instanceof H2Dialect) {
				return JsonBytesSqlTypeDescriptor.INSTANCE;
			}
			if (dialect instanceof Oracle8iDialect) {
				if (properties != null) {
					final DynamicParameterizedType.ParameterType parameterType = ParameterTypeUtils.resolve(properties);
					if (parameterType != null) {
						final String columnType = ParameterTypeUtils.getColumnType(parameterType);
						if (!StringUtils.isBlank(columnType)) {
							switch (columnType) {
							case "json":
								return JsonBytesSqlTypeDescriptor.of(Database.ORACLE);
							case "blob", "clob":
								return JsonBlobSqlTypeDescriptor.INSTANCE;
							case "varchar2", "nvarchar2":
								return JsonStringSqlTypeDescriptor.INSTANCE;
							default:
							}
						}
					}
				}
				if (metaDataInfo.getDatabaseMajorVersion() >= 21) {
					return JsonBytesSqlTypeDescriptor.of(Database.ORACLE);
				}
			}
			return JsonStringSqlTypeDescriptor.INSTANCE;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
		return new BasicExtractor<>(javaTypeDescriptor, this) {
			@Override
			protected X doExtract(final ResultSet rs, final String name, final WrapperOptions options) throws SQLException {
				return javaTypeDescriptor.wrap(extractJson(rs, name), options);
			}

			@Override
			protected X doExtract(final CallableStatement statement, final int index, final WrapperOptions options) throws SQLException {
				return javaTypeDescriptor.wrap(extractJson(statement, index), options);
			}

			@Override
			protected X doExtract(final CallableStatement statement, final String name, final WrapperOptions options) throws SQLException {
				return javaTypeDescriptor.wrap(extractJson(statement, name), options);
			}
		};
	}

	protected static Object extractJson(final ResultSet rs, final String name) throws SQLException {
		return rs.getString(name);
	}

	protected static Object extractJson(final CallableStatement statement, final int index) throws SQLException {
		return statement.getString(index);
	}

	protected static Object extractJson(final CallableStatement statement, final String name) throws SQLException {
		return statement.getString(name);
	}

}
