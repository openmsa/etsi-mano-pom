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
package com.ubiqube.etsi.mano.service.pkg.tosca.vnf;

import java.util.Map.Entry;

import com.ubiqube.etsi.mano.dao.mano.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.common.Checksum;
import com.ubiqube.etsi.mano.service.pkg.tosca.SizeConverter;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import tosca.artifacts.nfv.SwImage;
import tosca.datatypes.nfv.ChecksumData;
import tosca.nodes.nfv.vdu.VirtualBlockStorage;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class ArtefactMapper extends CustomMapper<VirtualBlockStorage, VnfStorage> {

	@Override
	public void mapAtoB(final VirtualBlockStorage a, final VnfStorage b, final MappingContext context) {
		final SizeConverter sc = new SizeConverter();
		final Entry aa = a.getArtifacts().entrySet().iterator().next();
		final SoftwareImage si = new SoftwareImage();
		if (aa.getValue() instanceof final SwImage sw) {
			final Checksum check = new Checksum();
			final ChecksumData cd = sw.getChecksum();
			check.setAlgorithm(cd.getAlgorithm());
			check.setHash(cd.getHash());
			si.setChecksum(check);
			si.setName((String) aa.getKey());
			b.setSoftwareImage(si);
			si.setContainerFormat(sw.getContainerFormat());
			si.setDiskFormat(sw.getDiskFormat());
			si.setImagePath(sw.getFile());
			si.setMinDisk(sc.convertTo(sw.getMinDisk(), null, context));
			si.setMinRam(sc.convertTo(sw.getMinRam(), null, context));
			si.setProvider(sw.getProvider());
			si.setVersion(sw.getVersion());
			b.setSize(sc.convertTo(sw.getSize(), null, context));
		}
		b.setToscaName(a.getInternalName());
		b.setType("BLOCK");
		super.mapAtoB(a, b, context);
	}

}