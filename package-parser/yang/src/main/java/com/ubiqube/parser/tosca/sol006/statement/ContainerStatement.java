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

import com.ubiqube.parser.tosca.generator.YangUtils;
import com.ubiqube.parser.tosca.sol006.ir.IrStatement;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
public class ContainerStatement extends AbstractStatementImpl {
	private List<ListStatement> list = new ArrayList<>();
	private List<LeafStatement> leaf = new ArrayList<>();
	private List<LeafListStatement> leafList = new ArrayList<>();
	private List<ContainerStatement> container = new ArrayList<>();
	private List<ChoiceStatement> choice = new ArrayList<>();
	private List<UsesStatement> uses = new ArrayList<>();
	private String name;
	private String description;
	private String reference;
	private String presence;
	private String when;

	@Override
	public String getYangName() {
		return "container";
	}

	@Override
	public void load(final IrStatement res) {
		name = res.getArgument().toString();
		final List<IrStatement> stmts = res.getStatements();
		stmts.forEach(this::doSwitch);
	}

	private void doSwitch(final IrStatement x) {
		switch (x.getKeyword().identifier()) {
		case "description" -> description = YangUtils.argumentToString(x.getArgument());
		case "reference" -> reference = YangUtils.argumentToString(x.getArgument());
		case "action" -> handleError(x);
		case "choice" -> handleChoice(x);
		case "config" -> handleError(x);
		case "container" -> handleContainer(x);
		case "grouping" -> handleError(x);
		case "if-feature" -> handleError(x);
		case "leaf" -> handleLeaf(x);
		case "leaf-list" -> handleLeafList(x);
		case "list" -> handleList(x);
		case "typedef" -> handleError(x);
		case "uses" -> handleUses(x);
		case "presence" -> presence = YangUtils.argumentToString(x.getArgument());
		case "when" -> when = YangUtils.argumentToString(x.getArgument());
		default -> throw new IllegalArgumentException(x.getKeyword() + "");
		}
	}

	private void handleUses(final IrStatement x) {
		final UsesStatement us = new UsesStatement();
		us.load(x);
		uses.add(us);
	}

	private void handleChoice(final IrStatement x) {
		final ChoiceStatement us = new ChoiceStatement();
		us.load(x);
		choice.add(us);
	}

	private void handleList(final IrStatement x) {
		final ListStatement us = new ListStatement();
		us.load(x);
		list.add(us);
	}

	private void handleLeafList(final IrStatement x) {
		final LeafListStatement us = new LeafListStatement();
		us.load(x);
		leafList.add(us);
	}

	private void handleContainer(final IrStatement x) {
		final ContainerStatement us = new ContainerStatement();
		us.load(x);
		container.add(us);
	}

	private void handleLeaf(final IrStatement x) {
		final LeafStatement us = new LeafStatement();
		us.load(x);
		leaf.add(us);
	}

	private void handleError(final IrStatement x) {
		throw new IllegalArgumentException(x.getKeyword() + "");
	}

}
