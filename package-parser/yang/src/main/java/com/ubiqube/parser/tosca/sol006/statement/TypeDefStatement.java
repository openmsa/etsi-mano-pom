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

import java.util.List;
import java.util.Objects;

import com.ubiqube.parser.tosca.generator.ErrorHelper;
import com.ubiqube.parser.tosca.generator.StatusType;
import com.ubiqube.parser.tosca.generator.YangUtils;
import com.ubiqube.parser.tosca.sol006.ir.IrStatement;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 * @see https://www.rfc-editor.org/rfc/rfc7950#section-7.3
 */
public class TypeDefStatement implements Statement {

	private String name;
	private String def;
	private String description;
	private String reference;
	private StatusType status;
	private String type;
	private String units;

	@Override
	public String getYangName() {
		return "typedef";
	}

	@Override
	public void load(final IrStatement res) {
		name = res.getArgument().toString();
		final List<IrStatement> stmts = res.getStatements();
		stmts.forEach(this::doSwitch);
	}

	private void doSwitch(final IrStatement x) {
		switch (x.getKeyword().identifier()) {
		case "default" -> def = YangUtils.argumentToString(x.getArgument());
		case "description" -> description = YangUtils.argumentToString(x.getArgument());
		case "reference" -> reference = YangUtils.argumentToString(x.getArgument());
		case "status" -> status = StatusType.fromValue(YangUtils.argumentToString(x.getArgument()));
		case "type" -> type = YangUtils.argumentToString(x.getArgument());
		case "units" -> units = YangUtils.argumentToString(x.getArgument());
		default -> ErrorHelper.handleError(x);
		}
		Objects.requireNonNull(type, "Type is mandatory in Typedef objects.");
	}

}
