package com.ubiqube.etsi.mano.service.cond.ast;

import com.ubiqube.etsi.mano.service.cond.Operator;
import com.ubiqube.etsi.mano.service.cond.Visitor;

public class GenericCondition implements ConditionExpr {

	private final Operator op;

	private NameExpr left;

	private final ValueExpr right;

	public GenericCondition(final NameExpr left, final Operator op, final ValueExpr right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}

	@Override
	public <R, A> R accept(final Visitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

	public Operator getOp() {
		return op;
	}

	@Override
	public void setLeft(final NameExpr left) {
		this.left = left;
	}

	public NameExpr getLeft() {
		return left;
	}

	public ValueExpr getRight() {
		return right;
	}

}
