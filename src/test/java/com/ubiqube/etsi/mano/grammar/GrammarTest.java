package com.ubiqube.etsi.mano.grammar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.grammar.Node.Operand;

public class GrammarTest {

	@Test
	public void testValueIssue() {
		final AstBuilder astBuilder = new AstBuilder("id.eq=fce04624-6f92-42b1-bf50-437b682288a5");
		final List<Node> nodes = astBuilder.getNodes();
		assertEquals(1, nodes.size());
		assertNode(nodes.get(0), "id", Operand.EQ, "fce04624-6f92-42b1-bf50-437b682288a5");
	}

	@Test
	public void testMultiValueIssue() {
		final AstBuilder astBuilder = new AstBuilder("id.eq=fce04624-6f92-42b1-bf50-437b682288a5,OOOOOOO");
		final List<Node> nodes = astBuilder.getNodes();
		final Node node = nodes.get(0);
		assertEquals("fce04624-6f92-42b1-bf50-437b682288a5", node.getValue());
	}

	@Test
	public void testMultiOp() {
		final AstBuilder astBuilder = new AstBuilder("id.eq=string&vnfdVersion.gt=bad");
		final List<Node> nodes = astBuilder.getNodes();
		assertEquals(2, nodes.size());
		assertNode(nodes.get(0), "id", Operand.EQ, "string");
		assertNode(nodes.get(1), "vnfdVersion", Operand.GT, "bad");
	}

	@Test
	public void testMultiAttr() {
		final AstBuilder astBuilder = new AstBuilder("id.my.bean.eq=string&vnfdVersion.gt=bad");
		final List<Node> nodes = astBuilder.getNodes();
		assertEquals(2, nodes.size());
		assertNode(nodes.get(0), "id.my.bean", Operand.EQ, "string");
		assertNode(nodes.get(1), "vnfdVersion", Operand.GT, "bad");
	}

	private static void assertNode(final Node node, final String key, final Operand op, final String value) {
		assertEquals(key, node.getName());
		assertEquals(op, node.getOp());
		assertEquals(value, node.getValue());
	}
}
