/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.etsi.mano.grammar.v25;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.AttrNameContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.FilterContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.OpMultiContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.OpOneContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.SimpleFilterExprMultiContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.SimpleFilterExprOneContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.ValueContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25BaseListener;
import com.ubiqube.etsi.mano.grammar.Node;
import com.ubiqube.etsi.mano.grammar.Node.Operand;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class TreeBuilderV25 extends EtsiFilterV25BaseListener {
	private static final String CURRENT_NODE_IS_NULL = "Current node is null";
	@Nullable
	private Node<String> currentNode;
	@Nonnull
	private final List<Node<String>> listNode = new ArrayList<>();

	public List<Node<String>> getListNode() {
		return listNode;
	}

	@Override
	public void enterSimpleFilterExprOne(final @Nullable SimpleFilterExprOneContext ctx) {
		currentNode = new Node<>();
	}

	@Override
	public void enterSimpleFilterExprMulti(final @Nullable SimpleFilterExprMultiContext ctx) {
		currentNode = new Node<>();
	}

	@Override
	public void exitSimpleFilterExprOne(final @Nullable SimpleFilterExprOneContext ctx) {
		listNode.add(currentNode);
		currentNode = null;
	}

	@Override
	public void exitSimpleFilterExprMulti(final @Nullable SimpleFilterExprMultiContext ctx) {
		listNode.add(currentNode);
		currentNode = null;
	}

	@Override
	public void exitFilter(final @Nullable FilterContext ctx) {
		Objects.requireNonNull(ctx);
		Objects.requireNonNull(currentNode, CURRENT_NODE_IS_NULL);
		currentNode.addValue(ctx.getText());
	}

	@Override
	public void exitOpOne(final @Nullable OpOneContext ctx) {
		Objects.requireNonNull(ctx);
		final Operand op = Operand.valueOf(ctx.getText().toUpperCase());
		Objects.requireNonNull(currentNode, CURRENT_NODE_IS_NULL);
		currentNode.setOp(op);
	}

	@Override
	public void exitOpMulti(final @Nullable OpMultiContext ctx) {
		Objects.requireNonNull(ctx);
		final Operand op = Operand.valueOf(ctx.getText().toUpperCase());
		Objects.requireNonNull(currentNode, CURRENT_NODE_IS_NULL);
		currentNode.setOp(op);
	}

	@Override
	public void exitAttrName(final @Nullable AttrNameContext ctx) {
		Objects.requireNonNull(ctx);
		final Node<String> cn = Objects.requireNonNull(currentNode, CURRENT_NODE_IS_NULL);
		final String currentName = cn.getName();
		if (null == currentName) {
			cn.setName(ctx.getText());
		} else {
			cn.setName(cn.getName() + "." + ctx.getText());
		}
	}

	@Override
	public void exitValue(final @Nullable ValueContext ctx) {
		Objects.requireNonNull(ctx);
		Objects.requireNonNull(currentNode, CURRENT_NODE_IS_NULL);
		currentNode.addValue(ctx.getText());
	}

}
