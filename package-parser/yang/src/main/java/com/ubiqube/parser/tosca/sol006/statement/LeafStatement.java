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
package com.ubiqube.parser.tosca.sol006.statement;

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
public class LeafStatement extends AbstractStatementImpl implements NamedStatement {

	private String name;
	private String description;
	private String reference;
	private TypeStatement type;
	private String mandatory;
	private String units;
	private String must;
	private String def;
	private StatusType status;
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
		return "leaf";
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
		case "config" -> ErrorHelper.handleError(x);
		case "default" -> def = YangUtils.argumentToString(x.getArgument());
		case "if-feature" -> handleError(x);
		case "mandatory" -> mandatory = YangUtils.argumentToString(x.getArgument());
		case "type" -> type = YangUtils.genericHandleSingle(this, x, TypeStatement::new);
		case "units" -> units = YangUtils.argumentToString(x.getArgument());
		case "must" -> must = YangUtils.argumentToString(x.getArgument());
		case "status" -> status = StatusType.fromValue(YangUtils.argumentToString(x.getArgument()));
		case "when" -> when = YangUtils.argumentToString(x.getArgument());
		default -> ErrorHelper.handleError(x);
		}
	}

	private static void handleError(final IrStatement x) {
		throw new IllegalArgumentException(x.getKeyword() + "");
	}

}
