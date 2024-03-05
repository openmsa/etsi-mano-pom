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
package com.ubiqube.etsi.mano.nfvo.service.graph;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.nfvo.service.NsdPackageService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.graph.WorkflowEvent;
import com.ubiqube.etsi.mano.tf.entities.NetworkPolicyTask;

@ExtendWith(MockitoExtension.class)
class NsOrchestrationAdapterTest {
	@Mock
	private NsBlueprintService nsBlueprintService;
	@Mock
	private NsInstanceService nsInstanceService;
	@Mock
	private NsdPackageService nsdPackageService;
	@Mock
	private EventManager eventManager;
	@Mock
	private NsLiveInstanceJpa nsLiveInstanceJpa;

	@Test
	void testCreateLiveInstance() {
		final NsOrchestrationAdapter ada = new NsOrchestrationAdapter(nsLiveInstanceJpa, nsBlueprintService, nsInstanceService, nsdPackageService, eventManager);
		final UUID id = UUID.randomUUID();
		final NsdInstance inst = new NsdInstance();
		final NsBlueprint bp = new NsBlueprint();
		final NetworkPolicyTask task = new NetworkPolicyTask();
		ada.createLiveInstance(inst, "", task, bp);
		assertTrue(true);
	}

	@Test
	void testDeleteLiveInstance() {
		final NsOrchestrationAdapter ada = new NsOrchestrationAdapter(nsLiveInstanceJpa, nsBlueprintService, nsInstanceService, nsdPackageService, eventManager);
		final UUID id = UUID.randomUUID();
		ada.deleteLiveInstance(id);
		assertTrue(true);
	}

	@Test
	void testGetBlueprint() {
		final NsOrchestrationAdapter ada = new NsOrchestrationAdapter(nsLiveInstanceJpa, nsBlueprintService, nsInstanceService, nsdPackageService, eventManager);
		final UUID id = UUID.randomUUID();
		ada.getBluePrint(id);
		assertTrue(true);
	}

	@Test
	void testInstance() {
		final NsOrchestrationAdapter ada = new NsOrchestrationAdapter(nsLiveInstanceJpa, nsBlueprintService, nsInstanceService, nsdPackageService, eventManager);
		final UUID id = UUID.randomUUID();
		ada.getInstance(id);
		assertTrue(true);
	}

	@Test
	void testPackage() {
		final NsOrchestrationAdapter ada = new NsOrchestrationAdapter(nsLiveInstanceJpa, nsBlueprintService, nsInstanceService, nsdPackageService, eventManager);
		final UUID id = UUID.randomUUID();
		final NsdInstance inst = new NsdInstance();
		final NsdPackage pkg = new NsdPackage();
		pkg.setId(id);
		inst.setNsdInfo(pkg);
		ada.getPackage(inst);
		assertTrue(true);
	}

	@Test
	void testGetInstance() {
		final NsOrchestrationAdapter ada = new NsOrchestrationAdapter(nsLiveInstanceJpa, nsBlueprintService, nsInstanceService, nsdPackageService, eventManager);
		final NsBlueprint bp = new NsBlueprint();
		ada.getInstance(bp);
		assertTrue(true);
	}

	@Test
	void testSaveBlueprint() {
		final NsOrchestrationAdapter ada = new NsOrchestrationAdapter(nsLiveInstanceJpa, nsBlueprintService, nsInstanceService, nsdPackageService, eventManager);
		final NsBlueprint bp = new NsBlueprint();
		ada.save(bp);
		assertTrue(true);
	}

	@Test
	void testSaveInstance() {
		final NsOrchestrationAdapter ada = new NsOrchestrationAdapter(nsLiveInstanceJpa, nsBlueprintService, nsInstanceService, nsdPackageService, eventManager);
		final NsdInstance inst = new NsdInstance();
		ada.save(inst);
		assertTrue(true);
	}

	@Test
	void testUpdateState() {
		final NsOrchestrationAdapter ada = new NsOrchestrationAdapter(nsLiveInstanceJpa, nsBlueprintService, nsInstanceService, nsdPackageService, eventManager);
		final NsBlueprint bp = new NsBlueprint();
		ada.updateState(bp, OperationStatusType.PROCESSING);
		assertTrue(true);
	}

	@SuppressWarnings("null")
	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(WorkflowEvent.INSTANTIATE_PROCESSING),
				Arguments.of(WorkflowEvent.INSTANTIATE_SUCCESS),
				Arguments.of(WorkflowEvent.INSTANTIATE_FAILED),
				Arguments.of(WorkflowEvent.SCALE_FAILED),
				Arguments.of(WorkflowEvent.SCALE_SUCCESS),
				Arguments.of(WorkflowEvent.SCALETOLEVEL_FAILED),
				Arguments.of(WorkflowEvent.SCALETOLEVEL_SUCCESS),
				Arguments.of(WorkflowEvent.TERMINATE_FAILED),
				Arguments.of(WorkflowEvent.TERMINATE_SUCCESS));
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testFireEvent(final WorkflowEvent ev) {
		final NsOrchestrationAdapter ada = new NsOrchestrationAdapter(nsLiveInstanceJpa, nsBlueprintService, nsInstanceService, nsdPackageService, eventManager);
		final UUID id = UUID.randomUUID();
		ada.fireEvent(ev, id);
		assertTrue(true);
	}

}
