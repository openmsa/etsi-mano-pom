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

import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.cond.Operator;
import com.ubiqube.etsi.mano.service.cond.SimpleNodeReturn;
import com.ubiqube.etsi.mano.service.cond.ast.AttrHolderExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MaxLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MinLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.NumberValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.RangeValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.SizeOfExpr;

public class RemoveSpecialOpVisitor extends SimpleNodeReturn<Void> {

	@Override
	public Node visit(final BooleanListExpr expr, final Void arg) {
		expr.getCondition().forEach(x -> x.accept(this, arg));
		return expr;
	}

	@Override
	public Node visit(final AttrHolderExpr expr, final Void args) {
		expr.setLeft(expr.getLeft().accept(this, args));
		expr.getConditions().forEach(x -> x.accept(this, args));
		return expr;
	}

	@Override
	public Node visit(final RangeValueExpr expr, final Void arg) {
		Operator opLeft = Operator.GREATER_OR_EQUAL;
		Operator opRigth = Operator.LESS_OR_EQUAL;
		if (expr.isNot()) {
			opLeft = Operator.LESS_THAN;
			opRigth = Operator.GREATER_THAN;
		}
		final GenericCondition left = new GenericCondition(expr.getLeft(), opLeft, NumberValueExpr.of(expr.getMin()));
		final GenericCondition rigth = new GenericCondition(expr.getLeft(), opRigth, NumberValueExpr.of(expr.getMax()));
		return new GenericCondition(left, Operator.AND, rigth);
	}

	@Override
	public Node visit(final LengthValueExpr expr, final Void arg) {
		return new GenericCondition(SizeOfExpr.of(expr.getLeft()), Operator.EQUAL, NumberValueExpr.of(expr.getValue()));
	}

	@Override
	public Node visit(final MinLengthValueExpr expr, final Void arg) {
		return new GenericCondition(SizeOfExpr.of(expr.getLeft()), Operator.GREATER_OR_EQUAL, NumberValueExpr.of(expr.getValue()));
	}

	@Override
	public Node visit(final MaxLengthValueExpr expr, final Void arg) {
		return new GenericCondition(SizeOfExpr.of(expr.getLeft()), Operator.LESS_OR_EQUAL, NumberValueExpr.of(expr.getValue()));
	}

	@Override
	public Node visit(final GenericCondition expr, final Void arg) {
		expr.setLeft(expr.getLeft().accept(this, arg));
		expr.setRight(expr.getRight().accept(this, arg));
		return expr;
	}

	@Override
	public Node visit(final SizeOfExpr expr, final Void arg) {
		expr.setLeft(expr.getLeft().accept(this, arg));
		return expr;
	}

}
