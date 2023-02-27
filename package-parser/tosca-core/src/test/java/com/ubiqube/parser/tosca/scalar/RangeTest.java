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

import org.junit.jupiter.api.Test;

import com.ubiqube.parser.tosca.ParseException;

@SuppressWarnings("static-method")
class RangeTest {

	@Test
	void testRange001() throws Exception {
		final Range r = new Range("0-1");
		assertEquals(0, r.getMin());
		assertEquals(1, r.getMax());
	}

	@Test
	void testRange002() throws Exception {
		final Range r = new Range("0-1");
		assertEquals("0 - 1", r.toString());
	}

	@Test
	void testRange003() throws Exception {
		final Range r = new Range("0-1");
		r.setMin(10);
		r.setMax(50);
		assertEquals(10, r.getMin());
		assertEquals(50, r.getMax());
	}

	@Test
	void testRange004() throws Exception {
		assertThrows(ParseException.class, () -> new Range("0-1-6"));
	}

}
