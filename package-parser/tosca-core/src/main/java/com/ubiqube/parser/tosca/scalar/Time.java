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
package com.ubiqube.parser.tosca.scalar;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ubiqube.parser.tosca.ParseException;

public class Time implements Scalar {
	private static final Pattern TIME_PATTERN = Pattern.compile("(?<time>[0-9]+)\\s*(?<unit>d|h|m|s|ms|us|ns)$");

	private final long ltime;

	private final String unit;

	public Time(final String value) {

		final Matcher m = TIME_PATTERN.matcher(value);
		if (!m.find()) {
			throw new ParseException("Time scalr: Unable to find a match for: " + value);
		}
		ltime = Long.parseLong(m.group("time"));
		unit = m.group("unit");
	}

	public Time(final Long _time) {
		ltime = _time.longValue();
		unit = "ns";
	}

	public Time(final BigDecimal in) {
		ltime = in.longValue();
		unit = "ns";
	}

	public String getToscaForm() {
		return ltime + " " + unit;
	}

	public BigDecimal getValue() {
		final BigDecimal mul = getMultiplier(unit);
		final BigDecimal sizeBd = new BigDecimal(ltime);
		return mul.multiply(sizeBd);
	}

	public static BigDecimal getMultiplier(final String unit2) {
		return switch (unit2.toLowerCase(Locale.ROOT)) {
		case "d" -> new BigDecimal("86400000000000");
		case "h" -> new BigDecimal("3600000000000");
		case "m" -> new BigDecimal("60000000000");
		case "s" -> new BigDecimal("1000000000");
		case "ms" -> new BigDecimal("1000000");
		case "us" -> new BigDecimal("1000");
		case "ns" -> new BigDecimal("1");
		default -> throw new ParseException("Unknown scalar unit " + unit2);
		};
	}
}
