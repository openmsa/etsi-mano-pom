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
import com.ubiqube.etsi.mano.service.cond.ast.NoopNode;
import com.ubiqube.etsi.mano.service.cond.ast.NumberValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.PatternValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.RangeValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.SizeOfExpr;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;

/**
 * Just for coverage
 *
 * @author olivier
 *
 */
public class ToStringVisitor implements Visitor<Node, Void> {

	@Override
	public Node visit(final BooleanValueExpr expr, final Void arg) {
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final BooleanListExpr expr, final Void arg) {
		final List<BooleanExpression> conds = expr.getCondition();
		conds.forEach(x -> x.accept(this, null));
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final AttrHolderExpr expr, final Void args) {
		final List<BooleanExpression> conds = expr.getConditions();
		conds.forEach(x -> x.accept(this, null));
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final RangeValueExpr expr, final Void arg) {
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final LengthValueExpr expr, final Void arg) {
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final MinLengthValueExpr expr, final Void arg) {
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final MaxLengthValueExpr expr, final Void arg) {
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final PatternValueExpr expr, final Void arg) {
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final GenericCondition expr, final Void arg) {
		if (null != expr.getLeft()) {
			expr.getLeft().accept(this, null);
		}
		expr.getRight().accept(this, null);
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final TestValueExpr expr, final Void arg) {
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final NumberValueExpr expr, final Void arg) {
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final ArrayValueExpr expr, final Void arg) {
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final LabelExpression expr, final Void arg) {
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final SizeOfExpr expr, final Void arg) {
		expr.toString();
		return expr;
	}

	@Override
	public Node visit(final NoopNode expr, final Void arg) {
		expr.toString();
		return expr;
	}

}
