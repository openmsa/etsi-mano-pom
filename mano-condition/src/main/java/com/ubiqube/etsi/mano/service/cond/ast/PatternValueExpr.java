package com.ubiqube.etsi.mano.service.cond.ast;

import java.util.regex.Pattern;

import com.ubiqube.etsi.mano.service.cond.Visitor;

public class PatternValueExpr extends AbstractBooleanExpression {

	private final Pattern p;

	public PatternValueExpr(final String textValue) {
		this.p = Pattern.compile(textValue);
	}

	@Override
	public <R, A> R accept(final Visitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

	public Pattern getP() {
		return p;
	}
}
