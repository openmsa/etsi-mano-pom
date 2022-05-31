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
package com.ubiqube.parser.tosca.sol006.ir;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
public class IrStatement implements IrNode {
	private final IrKeyword keyword;
	private final IrArgument argument;
	private int line;
	private int column;
	private List<IrStatement> statements;

	public IrStatement(final IrKeyword keyword, final IrArgument argument, final List<IrStatement> statements, final int line, final int column) {
		this.keyword = keyword;
		this.argument = argument;
		this.line = line;
		this.column = column;
		this.statements = statements;
	}

}
