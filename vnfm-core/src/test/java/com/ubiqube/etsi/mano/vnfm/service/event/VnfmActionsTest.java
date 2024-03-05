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
package com.ubiqube.etsi.mano.vnfm.service.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.OperationalStateType;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.service.VimResourceService;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vim.dummy.DummyVim;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfBlueprintService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;
import com.ubiqube.etsi.mano.vnfm.service.graph.VnfWorkflow;

@ExtendWith(MockitoExtension.class)
class VnfmActionsTest {
	@Mock
	private VimManager vimManager;
	@Mock
	private VnfOrchestrationAdapter orchestrationAdapter;
	@Mock
	private VnfInstanceService vnfInstanceService;
	@Mock
	private VnfBlueprintService blueprintService;
	@Mock
	private VimResourceService vimResourceService;
	@Mock
	private VnfLiveInstanceJpa vnfLiveInstance;
	@Mock
	private VnfInstanceServiceVnfm vnfInstanceServiceVnfm;
	@Mock
	private VnfWorkflow workflow;
	@Mock
	private Planner<Task> planner;

	private VnfmActions createService() {
		return new VnfmActions(vimManager, orchestrationAdapter, vnfInstanceService, blueprintService, vimResourceService, vnfLiveInstance, vnfInstanceServiceVnfm, workflow, planner);
	}

	@Test
	void test() {
		final VnfmActions srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfBlueprint value = new VnfBlueprint();
		final VnfInstance vnfInst = new VnfInstance();
		value.setVnfInstance(vnfInst);
		when(orchestrationAdapter.getInstance((UUID) any())).thenReturn(vnfInst);
		// Error
		when(orchestrationAdapter.getBluePrint(any())).thenReturn(value);
		srv.instantiate(id);
		assertNotNull(value.getError());
		assertEquals(OperationStatusType.FAILED, value.getOperationStatus());
	}

	@Test
	void test_Instantiate() {
		final VnfmActions srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfBlueprint value = TestFactory.createBlueprint();
		final VnfInstance vnfInst = TestFactory.createVnfInstance();
		value.setVnfInstance(vnfInst);
		when(orchestrationAdapter.getInstance((UUID) any())).thenReturn(vnfInst);
		// Error
		when(orchestrationAdapter.getBluePrint(any())).thenReturn(value);
		// instantiate
		value.getParameters().setScaleStatus(Set.of());
		value.setOperation(PlanOperationType.INSTANTIATE);
		value.getParameters().setNsStepStatus(Set.of());
		value.getParameters().setScaleStatus(Set.of());
		vnfInst.getInstantiatedVnfInfo().setScaleStatus(Set.of());
		when(orchestrationAdapter.save(value)).thenReturn(value);
		when(orchestrationAdapter.updateState(value, OperationStatusType.PROCESSING)).thenReturn(value);
		final OrchExecutionResults<VnfTask> res = new TestOrchestrationResults();
		when(workflow.execute(any(), any())).thenReturn(res);
		srv.instantiate(id);
		assertNull(value.getError());
	}

	@Test
	void testOperateStop() {
		final VnfmActions srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		final VnfTask vnfTask = new ComputeTask();
		blueprint.addTask(vnfTask);
		final VnfInstance inst = TestFactory.createVnfInstance();
		final VimConnectionInformation vimconn = new VimConnectionInformation();
		inst.addVimConnectionInfo(vimconn);
		blueprint.setVnfInstance(inst);
		when(blueprintService.findById(id)).thenReturn(blueprint);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(inst);
		final Vim vim = new DummyVim();
		when(vimManager.getVimById(any())).thenReturn(vim);
		srv.vnfOperate(id);
		assertEquals(OperationStatusType.COMPLETED, blueprint.getOperationStatus());
	}

	@Test
	void testOperateStart() {
		final VnfmActions srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		final VnfTask vnfTask = new ComputeTask();
		blueprint.addTask(vnfTask);
		blueprint.getOperateChanges().setTerminationType(OperationalStateType.STARTED);
		final VnfInstance inst = TestFactory.createVnfInstance();
		final VimConnectionInformation vimconn = new VimConnectionInformation();
		inst.addVimConnectionInfo(vimconn);
		blueprint.setVnfInstance(inst);
		when(blueprintService.findById(id)).thenReturn(blueprint);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(inst);
		final Vim vim = new DummyVim();
		when(vimManager.getVimById(any())).thenReturn(vim);
		srv.vnfOperate(id);
		assertEquals(OperationStatusType.COMPLETED, blueprint.getOperationStatus());
	}

	@Test
	void testOperateEmptyTasks() {
		final VnfmActions srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		final VnfInstance inst = TestFactory.createVnfInstance();
		final VimConnectionInformation vimconn = new VimConnectionInformation();
		inst.addVimConnectionInfo(vimconn);
		blueprint.setVnfInstance(inst);
		when(blueprintService.findById(id)).thenReturn(blueprint);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(inst);
		srv.vnfOperate(id);
		assertEquals(OperationStatusType.COMPLETED, blueprint.getOperationStatus());
	}

	@Test
	void testOperateFailed() {
		final VnfmActions srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		final VnfInstance inst = new VnfInstance();
		blueprint.setVnfInstance(inst);
		when(blueprintService.findById(id)).thenReturn(blueprint);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(inst);
		srv.vnfOperate(id);
		assertEquals(OperationStatusType.FAILED, blueprint.getOperationStatus());
	}

	@Test
	void testVnfHeal() {
		final VnfmActions srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		final VnfInstance inst = TestFactory.createVnfInstance();
		blueprint.setVnfInstance(inst);
		final VnfTask vnfTask = new ComputeTask();
		blueprint.addTask(vnfTask);
		blueprint.getParameters().setInstantiationLevelId("");
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		inst.addVimConnectionInfo(vimConn);
		when(blueprintService.findById(id)).thenReturn(blueprint);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(inst);
		final Vim vim = new DummyVim();
		when(vimManager.getVimById(any())).thenReturn(vim);
		srv.vnfHeal(id);
		assertEquals(OperationStatusType.COMPLETED, blueprint.getOperationStatus());
	}

	@Test
	void testVnfHealFailed() {
		final VnfmActions srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		final VnfInstance inst = TestFactory.createVnfInstance();
		blueprint.setVnfInstance(inst);
		final VnfTask vnfTask = new ComputeTask();
		blueprint.addTask(vnfTask);
		blueprint.getParameters().setInstantiationLevelId("");
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		inst.addVimConnectionInfo(vimConn);
		when(blueprintService.findById(id)).thenReturn(blueprint);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(inst);
		srv.vnfHeal(id);
		assertEquals(OperationStatusType.FAILED, blueprint.getOperationStatus());
	}
}
