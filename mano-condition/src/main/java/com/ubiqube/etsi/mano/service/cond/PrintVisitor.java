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
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
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

public class PrintVisitor implements Visitor<String, Void> {

	@Override
	public String visit(final BooleanValueExpr booleanValueExpr) {
		booleanValueExpr.isValue();
		return " booleanValueExpr ";
	}

	@Override
	public String visit(final BooleanListExpr booleanListExpr, final Void arg) {
		final StringBuilder sb = new StringBuilder("BooleanListExpr: { ");
		sb.append(booleanListExpr.getOp()).append(" ");
		booleanListExpr.getCondition().forEach(x -> {
			final String res = x.accept(this, null);
			sb.append(res);
			sb.append(", ");
		});
		return sb.append("} ").toString();
	}

	@Override
	public String visit(final BooleanExpression be, final Void arg) {
		final StringBuilder sb = new StringBuilder();
		if (be instanceof final AttrHolderExpr ah) {
			sb.append(ah.getAttrName()).append(" ===> ");
			ah.getConditions().forEach(x -> {
				final String res = x.accept(this, null);
				sb.append(res);
			});
		} else if (be instanceof final BooleanListExpr bl) {
			throw new AstException("Could not cast to " + bl);
		} else {
			throw new AstException("Unknown type " + be.getClass());
		}
		return sb.toString();
	}

	@Override
	public String visit(final RangeValueExpr rangeValueExpr, final Void arg) {
		final StringBuilder sb = new StringBuilder();
		sb.append(rangeValueExpr.getLeft());
		sb.append(" inrange(");
		sb.append(rangeValueExpr.getMin());
		sb.append(", ");
		sb.append(rangeValueExpr.getMax());
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String visit(final LengthValueExpr lengthValueExpr, final Void arg) {
		return " LENGHT ";
	}

	@Override
	public String visit(final MinLengthValueExpr minLengthValueExpr, final Void arg) {
		return " MIN LEN ";
	}

	@Override
	public String visit(final MaxLengthValueExpr maxLengthValueExpr, final Void arg) {
		return " MAX LEN ";
	}

	@Override
	public String visit(final PatternValueExpr patternValueExpr, final Void arg) {
		return " PATTERN ";
	}

	@Override
	public String visit(final GenericCondition genericCondition, final Void arg) {
		final StringBuilder sb = new StringBuilder();
		sb.append(genericCondition.getLeft());
		sb.append(" ").append(tojavaOp(genericCondition.getOp())).append(" ");
		sb.append(genericCondition.getRight().accept(this, null));
		sb.append(" ");
		return sb.toString();
	}

	private static String tojavaOp(final Operator op) {
		return switch (op) {
		case EQUAL -> "==";
		case GREATER_OR_EQUAL -> ">=";
		case GREATER_THAN -> ">";
		case LESS_OR_EQUAL -> "<=";
		case LESS_THAN -> "<";
		default -> throw new IllegalArgumentException("Unexpected value: " + op);
		};
	}

	@Override
	public String visit(final TestValueExpr testValueExpr, final Void arg) {
		return testValueExpr.getValue();
	}

	@Override
	public String visit(final NumberValueExpr numberValueExpr, final Void arg) {
		return "" + numberValueExpr.getValue();
	}

	@Override
	public String visit(final ArrayValueExpr arrayValueExpr, final Void arg) {
		return " ARRAY ";
	}

	@Override
	public String visit(final AttrHolderExpr expr, final Void args) {
		final StringBuilder sb = new StringBuilder("ATTR:(");
		sb.append(expr.getAttrName()).append(" ===> ");
		expr.getConditions().forEach(x -> {
			final String res = x.accept(this, args);
			sb.append(res);
		});
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String visit(final LabelExpression expr, final Void arg) {
		return expr.getName();
	}

}
