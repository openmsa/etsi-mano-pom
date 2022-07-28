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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPortPairTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsBundleAdapter;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnffgPortPairVt;
import com.ubiqube.etsi.mano.orchestrator.Bundle;
import com.ubiqube.etsi.mano.orchestrator.NamingStrategyImpl;
import com.ubiqube.etsi.mano.orchestrator.OrchestratorNamingStrategy;

@ExtendWith(MockitoExtension.class)
class VnfPortPairContributorTest {
	@Mock
	NsLiveInstanceJpa nsLiveInstanceJpa;

	OrchestratorNamingStrategy namingStrategy = new NamingStrategyImpl();

	@Test
	void testMinimalInvoke() throws Exception {
		final VnffgPortPairContributor cont = new VnffgPortPairContributor(nsLiveInstanceJpa, namingStrategy);
		final Bundle bundle;
		final NsBlueprint blueprint = new NsBlueprint();
		blueprint.setOperation(PlanOperationType.INSTANTIATE);
		blueprint.setTasks(Set.of());
		final List<NsVnffgPortPairVt> res = cont.contribute(null, blueprint);
		assertEquals(0, res.size());
	}

	@Test
	void testInvoke001() throws Exception {
		final VnffgPortPairContributor cont = new VnffgPortPairContributor(nsLiveInstanceJpa, namingStrategy);
		final Bundle bundle = new NsBundleAdapter(NsdFactory.createPackage());
		final NsBlueprint blueprint = new NsBlueprint();
		blueprint.setOperation(PlanOperationType.INSTANTIATE);
		final Set<NsTask> tasks = new LinkedHashSet<>();
		final NsTask vnf01 = NsdFactory.createVnf01();
		tasks.add(vnf01);
		blueprint.setTasks(tasks);
		final List<NsVnffgPortPairVt> res = cont.contribute(bundle, blueprint);
		assertEquals(1, res.size());
		final NsVnffgPortPairVt o = res.get(0);
		assertEquals("element_1", o.getName());
		final VnffgPortPairTask t = o.getParameters();
		assertEquals("pp-element_1-0001", t.getAlias());
		assertEquals("element_1", t.getToscaName());
	}

	@Test
	void testScaling001() throws Exception {
		final VnffgPortPairContributor cont = new VnffgPortPairContributor(nsLiveInstanceJpa, namingStrategy);
		final Bundle bundle = new NsBundleAdapter(NsdFactory.createPackage());
		final NsBlueprint blueprint = new NsBlueprint();
		blueprint.setOperation(PlanOperationType.SCALE);
		final Set<NsTask> tasks = new LinkedHashSet<>();
		final NsTask vnf01 = NsdFactory.createVnf01();
		tasks.add(vnf01);
		blueprint.setTasks(tasks);

		final List<NsLiveInstance> liveInstance = new ArrayList<>();
		final NsLiveInstance l01 = new NsLiveInstance();
		l01.setNsTask(NsdFactory.createVnf01());
		liveInstance.add(l01);
		when(nsLiveInstanceJpa.findByNsdInstanceAndClass(any(), any())).thenReturn(new ArrayList<>());

		blueprint.setNsInstance(NsdFactory.createNsInstance());
		final List<NsVnffgPortPairVt> res = cont.contribute(bundle, blueprint);
		assertEquals(1, res.size());
		final NsVnffgPortPairVt o = res.get(0);
		assertEquals("element_1", o.getName());
		final VnffgPortPairTask t = o.getParameters();
		assertEquals("pp-element_1-0001", t.getAlias());
		assertEquals("element_1", t.getToscaName());
	}
}
