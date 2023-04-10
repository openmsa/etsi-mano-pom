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
package com.ubiqube.parser.tosca.sol006.ir;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

import com.ubiqube.parser.tosca.generator.YangException;
import com.ubiqube.parser.tosca.sol006.statement.DescriptionStatement;
import com.ubiqube.parser.tosca.sol006.statement.GroupingStatement;
import com.ubiqube.parser.tosca.sol006.statement.ImportStatement;
import com.ubiqube.parser.tosca.sol006.statement.IncludeStatement;
import com.ubiqube.parser.tosca.sol006.statement.ModuleStatement;
import com.ubiqube.parser.tosca.sol006.statement.RevisionStatement;
import com.ubiqube.parser.tosca.sol006.statement.SubMouduleStatement;
import com.ubiqube.parser.tosca.sol006.statement.YangVersionStatement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YangRoot {

	List<ModuleStatement> module = new ArrayList<>();

	List<SubMouduleStatement> submodule = new ArrayList<>();

	private List<RevisionStatement> revision;

	private YangVersionStatement yangVersion;

	private List<IncludeStatement> include;

	private List<ImportStatement> imp;

	private DescriptionStatement description;

	private List<GroupingStatement> grouping;

	public static YangRoot of(final ParseTree tree) {
		return new YangRoot();
	}

	public void load(final IrStatement res) {
		if (res.getKeyword().identifier().equals("submodule")) {
			final SubMouduleStatement sub = new SubMouduleStatement();
			sub.load(res);
			submodule.add(sub);
		} else if (res.getKeyword().identifier().equals("module")) {
			final ModuleStatement sub = new ModuleStatement();
			sub.load(res);
			module.add(sub);
		}
	}

	public void rebuildNameSpaces() {
		submodule.stream()
				.filter(x -> x.getNamespace() == null)
				.forEach(x -> {
					final ModuleStatement mod = findModuleByName(x.getBelongsTo().getName());
					x.setNamespace(mod.getNamespace());
				});

	}

	private ModuleStatement findModuleByName(final String name) {
		return module.stream()
				.filter(x -> name.equals(x.getName()))
				.findFirst()
				.orElseThrow(() -> new YangException("Could not find " + name));
	}
}
