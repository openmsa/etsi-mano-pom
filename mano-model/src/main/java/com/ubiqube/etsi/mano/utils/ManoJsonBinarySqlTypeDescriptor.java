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
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;

public class ManoJsonBinarySqlTypeDescriptor extends AbstractJsonSqlTypeDescriptor {

	private static final long serialVersionUID = 1L;
	public static final ManoJsonBinarySqlTypeDescriptor INSTANCE = new ManoJsonBinarySqlTypeDescriptor();

	@Override
	public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
		return new BasicBinder<>(javaTypeDescriptor, this) {
			@Override
			protected void doBind(final PreparedStatement st, final X value, final int index, final WrapperOptions options) throws SQLException {
				st.setObject(index, javaTypeDescriptor.unwrap(value, String.class, options), getSqlType());
			}

			@Override
			protected void doBind(final CallableStatement st, final X value, final String name, final WrapperOptions options)
					throws SQLException {
				st.setObject(name, javaTypeDescriptor.unwrap(value, String.class, options), getSqlType());
			}
		};
	}
}
