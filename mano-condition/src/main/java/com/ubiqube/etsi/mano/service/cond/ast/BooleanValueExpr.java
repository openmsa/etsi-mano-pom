package com.ubiqube.etsi.mano.service.cond.ast;

import com.ubiqube.etsi.mano.service.cond.Visitor;

public class BooleanValueExpr implements ValueExpr {
	private final boolean value;

	public BooleanValueExpr(final boolean value) {
		this.value = value;
	}

	@Override
	public <R, A> R accept(final Visitor<R, A> v, final A arg) {
		return v.visit(this);
	}

	public boolean isValue() {
		return value;
	}

}
