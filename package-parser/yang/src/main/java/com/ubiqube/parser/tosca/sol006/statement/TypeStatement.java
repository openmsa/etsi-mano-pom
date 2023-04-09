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
 * @see https://www.rfc-editor.org/rfc/rfc7950#section-7.4
 */
@Getter
@Setter
public class TypeStatement extends AbstractStatementImpl {

	private List<String> base = new ArrayList<>();
	private List<BitStatement> bit = new ArrayList<>();
	private List<EnumStatement> enu = new ArrayList<>();
	private String fractionDigit;
	private String length;
	private String path;
	private List<String> pattern = new ArrayList<>();
	private RangeStatement range;
	// Boolean
	private String requireInstance;
	private List<TypeStatement> type = new ArrayList<>();
	private String name;

	@Override
	public String getYangName() {
		return "type";
	}

	@Override
	public void load(final IrStatement res) {
		name = res.getArgument().toString();
		final List<IrStatement> stmts = res.getStatements();
		stmts.forEach(this::doSwitch);
	}

	private void doSwitch(final IrStatement x) {
		switch (x.getKeyword().identifier()) {
		case "base" -> YangUtils.handleListable(x.getArgument(), base);
		case "bit" -> YangUtils.genericHandle(x, BitStatement::new, bit);
		case "enum" -> YangUtils.genericHandle(x, EnumStatement::new, enu);
		case "fraction-digits" -> fractionDigit = YangUtils.argumentToString(x.getArgument());
		case "length" -> length = YangUtils.argumentToString(x.getArgument());
		case "path" -> path = YangUtils.argumentToString(x.getArgument());
		case "pattern" -> YangUtils.argumentToString(x.getArgument());
		case "range" -> range = YangUtils.genericHandleSingle(x, RangeStatement::new);
		case "require-instance" -> requireInstance = YangUtils.argumentToString(x.getArgument());
		case "type" -> YangUtils.genericHandle(x, TypeStatement::new, type);
		default -> ErrorHelper.handleError(x);
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
