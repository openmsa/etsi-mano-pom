package com.ubiqube.etsi.mano.service.cond;

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

public class SimpleNodeReturn<A> implements Visitor<Node, A> {

	@Override
	public Node visit(final BooleanValueExpr booleanValueExpr, final A arg) {
		return booleanValueExpr;
	}

	@Override
	public Node visit(final BooleanListExpr booleanListExpr, final A arg) {
		return booleanListExpr;
	}

	@Override
	public Node visit(final RangeValueExpr rangeValueExpr, final A arg) {
		return rangeValueExpr;
	}

	@Override
	public Node visit(final LengthValueExpr lengthValueExpr, final A arg) {
		return lengthValueExpr;
	}

	@Override
	public Node visit(final MinLengthValueExpr minLengthValueExpr, final A arg) {
		return minLengthValueExpr;
	}

	@Override
	public Node visit(final MaxLengthValueExpr maxLengthValueExpr, final A arg) {
		return maxLengthValueExpr;
	}

	@Override
	public Node visit(final PatternValueExpr patternValueExpr, final A arg) {
		return patternValueExpr;
	}

	@Override
	public Node visit(final GenericCondition genericCondition, final A arg) {
		return genericCondition;
	}

	@Override
	public Node visit(final TestValueExpr testValueExpr, final A arg) {
		return testValueExpr;
	}

	@Override
	public Node visit(final NumberValueExpr numberValueExpr, final A arg) {
		return numberValueExpr;
	}

	@Override
	public Node visit(final ArrayValueExpr arrayValueExpr, final A arg) {
		return arrayValueExpr;
	}

	@Override
	public Node visit(final AttrHolderExpr expr, final A args) {
		return expr;
	}

	@Override
	public Node visit(final LabelExpression expr, final A arg) {
		return expr;
	}

	@Override
	public Node visit(final SizeOfExpr expr, final A arg) {
		return expr;
	}

}
