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

import java.util.List;

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

public class BooleanListExprRemoverVisitor implements Visitor<Node, BooleanOperatorEnum> {

	@Override
	public Node visit(final BooleanValueExpr booleanValueExpr, final BooleanOperatorEnum arg) {
		return booleanValueExpr;
	}

	@Override
	public Node visit(final BooleanListExpr booleanListExpr, final BooleanOperatorEnum arg) {
		final List<BooleanExpression> conds = booleanListExpr.getCondition();
		Node root = null;
		if (booleanListExpr.getOp() == BooleanOperatorEnum.NOT) {
			return booleanListExpr;
		}
		while (!conds.isEmpty()) {
			if (null == root) {
				root = conds.get(0).accept(this, booleanListExpr.getOp());
				conds.remove(0);
			}
			root = new GenericCondition(root, convert(booleanListExpr.getOp()), conds.get(0).accept(this, booleanListExpr.getOp()));
			conds.remove(0);
		}
		return root;
	}

	private static Operator convert(final BooleanOperatorEnum op) {
		return switch (op) {
		case AND -> Operator.AND;
		case OR -> Operator.OR;
		case NOT -> Operator.NOT;
		case ASSERT -> Operator.ASSERT;
		default -> throw new IllegalArgumentException("Unexpected value: " + op);
		};
	}

	@Override
	public Node visit(final AttrHolderExpr expr, final BooleanOperatorEnum args) {
		final List<BooleanExpression> conds = expr.getConditions();
		Node root = null;
		while (!conds.isEmpty()) {
			if (null == root) {
				root = conds.get(0).accept(this, args);
				conds.remove(0);
			}
			root = new GenericCondition(root, convert(args), conds.get(0).accept(this, args));
			conds.remove(0);
		}
		return root;
	}

	@Override
	public Node visit(final RangeValueExpr rangeValueExpr, final BooleanOperatorEnum arg) {
		return rangeValueExpr;
	}

	@Override
	public Node visit(final LengthValueExpr lengthValueExpr, final BooleanOperatorEnum arg) {
		return lengthValueExpr;
	}

	@Override
	public Node visit(final MinLengthValueExpr minLengthValueExpr, final BooleanOperatorEnum arg) {
		return minLengthValueExpr;
	}

	@Override
	public Node visit(final MaxLengthValueExpr maxLengthValueExpr, final BooleanOperatorEnum arg) {
		return maxLengthValueExpr;
	}

	@Override
	public Node visit(final PatternValueExpr patternValueExpr, final BooleanOperatorEnum arg) {
		return patternValueExpr;
	}

	@Override
	public Node visit(final GenericCondition genericCondition, final BooleanOperatorEnum arg) {
		return genericCondition;
	}

	@Override
	public Node visit(final TestValueExpr testValueExpr, final BooleanOperatorEnum arg) {
		return testValueExpr;
	}

	@Override
	public Node visit(final NumberValueExpr numberValueExpr, final BooleanOperatorEnum arg) {
		return numberValueExpr;
	}

	@Override
	public Node visit(final ArrayValueExpr arrayValueExpr, final BooleanOperatorEnum arg) {
		return arrayValueExpr;
	}

	@Override
	public Node visit(final LabelExpression expr, final BooleanOperatorEnum arg) {
		return expr;
	}

}
