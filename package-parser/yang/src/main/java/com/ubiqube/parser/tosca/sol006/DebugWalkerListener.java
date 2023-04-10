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
package com.ubiqube.parser.tosca.sol006;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.parser.tosca.sol006.statement.ContainerStatement;
import com.ubiqube.parser.tosca.sol006.statement.LeafListStatement;
import com.ubiqube.parser.tosca.sol006.statement.LeafStatement;
import com.ubiqube.parser.tosca.sol006.statement.ListStatement;
import com.ubiqube.parser.tosca.sol006.statement.UsesStatement;
import com.ubiqube.parser.tosca.walker.WalkerListener;

public class DebugWalkerListener implements WalkerListener {
	private static final Logger LOG = LoggerFactory.getLogger(DebugWalkerListener.class);

	int indent = 0;

	@Override
	public void startContainer(final ContainerStatement x) {
		indent("co " + x.getName());
		indent++;
	}

	@Override
	public void endContainer(final ContainerStatement x) {
		indent--;
	}

	@Override
	public void listStart(final ListStatement x) {
		indent("li " + x.getName());
		indent++;
	}

	@Override
	public void listEnd(final ListStatement x) {
		indent--;
	}

	@Override
	public void leafStart(final LeafStatement x) {
		indent("le " + x.getName());
	}

	@Override
	public void leafEnd(final LeafStatement x) {
		// LOG.info("leaf-end: {}", x.getName());
	}

	@Override
	public void leafListStart(final LeafListStatement x) {
		indent("ll " + x.getName());
		indent++;
	}

	@Override
	public void leafListEnd(final LeafListStatement x) {
		indent--;
	}

	@Override
	public void usesStart(final UsesStatement x) {
		indent("us " + x.getName());
		// indent++;
	}

	@Override
	public void usesEnd(final UsesStatement x) {
		// indent--;
	}

	private void indent(final String name) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append("    ");
		}
		sb.append("+ ").append(name);
		LOG.info(sb.toString());
	}

}
