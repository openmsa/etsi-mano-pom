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
package com.ubiqube.etsi.mano.grammar.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mano.etsi.grammar.v1.EtsiFilter.AttrNameContext;
import com.mano.etsi.grammar.v1.EtsiFilter.OpContext;
import com.mano.etsi.grammar.v1.EtsiFilter.SimpleFilterExprContext;
import com.mano.etsi.grammar.v1.EtsiFilter.ValueContext;
import com.mano.etsi.grammar.v1.EtsiFilterBaseListener;
import com.ubiqube.etsi.mano.grammar.Node;
import com.ubiqube.etsi.mano.grammar.Node.Operand;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class TreeBuilder extends EtsiFilterBaseListener {
	@Nullable
	private Node<String> currentNode;
	private final List<Node<String>> listNode = new ArrayList<>();

	@Override
	public void exitOp(final @Nullable OpContext ctx) {
		Objects.requireNonNull(ctx);
		final Operand op = Operand.valueOf(ctx.getText().toUpperCase());
		Objects.requireNonNull(currentNode);
		currentNode.setOp(op);
		super.exitOp(ctx);
	}

	@Override
	public void enterSimpleFilterExpr(final @Nullable SimpleFilterExprContext ctx) {
		currentNode = new Node<>();
		super.enterSimpleFilterExpr(ctx);
	}

	@Override
	public void exitValue(final @Nullable ValueContext ctx) {
		Objects.requireNonNull(ctx);
		Objects.requireNonNull(currentNode);
		currentNode.addValue(ctx.getText());
		super.exitValue(ctx);
	}

	@Override
	public void exitSimpleFilterExpr(final @Nullable SimpleFilterExprContext ctx) {
		listNode.add(currentNode);
		currentNode = null;
	}

	@Override
	public void exitAttrName(final @Nullable AttrNameContext ctx) {
		Objects.requireNonNull(ctx);
		final Node<String> cn = Objects.requireNonNull(currentNode);
		final String currentName = cn.getName();
		if (null == currentName) {
			cn.setName(ctx.getText());
		} else {
			cn.setName(cn.getName() + "." + ctx.getText());
		}
		super.exitAttrName(ctx);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("TreeBuilder [\n");
		for (final Node<String> node : listNode) {
			sb.append("\t").append(node.toString()).append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}

	public List<Node<String>> getListNode() {
		return listNode;
	}

}
