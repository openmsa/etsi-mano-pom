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
package com.ubiqube.parser.tosca.scalar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.ubiqube.parser.tosca.ParseException;

@SuppressWarnings("static-method")
class TimeTest {

	@Test
	void testTime001() throws Exception {
		final Time t = new Time("1s");
		assertEquals("1 s", t.getToscaForm());
		final BigDecimal bd = new BigDecimal(1000000000);
		assertEquals(bd, t.getValue());
	}

	@Test
	void testTime002() throws Exception {
		final Time t = new Time(1L);
		assertEquals("1 ns", t.getToscaForm());
		final BigDecimal bd = new BigDecimal(1);
		assertEquals(bd, t.getValue());
	}

	@Test
	void testTime003() throws Exception {
		final Time t = new Time("1 us");
		assertEquals("1 us", t.getToscaForm());
		final BigDecimal bd = new BigDecimal(1000);
		assertEquals(bd, t.getValue());
	}

	@Test
	void testTime004() throws Exception {
		final Time t = new Time("1 ms");
		assertEquals("1 ms", t.getToscaForm());
		final BigDecimal bd = new BigDecimal(1000000);
		assertEquals(bd, t.getValue());
	}

	@Test
	void testTime005() throws Exception {
		final Time t = new Time("1 m");
		assertEquals("1 m", t.getToscaForm());
		final BigDecimal bd = new BigDecimal("60000000000");
		assertEquals(bd, t.getValue());
	}

	@Test
	void testTime006() throws Exception {
		final Time t = new Time("1 h");
		assertEquals("1 h", t.getToscaForm());
		final BigDecimal bd = new BigDecimal("3600000000000");
		assertEquals(bd, t.getValue());
	}

	@Test
	void testTime007() throws Exception {
		final Time t = new Time("1 d");
		assertEquals("1 d", t.getToscaForm());
		final BigDecimal bd = new BigDecimal("86400000000000");
		assertEquals(bd, t.getValue());
	}

	@Test
	void testTime008() throws Exception {
		assertThrows(ParseException.class, () -> new Time("1 dddd"));
	}

	@Test
	void testTime009() throws Exception {
		final Time t = new Time(new BigDecimal("1"));
		assertEquals("1 ns", t.getToscaForm());
		final BigDecimal bd = new BigDecimal("1");
		assertEquals(bd, t.getValue());
	}

}
