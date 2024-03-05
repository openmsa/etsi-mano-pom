/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;

@ExtendWith(MockitoExtension.class)
class ContrailContributorTest {
	@Mock
	private NsLiveInstanceJpa nsLiveInstanceJpa;

	@Test
	void testBasic() throws Exception {
		final ContrailContributor cc = new ContrailContributor(nsLiveInstanceJpa);
		final NsdPackage bundle = new NsdPackage();
		bundle.setVnffgs(Set.of());
		//
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsdInstance = new NsdInstance();
		blueprint.setNsInstance(nsdInstance);
		blueprint.setId(UUID.randomUUID());
		final List<SclableResources<Object>> res = cc.contribute(bundle, blueprint);
		assertNotNull(res);
		assertTrue(res.isEmpty());
	}

	@Test
	void testAll() throws Exception {
		final ContrailContributor cc = new ContrailContributor(nsLiveInstanceJpa);
		final NsdPackage bundle = new NsdPackage();
		final VnffgDescriptor vnffg01 = new VnffgDescriptor();
		final NfpDescriptor nfpd01 = new NfpDescriptor();
		final VnffgInstance inst01 = new VnffgInstance();
		final CpPair cp01 = new CpPair();
		cp01.setToscaName("cp01");
		inst01.setToscaName("inst01");
		inst01.setPairs(List.of(cp01));
		nfpd01.setInstances(List.of(inst01));
		nfpd01.setToscaName("nfpd01");
		vnffg01.setNfpd(List.of(nfpd01));
		vnffg01.setName("vnffg01");
		bundle.setVnffgs(Set.of(vnffg01));
		//
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsdInstance = new NsdInstance();
		blueprint.setNsInstance(nsdInstance);
		blueprint.setId(UUID.randomUUID());
		final List<SclableResources<Object>> res = cc.contribute(bundle, blueprint);
		assertNotNull(res);
		assertEquals(5, res.size());
		TestUtils.assertNameNotNull(res);
	}

}
