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
public class UsesStatement extends AbstractStatementImpl implements NamedStatement {

	private String name;
	private String description;
	private String reference;

	@Override
	public String getYangName() {
		return "uses";
	}

	@Override
	public void load(final IrStatement res) {
		name = res.getArgument().toString();
		final List<IrStatement> stmts = res.getStatements();
		stmts.forEach(x -> doSwitch(x));
	}

	private void doSwitch(final IrStatement x) {
		switch (x.getKeyword().identifier()) {
		case "augment" -> handleAugment(x);
		case "description" -> description = x.getArgument().toString();
		case "reference" -> reference = x.getArgument().toString();
		case "if-feature" -> handleIfFeature(x);
		case "refine" -> handleRefine(x);
		case "status" -> handleStatus(x);
		case "when" -> handleWhen(x);
		default -> throw new IllegalArgumentException(x.getKeyword() + "");
		}
	}

	private void handleWhen(final IrStatement x) {
		throw new IllegalArgumentException(x.getKeyword() + "");
	}

	private Object handleStatus(final IrStatement x) {
		throw new IllegalArgumentException(x.getKeyword() + "");
	}

	private Object handleRefine(final IrStatement x) {
		throw new IllegalArgumentException(x.getKeyword() + "");
	}

	private Object handleIfFeature(final IrStatement x) {
		throw new IllegalArgumentException(x.getKeyword() + "");
	}

	private Object handleAugment(final IrStatement x) {
		throw new IllegalArgumentException(x.getKeyword() + "");
	}

}
