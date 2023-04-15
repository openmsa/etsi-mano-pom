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
package com.ubiqube.parser.tosca.walker;

import com.ubiqube.parser.tosca.sol006.statement.ContainerStatement;
import com.ubiqube.parser.tosca.sol006.statement.LeafListStatement;
import com.ubiqube.parser.tosca.sol006.statement.LeafStatement;
import com.ubiqube.parser.tosca.sol006.statement.ListStatement;
import com.ubiqube.parser.tosca.sol006.statement.TypeStatement;
import com.ubiqube.parser.tosca.sol006.statement.UsesStatement;

public class ClassCollectorListener implements WalkerListener {
	private final MultiValueMap map = new MultiValueMap();

	public MultiValueMap getMap() {
		return map;
	}

	@Override
	public void startContainer(final ContainerStatement container) {
		map.add(container);
	}

	@Override
	public void endContainer(final ContainerStatement x) {
		//
	}

	@Override
	public void listStart(final ListStatement x) {
		map.add(x);
	}

	@Override
	public void listEnd(final ListStatement x) {
		//
	}

	@Override
	public void leafStart(final LeafStatement x) {
		if ("enumeration".equals(x.getType().getName())) {
			map.add(x);
		}

	}

	@Override
	public void leafEnd(final LeafStatement x) {
		//

	}

	@Override
	public void leafListStart(final LeafListStatement x) {
		final TypeStatement typ = x.getType();
		if (!typ.getEnu().isEmpty()) {
			map.add(x.getType());
		}
	}

	@Override
	public void leafListEnd(final LeafListStatement x) {
		//

	}

	@Override
	public void usesStart(final UsesStatement x) {
		//

	}

	@Override
	public void usesEnd(final UsesStatement x) {
		//

	}

}
