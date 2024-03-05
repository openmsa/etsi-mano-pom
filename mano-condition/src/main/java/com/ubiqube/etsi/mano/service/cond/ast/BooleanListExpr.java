/**
 *     Copyright (C) 2019-2024 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.cond.ast;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.service.cond.BooleanOperatorEnum;
import com.ubiqube.etsi.mano.service.cond.Visitor;

public class BooleanListExpr extends AbstractBooleanExpression {

	private BooleanOperatorEnum op;
	private List<BooleanExpression> condition;

	@JsonCreator
	public BooleanListExpr(@JsonProperty("op") final BooleanOperatorEnum valueOf, @JsonProperty("condition") final List<BooleanExpression> res) {
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

	public void setCondition(final List<BooleanExpression> condition) {
		this.condition = condition;
	}

	public void setOp(final BooleanOperatorEnum op) {
		this.op = op;
	}

	@Override
	public String toString() {
		return "BooleanListExpr [op=" + op + ", condition=" + condition + "]";
	}

}
