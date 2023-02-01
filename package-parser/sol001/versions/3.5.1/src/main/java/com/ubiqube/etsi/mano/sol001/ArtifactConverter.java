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

import com.ubiqube.parser.tosca.Artifact;
import com.ubiqube.parser.tosca.ParseException;
import com.ubiqube.parser.tosca.objects.tosca.artifacts.nfv.SwImage;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.ChecksumData;

import jakarta.validation.constraints.NotNull;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import tosca.datatypes.nfv.SwImageData;

/**
 *
 * @author olivier
 *
 */
public class ArtifactConverter extends BidirectionalConverter<SwImageData, Artifact> {

	@Override
	public Artifact convertTo(final SwImageData s, final Type<Artifact> destinationType, final MappingContext mappingContext) {
		final SwImage sw = new SwImage();
		sw.setChecksum(map(s.getChecksum()));
		sw.setContainerFormat(s.getContainerFormat());
		sw.setDiskFormat(s.getDiskFormat());
		sw.setMinDisk(s.getMinDisk());
		sw.setMinRam(s.getMinRam());
		sw.setName(s.getName());
		sw.setOperatingSystem(s.getOperatingSystem());
		sw.setProvider(s.getProvider());
		sw.setSize(s.getSize());
		sw.setSupportedVirtualisationEnvironments(s.getSupportedVirtualisationEnvironments());
		sw.setVersion(s.getVersion());
		return sw;
	}

	private static ChecksumData map(final tosca.datatypes.nfv.@NotNull ChecksumData checksum) {
		final ChecksumData ck = new ChecksumData();
		ck.setAlgorithm(checksum.getAlgorithm());
		ck.setHash(checksum.getHash());
		return ck;
	}

	@Override
	public SwImageData convertFrom(final Artifact source, final Type<SwImageData> destinationType, final MappingContext mappingContext) {
		throw new ParseException("Could not convert Artifact to SwImageData");
	}

}
