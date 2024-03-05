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
package com.ubiqube.etsi.mano.service.cond.visitor;

import java.util.List;
import java.util.regex.Matcher;

import com.ubiqube.etsi.mano.service.cond.AstException;
import com.ubiqube.etsi.mano.service.cond.Context;
import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.cond.Operator;
import com.ubiqube.etsi.mano.service.cond.Visitor;
import com.ubiqube.etsi.mano.service.cond.ast.ArrayValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.AttrHolderExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LabelExpression;
import com.ubiqube.etsi.mano.service.cond.ast.LengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MaxLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MinLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.NoopNode;
import com.ubiqube.etsi.mano.service.cond.ast.NumberValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.PatternValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.RangeValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.SizeOfExpr;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;

public class EvaluatorVisitor implements Visitor<Boolean, Context> {

	private static final String ILLEGAL_CALL = "Illegal call.";

	@Override
	public Boolean visit(final BooleanValueExpr expr, final Context arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final BooleanListExpr expr, final Context arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final AttrHolderExpr expr, final Context args) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final RangeValueExpr expr, final Context arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final LengthValueExpr expr, final Context arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final MinLengthValueExpr expr, final Context arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final MaxLengthValueExpr expr, final Context arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final PatternValueExpr expr, final Context arg) {
		final Node left = expr.getLeft();
		if (!(left instanceof final LabelExpression le)) {
			throw new AstException(" " + left.getClass().getSimpleName());
		}
		final Object val = arg.lookup(le.name());
		if (val == null) {
			return false;
		}
		final String str = convert(val);
		final Matcher m = expr.getP().matcher(str);
		return m.find();
	}

	private static String convert(final Object val) {
		return val.toString();
	}

	@Override
	public Boolean visit(final GenericCondition expr, final Context arg) {
		final Operator op = expr.getOp();
		if ((op == Operator.AND) || (op == Operator.OR)) {
			final Boolean left = expr.getLeft().accept(this, arg);
			final Boolean right = expr.getRight().accept(this, arg);
			return switch (expr.getOp()) {
			case AND -> left && right;
			case OR -> left || right;
			default -> throw new IllegalArgumentException("Unexpected value: " + expr.getOp());
			};
		}
		final Node left = expr.getLeft();
		final Object lValue = getValue(left, arg);
		final Node right = expr.getRight();
		final Object rValue = getValue(right, arg);
		return evaluate(lValue, rValue, op);
	}

	private Object getValue(final Node node, final Context arg) {
		if (node instanceof final LabelExpression le) {
			return arg.lookup(le.name());
		}
		if (node instanceof final SizeOfExpr soe) {
			final Node n = soe.getLeft();
			final Object lValue = getValueOf(n, arg);
			return doSizeof(lValue);
		}
		if (node instanceof final TestValueExpr tve) {
			return tve.value();
		}
		if (node instanceof final NumberValueExpr nve) {
			return nve.value();
		}
		throw new AstException(" " + node.getClass().getSimpleName());
	}

	private Boolean evaluate(final Object lValue, final Object rValue, final Operator op) {
		if (op == Operator.EQUAL) {
			if (null == lValue) {
				return true;
			}
			if (lValue instanceof Enum) {
				return lValue.toString().equals(rValue);
			}
			return lValue.equals(rValue);
		}
		if (op == Operator.NOT) {
			return !lValue.equals(rValue);
		}
		if ((op == Operator.LESS_THAN) || (op == Operator.LESS_OR_EQUAL) || (op == Operator.GREATER_OR_EQUAL) || (op == Operator.GREATER_THAN)) {
			return compareNumber(lValue, rValue, op);
		}
		throw new AstException("Unknown operator: " + op);
	}

	private Boolean compareNumber(final Object lValue, final Object rValue, final Operator op) {
		final Double valLeft = convertToDouble(lValue);
		final Double valRight = convertToDouble(rValue);
		if (op == Operator.LESS_THAN) {
			return valLeft < valRight;
		}
		if (op == Operator.LESS_OR_EQUAL) {
			return valLeft <= valRight;
		}
		if (op == Operator.GREATER_THAN) {
			return valLeft > valRight;
		}
		if (op == Operator.GREATER_OR_EQUAL) {
			return valLeft >= valRight;
		}
		throw new AstException("Unknown operator " + op);
	}

	private Double convertToDouble(final Object value) {
		if (value instanceof final Double d2) {
			return d2;
		}
		if (value instanceof final Long l) {
			return Double.valueOf(l);
		}
		if (value instanceof final Integer i) {
			return Double.valueOf(i);
		}
		if (value instanceof String) {
			return Double.valueOf(value.toString());
		}
		throw new AstException("Error evaluating number, cannot cast left=" + value.getClass() + "=" + value);
	}

	private Object doSizeof(final Object val) {
		if (val instanceof final List<?> l) {
			return l.size();
		}
		if (val instanceof final String s) {
			return s.length();
		}
		throw new IllegalArgumentException("Object of type " + val.getClass().getSimpleName() + " doesn't have a lenght.");
	}

	private static Object getValueOf(final Node node, final Context ctx) {
		if (node instanceof final LabelExpression le) {
			return ctx.lookup(le.name());
		}
		throw new IllegalArgumentException("Type of node: " + node.getClass().getSimpleName() + " is inknown.");
	}

	@Override
	public Boolean visit(final TestValueExpr expr, final Context arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final NumberValueExpr expr, final Context arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final ArrayValueExpr expr, final Context arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final LabelExpression expr, final Context arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final SizeOfExpr expr, final Context arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public Boolean visit(final NoopNode expr, final Context arg) {
		return true;
	}

}
