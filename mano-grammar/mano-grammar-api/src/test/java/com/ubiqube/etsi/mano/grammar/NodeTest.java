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
package com.ubiqube.etsi.mano.grammar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.grammar.Node.Operand;

@SuppressWarnings("static-method")
class NodeTest {

	@Test
	void test() {
		final Node<?> n = new Node<>();
		assertNotNull(n);
		assertNull(n.getName());
		assertNull(n.getOp());
		assertNull(n.getValue());
		assertNotNull(n.getValues());
		assertNotNull(n.toString());
	}

	@Test
	void testName() throws Exception {
		final Node<String> n = new Node<>("name", Operand.CONT, List.of());
		assertNotNull(n);
		n.addValue("Value");
		assertEquals("name", n.getName());
		assertEquals(Operand.CONT, n.getOp());
		assertNotNull(n.getValue());
		n.setName("nn");
		assertEquals("nn", n.getName());
		n.setOp(Operand.GT);
		assertEquals(Operand.GT, n.getOp());
		n.setValue(null);
		assertNotNull(n.getValues());
	}

	@Test
	void testMultiValue() {
		final Node<String> n = new Node<>("name", Operand.CONT, List.of("a", "b"));
		assertThrows(GrammarException.class, () -> n.getValue());
	}

	@Test
	void testOf() {
		Node<String> n = Node.of("name", Operand.CONT, List.of());
		assertNotNull(n);
		n = Node.of("name", Operand.CONT, "");
	}
}
