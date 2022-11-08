package com.ubiqube.etsi.mano.service.cond.ast;

import com.ubiqube.etsi.mano.service.cond.Visitor;

public class RangeValueExpr extends AbstractBooleanExpression {

	private final int min;
	private final int max;

	public RangeValueExpr(final int min, final int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public <R, A> R accept(final Visitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

}
