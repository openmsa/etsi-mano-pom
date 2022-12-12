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

import java.util.regex.Matcher;

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
		final Object val = arg.lookup(le.getName());
		if (val == null) {
			return false;
		}
		final String str = convert(val, String.class);
		System.out.println("" + left.getClass().getSimpleName());
		final Matcher m = expr.getP().matcher(str);
		return m.find();
	}

	private static String convert(final Object val, final Class<?> class1) {
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
		if (left instanceof final LabelExpression le) {
			final Object val = arg.lookup(le.getName());
		} else if (left instanceof final SizeOfExpr soe) {
			final Node node = soe.getLeft();
			final Object val = getValueOf(node, arg);
			System.out.println("" + soe);
		} else {
			throw new AstException(" " + left.getClass().getSimpleName());
		}

		return false;
	}

	private static Object getValueOf(final Node node, final Context ctx) {
		if (node instanceof final LabelExpression le) {
			return ctx.lookup(le.getName());
		}
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

}
