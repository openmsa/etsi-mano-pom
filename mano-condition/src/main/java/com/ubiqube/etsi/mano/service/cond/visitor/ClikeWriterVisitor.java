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

import com.ubiqube.etsi.mano.service.cond.AstException;
import com.ubiqube.etsi.mano.service.cond.AstUtils;
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

public class ClikeWriterVisitor implements Visitor<String, Void> {

	private static final String ILLEGAL_CALL = "Illegal call.";

	@Override
	public String visit(final BooleanValueExpr expr, final Void arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public String visit(final BooleanListExpr expr, final Void arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public String visit(final AttrHolderExpr expr, final Void args) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public String visit(final RangeValueExpr expr, final Void arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public String visit(final LengthValueExpr expr, final Void arg) {
		return new StringBuffer("sizeof(")
				.append(expr.getLeft().accept(this, null))
				.append(")")
				.toString();
	}

	@Override
	public String visit(final MinLengthValueExpr expr, final Void arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public String visit(final MaxLengthValueExpr expr, final Void arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public String visit(final PatternValueExpr expr, final Void arg) {
		return new StringBuffer("pattern(")
				.append(expr.getLeft().accept(this, arg))
				.append(", \"")
				.append(expr.getPattern())
				.append("\")")
				.toString();
	}

	@Override
	public String visit(final GenericCondition expr, final Void arg) {
		final StringBuilder sb = new StringBuilder("(");
		sb.append(expr.getLeft().accept(this, null));
		sb.append(" ");
		sb.append(AstUtils.tojavaOp(expr.getOp()));
		sb.append(" ");
		sb.append(expr.getRight().accept(this, null));
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String visit(final TestValueExpr expr, final Void arg) {
		return "\"" + expr.value() + "\"";
	}

	@Override
	public String visit(final NumberValueExpr expr, final Void arg) {
		return expr.value() + "";
	}

	@Override
	public String visit(final ArrayValueExpr expr, final Void arg) {
		throw new AstException(ILLEGAL_CALL);
	}

	@Override
	public String visit(final LabelExpression expr, final Void arg) {
		return expr.name();
	}

	@Override
	public String visit(final SizeOfExpr expr, final Void arg) {
		return new StringBuffer("sizeof(")
				.append(expr.getLeft().accept(this, arg))
				.append(")")
				.toString();
	}

	@Override
	public String visit(final NoopNode expr, final Void arg) {
		return "[noop]";
	}

}
