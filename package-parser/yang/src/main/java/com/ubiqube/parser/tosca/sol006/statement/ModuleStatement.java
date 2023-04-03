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
import java.util.function.Supplier;

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
 */
@Getter
@Setter
public class ModuleStatement implements Statement {

	private List<RevisionStatement> revision = new ArrayList<>();

	private YangVersionStatement yangVersion;

	private List<IncludeStatement> include = new ArrayList<>();

	private List<ImportStatement> imp = new ArrayList<>();

	private List<GroupingStatement> grouping = new ArrayList<>();

	private List<ContainerStatement> container = new ArrayList<>();

	private List<TypeDefStatement> typeDef = new ArrayList<>();
	private List<UsesStatement> uses = new ArrayList<>();

	private String description;

	private String name;

	private Revision version;

	private BelongsToStatement belongsTo;

	private String namespace;

	private String prefix;

	private String organization;

	private String contact;

	@Override
	public String getYangName() {
		return "module";
	}

	@Override
	public void load(final IrStatement res) {
		final IrArgument arg = res.getArgument();
		name = arg.toString();
		final List<IrStatement> stmt = res.getStatements();
		stmt.forEach(this::parseStatement);
	}

	private Object parseStatement(final IrStatement x) {
		switch (x.getKeyword().identifier()) {
		case "contact" -> contact = YangUtils.argumentToString(x.getArgument());
		case "container" -> genericHandle(x, ContainerStatement::new, container);
		case "description" -> description = YangUtils.argumentToString(x.getArgument());
		case "grouping" -> handleGrouping(x);
		case "include" -> handleInclude(x);
		case "import" -> handleImport(x);
		case "namespace" -> namespace = YangUtils.argumentToString(x.getArgument());
		case "organization" -> organization = YangUtils.argumentToString(x.getArgument());
		case "prefix" -> prefix = YangUtils.argumentToString(x.getArgument());
		case "revision" -> genericHandle(x, RevisionStatement::new, revision);
		case "typedef" -> genericHandle(x, TypeDefStatement::new, typeDef);
		case "uses" -> genericHandle(x, UsesStatement::new, uses);
		case "yang-version" -> version = new Revision(x.getArgument().toString());
		default -> ErrorHelper.handleError(x);
		}
		return null;
	}

	private static <U extends Statement> void genericHandle(final IrStatement x, final Supplier<U> supp, final List<U> lst) {
		final U n = supp.get();
		n.load(x);
		lst.add(n);
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

}
