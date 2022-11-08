package com.ubiqube.etsi.mano.service.cond;

import com.ubiqube.etsi.mano.service.cond.ast.ArrayValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.AttrHolderExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MaxLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MinLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.NumberValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.PatternValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.RangeValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;

public class ForwardLeftVisitor implements Visitor<Node, Void> {

	@Override
	public Node visit(final BooleanValueExpr booleanValueExpr) {
		return booleanValueExpr;
	}

	@Override
	public Node visit(final BooleanListExpr booleanListExpr, final Void arg) {
		booleanListExpr.getCondition().forEach(x -> {
			x.accept(this, null);
		});
		return booleanListExpr;
	}

	@Override
	public Node visit(final BooleanExpression be, final Void arg) {
		if (be instanceof final AttrHolderExpr ah) {
			ah.getConditions().forEach(x -> {
				System.out.println("" + x.getClass());
			});
		} else if (be instanceof final BooleanListExpr bl) {
			throw new AstException("Could not cast to " + bl);
		} else {
			throw new AstException("");
		}
		return be;
	}

	@Override
	public Node visit(final RangeValueExpr rangeValueExpr, final Void arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node visit(final LengthValueExpr lengthValueExpr, final Void arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node visit(final MinLengthValueExpr minLengthValueExpr, final Void arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node visit(final MaxLengthValueExpr maxLengthValueExpr, final Void arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node visit(final PatternValueExpr patternValueExpr, final Void arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node visit(final GenericCondition genericCondition, final Void arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node visit(final TestValueExpr testValueExpr, final Void arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node visit(final NumberValueExpr numberValueExpr, final Void arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node visit(final ArrayValueExpr arrayValueExpr, final Void arg) {
		// TODO Auto-generated method stub
		return null;
	}

}
