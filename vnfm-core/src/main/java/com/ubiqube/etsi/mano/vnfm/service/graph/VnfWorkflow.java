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
import java.util.Optional;
import java.util.function.Function;

import org.jgrapht.ListenableGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.SubNetworkTask;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsHostTask;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsZoneTask;
import com.ubiqube.etsi.mano.dao.mano.v2.MonitoringTask;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.SecurityGroupTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.ContextHolder;
import com.ubiqube.etsi.mano.orchestrator.ExecutionGraph;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SecurityGroupNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SubNetwork;
import com.ubiqube.etsi.mano.orchestrator.v3.BlueprintBuilder;
import com.ubiqube.etsi.mano.orchestrator.v3.PreExecutionGraphV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfPlanService;
import com.ubiqube.etsi.mano.service.event.WorkflowV3;
import com.ubiqube.etsi.mano.service.graph.Edge2d;
import com.ubiqube.etsi.mano.service.graph.Vertex2d;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.ComputeVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.DnsHistVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.DnsZoneVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.MonitoringVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.NetWorkVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.SecurityGroupVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.StorageVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.SubNetworkVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.VnfPortVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.AbstractVnfmContributorV3;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnfWorkflow implements WorkflowV3<VnfPackage, VnfBlueprint, VnfTask> {

	private static final Logger LOG = LoggerFactory.getLogger(VnfWorkflow.class);

	private final List<AbstractVnfmContributorV3<VnfTask>> contributors;
	private final VnfPlanService planService;
	private final BlueprintBuilder blueprintBuilder;
	private final List<Class<? extends Node>> masterVertex;
	private final Map<ResourceTypeEnum, Function<VnfTask, VirtualTaskV3<? extends VnfTask>>> vts;

	private final Planner<VnfBlueprint, VnfTask, VnfTask> planv2;
	private final VnfLiveInstanceJpa liveInstanceJpa;

	public VnfWorkflow(final Planner<VnfBlueprint, VnfTask, VnfTask> planv2, final VnfLiveInstanceJpa vnfInstanceJpa,
			final List<AbstractVnfmContributorV3<?>> contributors, final VnfPlanService planService, final BlueprintBuilder blueprintBuilder) {
		this.planv2 = planv2;
		this.liveInstanceJpa = vnfInstanceJpa;
		this.contributors = (List<AbstractVnfmContributorV3<VnfTask>>) ((Object) contributors);
		this.planService = planService;
		this.blueprintBuilder = blueprintBuilder;
		vts = new EnumMap<>(ResourceTypeEnum.class);
		vts.put(ResourceTypeEnum.VL, x -> new NetWorkVt((NetworkTask) x));
		vts.put(ResourceTypeEnum.SUBNETWORK, x -> new SubNetworkVt((SubNetworkTask) x));
		vts.put(ResourceTypeEnum.COMPUTE, x -> new ComputeVt((ComputeTask) x));
		vts.put(ResourceTypeEnum.LINKPORT, x -> new VnfPortVt((VnfPortTask) x));
		vts.put(ResourceTypeEnum.SECURITY_GROUP, x -> new SecurityGroupVt((SecurityGroupTask) x));
		vts.put(ResourceTypeEnum.STORAGE, x -> new StorageVt((StorageTask) x));
		vts.put(ResourceTypeEnum.DNSZONE, x -> new DnsZoneVt((DnsZoneTask) x));
		vts.put(ResourceTypeEnum.DNSHOST, x -> new DnsHistVt((DnsHostTask) x));
		vts.put(ResourceTypeEnum.CNF, x -> new DnsHistVt((DnsHostTask) x));
		vts.put(ResourceTypeEnum.CNF_INFO, x -> new DnsHistVt((DnsHostTask) x));
		vts.put(ResourceTypeEnum.MONITORING, x -> new MonitoringVt((MonitoringTask) x));
		masterVertex = List.of(Network.class, Compute.class);
	}

	@Override
	public PreExecutionGraphV3<VnfTask> setWorkflowBlueprint(final VnfPackage bundle, final VnfBlueprint blueprint) {
		final List<SclableResources<VnfTask>> sr = contributors.stream().flatMap(x -> x.contribute(bundle, blueprint).stream()).toList();
		final ListenableGraph<Vertex2d, Edge2d> g = planService.getPlanFor(bundle.getId());
		return blueprintBuilder.buildPlan(sr, g, x -> {
			LOG.trace("Running for {}={}", x.getType(), x.getToscaName());
			blueprint.addTask(x);
			final VnfTask nc = x.copy();
			return (VirtualTaskV3<VnfTask>) Optional.ofNullable(vts.get(x.getType()))
					.orElseThrow(() -> new GenericException("Unable to find " + x.getType()))
					.apply(nc);
		}, buildContext(blueprint.getInstance()), masterVertex);
	}

	private List<ContextHolder> buildContext(final VnfInstance instance) {
		final List<VnfLiveInstance> live = liveInstanceJpa.findByVnfInstanceId(instance.getId());
		return live.stream().map(this::convert).toList();
	}

	private ContextHolder convert(final VnfLiveInstance x) {
		final Class<? extends Node> t = switch (x.getTask().getType()) {
		case VL -> Network.class;
		case SUBNETWORK -> SubNetwork.class;
		case COMPUTE -> Compute.class;
		case STORAGE -> Storage.class;
		case SECURITY_GROUP -> SecurityGroupNode.class;
		default -> throw new GenericException(x.getTask().getType() + " is not handled.");
		};
		return new ContextHolder(x.getId(), t, x.getTask().getToscaName(), x.getRank(), x.getResourceId());
	}

	@Override
	public OrchExecutionResults<VnfTask> execute(final PreExecutionGraphV3<VnfTask> plan, final VnfBlueprint parameters) {
		plan.toDotFile("orch-added.dot");
		final ExecutionGraph imp = planv2.implement(plan);
		return planv2.execute(imp, new OrchListenetImpl(parameters, liveInstanceJpa));
	}

	@Override
	public void refresh(final PreExecutionGraphV3<VnfTask> prePlan, final Blueprint<VnfTask, ?> localPlan) {
		prePlan.getPreTasks().forEach(x -> {
			final VnfTask task = find(x.getTemplateParameters().getToscaId(), localPlan);
			x.setTemplateParameters(task);
		});
	}

	private static VnfTask find(final String id, final Blueprint<VnfTask, ?> localPlan) {
		return localPlan.getTasks().stream().filter(x -> x.getToscaId().equals(id)).findFirst().orElseThrow();
	}

}
