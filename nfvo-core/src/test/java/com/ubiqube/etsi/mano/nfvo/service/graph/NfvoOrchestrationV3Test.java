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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfExtractorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfInstantiateTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdExtractorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdInstantiateTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgLoadbalancerTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPortPairTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPostTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.ResourceTypeConverterNfvo;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.v3.AbstractNsdContributorV3;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.v3.BlueprintBuilder;
import com.ubiqube.etsi.mano.orchestrator.v3.PreExecutionGraphV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.ResourceTypeConverter;
import com.ubiqube.etsi.mano.tf.entities.NetworkPolicyTask;
import com.ubiqube.etsi.mano.tf.entities.PortTupleTask;
import com.ubiqube.etsi.mano.tf.entities.PtLinkTask;
import com.ubiqube.etsi.mano.tf.entities.ServiceInstanceTask;
import com.ubiqube.etsi.mano.tf.entities.ServiceTemplateTask;

@ExtendWith(MockitoExtension.class)
class NfvoOrchestrationV3Test {

	@Mock
	private BlueprintBuilder blueprintBuilder;
	@Mock
	private NsPlanService planService;
	@Mock
	private NsLiveInstanceJpa nsLiveInstanceJpa;
	@Mock
	private Planner<NsTask> planV2;
	@Mock
	private PreExecutionGraphV3<NsTask> plan;
	private final ResourceTypeConverter<NsTask> resourceTypeConverter = new ResourceTypeConverterNfvo();
	@Captor
	ArgumentCaptor<Function> converter;

	@Test
	void testSetWorkflow() {
		final List<AbstractNsdContributorV3<?>> contributors = List.of();
		final NfvoOrchestrationV3 nfvo = createService(contributors);
		final NsdPackage bundle = new NsdPackage();
		final NsBlueprint bluePrint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		bluePrint.setNsInstance(instance);
		when(nsLiveInstanceJpa.findByNsInstanceId(any())).thenReturn(List.of());
		nfvo.setWorkflowBlueprint(bundle, bluePrint);
		assertTrue(true);
	}

	private NfvoOrchestrationV3 createService(final List<AbstractNsdContributorV3<?>> contributors) {
		return new NfvoOrchestrationV3(contributors, blueprintBuilder, planService, nsLiveInstanceJpa, planV2, resourceTypeConverter);
	}

