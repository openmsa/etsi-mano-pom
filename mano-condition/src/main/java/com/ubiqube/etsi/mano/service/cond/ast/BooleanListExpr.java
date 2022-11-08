package com.ubiqube.etsi.mano.service.cond.ast;

import java.util.List;

import com.ubiqube.etsi.mano.service.cond.BooleanOperatorEnum;
import com.ubiqube.etsi.mano.service.cond.Visitor;

public class BooleanListExpr extends AbstractBooleanExpression {

	private final BooleanOperatorEnum op;
	private final List<BooleanExpression> condition;

	public BooleanListExpr(final BooleanOperatorEnum valueOf, final List<BooleanExpression> res) {
		this.op = valueOf;
		this.condition = res;
	}

	@Override
	public <R, A> R accept(final Visitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

	public BooleanOperatorEnum getOp() {
		return op;
	}

	public List<BooleanExpression> getCondition() {
		return condition;
	}

}
