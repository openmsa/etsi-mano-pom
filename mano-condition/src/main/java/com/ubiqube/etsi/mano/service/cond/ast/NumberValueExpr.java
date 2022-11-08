package com.ubiqube.etsi.mano.service.cond.ast;

import com.ubiqube.etsi.mano.service.cond.Visitor;

public class NumberValueExpr implements ValueExpr {
	private final double value;

	public NumberValueExpr(final double value) {
		this.value = value;
	}

	@Override
	public <R, A> R accept(final Visitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

	public double getValue() {
		return value;
	}

}