	@SuppressWarnings("null")
	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(args.of("NsVirtualLinkTask", NsVirtualLinkTask::new)),
				Arguments.of(args.of("VnffgLoadbalancerTask", VnffgLoadbalancerTask::new)),
				Arguments.of(args.of("VnffgPostTask", VnffgPostTask::new)),
				Arguments.of(args.of("VnffgPortPairTask", VnffgPortPairTask::new)),
				Arguments.of(args.of("NsVnfTask", NsdTask::new)),
				Arguments.of(args.of("NsdInstantiateTask", NsdInstantiateTask::new)),
				Arguments.of(args.of("NsdExtractorTask", NsdExtractorTask::new)),
				Arguments.of(args.of("NsVnfTask", NsVnfTask::new)),
				Arguments.of(args.of("NsVnfInstantiateTask", NsVnfInstantiateTask::new)),
				Arguments.of(args.of("NsVnfExtractorTask", NsVnfExtractorTask::new)),
				Arguments.of(args.of("NetworkPolicyTask", NetworkPolicyTask::new)),
				Arguments.of(args.of("PortTupleTask", PortTupleTask::new)),
				Arguments.of(args.of("PtLinkTask", PtLinkTask::new)),
				Arguments.of(args.of("ServiceInstanceTask", ServiceInstanceTask::new)),
				Arguments.of(args.of("ServiceTemplateTask", ServiceTemplateTask::new)));
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testSetWorkflowSclableResources(final args arg) {
		final SclableResources<TestNsTask> sr = new SclableResources<>(Network.class, "name", 0, 1, null);
		final TestContributor cont = new TestContributor(nsLiveInstanceJpa, List.of(sr));
		final List<AbstractNsdContributorV3<?>> contributors = List.of(cont);
		final NfvoOrchestrationV3 nfvo = createService(contributors);
		final NsdPackage bundle = new NsdPackage();
		final NsBlueprint bluePrint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		bluePrint.setNsInstance(instance);
		when(nsLiveInstanceJpa.findByNsInstanceId(any())).thenReturn(List.of());
		when(blueprintBuilder.buildPlan(anyList(), any(), converter.capture(), any(), any())).thenReturn(plan);
		nfvo.setWorkflowBlueprint(bundle, bluePrint);
		assertNotNull(converter.getValue());
		final NsTask task = arg.func().get();
		task.setToscaName("name");
		converter.getValue().apply(task);
	}

	@Test
	void testSetWorkflowContext() {
		final List<AbstractNsdContributorV3<?>> contributors = List.of();
		final NfvoOrchestrationV3 nfvo = createService(contributors);
		final NsdPackage bundle = new NsdPackage();
		final NsBlueprint bluePrint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		bluePrint.setNsInstance(instance);
		final ArrayList<NsLiveInstance> lives = new ArrayList<>();
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.VL), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.VNF_CREATE), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.VNF_INSTANTIATE), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.VNF_EXTRACTOR), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.NSD_CREATE), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.NSD_EXTRACTOR), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.NSD_INSTANTIATE), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.VNFFG), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.VNFFG_LOADBALANCER), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.VNFFG_PORT_PAIR), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.VNFFG_POST), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.TF_NETWORK_POLICY), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.TF_PORT_TUPLE), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.TF_PT_LINK), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.TF_SERVICE_INSTANCE), bluePrint, instance));
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.TF_SERVICE_TEMPLATE), bluePrint, instance));
		when(nsLiveInstanceJpa.findByNsInstanceId(any())).thenReturn(lives);
		nfvo.setWorkflowBlueprint(bundle, bluePrint);
		assertTrue(true);
	}

	@Test
	void testSetWorkflowContextFail() {
		final List<AbstractNsdContributorV3<?>> contributors = List.of();
		final NfvoOrchestrationV3 nfvo = createService(contributors);
		final NsdPackage bundle = new NsdPackage();
		final NsBlueprint bluePrint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		bluePrint.setNsInstance(instance);
		final ArrayList<NsLiveInstance> lives = new ArrayList<>();
		lives.add(new NsLiveInstance(null, new TestNsTask(ResourceTypeEnum.AFFINITY_RULE), bluePrint, instance));
		when(nsLiveInstanceJpa.findByNsInstanceId(any())).thenReturn(lives);
		assertThrows(GenericException.class, () -> nfvo.setWorkflowBlueprint(bundle, bluePrint));
	}

	@Test
	void testExecute() throws Exception {
		final List<AbstractNsdContributorV3<?>> contributors = List.of();
		final NfvoOrchestrationV3 nfvo = createService(contributors);
		nfvo.execute(plan, null);
		assertTrue(true);
	}

	@Test
	void testRefresh() throws Exception {
		final List<AbstractNsdContributorV3<?>> contributors = List.of();
		final NfvoOrchestrationV3 nfvo = createService(contributors);
		nfvo.refresh(plan, null);
		assertTrue(true);
	}

	@Test
	void testRefresh002() throws Exception {
		final List<AbstractNsdContributorV3<?>> contributors = List.of();
		final NfvoOrchestrationV3 nfvo = createService(contributors);
		final TestNsTask task = new TestNsTask(ResourceTypeEnum.VL);
		final VirtualTaskV3 vt = new TestNsVt(task);
		when(plan.getPreTasks()).thenReturn(List.of(vt));
		final NsBlueprint bp = new NsBlueprint();
		bp.addTask(task);
		nfvo.refresh(plan, bp);
		assertTrue(true);
	}
}

record args(String name, Supplier<NsTask> func) {
	public static args of(final String name, final Supplier<NsTask> f) {
		return new args(name, f);
	}
}
