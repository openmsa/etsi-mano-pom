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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.vnfm.service.graph;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
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
import com.ubiqube.etsi.mano.service.ResourceTypeConverter;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.VnfPlanService;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfTaskJpa;
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
	@Mock
	private VnfTaskJpa vnfTaskJpa;
	@Mock
	ResourceTypeConverter resourceTypeConverter;

	@Test
	void testExecute() {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa, List.of());
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = createService(contributors);
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		blueprint.addExtManagedVirtualLinks(Set.of());
		final PreExecutionGraphV3<VnfTask> plan = new TestPreExecutionGraphV3();
		srv.execute(plan, blueprint);
		assertTrue(true);
	}

	private VnfWorkflow createService(final List<AbstractVnfmContributor<?>> contributors) {
		return new VnfWorkflow(planv2, vnfInstanceJpa, contributors, planService, blueprintBuilder, vnfPackageService, vnfTaskJpa, resourceTypeConverter);
	}

	@Test
	void testExecuteFull() {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa, List.of());
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = createService(contributors);
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
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa, List.of());
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = createService(contributors);
		final PreExecutionGraphV3<VnfTask> prePlan = new TestPreExecutionGraphV3();
		final Blueprint<VnfTask, ?> local = TestFactory.createBlueprint();
		srv.refresh(prePlan, local);
		assertTrue(true);
	}

	@Test
	void testRefresh2() {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa, List.of());
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = createService(contributors);
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
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa, List.of());
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = createService(contributors);
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

	@ParameterizedTest
	@EnumSource(value = ResourceTypeEnum.class, mode = Mode.INCLUDE, names = { "VL", "SUBNETWORK", "COMPUTE", "STORAGE", "SECURITY_GROUP", "LINKPORT", "VNF_EXTCP", "MONITORING", "AFFINITY_RULE", "OS_CONTAINER_INFO", "MCIOP_USER", "DNSHOST", "DNSZONE",
			"OS_CONTAINER", "OS_CONTAINER_DEPLOYABLE", "HELM", "VNF_INDICATOR" })
	void testSetWorkflowBlueprint(final ResourceTypeEnum param) {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa, List.of());
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = createService(contributors);
		final VnfPackage bundle = TestFactory.createVnfPkg(UUID.randomUUID());
		bundle.setVirtualLinks(new LinkedHashSet<>());
		final ListKeyPair lp = new ListKeyPair();
		lp.setIdx(0);
		bundle.getVirtualLinks().add(lp);
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		final VnfInstance inst = TestFactory.createVnfInstance();
		blueprint.setVnfInstance(inst);
		final ExtManagedVirtualLinkDataEntity extManagedVl = new ExtManagedVirtualLinkDataEntity();
		extManagedVl.setVnfVirtualLinkDescId("virtual_link");
		blueprint.getParameters().setExtManagedVirtualLinks(Set.of(extManagedVl));
		when(vnfInstanceJpa.findByVnfInstanceId(any())).thenReturn(List.of());
		when(vnfPackageService.findById(any())).thenReturn(bundle);
		final VnfLiveInstance l1 = new VnfLiveInstance();
		final VnfTask task = new VnfTask() {

			@Override
			public VnfTask copy() {
				return this;
			}
		};
		task.setType(param);
		l1.setTask(task);
		when(vnfInstanceJpa.findByVnfInstanceId(any())).thenReturn(List.of(l1));
		srv.setWorkflowBlueprint(bundle, blueprint);
		assertTrue(true);
	}

	@Test
	void testSetWorkflowBlueprintFail() {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa, List.of());
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = createService(contributors);
		final VnfPackage bundle = TestFactory.createVnfPkg(UUID.randomUUID());
		bundle.setVirtualLinks(new LinkedHashSet<>());
		final ListKeyPair lp = new ListKeyPair();
		lp.setIdx(0);
		bundle.getVirtualLinks().add(lp);
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		final VnfInstance inst = TestFactory.createVnfInstance();
		blueprint.setVnfInstance(inst);
		final ExtManagedVirtualLinkDataEntity extManagedVl = new ExtManagedVirtualLinkDataEntity();
		extManagedVl.setVnfVirtualLinkDescId("virtual_link");
		blueprint.getParameters().setExtManagedVirtualLinks(Set.of(extManagedVl));
		when(vnfInstanceJpa.findByVnfInstanceId(any())).thenReturn(List.of());
		when(vnfPackageService.findById(any())).thenReturn(bundle);
		final VnfLiveInstance l1 = new VnfLiveInstance();
		final VnfTask task = new VnfTask() {

			@Override
			public VnfTask copy() {
				return this;
			}
		};
		task.setType(ResourceTypeEnum.NSD_CREATE);
		l1.setTask(task);
		when(vnfInstanceJpa.findByVnfInstanceId(any())).thenReturn(List.of(l1));
		assertThrows(GenericException.class, () -> srv.setWorkflowBlueprint(bundle, blueprint));
		assertTrue(true);
	}

	@Test
	void testSetWorkflowBlueprintSimple() {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa, List.of());
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = createService(contributors);
		final VnfPackage bundle = TestFactory.createVnfPkg(UUID.randomUUID());
		bundle.setVirtualLinks(new LinkedHashSet<>());
		final ListKeyPair lp = new ListKeyPair();
		lp.setIdx(0);
		bundle.getVirtualLinks().add(lp);
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		final VnfInstance inst = TestFactory.createVnfInstance();
		blueprint.setVnfInstance(inst);
		final ExtManagedVirtualLinkDataEntity extManagedVl = new ExtManagedVirtualLinkDataEntity();
		extManagedVl.setVnfVirtualLinkDescId("virtual_link");
		blueprint.getParameters().setExtManagedVirtualLinks(Set.of(extManagedVl));
		when(vnfInstanceJpa.findByVnfInstanceId(any())).thenReturn(List.of());
		when(vnfPackageService.findById(any())).thenReturn(bundle);
		srv.setWorkflowBlueprint(bundle, blueprint);
		assertTrue(true);
	}

	@Test
	void testSetWorkflowBlueprintSimpleVl() {
		final TestVnfmContributor tc = new TestVnfmContributor(vnfInstanceJpa, List.of());
		final List<AbstractVnfmContributor<?>> contributors = List.of(tc);
		final VnfWorkflow srv = createService(contributors);
		final VnfPackage bundle = TestFactory.createVnfPkg(UUID.randomUUID());
		bundle.setVirtualLinks(new LinkedHashSet<>());
		final ListKeyPair lp = new ListKeyPair();
		lp.setIdx(5);
		bundle.getVirtualLinks().add(lp);
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		final VnfInstance inst = TestFactory.createVnfInstance();
		blueprint.setVnfInstance(inst);
		final ExtManagedVirtualLinkDataEntity extManagedVl = new ExtManagedVirtualLinkDataEntity();
		extManagedVl.setVnfVirtualLinkDescId("virtual_link_5");
		blueprint.getParameters().setExtManagedVirtualLinks(Set.of(extManagedVl));
		when(vnfInstanceJpa.findByVnfInstanceId(any())).thenReturn(List.of());
		when(vnfPackageService.findById(any())).thenReturn(bundle);
		srv.setWorkflowBlueprint(bundle, blueprint);
		assertTrue(true);
	}
}