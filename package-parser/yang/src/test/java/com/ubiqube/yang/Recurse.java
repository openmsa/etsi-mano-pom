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
package com.ubiqube.yang;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.parser.tosca.sol006.statement.GroupingStatement;
import com.ubiqube.parser.tosca.sol006.statement.LeafListStatement;
import com.ubiqube.parser.tosca.sol006.statement.LeafStatement;
import com.ubiqube.parser.tosca.sol006.statement.ListStatement;
import com.ubiqube.parser.tosca.sol006.statement.ModuleStatement;
import com.ubiqube.parser.tosca.sol006.statement.UsesStatement;
import com.ubiqube.parser.tosca.walker.WalkerListener;

public class Recurse {
	private static final Logger LOG = LoggerFactory.getLogger(Recurse.class);

	public static void doIt(final ModuleStatement root, final WalkerListener listener) {
		root.getContainer().forEach(x -> {
			//
			listener.startContainer(x);
			LOG.info("cont: {}", x.getName());
			handleList(root, x.getList());
			listener.endContainer(x);
		});
		System.out.println("");
	}

	private static void handleList(final ModuleStatement root, final List<ListStatement> list) {
		LOG.info("List:");
		list.forEach(x -> {
			LOG.info(" - {} / {}", x.getKey(), x.getName());
			handleList(root, x.getList());
			handleLeaf(root, x.getLeaf());
			handleLeafList(root, x.getLeafList());
			// choice
			handleUses(root, x.getUses());
			// container
		});
	}

	private static void handleLeafList(final ModuleStatement root, final List<LeafListStatement> leafList) {
		leafList.forEach(x -> {
			LOG.info("- list-leaf {} / {}", x.getName(), x.getType());
		});
	}

	private static void handleLeaf(final ModuleStatement root, final List<LeafStatement> leaf) {
		leaf.forEach(x -> {
			LOG.info("- leaf: {} / {}", x.getName(), x.getType());
		});
	}

	private static void handleUses(final ModuleStatement root, final List<UsesStatement> uses) {
		LOG.info("Uses:");
		uses.forEach(x -> {
			final GroupingStatement gs = findGrouping(root, x.getName());
			LOG.info(" - {}", gs.getName());
			handleGrouping(root, gs);
		});
	}

	private static void handleGrouping(final ModuleStatement root, final GroupingStatement gs) {
		handleLeaf(root, gs.getLeaf());
		handleLeafList(root, gs.getLeafList());
		handleList(root, gs.getList());
		handleUses(root, gs.getUses());
	}

	private static GroupingStatement findGrouping(final ModuleStatement root, final String name) {
		return root.getGrouping().stream().filter(x -> x.getName().equals(name)).findFirst().orElseThrow();
	}

}
