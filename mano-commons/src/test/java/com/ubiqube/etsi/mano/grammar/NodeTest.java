/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.grammar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.grammar.Node.Operand;

class NodeTest {

	@Test
	void test() {
		Node n2 = new Node<>();
		assertNotNull(n2);
		n2 = new Node<>("name", Operand.CONT, List.of());
		assertNotNull(n2);
		n2 = Node.of("name", Operand.GT, List.of());
		assertNotNull(n2);
		assertNotNull(n2.getValues());
		assertNull(n2.getValue());
		final Node<String> n = Node.of("name", Operand.IN, "value");
		assertNotNull(n);
		assertEquals("name", n.getName());
		assertEquals(Operand.IN, n.getOp());
		assertEquals("value", n.getValue());
		final List lst = new ArrayList();
		lst.add("1");
		lst.add("2");
		n.setValue(lst);
		n.addValue("3");
		assertNotNull(n.toString());
		final List vals = n.getValues();
		assertEquals(3, vals.size());
		assertThrows(GenericException.class, () -> n.getValue());
		n.setValue(null);
		assertNotNull(n.getValues());
		assertNull(n.getValue());
		n.setName("newName");
		n.setOp(Operand.NEQ);
	}

}
