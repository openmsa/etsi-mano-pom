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
package com.ubiqube.etsi.mano.sol001;

import com.ubiqube.parser.tosca.api.OrikaMapper;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.Mciop;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.vdu.Compute;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.vdu.OsContainer;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.vdu.VirtualBlockStorage;

import ma.glasnost.orika.MapperFactory;

/**
 *
 * @author olivier
 *
 */
public class OrikaMapper421Impl implements OrikaMapper {

	@Override
	public void configureMapper(final MapperFactory mapper) {
		mapper.classMap(Compute.class, tosca.nodes.nfv.vdu.Compute.class)
				.customize(new ArtifactMapper())
				.byDefault()
				.register();
		mapper.classMap(VirtualBlockStorage.class, tosca.nodes.nfv.vdu.VirtualBlockStorage.class)
				.customize(new BlockStorageMapper())
				.byDefault()
				.register();
		mapper.classMap(OsContainer.class, tosca.nodes.nfv.vdu.OsContainer.class)
				.customize(new ArtifactStorageMapper())
				.byDefault()
				.register();
		mapper.classMap(Mciop.class, tosca.nodes.nfv.Mciop.class)
				.customize(new MciopArtifactMapper())
				.byDefault()
				.register();
	}

}
