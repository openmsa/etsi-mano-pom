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

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.ubiqube.parser.tosca.ParseException;

@SuppressWarnings("static-method")
class SizeTest {

	@Test
	void testSize001() {
		final Size sz = new Size("10gb");
		assertNotNull(sz);
	}

	@Test
	void testSize002() {
		final Size sz = new Size("10 Mib");
		assertNotNull(sz);
		assertEquals("10 Mib", sz.getToscaForm());
		final BigDecimal bd = new BigDecimal("10485760");
		assertEquals(bd, sz.getValue());
	}

	@Test
	void testSize003() {
		final Size sz = new Size(10L);
		assertNotNull(sz);
		assertEquals("10 b", sz.getToscaForm());
		final BigDecimal bd = new BigDecimal("10");
		assertEquals(bd, sz.getValue());
	}

	@Test
	void testSize004() {
		final Size sz = new Size("10 kb");
		assertNotNull(sz);
		assertEquals("10 kb", sz.getToscaForm());
		final BigDecimal bd = new BigDecimal("10000");
		assertEquals(bd, sz.getValue());
	}

	@Test
	void testSize005() {
		final Size sz = new Size("10 kiB");
		assertNotNull(sz);
		assertEquals("10 kiB", sz.getToscaForm());
		final BigDecimal bd = new BigDecimal("10240");
		assertEquals(bd, sz.getValue());
	}

	@Test
	void testSize006() {
		final Size sz = new Size("10 mb");
		assertNotNull(sz);
		assertEquals("10 mb", sz.getToscaForm());
		final BigDecimal bd = new BigDecimal("10000000");
		assertEquals(bd, sz.getValue());
	}

	@Test
	void testSize007() {
		final Size sz = new Size("10 mib");
		assertNotNull(sz);
		assertEquals("10 mib", sz.getToscaForm());
		final BigDecimal bd = new BigDecimal("10485760");
		assertEquals(bd, sz.getValue());
	}

	@Test
	void testSize008() {
		final Size sz = new Size("10 gb");
		assertNotNull(sz);
		assertEquals("10 gb", sz.getToscaForm());
		final BigDecimal bd = new BigDecimal("10000000000");
		assertEquals(bd, sz.getValue());
	}

	@Test
	void testSize009() {
		final Size sz = new Size("10 gib");
		assertNotNull(sz);
		assertEquals("10 gib", sz.getToscaForm());
		final BigDecimal bd = new BigDecimal("10737418240");
		assertEquals(bd, sz.getValue());
	}

	@Test
	void testSize010() {
		final Size sz = new Size("10 tib");
		assertNotNull(sz);
		assertEquals("10 tib", sz.getToscaForm());
		final BigDecimal bd = new BigDecimal("10995116277760");
		assertEquals(bd, sz.getValue());
	}

	@Test
	void testSize011() {
		final Size sz = new Size("10 tb");
		assertNotNull(sz);
		assertEquals("10 tb", sz.getToscaForm());
		final BigDecimal bd = new BigDecimal("10000000000000");
		assertEquals(bd, sz.getValue());
	}

	@Test
	void testSize012() {
		assertThrows(ParseException.class, () -> new Size(""));
	}

	@Test
	void testSize013() {
		final Size sz = new Size("10 tbzz");
		assertNotNull(sz);
		assertEquals("10 tbzz", sz.getToscaForm());
		assertThrows(ParseException.class, () -> sz.getValue());
	}
}
