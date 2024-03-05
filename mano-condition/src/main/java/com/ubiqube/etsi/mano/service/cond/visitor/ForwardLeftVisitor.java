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

import java.util.ArrayList;
import java.util.List;

import com.ubiqube.etsi.mano.service.cond.BooleanOperatorEnum;
import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.cond.SimpleNodeReturn;
import com.ubiqube.etsi.mano.service.cond.ast.AttrHolderExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.LabelExpression;

public class ForwardLeftVisitor extends SimpleNodeReturn<Void> {

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

}
