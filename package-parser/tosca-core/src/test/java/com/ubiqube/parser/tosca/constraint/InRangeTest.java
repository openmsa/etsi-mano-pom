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
package com.ubiqube.parser.tosca.constraint;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ubiqube.parser.tosca.constraints.InRange;

@SuppressWarnings("static-method")
class InRangeTest {

	@Test
	void testInRange001() throws Exception {
		final InRange ir = new InRange();
		ir.setMin("0");
		ir.setMax("10");
		final Object res = ir.evaluate(0);
		assertTrue((Boolean) res);
	}

	@Test
	void testInRange002() throws Exception {
		final InRange ir = new InRange();
		ir.setMin("0");
		ir.setMax("10");
		final Object res = ir.evaluate(0d);
		assertTrue((Boolean) res);
	}

	@Test
	void testInRange003() throws Exception {
		final InRange ir = new InRange();
		ir.setMin("0");
		ir.setMax("10");
		final Object res = ir.evaluate(110);
		assertFalse((Boolean) res);
	}

	@Test
	void testInRange004() throws Exception {
		final InRange ir = new InRange();
		ir.setMin("0");
		ir.setMax("10");
		final Object res = ir.evaluate(110d);
		assertFalse((Boolean) res);
	}
}
