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
package com.ubiqube.parser.tosca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.sol001.OrikaMapper351Impl;
import com.ubiqube.parser.tosca.api.ToscaApi;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.VnfExtCp;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.VnfVirtualLink;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.vdu.Compute;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduScalingAspectDeltas;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VnfIndicator;

import ma.glasnost.orika.MapperFactory;

class UbiVnfToscaTest {

	private static final Logger LOG = LoggerFactory.getLogger(UbiVnfToscaTest.class);

	private final Map<String, String> parameters = new HashMap<>();

	private final ToscaApi toscaApi;

	public UbiVnfToscaTest() {
		final MapperFactory mapperFactory = Utils.createMapperFactory();
		final OrikaMapper351Impl orika = new OrikaMapper351Impl();
		orika.configureMapper(mapperFactory);
		toscaApi = new ToscaApi(this.getClass().getClassLoader(), mapperFactory.getMapperFacade());
	}

	@Test
	void testUbiCsar() throws Exception {
		StaticTestTools.createVnfPackage();
		final ToscaParser toscaParser = new ToscaParser(new File("/tmp/ubi-tosca.csar"));
		final ToscaContext root = toscaParser.getContext();

		final List<VnfVirtualLink> list = toscaApi.getObjects(root, parameters, VnfVirtualLink.class);
		assertEquals(3, list.size());
		final VnfVirtualLink elem = list.get(0);
		assertEquals("leftVl01", elem.getInternalName());
		assertEquals("192.168.0.100", elem.getVlProfile().getVirtualLinkProtocolData().get(0).getL3ProtocolData().getIpAllocationPools().get(0).getStartIpAddress());

		final List<VnfIndicator> l2 = toscaApi.getObjects(root, parameters, VnfIndicator.class);
		assertEquals(2, l2.size());
		final List<Compute> lComp = toscaApi.getObjects(root, parameters, Compute.class);
		assertEquals(2, lComp.size());
		lComp.stream().forEach(x -> {
			assertEquals(1, x.getArtifacts().size());
		});
	}

	@Test
	void testUbiCsarCompute() throws Exception {
		StaticTestTools.createVnfPackage();
		final ToscaParser toscaParser = new ToscaParser(new File("/tmp/ubi-tosca.csar"));
		final ToscaContext root = toscaParser.getContext();

		final List<Compute> list = toscaApi.getObjects(root, parameters, Compute.class);
		LOG.debug("{}", list);
		final List<VnfExtCp> extCp = toscaApi.getObjects(root, parameters, VnfExtCp.class);
		LOG.debug("{}", extCp);
		final List<VduScalingAspectDeltas> vsad = toscaApi.getObjects(root, parameters, VduScalingAspectDeltas.class);
		LOG.debug("vsad {}", vsad);
		assertNotNull(vsad);
	}
}
