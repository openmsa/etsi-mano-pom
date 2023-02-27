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
package com.ubiqube.parser.tosca.api;

import java.time.ZonedDateTime;

import com.ubiqube.parser.tosca.scalar.Frequency;
import com.ubiqube.parser.tosca.scalar.Size;
import com.ubiqube.parser.tosca.scalar.Time;
import com.ubiqube.parser.tosca.scalar.Version;

public class ValueConverter {
	private ValueConverter() {
		//
	}

	public static Object convert(final String type, final Object value) {
		if (null == value) {
			return null;
		}
		if ("integer".equals(type)) {
			return Integer.valueOf(value.toString());
		}
		if ("scalar-unit.size".equals(type)) {
			return new Size(value.toString());
		}
		if ("scalar-unit.frequency".equals(type)) {
			return new Frequency(value.toString());
		}
		if ("scalar-unit.time".equals(type)) {
			return new Time(value.toString());
		}
		if ("string".equals(type)) {
			return value.toString();
		}
		if ("range".equals(type)) {
			throw new UnsupportedOperationException("Range operator not supported.");
		}
		if ("boolean".equals(type)) {
			return Boolean.valueOf(value.toString());
		}
		if ("float".equals(type)) {
			return Double.valueOf(value.toString());
		}
		if ("version".equals(type)) {
			return new Version(value.toString());
		}
		if ("timestamp".equals(type)) {
			return ZonedDateTime.parse(value.toString());
		}
		throw new UnsupportedOperationException(type + " operator not supported.");
	}
}
