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

import com.ubiqube.etsi.mano.service.cond.AstUtils;
import com.ubiqube.etsi.mano.service.cond.BooleanOperatorEnum;
import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.cond.SimpleNodeReturn;
import com.ubiqube.etsi.mano.service.cond.ast.AttrHolderExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LabelExpression;
import com.ubiqube.etsi.mano.service.cond.ast.SizeOfExpr;

public class OptimizeVisitor extends SimpleNodeReturn<Void> {

	@Override
	public Node visit(final BooleanListExpr booleanListExpr, final Void arg) {
		final List<BooleanExpression> l = booleanListExpr.getCondition();
		if (l.size() == 1) {
			final Node expr = booleanListExpr.getCondition().get(0).accept(this, null);
			if ((booleanListExpr.getOp() == BooleanOperatorEnum.NOT) && expr instanceof final GenericCondition gc) {
				gc.setOp(AstUtils.invert(gc.getOp()));
				return gc;
			}
			return expr.accept(this, null);
		}
		final ArrayList<BooleanExpression> ret = new ArrayList<>();
		l.forEach(x -> {
			final Node res = x.accept(this, null);
			ret.add((BooleanExpression) res);
		});
		booleanListExpr.setCondition(ret);
		return booleanListExpr;
	}

	@Override
	public Node visit(final AttrHolderExpr expr, final Void args) {
		final List<BooleanExpression> ret = new ArrayList<>();
		final List<BooleanExpression> conds = expr.getConditions();
		conds.forEach(x -> {
			final Node res = x.accept(this, null);
			ret.add((BooleanExpression) res);
		});
		expr.setConditions(ret);
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
