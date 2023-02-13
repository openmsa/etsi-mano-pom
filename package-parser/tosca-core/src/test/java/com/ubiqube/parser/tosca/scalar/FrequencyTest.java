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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ubiqube.parser.tosca.ParseException;

@SuppressWarnings("static-method")
class FrequencyTest {

	@Test
	void testFrequency001() {
		final Frequency sz = new Frequency("10 hz");
		assertNotNull(sz);
		assertEquals("10.0 hz", sz.getToscaForm());
		assertEquals(10, sz.getValue());
	}

	@Test
	void testFrequency002() {
		final Frequency sz = new Frequency("10 khz");
		assertNotNull(sz);
		assertEquals("10.0 khz", sz.getToscaForm());
		assertEquals(10000, sz.getValue());
	}

	@Test
	void testFrequency003() {
		final Frequency sz = new Frequency("10 mhz");
		assertNotNull(sz);
		assertEquals("10.0 mhz", sz.getToscaForm());
		assertEquals(10000000D, sz.getValue());
	}

	@Test
	void testFrequency004() {
		final Frequency sz = new Frequency("10 ghz");
		assertNotNull(sz);
		assertEquals("10.0 ghz", sz.getToscaForm());
		assertEquals(10000000000D, sz.getValue());
	}

	@Test
	void testFrequency005() {
		assertThrows(ParseException.class, () -> new Frequency(""));
	}

	@Test
	void testFrequency006() {
		final Frequency sz = new Frequency("10 ghza");
		assertNotNull(sz);
		assertEquals("10.0 ghza", sz.getToscaForm());
		assertThrows(ParseException.class, () -> sz.getValue());
	}
}
