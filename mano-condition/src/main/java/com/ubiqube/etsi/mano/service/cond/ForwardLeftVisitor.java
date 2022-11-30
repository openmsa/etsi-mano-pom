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

import java.util.ArrayList;
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
import com.ubiqube.etsi.mano.service.cond.ast.SizeOfExpr;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;

public class ForwardLeftVisitor implements Visitor<Node, Void> {

	@Override
	public Node visit(final BooleanValueExpr booleanValueExpr, final Void arg) {
		return booleanValueExpr;
	}

	@Override
	public Node visit(final BooleanListExpr booleanListExpr, final Void arg) {
		final List<BooleanExpression> ret = new ArrayList<>();
		booleanListExpr.getCondition().forEach(x -> {
			final BooleanExpression res = (BooleanExpression) x.accept(this, null);
			ret.add(res);
		});
		booleanListExpr.setCondition(ret);
		return booleanListExpr;
	}

	@Override
	public Node visit(final RangeValueExpr rangeValueExpr, final Void arg) {
		return rangeValueExpr;
	}

	@Override
	public Node visit(final LengthValueExpr lengthValueExpr, final Void arg) {
		return lengthValueExpr;
	}

	@Override
	public Node visit(final MinLengthValueExpr minLengthValueExpr, final Void arg) {
		return minLengthValueExpr;
	}

	@Override
	public Node visit(final MaxLengthValueExpr maxLengthValueExpr, final Void arg) {
		return maxLengthValueExpr;
	}

	@Override
	public Node visit(final PatternValueExpr patternValueExpr, final Void arg) {
		return patternValueExpr;
	}

	@Override
	public Node visit(final GenericCondition genericCondition, final Void arg) {
		return genericCondition;
	}

	@Override
	public Node visit(final TestValueExpr testValueExpr, final Void arg) {
		return testValueExpr;
	}

	@Override
	public Node visit(final NumberValueExpr numberValueExpr, final Void arg) {
		return numberValueExpr;
	}

	@Override
	public Node visit(final ArrayValueExpr arrayValueExpr, final Void arg) {
		return arrayValueExpr;
	}

	@Override
	public Node visit(final AttrHolderExpr expr, final Void args) {
		final List<BooleanExpression> conds = expr.getConditions();
		if (conds.size() == 1) {
			final BooleanExpression cond = conds.get(0);
			cond.setLeft(LabelExpression.of(expr.getAttrName()));
			return cond.accept(this, args);
		}
		for (final BooleanExpression booleanExpression : conds) {
			if (booleanExpression instanceof final BooleanListExpr ble && (ble.getOp() == BooleanOperatorEnum.NOT)) {
				ble.getCondition().forEach(x -> x.setLeft(LabelExpression.of(expr.getAttrName())));
			} else {
				booleanExpression.setLeft(LabelExpression.of(expr.getAttrName()));
			}
		}
		return expr;
	}

	@Override
	public Node visit(final LabelExpression expr, final Void arg) {
		return expr;
	}

	@Override
	public Node visit(final SizeOfExpr expr, final Void arg) {
		return expr;
	}

}
