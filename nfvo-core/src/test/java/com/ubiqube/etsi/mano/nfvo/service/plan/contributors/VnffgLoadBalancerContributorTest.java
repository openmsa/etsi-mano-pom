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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgLoadbalancerTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPortPairTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsBundleAdapter;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.VnffgLoadbalancerVt;
import com.ubiqube.etsi.mano.orchestrator.Bundle;

/**
 *
 * @author olivier
 *
 */
@ExtendWith(MockitoExtension.class)
class VnffgLoadBalancerContributorTest {
	@Mock
	NsLiveInstanceJpa nsLiveInstanceJpa;

	@Test
	void testMinimalInvoke() throws Exception {
		final VnffgLoadBalancerContributor cont = new VnffgLoadBalancerContributor(nsLiveInstanceJpa);
		final Bundle bundle = new NsBundleAdapter(NsdFactory.createPackage());
		final NsBlueprint blueprint = new NsBlueprint();
		blueprint.setOperation(PlanOperationType.INSTANTIATE);
		blueprint.setTasks(new LinkedHashSet<>());
		final List<VnffgLoadbalancerVt> res = cont.contribute(bundle, blueprint);
		assertEquals(0, res.size());
	}

	@Test
	void testInvoke001() throws Exception {
		final VnffgLoadBalancerContributor cont = new VnffgLoadBalancerContributor(nsLiveInstanceJpa);
		final Bundle bundle = new NsBundleAdapter(NsdFactory.createPackage());
		final NsBlueprint blueprint = new NsBlueprint();
		blueprint.setOperation(PlanOperationType.INSTANTIATE);
		final Set<NsTask> tasks = new LinkedHashSet<>();
		final NsTask vnf01 = NsdFactory.createVnf01();
		tasks.add(vnf01);
		final NsTask pp01 = NsdFactory.createPortPair();
		tasks.add(pp01);
		blueprint.setTasks(tasks);
		final List<VnffgLoadbalancerVt> res = cont.contribute(bundle, blueprint);
		assertEquals(1, res.size());
		final VnffgLoadbalancerVt o = res.get(0);
		assertEquals("nfp_position_1", o.getName());
		final VnffgLoadbalancerTask t = o.getParameters();
		assertEquals("nfp_position_1", t.getAlias());
		assertEquals("nfp_position_1", t.getToscaName());
	}

	@Test
	void testScaling01() {
		final VnffgLoadBalancerContributor cont = new VnffgLoadBalancerContributor(nsLiveInstanceJpa);
		final Bundle bundle = new NsBundleAdapter(NsdFactory.createPackage());
		final NsBlueprint blueprint = new NsBlueprint();
		blueprint.setOperation(PlanOperationType.SCALE);
		final Set<NsTask> tasks = new LinkedHashSet<>();
		final NsTask vnf01 = NsdFactory.createVnf01();
		tasks.add(vnf01);
		final NsTask pp01 = NsdFactory.createPortPair();
		tasks.add(pp01);
		final NsTask vnf02 = NsdFactory.createVnf01();
		vnf02.setChangeType(ChangeType.REMOVED);
		tasks.add(vnf02);
		final NsTask pp02 = NsdFactory.createPortPair();
		pp02.setChangeType(ChangeType.REMOVED);
		tasks.add(pp02);
		blueprint.setTasks(tasks);
		final List<NsLiveInstance> liveInstance = new ArrayList<>();
		final NsLiveInstance l01 = new NsLiveInstance();
		l01.setNsTask(pp02);
		liveInstance.add(l01);
		final List<NsLiveInstance> liveInstance02 = new ArrayList<>();
		final NsLiveInstance l02 = new NsLiveInstance();
		l02.setNsTask(NsdFactory.createLoadbalancer());
		liveInstance02.add(l02);
		when(nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(any(), any(), eq(VnffgPortPairTask.class.getSimpleName()))).thenReturn(liveInstance);
		when(nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(any(), any(), eq(VnffgLoadbalancerTask.class.getSimpleName()))).thenReturn(liveInstance02);
		final List<VnffgLoadbalancerVt> res = cont.contribute(bundle, blueprint);
		assertEquals(1, res.size());
		final VnffgLoadbalancerVt o = res.get(0);
		assertEquals("nfp_position_1", o.getName());
		final VnffgLoadbalancerTask t = o.getParameters();
		assertEquals("nfp_position_1", t.getAlias());
		assertEquals("nfp_position_1", t.getToscaName());
		assertTrue(t.isRemove());
	}

	@Test
	void testScalingDontDelete() {
		final VnffgLoadBalancerContributor cont = new VnffgLoadBalancerContributor(nsLiveInstanceJpa);
		final Bundle bundle = new NsBundleAdapter(NsdFactory.createPackage());
		final NsBlueprint blueprint = new NsBlueprint();
		blueprint.setOperation(PlanOperationType.SCALE);
		final Set<NsTask> tasks = new LinkedHashSet<>();
		final NsTask vnf01 = NsdFactory.createVnf01();
		tasks.add(vnf01);

		final NsTask pp01 = NsdFactory.createPortPair();
		tasks.add(pp01);

		final NsTask vnf02 = NsdFactory.createVnf01();
		vnf02.setChangeType(ChangeType.REMOVED);
		tasks.add(vnf02);

		final NsTask pp02 = NsdFactory.createPortPair();
		pp02.setChangeType(ChangeType.REMOVED);
		tasks.add(pp02);
		blueprint.setTasks(tasks);

		final List<NsLiveInstance> liveInstance = new ArrayList<>();
		final NsLiveInstance nl01 = new NsLiveInstance();
		nl01.setNsTask(pp02);
		liveInstance.add(nl01);

		final NsLiveInstance nl02 = new NsLiveInstance();
		nl02.setNsTask(pp02);
		liveInstance.add(nl02);

		final List<NsLiveInstance> liveInstance02 = new ArrayList<>();
		final NsLiveInstance l02 = new NsLiveInstance();
		l02.setNsTask(NsdFactory.createLoadbalancer());
		liveInstance02.add(l02);
		when(nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(any(), any(), eq(VnffgPortPairTask.class.getSimpleName()))).thenReturn(liveInstance);
		when(nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(any(), any(), eq(VnffgLoadbalancerTask.class.getSimpleName()))).thenReturn(liveInstance02);
		final List<VnffgLoadbalancerVt> res = cont.contribute(bundle, blueprint);
		assertEquals(1, res.size());
		final VnffgLoadbalancerVt o = res.get(0);
		assertEquals("nfp_position_1", o.getName());
		final VnffgLoadbalancerTask t = o.getParameters();
		assertEquals("nfp_position_1", t.getAlias());
		assertEquals("nfp_position_1", t.getToscaName());
		assertFalse(t.isRemove());
	}
}
