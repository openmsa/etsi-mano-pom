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
package com.ubiqube.parser.tosca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ToscaClassHolderTest {

	@Test
	void test() {
		final ToscaClass node = new ToscaClass();
		final ToscaClassHolder srv = new ToscaClassHolder("name", node);
		srv.setParent(srv);
		assertEquals(srv, srv.getParent());
		srv.setParent(null);
		srv.toString();
		assertTrue(true);
	}

	@Test
	void testIsInstance() {
		final ToscaClass node = new ToscaClass();
		final ToscaClassHolder srv = new ToscaClassHolder("name", node);
		srv.setParent(null);
		assertTrue(srv.isInstanceOf("name"));
	}

	@Test
	void testIsInstance2() {
		final ToscaClass node = new ToscaClass();
		final ToscaClassHolder srv = new ToscaClassHolder("name", node);
		srv.setParent(null);
		assertFalse(srv.isInstanceOf("bad"));
	}

	@Test
	void testIsInstance3() {
		final ToscaClass node = new ToscaClass();
		node.setDerivedFrom("test");
		final ToscaClassHolder srv = new ToscaClassHolder("name", node);
		srv.setParent(null);
		assertTrue(srv.isInstanceOf("test"));
	}

	@Test
	void testIsInstance4() {
		final ToscaClass node = new ToscaClass();
		node.setDerivedFrom("test");
		final ToscaClassHolder srv = new ToscaClassHolder("name", node);
		srv.setParent(null);
		assertFalse(srv.isInstanceOf("testBad"));
	}

	@Test
	void testIsInstance5() {
		final ToscaClass node = new ToscaClass();
		node.setDerivedFrom("test");
		final ToscaClassHolder srv = new ToscaClassHolder("name", node);
		final ToscaClassHolder n2 = new ToscaClassHolder("name2", node);
		srv.setParent(n2);
		assertTrue(srv.isInstanceOf("name2"));
	}
}
