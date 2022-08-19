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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jgrapht.ListenableGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.ContextHolder;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.NsdExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.VnfExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NsdCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NsdInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.PortPairNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgLoadbalancerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgPostNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.v3.BlueprintBuilder;
import com.ubiqube.etsi.mano.orchestrator.v3.PreExecutionGraphV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfPlanService;
import com.ubiqube.etsi.mano.service.event.WorkflowV3;
import com.ubiqube.etsi.mano.service.graph.Edge2d;
import com.ubiqube.etsi.mano.service.graph.Vertex2d;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.NetWorkVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.AbstractVnfmContributorV3;

@Service
public class VnfmOrchestrationV3 implements WorkflowV3<VnfPackage, VnfBlueprint, VnfTask> {

	private static final Logger LOG = LoggerFactory.getLogger(VnfmOrchestrationV3.class);

	private final List<AbstractVnfmContributorV3<VnfTask>> contributors;
	private final BlueprintBuilder blueprintBuilder;
	private final VnfPlanService planService;
	private final VnfLiveInstanceJpa vnfLiveInstanceJpa;
	private final List<Class<? extends Node>> masterVertex;
	private final Map<ResourceTypeEnum, Function<VnfTask, VirtualTaskV3>> vts;

	public VnfmOrchestrationV3(final List<AbstractVnfmContributorV3<VnfTask>> contributors, final BlueprintBuilder blueprintBuilder, final VnfPlanService planService, final VnfLiveInstanceJpa vnfLiveInstanceJpa,
			List<Class<? extends Node>> masterVertex) {
		this.contributors = contributors;
		this.blueprintBuilder = blueprintBuilder;
		this.planService = planService;
		this.vnfLiveInstanceJpa = vnfLiveInstanceJpa;
		this.masterVertex = masterVertex;
		vts = new EnumMap<>(ResourceTypeEnum.class);
		vts.put(ResourceTypeEnum.VL, x -> new NetWorkVt((NetworkTask) x));
		masterVertex = List.of(Network.class, Compute.class);

	}

	@Override
	public PreExecutionGraphV3<VnfTask> setWorkflowBlueprint(final VnfPackage bundle, final VnfBlueprint blueprint) {
		final List<SclableResources<VnfTask>> sr = contributors.stream().flatMap(x -> x.contribute(bundle, blueprint).stream()).toList();
		final ListenableGraph<Vertex2d, Edge2d> g = planService.getPlanFor(bundle.getId());
		return blueprintBuilder.buildPlan(sr, g, x -> {
			LOG.debug("Running for {}={}", x.getType(), x.getToscaName());
			blueprint.addTask(x);
			final VnfTask nc = x.copy();
			return vts.get(x.getType()).apply(nc);
		}, buildContext(blueprint.getInstance()), masterVertex);
	}

	private List<ContextHolder> buildContext(final VnfInstance instance) {
		final List<VnfLiveInstance> live = vnfLiveInstanceJpa.findByVnfInstanceId(instance.getId());
		return live.stream().map(this::convert).toList();
	}

	private ContextHolder convert(final VnfLiveInstance x) {
		final Class<? extends Node> t = switch (x.getTask().getType()) {
		case VL -> Network.class;
		case VNF_CREATE -> VnfCreateNode.class;
		case VNF_INSTANTIATE -> VnfInstantiateNode.class;
		case VNF_EXTRACTOR -> VnfExtractorNode.class;
		case NSD_CREATE -> NsdCreateNode.class;
		case NSD_INSTANTIATE -> NsdInstantiateNode.class;
		case NSD_EXTRACTOR -> NsdExtractorNode.class;
		case VNFFG -> VnffgPostNode.class;
		case VNFFG_LOADBALANCER -> VnffgLoadbalancerNode.class;
		case VNFFG_POST -> VnffgPostNode.class;
		case VNFFG_PORT_PAIR -> PortPairNode.class;
		default -> throw new GenericException(x.getTask().getType() + " is not handled.");
		};
		return new ContextHolder(x.getId(), t, x.getTask().getToscaName(), x.getRank(), x.getResourceId());
	}

	@Override
	public OrchExecutionResults<VnfTask> execute(final PreExecutionGraphV3<VnfTask> plan, final VnfBlueprint parameters) {
		plan.toDotFile("orch-added.dot");
		return null;
	}

	@Override
	public void refresh(final PreExecutionGraphV3<VnfTask> prePlan, final Blueprint<VnfTask, ?> localPlan) {
		prePlan.getPreTasks().forEach(x -> {
			final VnfTask task = find(x.getTemplateParameters().getToscaId(), localPlan);
			x.setTemplateParameters(task);
		});
	}

	private static VnfTask find(final String id, final Blueprint<VnfTask, ?> localPlan) {
		return localPlan.getTasks().stream()
				.filter(x -> x.getToscaId().equals(id))
				.findFirst()
				.orElseThrow(() -> new GenericException("Could not find " + id));
	}
}
