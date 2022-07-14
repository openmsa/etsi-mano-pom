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
import java.util.Map;

import com.ubiqube.parser.tosca.Artifact;
import com.ubiqube.parser.tosca.api.OrikaMapper;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.vdu.Compute;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import tosca.datatypes.nfv.SwImageData;

/**
 *
 * @author olivier
 *
 */
public class OrikaMapper351Impl implements OrikaMapper {
	@Override
	public void configureMapper(final MapperFactory mapper) {
		mapper.classMap(Compute.class, tosca.nodes.nfv.vdu.Compute.class)
				.customize(new CustomMapper<Compute, tosca.nodes.nfv.vdu.Compute>() {
					@Override
					public void mapBtoA(final tosca.nodes.nfv.vdu.Compute b, final Compute a, final MappingContext context) {
						if (null == a.getArtifacts()) {
							a.setArtifacts(new HashMap<>());
						}
						final Map<String, Artifact> tgt = a.getArtifacts();
						tgt.putAll(b.getArtifacts());
						if (null != b.getSwImageData()) {
							final SwImageData swid = b.getSwImageData();
							if (tgt.get(swid.getName()) != null) {
								return;
							}
							final ArtifactConverter cnv = new ArtifactConverter();
							final Artifact res = cnv.convertTo(swid, null, context);
							tgt.put(swid.getName(), res);
						}
						super.mapBtoA(b, a, context);
					}
				})
				.byDefault()
				.register();
		// SwImage
	}

}
