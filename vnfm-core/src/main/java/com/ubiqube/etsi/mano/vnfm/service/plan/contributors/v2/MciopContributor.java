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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.MciopTask;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.orchestrator.Bundle;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.service.VnfInstanceGatewayService;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.graph.VnfBundleAdapter;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.MciopVt;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class MciopContributor extends AbstractContributorV2Base<MciopTask, MciopVt> {
	private final VnfInstanceGatewayService vnfInstanceGatewayService;
	private final VnfLiveInstanceJpa vnfLiveInstanceJpa;

	public MciopContributor(final VnfInstanceGatewayService vnfInstanceGatewayService, final VnfLiveInstanceJpa vnfLiveInstanceJpa) {
		this.vnfInstanceGatewayService = vnfInstanceGatewayService;
		this.vnfLiveInstanceJpa = vnfLiveInstanceJpa;
	}

	@Override
	public Class<? extends Node> getNode() {
		return HelmNode.class;
	}

	@Override
	protected List<MciopVt> vnfContribute(final Bundle bundle, final VnfBlueprint blueprint) {
		if (blueprint.getOperation() == PlanOperationType.TERMINATE) {
			return doTerminatePlan(blueprint.getVnfInstance());
		}
		final List<MciopVt> ret = new ArrayList<>();
		final VnfPackage vnfPackage = ((VnfBundleAdapter) bundle).vnfPackage();
		final VnfInstance vnfInstance = vnfInstanceGatewayService.findById(blueprint.getInstance().getId());
		vnfPackage.getMciops().stream()
				.forEach(x -> {
					final int c = vnfLiveInstanceJpa.countByVnfInstanceAndTaskToscaName(vnfInstance, x.getToscaName());
					if (c > 0) {
						return;
					}
					x.getAssociatedVdu().forEach(y -> {
						final MciopTask inst = createInstances(x, blueprint);
						inst.setParentVdu(y);
						inst.setVnfPackageId(vnfPackage.getId());
						ret.add(new MciopVt(inst));
					});
				});
		return ret;
	}

	private static MciopTask createInstances(final McIops x, final VnfBlueprint blueprint) {
		final MciopTask t = createTask(MciopTask::new);
		t.setBlueprint(blueprint);
		t.setMciop(x);
		t.setAlias(x.getToscaName());
		t.setToscaName(x.getToscaName());
		t.setType(ResourceTypeEnum.CNF);
		return t;
	}

	private List<MciopVt> doTerminatePlan(final VnfInstance vnfInstance) {
		final List<VnfLiveInstance> instances = vnfLiveInstanceJpa.findByVnfInstanceIdAndClass(vnfInstance, MciopTask.class.getSimpleName());
		return instances.stream()
				.map(x -> {
					final MciopTask task = createDeleteTask(MciopTask::new, x);
					task.setType(ResourceTypeEnum.CNF);
					task.setMciop(((MciopTask) x.getTask()).getMciop());
					task.setVimResourceId(null);
					return new MciopVt(task);
				})
				.toList();
	}
}
