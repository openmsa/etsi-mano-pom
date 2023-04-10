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
			handleList(listener, root, x.getList());
			listener.endContainer(x);
		});
	}

	private static void handleList(final WalkerListener listener, final ModuleStatement root, final List<ListStatement> list) {
		list.forEach(x -> {
			listener.listStart(x);
			handleList(listener, root, x.getList());
			handleLeaf(listener, root, x.getLeaf());
			handleLeafList(listener, root, x.getLeafList());
			// choice
			handleUses(listener, root, x.getUses());
			// container
			listener.listEnd(x);
		});
	}

	private static void handleLeafList(final WalkerListener listener, final ModuleStatement root, final List<LeafListStatement> leafList) {
		leafList.forEach(x -> {
			listener.leafListStart(x);
			listener.leafListEnd(x);
		});
	}

	private static void handleLeaf(final WalkerListener listener, final ModuleStatement root, final List<LeafStatement> leaf) {
		leaf.forEach(x -> {
			listener.leafStart(x);
			listener.leafEnd(x);
		});
	}

	private static void handleUses(final WalkerListener listener, final ModuleStatement root, final List<UsesStatement> uses) {
		uses.forEach(x -> {
			listener.usesStart(x);
			final GroupingStatement gs = findGrouping(root, x.getName());
			handleGrouping(listener, root, gs);
			listener.usesEnd(x);
		});
	}

	private static void handleGrouping(final WalkerListener listener, final ModuleStatement root, final GroupingStatement gs) {
		handleLeaf(listener, root, gs.getLeaf());
		handleLeafList(listener, root, gs.getLeafList());
		handleList(listener, root, gs.getList());
		handleUses(listener, root, gs.getUses());
	}

	private static GroupingStatement findGrouping(final ModuleStatement root, final String name) {
		return root.getGrouping().stream().filter(x -> x.getName().equals(name)).findFirst().orElseThrow();
	}

}
