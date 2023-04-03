/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.parser.tosca.sol006.statement;

import java.util.ArrayList;
import java.util.List;

import com.ubiqube.parser.tosca.sol006.ir.IrStatement;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 * @See https://www.rfc-editor.org/rfc/rfc7950#section-7.11.4
 */
@Getter
@Setter
public class GroupingStatement implements Statement {
	private List<ContainerStatement> container = new ArrayList<>();
	private List<UsesStatement> uses = new ArrayList<>();
	private List<ListStatement> list = new ArrayList<>();
	private List<LeafStatement> leaf = new ArrayList<>();
	private List<LeafListStatement> leafList = new ArrayList<>();
	private String name;
	private String description;
	private String reference;

	@Override
	public String getYangName() {
		return "grouping";
	}

	@Override
	public void load(final IrStatement res) {
		name = res.getArgument().toString();
		final List<IrStatement> stmts = res.getStatements();
		stmts.forEach(x -> doSwitch(x));
	}

	private void doSwitch(final IrStatement x) {
		switch (x.getKeyword().identifier()) {
		case "description" -> description = x.getArgument().toString();
		case "reference" -> reference = x.getArgument().toString();
		case "leaf" -> handleLeaf(x);
		case "leaf-list" -> handleLeafList(x);
		case "list" -> handleList(x);
		case "uses" -> handleUses(x);
		case "container" -> handleContainser(x);
		default -> throw new IllegalArgumentException(x.getKeyword() + "");
		}
	}

	private void handleContainser(final IrStatement x) {
		final ContainerStatement cs = new ContainerStatement();
		cs.load(x);
		container.add(cs);
	}

	private void handleUses(final IrStatement x) {
		final UsesStatement us = new UsesStatement();
		us.load(x);
		uses.add(us);
	}

	private void handleLeafList(final IrStatement x) {
		final LeafListStatement ls = new LeafListStatement();
		ls.load(x);
		leafList.add(ls);
	}

	private void handleLeaf(final IrStatement x) {
		final LeafStatement ls = new LeafStatement();
		ls.load(x);
		leaf.add(ls);
	}

	private void handleList(final IrStatement x) {
		final ListStatement l = new ListStatement();
		l.load(x);
		list.add(l);
	}
}
