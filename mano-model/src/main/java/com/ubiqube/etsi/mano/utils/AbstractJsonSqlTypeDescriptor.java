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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

public abstract class AbstractJsonSqlTypeDescriptor implements SqlTypeDescriptor {

	private static final long serialVersionUID = 1L;

	@Override
	public int getSqlType() {
		return Types.OTHER;
	}

	@Override
	public boolean canBeRemapped() {
		return true;
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

	protected Object extractJson(final ResultSet rs, final String name) throws SQLException {
		return rs.getObject(name);
	}

	protected Object extractJson(final CallableStatement statement, final int index) throws SQLException {
		return statement.getObject(index);
	}

	protected Object extractJson(final CallableStatement statement, final String name) throws SQLException {
		return statement.getObject(name);
	}
}
