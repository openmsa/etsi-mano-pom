package com.ubiqube.etsi.mano.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.OracleDialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.engine.jdbc.dialect.spi.DatabaseMetaDataDialectResolutionInfoAdapter;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;
import org.hibernate.usertype.ParameterizedType;

public class ManoJsonJdbcTypeDescriptor extends AbstractJsonJdbcTypeDescriptor implements ParameterizedType {

	private static final long serialVersionUID = 1L;
	private volatile Dialect dialect;
	private volatile AbstractJsonJdbcTypeDescriptor jdbcTypeDescriptor;

	private volatile Properties properties;

	public ManoJsonJdbcTypeDescriptor() {
	}

	public ManoJsonJdbcTypeDescriptor(final Properties properties) {
		this.properties = properties;
	}

	@Override
	public <X> ValueBinder<X> getBinder(final JavaType<X> javaType) {
		return new BasicBinder<>(javaType, this) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void doBind(final PreparedStatement st, final X value, final int index, final WrapperOptions options) throws SQLException {
				sqlTypeDescriptor(st.getConnection()).getBinder(javaType).bind(
						st, value, index, options);
			}

			@Override
			protected void doBind(final CallableStatement st, final X value, final String name, final WrapperOptions options)
					throws SQLException {
				sqlTypeDescriptor(st.getConnection()).getBinder(javaType).bind(
						st, value, name, options);
			}
		};
	}

	@Override
	protected Object extractJson(final ResultSet rs, final int paramIndex) throws SQLException {
		return sqlTypeDescriptor(rs.getStatement().getConnection()).extractJson(rs, paramIndex);
	}

	@Override
	protected Object extractJson(final CallableStatement statement, final int index) throws SQLException {
		return sqlTypeDescriptor(statement.getConnection()).extractJson(statement, index);
	}

	@Override
	protected Object extractJson(final CallableStatement statement, final String name) throws SQLException {
		return sqlTypeDescriptor(statement.getConnection()).extractJson(statement, name);
	}

	private AbstractJsonJdbcTypeDescriptor sqlTypeDescriptor(final Connection connection) {
		if (jdbcTypeDescriptor == null) {
			jdbcTypeDescriptor = resolveJdbcTypeDescriptor(connection);
		}
		return jdbcTypeDescriptor;
	}

	private AbstractJsonJdbcTypeDescriptor resolveJdbcTypeDescriptor(final Connection connection) {
		try {
			final StandardDialectResolver dialectResolver = new StandardDialectResolver();
			final DatabaseMetaDataDialectResolutionInfoAdapter metaDataInfo = new DatabaseMetaDataDialectResolutionInfoAdapter(connection.getMetaData());
			dialect = dialectResolver.resolveDialect(metaDataInfo);
			if (dialect instanceof PostgreSQLDialect) {
				return JsonBinaryJdbcTypeDescriptor.INSTANCE;
			}
			if (dialect instanceof H2Dialect) {
				return JsonBytesJdbcTypeDescriptor.INSTANCE;
			}
			if (dialect instanceof OracleDialect) {
				throw new UnsupportedOperationException();
			}
			return JsonStringJdbcTypeDescriptor.INSTANCE;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public int getJdbcTypeCode() {
		return jdbcTypeDescriptor != null ? jdbcTypeDescriptor.getJdbcTypeCode() : super.getJdbcTypeCode();
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
