package com.ubiqube.etsi.mano.service.cond.ast;

import java.util.List;

import com.ubiqube.etsi.mano.service.cond.Visitor;

public class ArrayValueExpr implements ValueExpr {

	private final List<Object> value;

	public ArrayValueExpr(final List<Object> value) {
		this.value = value;
	}

	@Override
	public <R, A> R accept(final Visitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

	public List<Object> getValue() {
		return value;
	}

}
