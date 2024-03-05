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

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ubiqube.etsi.mano.service.cond.ast.AttrHolderExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.ConditionExpr;

public class JsonToExpression {
	private static final List<String> CONDITIONS = List.of("and", "or", "not", "assert");
	private static final List<String> OPERATORS = List.of("equal", "greater_than", "greater_or_equal", "less_than", "less_or_equal", "in_range", "valid_values", "length", "min_length", "max_length", "pattern", "schema");

	private JsonToExpression() {
		//
	}

	/**
	 * Condition can be: - A List of superCondition (or, not, and). - A list of
	 * simple assignement.
	 *
	 * @param actualObj
	 * @return A list.
	 */
	public static List<BooleanExpression> parseCondition(final JsonNode actualObj) {
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
			if (CONDITIONS.contains(field.getKey())) {
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
		return OPERATORS.contains(name);
	}

}
