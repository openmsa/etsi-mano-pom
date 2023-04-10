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
import com.ubiqube.parser.tosca.generator.StatusType;
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
public class LeafListStatement extends AbstractStatementImpl {
	private List<MustStatement> must = new ArrayList<>();
	private String name;
	private String description;
	private String reference;
	private TypeStatement type;
	private String minElement;
	private String maxElement;
	private String def;
	private String orderBy;
	private StatusType status;
	private String units;
	private String when;

	@Override
	public String getYangName() {
		return "leaf-list";
	}

	@Override
	public void load(final IrStatement res) {
		name = res.getArgument().toString();
		final List<IrStatement> stmts = res.getStatements();
		stmts.forEach(this::doSwitch);
	}

	private void doSwitch(final IrStatement x) {
		switch (x.getKeyword().identifier()) {
		case "config" -> ErrorHelper.handleError(x);
		case "default" -> def = YangUtils.argumentToString(x.getArgument());
		case "description" -> description = YangUtils.argumentToString(x.getArgument());
		case "if-feature" -> ErrorHelper.handleError(x);
		case "min-elements" -> minElement = YangUtils.argumentToString(x.getArgument());
		case "max-elements" -> maxElement = YangUtils.argumentToString(x.getArgument());
		case "must" -> YangUtils.genericHandle(this, x, MustStatement::new, must);
		case "order-by" -> orderBy = YangUtils.argumentToString(x.getArgument());
		case "reference" -> reference = YangUtils.argumentToString(x.getArgument());
		case "status" -> status = StatusType.fromValue(YangUtils.argumentToString(x.getArgument()));
		case "type" -> type = YangUtils.genericHandleSingle(this, x, TypeStatement::new);
		case "units" -> units = YangUtils.argumentToString(x.getArgument());
		case "when" -> when = YangUtils.argumentToString(x.getArgument());
		default -> throw new IllegalArgumentException(x.getKeyword() + "");
		}
		// Objects.requireNonNull(type, "Type is mandatory on leaf-list object.")
	}

}
