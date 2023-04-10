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
import com.ubiqube.parser.tosca.sol006.Revision;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument;
import com.ubiqube.parser.tosca.sol006.ir.IrStatement;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 * @see https://www.rfc-editor.org/rfc/rfc7950#section-7.2
 */
@Getter
@Setter
public class SubMouduleStatement extends AbstractStatementImpl {

	private YangVersionStatement yangVersion;

	private List<IncludeStatement> include = new ArrayList<>();

	private List<ImportStatement> imp = new ArrayList<>();

	private String description;

	private List<GroupingStatement> grouping = new ArrayList<>();

	private List<IdentityStatement> identity = new ArrayList<>();

	private List<TypeDefStatement> typedef = new ArrayList<>();

	private String name;

	private Revision version;

	private BelongsToStatement belongsTo;

	private String prefix;

	private String organization;

	@Override
	public String getYangName() {
		return "submodule";
	}

	@Override
	public void load(final IrStatement res) {
		final IrArgument arg = res.getArgument();
		name = arg.toString();
		final List<IrStatement> stmt = res.getStatements();
		stmt.forEach(this::parseStatement);
	}

	private void parseStatement(final IrStatement x) {
		switch (x.getKeyword().identifier()) {
		case "belongs-to" -> belongsTo = YangUtils.genericHandleSingle(this, x, BelongsToStatement::new);
		case "description" -> description = YangUtils.argumentToString(x.getArgument());
		case "identity" -> YangUtils.genericHandle(this, x, IdentityStatement::new, identity);
		case "include" -> YangUtils.genericHandle(this, x, IncludeStatement::new, include);
		case "import" -> YangUtils.genericHandle(this, x, ImportStatement::new, imp);
		case "grouping" -> YangUtils.genericHandle(this, x, GroupingStatement::new, grouping);
		case "organization" -> organization = YangUtils.argumentToString(x.getArgument());
		case "prefix" -> prefix = YangUtils.argumentToString(x.getArgument());
		case "revision" -> YangUtils.genericHandle(this, x, RevisionStatement::new, revision);
		case "typedef" -> YangUtils.genericHandle(this, x, TypeDefStatement::new, typedef);
		case "yang-version" -> version = new Revision(YangUtils.argumentToString(x.getArgument()));
		default -> ErrorHelper.handleError(x);
		}
	}

}
