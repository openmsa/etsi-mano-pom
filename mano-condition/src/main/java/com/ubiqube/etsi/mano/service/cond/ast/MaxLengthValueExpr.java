package com.ubiqube.etsi.mano.service.cond.ast;

import com.ubiqube.etsi.mano.service.cond.Visitor;

public class MaxLengthValueExpr extends AbstractBooleanExpression {

	private final int value;

	public MaxLengthValueExpr(final int value) {
		this.value = value;
	}

	@Override
	public <R, A> R accept(final Visitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

	public int getValue() {
		return value;
	}

}
