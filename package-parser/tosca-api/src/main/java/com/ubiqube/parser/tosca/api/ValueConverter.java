package com.ubiqube.parser.tosca.api;

import java.time.ZonedDateTime;

import com.ubiqube.parser.tosca.scalar.Frequency;
import com.ubiqube.parser.tosca.scalar.Size;
import com.ubiqube.parser.tosca.scalar.Time;
import com.ubiqube.parser.tosca.scalar.Version;

public class ValueConverter {

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
