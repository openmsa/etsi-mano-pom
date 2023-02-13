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
package com.ubiqube.parser.tosca.constraint;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.parser.tosca.constraints.LessThan;

@SuppressWarnings("static-method")
class LessThanTest {

	@Test
	void testLessThan001() throws Exception {
		final LessThan lt = new LessThan();
		lt.setValue(123);
		final Object res = lt.evaluate(123);
		assertFalse((Boolean) res);
	}

	@Test
	void testLessThan002() throws Exception {
		final LessThan lt = new LessThan();
		lt.setValue(123);
		final Object res = lt.evaluate(230);
		assertTrue((Boolean) res);
	}

	@Test
	void testLessThan003() throws Exception {
		final LessThan lt = new LessThan();
		lt.setValue(123d);
		final Object res = lt.evaluate(123d);
		assertFalse((Boolean) res);
	}

	@Test
	void testLessThan004() throws Exception {
		final LessThan lt = new LessThan();
		lt.setValue(123d);
		final Object res = lt.evaluate(230d);
		assertTrue((Boolean) res);
	}

	@Test
	void testLessThan00ListFalse() throws Exception {
		final LessThan lt = new LessThan();
		lt.setValue(1);
		final Object res = lt.evaluate(List.of("", ""));
		assertFalse((Boolean) res);
	}

	@Test
	void testLessThanListTrue() throws Exception {
		final LessThan lt = new LessThan();
		lt.setValue(123);
		final Object res = lt.evaluate(List.of(""));
		assertTrue((Boolean) res);
	}
}
