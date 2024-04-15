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
package com.ubiqube.etsi.mano.service.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;
import com.ubiqube.etsi.mano.orchestrator.ResultType;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;
import com.ubiqube.etsi.mano.service.TestBluePrint;
import com.ubiqube.etsi.mano.service.TestInstance;
import com.ubiqube.etsi.mano.service.TestTask;
import com.ubiqube.etsi.mano.service.VimResourceService;

@ExtendWith(MockitoExtension.class)
class AbstractGenericActionV3Test {
	@Mock
	private WorkflowV3 workflow;
	@Mock
	private VimResourceService vimResourceService;
	@Mock
	private OrchestrationAdapter<?, ?> orchestrationAdapter;
	@Mock
	private NsScaleStrategyV3 nsScaleStrategy;

	@Test
	void testInstantiateFail() throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy);
		final UUID id = UUID.randomUUID();
		final Blueprint bp = new TestBluePrint();
		final TestBluePrint tp2 = (TestBluePrint) bp;
		final TestInstance inst = new TestInstance();
		inst.setId(id);
		tp2.setId(id);
		tp2.setInstance(inst);
		when(orchestrationAdapter.getBluePrint(id)).thenReturn(bp);
		when(orchestrationAdapter.getInstance(id)).thenReturn(inst);
		srv.instantiate(id);
		assertEquals(OperationStatusType.FAILED, tp2.getOperationStatus());
	}

	static <T, U, R> Arguments akkkrgs(final BiFunction<T, U, R> method, final U expected) {
		return Arguments.of(method, expected);
	}

	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(args.of(TestGenericAction::instantiate)),
				Arguments.of(args.of(TestGenericAction::terminate)),
				Arguments.of(args.of(TestGenericAction::scale)),
				Arguments.of(args.of(TestGenericAction::scaleToLevel)));
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testActionOk(final args arg) throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy);
		final UUID id = UUID.randomUUID();
		final Blueprint bp = new TestBluePrint();
		final TestBluePrint tp2 = (TestBluePrint) bp;
		final TestInstance inst = new TestInstance();
		final BlueprintParameters instVnfInfo = new BlueprintParameters();
		instVnfInfo.setScaleStatus(new LinkedHashSet<>());
		inst.setInstantiatedVnfInfo(instVnfInfo);
		inst.setId(id);
		tp2.setId(id);
		tp2.setInstance(inst);
		tp2.getParameters().setScaleStatus(new HashSet<>());
		tp2.setVimConnections(new LinkedHashSet<>());
		final OrchExecutionResults results = new TestOrchExecutionResults<>();
		when(orchestrationAdapter.getBluePrint(id)).thenReturn(bp);
		when(orchestrationAdapter.getInstance(id)).thenReturn(inst);
		when(orchestrationAdapter.save(bp)).thenReturn(bp);
		when(orchestrationAdapter.updateState(bp, OperationStatusType.PROCESSING)).thenReturn(bp);
		when(workflow.execute(null, bp)).thenReturn(results);
		//
		arg.func().accept(srv, id);
		assertEquals(OperationStatusType.COMPLETED, tp2.getOperationStatus());
	}

	@Test
	void testLiveStatusOk() throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy);
		final UUID id = UUID.randomUUID();
		final Blueprint bp = new TestBluePrint();
		final TestBluePrint tp2 = (TestBluePrint) bp;
		final TestInstance inst = new TestInstance();
		final BlueprintParameters instVnfInfo = new BlueprintParameters();
		instVnfInfo.setScaleStatus(new LinkedHashSet<>());
		inst.setInstantiatedVnfInfo(instVnfInfo);
		inst.setId(id);
		tp2.setId(id);
		tp2.setInstance(inst);
		tp2.getParameters().setScaleStatus(new HashSet<>());
		tp2.setVimConnections(new LinkedHashSet<>());
		final TestOrchExecutionResults results = new TestOrchExecutionResults<>();
		final TestUnitOfWorkV3 uow = new TestUnitOfWorkV3();
		final VirtualTaskV3<TestTask> vt = new TestVirtualTaskV3();
		final TestTask tt = new TestTask(ResourceTypeEnum.COMPUTE);
		tt.setChangeType(ChangeType.ADDED);
		vt.setTemplateParameters(tt);
		uow.setTask(vt);
		results.addSuccess(new TestOrchExecutionResult(uow, ResultType.SUCCESS, ""));
		results.addAll(results);
		when(orchestrationAdapter.getBluePrint(id)).thenReturn(bp);
		when(orchestrationAdapter.getInstance(id)).thenReturn(inst);
		when(orchestrationAdapter.save(bp)).thenReturn(bp);
		when(orchestrationAdapter.updateState(bp, OperationStatusType.PROCESSING)).thenReturn(bp);
		when(workflow.execute(null, bp)).thenReturn(results);
		//
		srv.instantiate(id);
		assertEquals(OperationStatusType.COMPLETED, tp2.getOperationStatus());
	}

}

record args(BiConsumer<TestGenericAction, UUID> func) {
	public static args of(final BiConsumer<TestGenericAction, UUID> f) {
		return new args(f);
	}
}
