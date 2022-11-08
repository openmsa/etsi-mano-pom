package com.ubiqube.etsi.mano.service.cond;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ubiqube.etsi.mano.service.cond.ast.AttrHolderExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.ConditionExpr;

class ConditionParser {
	private static final List<String> conditions = List.of("and", "or", "not", "assert");
	private static final List<String> operators = List.of("equal", "greater_than", "greater_or_equal", "less_than", "less_or_equal", "in_range", "valid_values", "length", "min_length", "max_length", "pattern", "schema");

	@SuppressWarnings("static-method")
	@Test
	void testName() throws Exception {
		final String condition = "[{\"utilization_vnf_indicator\":[{\"greater_or_equal\":60.0}]},{\"call_proc_scale_level\":[{\"less_than\":3}]}]";
		final String cond2 = "[{\"or\":[{\"my_attribute\":[{\"equal\":\"my_value\"}]},{\"my_other_attribute\":[{\"less_than\": 3}]}]}]";
		final ObjectMapper mapper = new ObjectMapper();
		final JsonNode actualObj = mapper.readTree(cond2);
		System.out.println(actualObj.toPrettyString());
		final List<BooleanExpression> res = parseCondition(actualObj);
		final BooleanListExpr r = new BooleanListExpr(BooleanOperatorEnum.AND, res);
		final ForwardLeftVisitor v1 = new ForwardLeftVisitor();
		final Node r1 = r.accept(v1, null);
		final PrintVisitor visitor = new PrintVisitor();
		final String str = r1.accept(visitor, null);
		System.out.println(str);
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
		final ObjectNode value = extractFirstElement((ArrayNode) field.getValue());
		final List<BooleanExpression> res = handleObject(value);
		return new AttrHolderExpr(attrName, res);
	}

	private static ObjectNode extractFirstElement(final ArrayNode value) {
		if (value.size() != 1) {
			throw new AstException("An attribute cannot have more than 1 value.");
		}
		return (ObjectNode) value.get(0);
	}

	private static BooleanExpression readBinaryCondition(final Entry<String, JsonNode> field) {
		final JsonNode val = field.getValue();
		if (!val.isArray()) {
			throw new AstException("List condiftion must be followed by a list.");
		}
		final String op = field.getKey();
		final List<BooleanExpression> res = parseArrayCondition((ArrayNode) val);
		return ConditionFactory.createListCondition(op, res);
	}

	private static List<BooleanExpression> handleObject(final ObjectNode actualObj) {
		if (actualObj.size() != 1) {
			throw new RuntimeException();
		}
		final Entry<String, JsonNode> jsonNode = actualObj.fields().next();
		final String key = jsonNode.getKey();
		if (isOperator(key)) {
			final ConditionExpr ret = handleCondition(key, jsonNode.getValue());
			return List.of(ret);
		}
		System.out.println("Object: " + jsonNode.getKey());
		final List<BooleanExpression> val = handleValue(jsonNode.getValue());
		return List.of(new BooleanListExpr(BooleanOperatorEnum.AND, val));
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
