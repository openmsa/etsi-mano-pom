package com.ubiqube.etsi.mano.service.cond.ast;

import com.ubiqube.etsi.mano.service.cond.Visitor;

public class LengthValueExpr extends AbstractBooleanExpression {

	private final int value;

	public LengthValueExpr(final int value) {
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
