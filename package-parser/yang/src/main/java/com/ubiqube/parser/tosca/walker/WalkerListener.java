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
import com.ubiqube.parser.tosca.sol006.statement.UsesStatement;

/**
 *
 * @author Olivier Vignaud
 *
 */
public interface WalkerListener {

	void startContainer(ContainerStatement container);

	void endContainer(ContainerStatement x);

	void listStart(ListStatement x);

	void listEnd(ListStatement x);

	void leafStart(LeafStatement x);

	void leafEnd(LeafStatement x);

	void leafListStart(LeafListStatement x);

	void leafListEnd(LeafListStatement x);

	void usesStart(UsesStatement x);

	void usesEnd(UsesStatement x);

}
