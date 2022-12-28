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

import java.util.Properties;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.ParameterizedType;

public class ManoJsonType extends AbstractSingleColumnStandardBasicType<Object> implements DynamicParameterizedType {

	private static final long serialVersionUID = 1L;

	public static final ManoJsonType INSTANCE = new ManoJsonType();

	public ManoJsonType() {
		super(
				new ManoJsonSqlTypeDescriptor(),
				new ManoJsonTypeDescriptor());
	}
//
//	public ManoJsonType(final Type javaType) {
//		super(
//				new ManoJsonSqlTypeDescriptor(),
//				new ManoJsonTypeDescriptor(ManoObjectMapper.getInstance(), javaType));
//	}
//
//	public ManoJsonType(final ManoObjectMapper objectMapperWrapper) {
//		super(
//				new ManoJsonSqlTypeDescriptor(),
//				new ManoJsonTypeDescriptor(objectMapperWrapper));
//	}
//
//	public ManoJsonType(final ManoObjectMapper objectMapperWrapper, final Type javaType) {
//		super(
//				new ManoJsonSqlTypeDescriptor(),
//				new ManoJsonTypeDescriptor(objectMapperWrapper, javaType));
//	}

	@Override
	public String getName() {
		return "json";
	}

	@Override
	public void setParameterValues(final Properties parameters) {
		((ManoJsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
		final SqlTypeDescriptor sqlTypeDescriptor = getSqlTypeDescriptor();
		if (sqlTypeDescriptor instanceof final ParameterizedType parameterizedType) {
			parameterizedType.setParameterValues(parameters);
		}
	}
}
