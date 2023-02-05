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

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.mapping.IndexedConsumer;
import org.hibernate.metamodel.mapping.BasicValuedMapping;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.MappingType;
import org.hibernate.query.sqm.SqmExpressible;
import org.hibernate.sql.ast.Clause;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class ManoJsonType implements DynamicParameterizedType, UserType<Object>, SqmExpressible<Object>, BasicValuedMapping {

	public static final ManoJsonType INSTANCE = new ManoJsonType();

	private final JdbcType jdbcType;
	private final JavaType<Object> javaType;

	private final Class<Object> returnedClass;

	private JdbcMapping jdbcMapping;

	public ManoJsonType() {
		returnedClass = Object.class;
		jdbcType = new ManoJsonJdbcTypeDescriptor();
		javaType = new ManoJsonJavaTypeDescriptor();
	}

	@Override
	public void setParameterValues(final Properties parameters) {
		if (javaType instanceof final ParameterizedType parameterizedType) {
			parameterizedType.setParameterValues(parameters);
		}
		if (jdbcType instanceof final ParameterizedType parameterizedType) {
			parameterizedType.setParameterValues(parameters);
		}
	}

	@Override
	public Class<Object> getBindableJavaType() {
		return returnedClass;
	}

	@Override
	public MappingType getMappedType() {
		return jdbcMapping;
	}

	@Override
	public Object disassemble(final Object value, final SharedSessionContractImplementor session) {
		return disassemble(value);
	}

	@Override
	public int forEachDisassembledJdbcValue(final Object value, final Clause clause, final int offset, final JdbcValuesConsumer valuesConsumer, final SharedSessionContractImplementor session) {
		valuesConsumer.consume(offset, value, jdbcMapping);
		return getJdbcTypeCount();
	}

	@Override
	public int forEachJdbcType(final int offset, final IndexedConsumer<JdbcMapping> action) {
		action.accept(offset, jdbcMapping);
		return getJdbcTypeCount();
	}

	@Override
	public JdbcMapping getJdbcMapping() {
		return jdbcMapping;
	}

	@Override
	public JavaType<Object> getExpressibleJavaType() {
		return javaType;
	}

	@Override
	public int getSqlType() {
		return jdbcType.getJdbcTypeCode();
	}

	@Override
	public Class<Object> returnedClass() {
		return returnedClass;
	}

	@Override
	public boolean equals(final Object x, final Object y) {
		return javaType.areEqual(x, y);
	}

	@Override
	public int hashCode(final Object x) {
		return javaType.extractHashCode(x);
	}

	@Override
	public Object nullSafeGet(final ResultSet rs, final int position, final SharedSessionContractImplementor session, final Object owner) throws SQLException {
		return jdbcType.getExtractor(javaType).extract(rs, position, session);
	}

	@Override
	public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SharedSessionContractImplementor session) throws SQLException {
		jdbcType.getBinder(javaType).bind(st, value, index, session);
	}

	@Override
	public Object deepCopy(final Object value) {
		return javaType.getMutabilityPlan().deepCopy(value);
	}

	@Override
	public boolean isMutable() {
		return javaType.getMutabilityPlan().isMutable();
	}

	@Override
	public Serializable disassemble(final Object value) {
		return javaType.getMutabilityPlan().disassemble(value, null);
	}

	@Override
	public Object assemble(final Serializable cached, final Object owner) {
		return javaType.getMutabilityPlan().assemble(cached, null);
	}

	@Override
	public Object replace(final Object detached, final Object managed, final Object owner) {
		return deepCopy(detached);
	}

}
