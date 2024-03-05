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
package com.ubiqube.grammar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.grammar.JsonBeanUtil;
import com.ubiqube.etsi.mano.grammar.JsonFilter;
import com.ubiqube.etsi.mano.grammar.Node;
import com.ubiqube.etsi.mano.grammar.Node.Operand;

@ExtendWith(MockitoExtension.class)
class JsonFilterTest {
	private final JsonBeanUtil jsonBeanUtil = new JsonBeanUtil();

	@Test
	void testBasic() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final Object obj = new Object();
		final List<Node<String>> nodes = List.of();
		final boolean res = jf.apply(obj, nodes);
		assertTrue(res);
	}

	@Test
	void testBadProperty() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final Object obj = new SimpleBean();
		final List<Node<String>> nodes = List.of(new Node<>("testBad", Operand.EQ, List.of("value")));
		final boolean res = jf.apply(obj, nodes);
		assertTrue(res);
	}

	@Test
	void testBadValue() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final Object obj = new SimpleBean();
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.EQ, List.of("value")));
		final boolean res = jf.apply(obj, nodes);
		assertFalse(res);
	}

	@Test
	void testBNotNullValue() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("eulav");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.EQ, List.of("value")));
		final boolean res = jf.apply(obj, nodes);
		assertFalse(res);
	}

	@Test
	void testNeqOk() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("eulav");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.NEQ, List.of("value")));
		final boolean res = jf.apply(obj, nodes);
		assertTrue(res);
	}

	@Test
	void testNeqNok() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("value");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.NEQ, List.of("value")));
		final boolean res = jf.apply(obj, nodes);
		assertFalse(res);
	}

	@Test
	void testEmptyList() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setList(List.of("value"));
		final List<Node<String>> nodes = List.of(new Node<>("list", Operand.EQ, List.of("value")));
		final boolean res = jf.apply(obj, nodes);
		assertTrue(res);
	}

	@Test
	void testLt() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("100");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.LT, List.of("1")));
		final boolean res = jf.apply(obj, nodes);
		assertFalse(res);
	}

	@Test
	void testLtNok() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("100");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.LT, List.of("110")));
		final boolean res = jf.apply(obj, nodes);
		assertTrue(res);
	}

	@Test
	void testGt() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("100");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.GT, List.of("1")));
		final boolean res = jf.apply(obj, nodes);
		assertTrue(res);
	}

	@Test
	void testGtNok() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("100");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.GT, List.of("110")));
		final boolean res = jf.apply(obj, nodes);
		assertFalse(res);
	}

	@Test
	void testGte() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("100");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.GTE, List.of("1")));
		final boolean res = jf.apply(obj, nodes);
		assertTrue(res);
	}

	@Test
	void testGteNok() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("100");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.GTE, List.of("110")));
		final boolean res = jf.apply(obj, nodes);
		assertFalse(res);
	}

	@Test
	void testLte() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("100");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.LTE, List.of("1")));
		final boolean res = jf.apply(obj, nodes);
		assertFalse(res);
	}

	@Test
	void testLteNok() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("100");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.LTE, List.of("110")));
		final boolean res = jf.apply(obj, nodes);
		assertTrue(res);
	}

	@Test
	void testOpNull() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("100");
		final List<Node<String>> nodes = List.of(new Node<>("test", null, List.of("110")));
		final boolean res = jf.apply(obj, nodes);
		assertTrue(res);
	}

	@Test
	void testBadOp() throws Exception {
		final JsonFilter jf = new JsonFilter(jsonBeanUtil);
		final SimpleBean obj = new SimpleBean();
		obj.setTest("100");
		final List<Node<String>> nodes = List.of(new Node<>("test", Operand.IN, List.of("110")));
		final boolean res = jf.apply(obj, nodes);
		assertFalse(res);
	}
}
