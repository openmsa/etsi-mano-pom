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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;

@ExtendWith(MockitoExtension.class)
class NetworkContributorV3Test {
	@Mock
	private NsLiveInstanceJpa nsLiveInstanceJpa;

	@Test
	void testBasic() throws Exception {
		final NetworkContributorV3 nc = new NetworkContributorV3(nsLiveInstanceJpa);
		final NsdPackage bundle = new NsdPackage();
		bundle.setNsVirtualLinks(Set.of());
		final NsBlueprint blueprint = new NsBlueprint();
		final List<SclableResources<NsVirtualLinkTask>> res = nc.contribute(bundle, blueprint);
		assertNotNull(res);
		assertTrue(res.isEmpty());
	}

	@Test
	void testOne() throws Exception {
		final NetworkContributorV3 nc = new NetworkContributorV3(nsLiveInstanceJpa);
		final NsdPackage bundle = new NsdPackage();
		final NsVirtualLink vl01 = new NsVirtualLink();
		vl01.setToscaName("vl01");
		bundle.setNsVirtualLinks(Set.of(vl01));
		final NsBlueprint blueprint = new NsBlueprint();
		blueprint.setOperation(PlanOperationType.TERMINATE);
		final List<SclableResources<NsVirtualLinkTask>> res = nc.contribute(bundle, blueprint);
		assertNotNull(res);
		assertEquals(1, res.size());
		TestUtils.assertNameNotNull(res);
	}

}
