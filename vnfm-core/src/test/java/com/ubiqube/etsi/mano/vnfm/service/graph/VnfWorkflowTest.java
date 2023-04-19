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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.ExecutionGraph;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.orchestrator.v3.BlueprintBuilder;
import com.ubiqube.etsi.mano.orchestrator.v3.PreExecutionGraphV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.VnfPlanService;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.AbstractVnfmContributor;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.HelmVt;

@ExtendWith(MockitoExtension.class)
class VnfWorkflowTest {
	@Mock
	private Planner<VnfTask> planv2;
	@Mock
	private VnfLiveInstanceJpa vnfInstanceJpa;
	@Mock
	private VnfPlanService planService;
	@Mock
	private BlueprintBuilder blueprintBuilder;
	@Mock
	private VnfPackageService vnfPackageService;

	@Test
	void testExecute() {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa);
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = new VnfWorkflow(planv2, vnfInstanceJpa, contributors, planService, blueprintBuilder, vnfPackageService);
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		blueprint.addExtManagedVirtualLinks(Set.of());
		final PreExecutionGraphV3<VnfTask> plan = new TestPreExecutionGraphV3();
		srv.execute(plan, blueprint);
		assertTrue(true);
	}

	@Test
	void testExecuteFull() {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa);
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = new VnfWorkflow(planv2, vnfInstanceJpa, contributors, planService, blueprintBuilder, vnfPackageService);
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		final ExtManagedVirtualLinkDataEntity ext = new ExtManagedVirtualLinkDataEntity();
		blueprint.addExtManagedVirtualLinks(Set.of(ext));
		final PreExecutionGraphV3<VnfTask> plan = new TestPreExecutionGraphV3();
		final ExecutionGraph graph = new TestExecutionGraph();
		when(planv2.implement(plan)).thenReturn(graph);
		srv.execute(plan, blueprint);
		assertTrue(true);
	}

	@Test
	void testRefresh() {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa);
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = new VnfWorkflow(planv2, vnfInstanceJpa, contributors, planService, blueprintBuilder, vnfPackageService);
		final PreExecutionGraphV3<VnfTask> prePlan = new TestPreExecutionGraphV3();
		final Blueprint<VnfTask, ?> local = TestFactory.createBlueprint();
		srv.refresh(prePlan, local);
		assertTrue(true);
	}

	@Test
	void testRefresh2() {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa);
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = new VnfWorkflow(planv2, vnfInstanceJpa, contributors, planService, blueprintBuilder, vnfPackageService);
		final HelmTask nt = new HelmTask();
		final VirtualTaskV3<HelmTask> h = new HelmVt(nt);
		final List list = new ArrayList<>();
		list.add(h);
		final PreExecutionGraphV3<VnfTask> prePlan = new TestPreExecutionGraphV3(list);
		final Blueprint<VnfTask, ?> local = TestFactory.createBlueprint();
		assertThrows(GenericException.class, () -> srv.refresh(prePlan, local));
	}

	@Test
	void testRefresh3() {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa);
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = new VnfWorkflow(planv2, vnfInstanceJpa, contributors, planService, blueprintBuilder, vnfPackageService);
		final HelmTask nt = new HelmTask();
		nt.setToscaName("helm");
		final VirtualTaskV3<HelmTask> h = new HelmVt(nt);

		final List list = new ArrayList<>();
		list.add(h);
		final PreExecutionGraphV3<VnfTask> prePlan = new TestPreExecutionGraphV3(list);
		final Blueprint<VnfTask, ?> local = TestFactory.createBlueprint();
		local.addTask(nt);
		srv.refresh(prePlan, local);
		assertTrue(true);
	}
}