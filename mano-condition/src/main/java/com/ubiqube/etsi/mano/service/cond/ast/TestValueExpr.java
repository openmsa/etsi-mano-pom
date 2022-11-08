package com.ubiqube.etsi.mano.service.cond.ast;

import com.ubiqube.etsi.mano.service.cond.Visitor;

public class TestValueExpr implements ValueExpr {

	private final String value;

	public TestValueExpr(final String value) {
		this.value = value;
	}

	@Override
	public <R, A> R accept(final Visitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

	public String getValue() {
		return value;
	}

}
