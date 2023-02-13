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
package com.ubiqube.parser.tosca.converter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ubiqube.parser.tosca.ParseException;
import com.ubiqube.parser.tosca.convert.ConvertApi;
import com.ubiqube.parser.tosca.convert.FloatConverter;
import com.ubiqube.parser.tosca.convert.FrequencyConverter;
import com.ubiqube.parser.tosca.convert.IntegerConverter;
import com.ubiqube.parser.tosca.convert.RangeConverter;
import com.ubiqube.parser.tosca.convert.SizeConverter;
import com.ubiqube.parser.tosca.convert.TimeConverter;
import com.ubiqube.parser.tosca.convert.VersionConverter;
import com.ubiqube.parser.tosca.scalar.Frequency;
import com.ubiqube.parser.tosca.scalar.Range;
import com.ubiqube.parser.tosca.scalar.Size;
import com.ubiqube.parser.tosca.scalar.Time;
import com.ubiqube.parser.tosca.scalar.Version;

class ConverApiTest {

	private final ConvertApi conv;

	public ConverApiTest() {
		conv = new ConvertApi();
		conv.register(Size.class.getCanonicalName(), new SizeConverter());
		conv.register(Time.class.getCanonicalName(), new TimeConverter());
		conv.register(Double.class.getCanonicalName(), new FloatConverter());
		conv.register(Float.class.getCanonicalName(), new FloatConverter());
		conv.register(Frequency.class.getCanonicalName(), new FrequencyConverter());
		conv.register(Version.class.getCanonicalName(), new VersionConverter());
		conv.register(Range.class.getCanonicalName(), new RangeConverter());
		conv.register(Integer.class.getCanonicalName(), new IntegerConverter());
	}

	@Test
	void testSize001() throws Exception {
		final Object res = conv.map(Size.class.getCanonicalName(), "1 mb");
		assertNotNull(res);
	}

	@Test
	void testTime001() throws Exception {
		final Object res = conv.map(Time.class.getCanonicalName(), "1 d");
		assertNotNull(res);
	}

	@Test
	void testDouble001() throws Exception {
		final String clazz = Double.class.getCanonicalName();
		assertThrows(ParseException.class, () -> conv.map(clazz, "1.333"));
	}

	@Test
	void testDouble002() throws Exception {
		final Object res = conv.map(Double.class.getCanonicalName(), 1.333D);
		assertNotNull(res);
	}

	@Test
	void testFloat001() throws Exception {
		final String clazz = Float.class.getCanonicalName();
		assertThrows(ParseException.class, () -> conv.map(clazz, "1.333"));
	}

	@Test
	void testFloat002() throws Exception {
		final Object res = conv.map(Float.class.getCanonicalName(), 1.324d);
		assertNotNull(res);
	}

	@Test
	void testFrequency001() throws Exception {
		final Object res = conv.map(Frequency.class.getCanonicalName(), "1 khz");
		assertNotNull(res);
	}

	@Test
	void testVersion001() throws Exception {
		final Object res = conv.map(Version.class.getCanonicalName(), "1.2.3");
		assertNotNull(res);
	}

	@Test
	void testRange001() throws Exception {
		final Object res = conv.map(Range.class.getCanonicalName(), "1-4");
		assertNotNull(res);
	}

	@Test
	void testInteger001() throws Exception {
		final String clazz = Integer.class.getCanonicalName();
		assertThrows(ParseException.class, () -> conv.map(clazz, "123"));
	}

	@Test
	void testInteger002() throws Exception {
		final Object res = conv.map(Integer.class.getCanonicalName(), 123);
		assertNotNull(res);
	}

	@Test
	void testInteger003() throws Exception {
		final Object res = conv.map(Integer.class.getCanonicalName(), 123L);
		assertNotNull(res);
	}

	@Test
	void testBad001() throws Exception {
		assertThrows(ParseException.class, () -> conv.map("bad", ""));
	}
}
