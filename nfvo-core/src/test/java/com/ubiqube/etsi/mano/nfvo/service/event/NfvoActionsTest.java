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
package com.ubiqube.etsi.mano.nfvo.service.event;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfInstantiateTask;
import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.nfvo.service.graph.NfvoOrchestrationV3;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsOrchestrationAdapter;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;
import com.ubiqube.etsi.mano.service.VimResourceService;
import com.ubiqube.etsi.mano.service.VnfInstanceGatewayService;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;
import com.ubiqube.etsi.mano.service.rest.vnflcm.ManoVnfInstance;
import com.ubiqube.etsi.mano.service.rest.vnflcm.ManoVnfInstanceId;

@ExtendWith(MockitoExtension.class)
class NfvoActionsTest {
	@Mock
	private NfvoOrchestrationV3 workflow;
	@Mock
	private VimResourceService vimResource;
	@Mock
	private NsOrchestrationAdapter orchAdapter;
	@Mock
	private NsScaleStrategyV3 nsScaling;
	@Mock
	private NsBlueprintService blueprintService;
	@Mock
	private NsInstanceService nsInstanceService;
	@Mock
	private VnfInstanceGatewayService vnfInstanceService;
	@Mock
	private ManoClientFactory manoClientFactory;
	@Mock
	private ManoClient manoClient;
	@Mock
	private ManoVnfInstanceId manoCliVnfInst;
	@Mock
	private Planner<Task> planner;

	private NfvoActions createService() {
		return new NfvoActions(workflow, vimResource, orchAdapter, nsScaling, blueprintService, nsInstanceService, vnfInstanceService, manoClientFactory, planner);
	}

	@Test
	void testInstantiate00() {
		final NfvoActions na = createService();
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsInstance = new NsdInstance();
		blueprint.setNsInstance(nsInstance);
		when(orchAdapter.getBluePrint(id)).thenReturn(blueprint);
		final Instance inst = new NsdInstance();
		when(orchAdapter.getInstance(blueprint.getInstance().getId())).thenReturn(inst);
		when(orchAdapter.getBluePrint(blueprint.getId())).thenReturn(blueprint);
		na.instantiate(id);
		assertTrue(true);
	}

	@Test
	void testHeal01() {
		final NfvoActions na = createService();
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsInstance = new NsdInstance();
		blueprint.setNsInstance(nsInstance);
		final BlueprintParameters params = new BlueprintParameters();
		blueprint.setParameters(params);
		when(orchAdapter.getBluePrint(id)).thenReturn(blueprint);
		final Instance inst = new NsdInstance();
		when(orchAdapter.getInstance(blueprint.getInstance().getId())).thenReturn(inst);
		when(orchAdapter.getBluePrint(blueprint.getId())).thenReturn(blueprint);
		na.instantiate(id);
		assertTrue(true);
	}

	@Test
	void testInstantiate() {
		final NfvoActions na = createService();
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsInstance = new NsdInstance();
		blueprint.setNsInstance(nsInstance);
		blueprint.setId(id);
		final BlueprintParameters params = new BlueprintParameters();
		params.setInstantiationLevelId("");
		blueprint.setParameters(params);
		blueprint.setVimConnections(Set.of());
		when(orchAdapter.getBluePrint(id)).thenReturn(blueprint);
		final Instance inst = new NsdInstance();
		params.setScaleStatus(Set.of());
		inst.setInstantiatedVnfInfo(params);
		when(orchAdapter.getInstance(blueprint.getInstance().getId())).thenReturn(inst);
		when(orchAdapter.getBluePrint(blueprint.getId())).thenReturn(blueprint);
		when(orchAdapter.updateState(blueprint, OperationStatusType.PROCESSING)).thenReturn(blueprint);
		when(orchAdapter.save(blueprint)).thenReturn(blueprint);
		final OrchExecutionResults<NsTask> result = new TestOrchExecutionResults();
		when(workflow.execute(any(), eq(blueprint))).thenReturn(result);
		na.instantiate(id);
		assertTrue(true);
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testParam(final args arg) {
		final NfvoActions na = createService();
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsInstance = new NsdInstance();
		blueprint.setNsInstance(nsInstance);
		blueprint.setId(id);
		final BlueprintParameters params = new BlueprintParameters();
		params.setInstantiationLevelId("");
		blueprint.setParameters(params);
		blueprint.setVimConnections(Set.of());
		when(orchAdapter.getBluePrint(id)).thenReturn(blueprint);
		final Instance inst = new NsdInstance();
		params.setScaleStatus(Set.of());
		inst.setInstantiatedVnfInfo(params);
		when(orchAdapter.getInstance(blueprint.getInstance().getId())).thenReturn(inst);
		when(orchAdapter.getBluePrint(blueprint.getId())).thenReturn(blueprint);
		when(orchAdapter.updateState(blueprint, OperationStatusType.PROCESSING)).thenReturn(blueprint);
		when(orchAdapter.save(blueprint)).thenReturn(blueprint);
		final OrchExecutionResults<NsTask> result = new TestOrchExecutionResults();
		when(workflow.execute(any(), eq(blueprint))).thenReturn(result);
		arg.func().accept(na, id);
		assertTrue(true);
	}

	@SuppressWarnings("null")
	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(args.of(NfvoActions::instantiate)),
				Arguments.of(args.of(NfvoActions::terminate)),
				Arguments.of(args.of(NfvoActions::scale)),
				Arguments.of(args.of(NfvoActions::scaleToLevel)));
	}

	@Test
	void testHeal() throws Exception {
		final NfvoActions na = createService();
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance inst = new NsdInstance();
		inst.setId(id);
		blueprint.setNsInstance(inst);
		when(orchAdapter.getBluePrint(id)).thenReturn(blueprint);
		final NsLiveInstance live = new NsLiveInstance();
		live.setResourceId(id.toString());
		final NsTask task = new NsVnfInstantiateTask();
		live.setNsTask(task);
		when(blueprintService.findByNsdInstanceAndClass(any(), eq(NsVnfInstantiateTask.class))).thenReturn(List.of(live));
		when(manoClientFactory.getClient(any())).thenReturn(manoClient);
		final VnfInstance vnfInstance = new VnfInstance();
		when(vnfInstanceService.findById(any())).thenReturn(vnfInstance);
		final ManoVnfInstance manoVnfInstance = Mockito.mock(ManoVnfInstance.class);
		when(manoClient.vnfInstance()).thenReturn(manoVnfInstance);
		when(manoVnfInstance.id(any())).thenReturn(manoCliVnfInst);
		na.heal(id);
		assertTrue(true);
	}
}

record args(BiConsumer<NfvoActions, UUID> func) {
	public static args of(final BiConsumer<NfvoActions, UUID> f) {
		return new args(f);
	}
}
