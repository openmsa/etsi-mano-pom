package com.ubiqube.etsi.mano.service.cond.ast;

public abstract class AbstractBooleanExpression implements ConditionExpr {
	private NameExpr left;

	@Override
	public void setLeft(final NameExpr left) {
		this.left = left;
	}

	public NameExpr getLeft() {
		return left;
	}

}
