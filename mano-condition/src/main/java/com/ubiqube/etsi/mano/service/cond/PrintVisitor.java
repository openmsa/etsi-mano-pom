/**
 *     Copyright (C) 2019-2020 Ubiqube.
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

import com.ubiqube.etsi.mano.service.cond.ast.ArrayValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.AttrHolderExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LabelExpression;
import com.ubiqube.etsi.mano.service.cond.ast.LengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MaxLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MinLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.NumberValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.PatternValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.RangeValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;

public class PrintVisitor implements Visitor<String, Integer> {

	@Override
	public String visit(final BooleanValueExpr booleanValueExpr, final Integer arg) {
		booleanValueExpr.isValue();
		return " booleanValueExpr ";
	}

	@Override
	public String visit(final BooleanListExpr booleanListExpr, final Integer arg) {
		final StringBuilder sb = new StringBuilder(indent(arg)).append("BooleanListExpr: ");
		sb.append(booleanListExpr.getOp()).append("\n");
		booleanListExpr.getCondition().forEach(x -> {
			final String res = x.accept(this, arg + 1);
			sb.append(res);
		});
		return sb.toString();
	}

	@Override
	public String visit(final RangeValueExpr rangeValueExpr, final Integer arg) {
		final StringBuilder sb = new StringBuilder(indent(arg));
		sb.append("IN_RANGE ");
		sb.append(" inrange(");
		sb.append(rangeValueExpr.getMin());
		sb.append(", ");
		sb.append(rangeValueExpr.getMax());
		sb.append(")\n");
		if (null != rangeValueExpr.getLeft()) {
			sb.append(rangeValueExpr.getLeft().accept(this, arg + 1));
		}
		return sb.toString();
	}

	@Override
	public String visit(final LengthValueExpr lengthValueExpr, final Integer arg) {
		final StringBuilder sb = new StringBuilder(indent(arg));
		sb.append("LENGHT (").append(lengthValueExpr.getValue()).append(")\n");
		if (null != lengthValueExpr.getLeft()) {
			sb.append(lengthValueExpr.getLeft().accept(this, arg + 1));
		}
		return sb.toString();
	}

	@Override
	public String visit(final MinLengthValueExpr minLengthValueExpr, final Integer arg) {
		final StringBuilder sb = new StringBuilder(indent(arg));
		sb.append("MIN_LENGHT (").append(minLengthValueExpr.getValue()).append(")\n");
		if (null != minLengthValueExpr.getLeft()) {
			sb.append(minLengthValueExpr.getLeft().accept(this, arg + 1));
		}
		return sb.toString();
	}

	@Override
	public String visit(final MaxLengthValueExpr maxLengthValueExpr, final Integer arg) {
		final StringBuilder sb = new StringBuilder(indent(arg));
		sb.append("MAX_LENGHT (").append(maxLengthValueExpr.getValue()).append(")\n");
		if (null != maxLengthValueExpr.getLeft()) {
			sb.append(maxLengthValueExpr.getLeft().accept(this, arg + 1));
		}
		return sb.toString();
	}

	@Override
	public String visit(final PatternValueExpr patternValueExpr, final Integer arg) {
		final StringBuilder sb = new StringBuilder(indent(arg));
		sb.append("PATTERN (").append(patternValueExpr.getPattern()).append(")\n");
		if (null != patternValueExpr.getLeft()) {
			sb.append(patternValueExpr.getLeft().accept(this, arg + 1));
		}
		return sb.toString();
	}

	@Override
	public String visit(final GenericCondition genericCondition, final Integer arg) {
		final StringBuilder sb = new StringBuilder(indent(arg));
		sb.append("GENERIC_COND ").append(tojavaOp(genericCondition.getOp())).append("\n");
		if (null != genericCondition.getLeft()) {
			sb.append(genericCondition.getLeft().accept(this, arg + 1));
		}
		if (null != genericCondition.getRight()) {
			sb.append(genericCondition.getRight().accept(this, arg + 1));
		}
		return sb.toString();
	}

	private static String tojavaOp(final Operator op) {
		return switch (op) {
		case EQUAL -> "==";
		case GREATER_OR_EQUAL -> ">=";
		case GREATER_THAN -> ">";
		case LESS_OR_EQUAL -> "<=";
		case LESS_THAN -> "<";
		case OR -> "||";
		case AND -> "&&";
		case NOT -> "!=";
		default -> throw new IllegalArgumentException("Unexpected value: " + op);
		};
	}

	@Override
	public String visit(final TestValueExpr testValueExpr, final Integer arg) {
		return new StringBuffer(indent(arg))
				.append("TEST_VALUE ")
				.append(testValueExpr.getValue())
				.append("\n")
				.toString();
	}

	@Override
	public String visit(final NumberValueExpr numberValueExpr, final Integer arg) {
		return new StringBuffer(indent(arg))
				.append("NUMBER ")
				.append(numberValueExpr.getValue())
				.append("\n")
				.toString();
	}

	@Override
	public String visit(final ArrayValueExpr arrayValueExpr, final Integer arg) {
		return " ARRAY ";
	}

	@Override
	public String visit(final AttrHolderExpr expr, final Integer args) {
		final StringBuilder sb = new StringBuilder(indent(args));
		sb.append("ATTR ");
		sb.append(expr.getAttrName()).append("\n");
		expr.getConditions().forEach(x -> {
			final String res = x.accept(this, args);
			sb.append(res);
		});
		return sb.toString();
	}

	@Override
	public String visit(final LabelExpression expr, final Integer arg) {
		return new StringBuffer(indent(arg))
				.append("LABEL ")
				.append(expr.getName())
				.append("\n")
				.toString();
	}

	private static String indent(final int i) {
		if (i == 0) {
			return "";
		}
		final StringBuilder sb = new StringBuilder();
		for (int x = 0; x < (i - 1); x++) {
			sb.append("|   ");
		}
		sb.append("+---");
		return sb.toString();
	}

}
