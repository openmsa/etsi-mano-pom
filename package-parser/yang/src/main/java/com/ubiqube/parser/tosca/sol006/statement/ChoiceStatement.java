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
public class ChoiceStatement extends AbstractStatementImpl {
	private List<ListStatement> list = new ArrayList<>();
	private List<LeafStatement> leaf = new ArrayList<>();
	private List<LeafListStatement> leafList = new ArrayList<>();
	private List<ChoiceStatement> choice = new ArrayList<>();
	private List<ContainerStatement> container = new ArrayList<>();

	private String description;
	private String reference;
	private String name;
	private String mandatory;

	@Override
	public String getYangName() {
		return "choice";
	}

	@Override
	public void load(final IrStatement res) {
		name = res.getArgument().toString();
		final List<IrStatement> stmts = res.getStatements();
		stmts.forEach(x -> doSwitch(x));
	}

	private void doSwitch(final IrStatement x) {
		switch (x.getKeyword().identifier()) {
		case "description" -> description = YangUtils.argumentToString(x.getArgument());
		case "reference" -> reference = YangUtils.argumentToString(x.getArgument());
		case "leaf" -> YangUtils.genericHandle(this, x, LeafStatement::new, leaf);
		case "leaf-list" -> YangUtils.genericHandle(this, x, LeafListStatement::new, leafList);
		case "list" -> YangUtils.genericHandle(this, x, ListStatement::new, list);
		case "choice" -> YangUtils.genericHandle(this, x, ChoiceStatement::new, choice);
		case "container" -> YangUtils.genericHandle(this, x, ContainerStatement::new, container);
		case "mandatory" -> mandatory = YangUtils.argumentToString(x.getArgument());
		default -> throw new IllegalArgumentException(x.getKeyword() + "");
		}
	}

	private void handleContainer(final IrStatement x) {
		final ContainerStatement l = new ContainerStatement();
		l.load(x);
		container.add(l);
	}

	private void handleChoice(final IrStatement x) {
		final ChoiceStatement l = new ChoiceStatement();
		l.load(x);
		choice.add(l);
	}

	private void handleList(final IrStatement x) {
		final ListStatement l = new ListStatement();
		l.load(x);
		list.add(l);
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

}
