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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.cond;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ubiqube.etsi.mano.service.cond.ast.AttrHolderExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.ConditionExpr;
import com.ubiqube.etsi.mano.service.cond.visitor.BooleanListExprRemoverVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.ClikeWriterVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.EvaluatorVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.ForwardLeftVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.OptimizeVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.PrintVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.RemoveSpecialOpVisitor;

@SuppressWarnings("static-method")
class ConditionParserTest {
	private static final List<String> conditions = List.of("and", "or", "not", "assert");
	private static final List<String> operators = List.of("equal", "greater_than", "greater_or_equal", "less_than", "less_or_equal", "in_range", "valid_values", "length", "min_length", "max_length", "pattern", "schema");

	@Test
	void testName() throws JsonProcessingException {
		final ToStringVisitor tsv = new ToStringVisitor();
		final String cond4 = "[{\"or\":[{\"my_attribute\":[{\"equal\":\"my_value\"}]},{\"my_other_attribute\":[{\"equal\":\"my_other_value\"}]},{\"and\":[{\"my_second\":[{\"less_than\":2},{\"not\":[{\"equal\":0}]}]},{\"another\":[{\"length\":5}]},{\"aaa\":[{\"pattern\":\"^.*$\"}]},{\"aab\":[{\"min_length\":8}]},{\"aac\":[{\"max_length\":8},{\"less_or_equal\":99}]},{\"aad\":[{\"greater_than\":99},{\"greater_or_equal\":5},{\"less_than\":5}]},{\"not\":[{\"four\":[{\"equal\":5.55}]},{\"five\":[{\"in_range\":[1,10]}]}]}]}]}]";
		final ObjectMapper mapper = new ObjectMapper();
		final JsonNode actualObj = mapper.readTree(cond4);
		final List<BooleanExpression> res = JsonToExpression.parseCondition(actualObj);
		final BooleanListExpr r = new BooleanListExpr(BooleanOperatorEnum.AND, res);
		tsv.visit(r, null);
		Node root = r;
		final PrintVisitor visitor = new PrintVisitor();
		final String str = root.accept(visitor, 0);
		System.out.println("Initial\n" + str);
		// root = applyOptimizer(new SwapNotVisitor(), root)
		root = applyOptimizer(new ForwardLeftVisitor(), root);
		root = applyOptimizer(new OptimizeVisitor(), root);
		root = applyOptimizer(new OptimizeVisitor(), root);
		root = applyOptimizer(new BooleanListExprRemoverVisitor(), root);
		root = applyOptimizer(new RemoveSpecialOpVisitor(), root);
		tsv.visit(r, null);
		final ClikeWriterVisitor clike = new ClikeWriterVisitor();
		final String c = root.accept(clike, null);
		assertNotNull(c);
		System.out.println(c);
		final EvaluatorVisitor eval = new EvaluatorVisitor();
		root.accept(eval, new TestContext());
	}

	static <A> Node applyOptimizer(final Visitor<Node, A> v, final Node node) {
		final Node ret = node.accept(v, null);
		final PrintVisitor visitor = new PrintVisitor();
		final String str = ret.accept(visitor, 0);
		System.out.println("Applying " + v.getClass().getSimpleName() + "\n" + str);
		return ret;
	}

	/**
	 * Condition can be: - A List of superCondition (or, not, and). - A list of
	 * simple assignement.
	 *
	 * @param actualObj
	 * @return
	 */
	static List<BooleanExpression> parseCondition(final JsonNode actualObj) {
		if (actualObj.isArray()) {
			return parseArrayCondition((ArrayNode) actualObj);
		}
		if (actualObj.isObject()) {
			return handleObject((ObjectNode) actualObj);
		}
		throw new AstException("Simple value detected. Unable to parse condition.");
	}

	private static List<BooleanExpression> parseArrayCondition(final ArrayNode actualObj) {
		final List<BooleanExpression> ret = new ArrayList<>();
		for (final JsonNode jsonNode : actualObj) {
			final Entry<String, JsonNode> field = jsonNode.fields().next();
			if (conditions.contains(field.getKey())) {
				// Read Condition.
				ret.add(readBinaryCondition(field));
			} else {
				// This is a Attibute.
				final AttrHolderExpr res = readAttribute((ObjectNode) jsonNode);
				ret.add(new BooleanListExpr(BooleanOperatorEnum.AND, List.of(res)));
			}
		}
		return ret;
	}

	private static AttrHolderExpr readAttribute(final ObjectNode jsonNode) {
		final Entry<String, JsonNode> field = jsonNode.fields().next();
		final String attrName = field.getKey();
		final List<BooleanExpression> value = extractFirstElement((ArrayNode) field.getValue());
		return new AttrHolderExpr(attrName, value);
	}

	private static List<BooleanExpression> extractFirstElement(final ArrayNode value) {
		final List<BooleanExpression> ret = new ArrayList<>();
		for (final JsonNode jsonNode : value) {
			final List<BooleanExpression> l = handleObject((ObjectNode) jsonNode);
			ret.addAll(l);
		}
		return ret;
	}

	private static BooleanExpression readBinaryCondition(final Entry<String, JsonNode> field) {
		final JsonNode val = field.getValue();
		if (!val.isArray()) {
			throw new AstException("List condition must be followed by a list.");
		}
		final String op = field.getKey();
		final List<BooleanExpression> res = parseArrayCondition((ArrayNode) val);
		return ConditionFactory.createListCondition(op, res);
	}

	private static List<BooleanExpression> handleObject(final ObjectNode actualObj) {
		if (actualObj.size() != 1) {
			throw new AstException("");
		}
		final Entry<String, JsonNode> jsonNode = actualObj.fields().next();
		final String key = jsonNode.getKey();
		if (isOperator(key)) {
			final ConditionExpr ret = handleCondition(key, jsonNode.getValue());
			return List.of(ret);
		}
		System.out.println("Object: " + jsonNode.getKey());
		final List<BooleanExpression> val = handleValue(jsonNode.getValue());
		final BooleanOperatorEnum op = "not".equals(key) ? BooleanOperatorEnum.NOT : BooleanOperatorEnum.AND;
		return List.of(new BooleanListExpr(op, val));
	}

	private static ConditionExpr handleCondition(final String key, final JsonNode value) {
		return ConditionFactory.createConditionExpr(key, value);
	}

	private static List<BooleanExpression> handleValue(final JsonNode actualObj) {
		if (actualObj.isArray()) {
			return handleArrayOfCondition((ArrayNode) actualObj);
		}
		if (actualObj.isObject()) {
			final ConditionExpr cond = handleCondition((ObjectNode) actualObj);
			return List.of(cond);
		}
		throw new AstException("Unable to handle condition: " + actualObj);
	}

	private static ConditionExpr handleCondition(final ObjectNode actualObj) {
		final Entry<String, JsonNode> field = actualObj.fields().next();
		return ConditionFactory.createConditionExpr(field.getKey(), field.getValue());
	}

	private static List<BooleanExpression> handleArrayOfCondition(final ArrayNode actualObj) {
		final List<BooleanExpression> ret = new ArrayList<>();
		for (final JsonNode jsonNode : actualObj) {
			final ConditionExpr cond = handleCondition((ObjectNode) jsonNode);
			ret.add(cond);
		}
		return ret;
	}

	static boolean isOperator(final String name) {
		return operators.contains(name);
	}
}
