package com.ubiqube.etsi.mano.service.cond.ast;

import java.util.List;

import com.ubiqube.etsi.mano.service.cond.AstException;
import com.ubiqube.etsi.mano.service.cond.Visitor;

public class AttrHolderExpr implements ConditionExpr {

	private final String attrName;
	private final List<ConditionExpr> conditions;

	public AttrHolderExpr(final String attrName, final List<ConditionExpr> res) {
		this.attrName = attrName;
		this.conditions = res;
	}

	@Override
	public <R, A> R accept(final Visitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

	public String getAttrName() {
		return attrName;
	}

	public List<ConditionExpr> getConditions() {
		return conditions;
	}

	@Override
	public void setLeft(final NameExpr left) {
		throw new AstException("");

	}

}
