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
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ubiqube.etsi.mano.service.cond.ast.ArrayValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.ConditionExpr;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MaxLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MinLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.NumberValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.PatternValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.RangeValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.ValueExpr;

public class ConditionFactory {

	private ConditionFactory() {
		//
	}

	private static final Map<String, Operator> GENERIC_COND = Map.of("equal", Operator.EQUAL,
			"greater_than", Operator.GREATER_THAN, "greater_or_equal", Operator.GREATER_OR_EQUAL,
			"less_than", Operator.LESS_THAN, "less_or_equal", Operator.LESS_OR_EQUAL);

	public static ConditionExpr createConditionExpr(final String key, final JsonNode value) {
		if (GENERIC_COND.containsKey(key)) {
			final Operator op = convertToOp(key);
			final ValueExpr val = convertValue(value);
			return new GenericCondition(null, op, val);
		}
		if ("in_range".equals(key)) {
			final ArrayNode arr = (ArrayNode) value;
			return new RangeValueExpr(arr.get(0).asInt(), arr.get(1).asInt());
		}
		if ("length".equals(key)) {
			return new LengthValueExpr(value.intValue());
		}
		if ("min_length".equals(key)) {
			return new MinLengthValueExpr(value.asInt());
		}
		if ("max_length".equals(key)) {
			return new MaxLengthValueExpr(value.asInt());
		}
		if ("pattern".equals(key)) {
			return new PatternValueExpr(value.textValue());
		}
		throw new AstException("Unknown method " + key);
	}

	private static ValueExpr convertValue(final JsonNode value) {
		if (value.isBoolean()) {
			return new BooleanValueExpr(value.asBoolean());
		}
		if (value.isArray()) {
			final List<Object> lst = extractList((ArrayNode) value);
			return new ArrayValueExpr<>(lst);
		}
		if (value.isNumber()) {
			return new NumberValueExpr(value.asDouble());
		}
		if (value.isTextual()) {
			return new TestValueExpr(value.asText());
		}
		return null;
	}

	private static List<Object> extractList(final ArrayNode value) {
		final List<Object> lst = new ArrayList<>();
		for (final JsonNode jsonNode : value) {
			if (jsonNode.isTextual()) {
				lst.add(jsonNode.textValue());
			} else if (jsonNode.isNumber()) {
				lst.add(jsonNode.doubleValue());
			} else {
				throw new AstException("Unknown node: " + value);
			}
		}
		return lst;
	}

	private static Operator convertToOp(final String key) {
		return GENERIC_COND.get(key);
	}

	public static BooleanExpression createListCondition(final String op, final List<BooleanExpression> res) {
		return new BooleanListExpr(BooleanOperatorEnum.valueOf(op.toUpperCase()), res);
	}
}
