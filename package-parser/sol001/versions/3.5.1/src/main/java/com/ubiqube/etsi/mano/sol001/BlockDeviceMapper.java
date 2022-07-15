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
package com.ubiqube.etsi.mano.sol001;

import java.util.HashMap;

import javax.validation.constraints.NotNull;

import com.ubiqube.parser.tosca.objects.tosca.artifacts.nfv.SwImage;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.ChecksumData;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.vdu.VirtualBlockStorage;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import tosca.datatypes.nfv.SwImageData;

/**
 *
 * @author olivier
 *
 */
public class BlockDeviceMapper extends CustomMapper<VirtualBlockStorage, tosca.nodes.nfv.vdu.VirtualBlockStorage> {

	@Override
	public void mapBtoA(final tosca.nodes.nfv.vdu.VirtualBlockStorage b, final VirtualBlockStorage a, final MappingContext context) {
		final SwImageData sw = b.getSwImageData();
		if (null == sw) {
			return;
		}
		final SwImage si = new SwImage();
		si.setArtifactVersion(sw.getVersion());
		si.setChecksum(map(sw.getChecksum()));
		si.setContainerFormat("");
		si.setDeployPath(null);
		si.setDescription(sw.getInternalDescription());
		si.setDiskFormat(sw.getDiskFormat());
		si.setFile(null);
		si.setMinDisk(sw.getMinDisk());
		si.setSupportedVirtualisationEnvironments(sw.getSupportedVirtualisationEnvironments());
		si.setType(SwImage.class.getName());
		si.setVersion(sw.getVersion());
		if (null == a.getArtifacts()) {
			a.setArtifacts(new HashMap<>());
		}
		a.getArtifacts().put(sw.getName(), si);
	}

	private @NotNull ChecksumData map(final tosca.datatypes.nfv.@NotNull ChecksumData checksum) {
		final ChecksumData ck = new ChecksumData();
		ck.setAlgorithm(checksum.getAlgorithm());
		ck.setHash(checksum.getHash());
		return ck;
	}

}
