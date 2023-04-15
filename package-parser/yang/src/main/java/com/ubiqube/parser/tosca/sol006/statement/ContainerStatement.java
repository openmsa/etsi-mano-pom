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

import com.ubiqube.parser.tosca.generator.ErrorHelper;
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
public class ContainerStatement extends AbstractStatementImpl implements NamedStatement {
	private List<ListStatement> list = new ArrayList<>();
	private List<LeafStatement> leaf = new ArrayList<>();
	private List<LeafListStatement> leafList = new ArrayList<>();
	private List<ContainerStatement> container = new ArrayList<>();
	private List<ChoiceStatement> choice = new ArrayList<>();
	private List<UsesStatement> uses = new ArrayList<>();
	private List<GroupingStatement> grouping = new ArrayList<>();
	private String name;
	private String description;
	private String reference;
	private String presence;
	private String when;
	private String className;

	@Override
	public String getClassName() {
		if (null != className) {
			return className;
		}
		return name;
	}

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
		case "action" -> ErrorHelper.handleError(x);
		case "choice" -> YangUtils.genericHandle(this, x, ChoiceStatement::new, choice);
		case "config" -> ErrorHelper.handleError(x);
		case "container" -> YangUtils.genericHandle(this, x, ContainerStatement::new, container);
		case "grouping" -> YangUtils.genericHandle(this, x, GroupingStatement::new, grouping);
		case "if-feature" -> ErrorHelper.handleError(x);
		case "leaf" -> YangUtils.genericHandle(this, x, LeafStatement::new, leaf);
		case "leaf-list" -> YangUtils.genericHandle(this, x, LeafListStatement::new, leafList);
		case "list" -> YangUtils.genericHandle(this, x, ListStatement::new, list);
		case "typedef" -> ErrorHelper.handleError(x);
		case "uses" -> YangUtils.genericHandle(this, x, UsesStatement::new, uses);
		case "presence" -> presence = YangUtils.argumentToString(x.getArgument());
		case "when" -> when = YangUtils.argumentToString(x.getArgument());
		default -> throw new IllegalArgumentException(x.getKeyword() + "");
		}
	}

}
