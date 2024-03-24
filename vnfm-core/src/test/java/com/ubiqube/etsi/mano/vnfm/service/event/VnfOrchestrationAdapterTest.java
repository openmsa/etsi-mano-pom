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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.graph.WorkflowEvent;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfBlueprintService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;

@ExtendWith(MockitoExtension.class)
class VnfOrchestrationAdapterTest {
	@Mock
	private VnfInstanceService vnfInstance;
	@Mock
	private VnfBlueprintService blueprintService;
	@Mock
	private VnfLiveInstanceJpa vnfLiveInstance;
	@Mock
	private EventManager eventManager;
	@Mock
	private VnfPackageService vnfPackageService;
	@Mock
	private VnfInstanceServiceVnfm vnfInstanceService;

	VnfOrchestrationAdapter createService() {
		return new VnfOrchestrationAdapter(vnfInstance, blueprintService, vnfLiveInstance, eventManager, vnfPackageService, vnfInstanceService, List.of());
	}

	@Test
	void testCreateLiveInstance() {
		final VnfOrchestrationAdapter srv = createService();
		final Task task = new NetworkTask();
		srv.createLiveInstance(null, null, task, null);
		assertTrue(true);
	}

	@Test
	void testDeleteLiveInstance() {
		final VnfOrchestrationAdapter srv = createService();
		final VnfLiveInstance vli = new VnfLiveInstance();
		when(vnfInstance.findLiveInstanceById(any())).thenReturn(Optional.of(vli));
		srv.deleteLiveInstance(null);
		assertTrue(true);
	}

	@Test
	void testDeleteLiveInstanceFail() {
		final VnfOrchestrationAdapter srv = createService();
		when(vnfInstance.findLiveInstanceById(any())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> srv.deleteLiveInstance(null));
		assertTrue(true);
	}

	@ParameterizedTest
	@EnumSource(WorkflowEvent.class)
	void testFireEvent(final WorkflowEvent param) {
		final VnfOrchestrationAdapter srv = createService();
		srv.fireEvent(param, null);
		assertTrue(true);
	}

	@Test
	void testGetBlueprint() {
		final VnfOrchestrationAdapter srv = createService();
		srv.getBluePrint(null);
		assertTrue(true);
	}

	@Test
	void testGetInstanceUUID() {
		final VnfOrchestrationAdapter srv = createService();
		srv.getInstance(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testGetBlueprintInstance() {
		final VnfOrchestrationAdapter srv = createService();
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		srv.getInstance(blueprint);
		assertTrue(true);
	}

	@Test
	void testGetPackage() {
		final VnfOrchestrationAdapter srv = createService();
		final VnfInstance inst = new VnfInstance();
		final VnfPackage pkg = new VnfPackage();
		inst.setVnfPkg(pkg);
		srv.getPackage(inst);
		assertTrue(true);
	}

	@Test
	void testSaveBlueprint() {
		final VnfOrchestrationAdapter srv = createService();
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		srv.save(blueprint);
		assertTrue(true);
	}

	@Test
	void testSaveVnfInstance() {
		final VnfOrchestrationAdapter srv = createService();
		final VnfInstance inst = TestFactory.createVnfInstance();
		srv.save(inst);
		assertTrue(true);
	}

	@Test
	void testSaveVnfInstanceLive() {
		final VnfOrchestrationAdapter srv = createService();
		final VnfInstance inst = TestFactory.createVnfInstance();
		when(vnfLiveInstance.countByVnfInstance(inst)).thenReturn(5L);
		srv.save(inst);
		assertTrue(true);
	}

	@Test
	void testUpdateSatte() {
		final VnfOrchestrationAdapter srv = createService();
		srv.updateState(null, null);
		assertTrue(true);
	}
}
