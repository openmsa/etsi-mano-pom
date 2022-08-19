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

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.orchestrator.ExecutionGraph;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.v3.PreExecutionGraphV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.event.WorkflowV3;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnfWorkflow implements WorkflowV3<VnfPackage, VnfBlueprint, VnfTask> {
	private final Planner<VnfBlueprint, VnfTask, VnfTask> planv2;
	private final VnfLiveInstanceJpa vnfInstanceJpa;

	public VnfWorkflow(final Planner<VnfBlueprint, VnfTask, VnfTask> planv2, final VnfLiveInstanceJpa vnfInstanceJpa) {
		this.planv2 = planv2;
		this.vnfInstanceJpa = vnfInstanceJpa;
	}

	@Override
	public PreExecutionGraphV3<VnfTask> setWorkflowBlueprint(final VnfPackage bundle, final VnfBlueprint blueprint) {
		final List<Class<? extends Node>> planConstituent = new ArrayList<>();
		// Doesn't works with jdk17 but fine with eclipse.
		final PreExecutionGraphV3<VnfTask> plan = planv2.makePlan(new VnfBundleAdapter(bundle), planConstituent, blueprint);
		plan.getPreTasks().stream().map(VirtualTaskV3::getTemplateParameters).forEach(blueprint::addTask);
		return plan;
	}

	@Override
	public OrchExecutionResults execute(final PreExecutionGraphV3<VnfTask> plan, final VnfBlueprint parameters) {
		final ExecutionGraph impl = planv2.implement(plan);
		// populateExtNetworks(context, parameters);
		return planv2.execute(impl, new OrchListenetImpl(parameters, vnfInstanceJpa));
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
