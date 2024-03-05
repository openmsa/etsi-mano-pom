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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.cond.Operator;
import com.ubiqube.etsi.mano.service.cond.Visitor;

public class GenericCondition implements ConditionExpr {
	private Node left;

	private Operator op;

	private Node right;

	@JsonCreator
	public GenericCondition(@JsonProperty("left") final Node left, @JsonProperty("op") final Operator op, @JsonProperty("right") final Node right) {
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
	public void setLeft(final Node left) {
		this.left = left;
	}

	@Override
	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(final Node right) {
		this.right = right;
	}

	public void setOp(final Operator op) {
		this.op = op;
	}

	@Override
	public String toString() {
		return "GenericCondition [left=" + left + ", op=" + op + ", right=" + right + "]";
	}

}
