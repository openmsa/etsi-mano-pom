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
	private List<RevisionStatement> revision = new ArrayList<>();

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
		case "belongs-to" -> handleBelongsTo(x);
		case "description" -> description = YangUtils.argumentToString(x.getArgument());
		case "identity" -> handleIdentity(x);
		case "include" -> handleInclude(x);
		case "import" -> handleImport(x);
		case "grouping" -> handleGrouping(x);
		case "organization" -> organization = YangUtils.argumentToString(x.getArgument());
		case "prefix" -> prefix = YangUtils.argumentToString(x.getArgument());
		case "revision" -> handleRevision(x);
		case "typedef" -> YangUtils.genericHandle(x, TypeDefStatement::new, typedef);
		case "yang-version" -> version = new Revision(YangUtils.argumentToString(x.getArgument()));
		default -> ErrorHelper.handleError(x);
		}
	}

	private void handleIdentity(final IrStatement x) {
		final IdentityStatement is = new IdentityStatement();
		is.load(x);
		identity.add(is);
	}

	private void handleImport(final IrStatement x) {
		final ImportStatement is = new ImportStatement();
		is.load(x);
		imp.add(is);
	}

	private void handleGrouping(final IrStatement x) {
		final GroupingStatement gs = new GroupingStatement();
		gs.load(x);
		grouping.add(gs);
	}

	private void handleRevision(final IrStatement x) {
		final RevisionStatement rs = new RevisionStatement();
		rs.load(x);
		revision.add(rs);
	}

	private void handleInclude(final IrStatement x) {
		final IncludeStatement is = new IncludeStatement();
		is.load(x);
		include.add(is);
	}

	private void handleBelongsTo(final IrStatement x) {
		belongsTo = new BelongsToStatement();
		belongsTo.load(x);
	}

}
