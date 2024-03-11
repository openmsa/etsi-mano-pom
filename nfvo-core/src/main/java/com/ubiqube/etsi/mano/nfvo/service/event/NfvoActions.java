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

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfInstantiateTask;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.nfvo.service.graph.NfvoOrchestrationV3;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsOrchestrationAdapter;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;
import com.ubiqube.etsi.mano.service.VimResourceService;
import com.ubiqube.etsi.mano.service.VnfInstanceGatewayService;
import com.ubiqube.etsi.mano.service.event.AbstractGenericActionV3;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NfvoActions extends AbstractGenericActionV3 {

	private final NsBlueprintService blueprintService;
	private final NsInstanceService nsInstanceService;
	private final VnfInstanceGatewayService vnfInstancesService;
	private final ManoClientFactory manoClientFactory;

	public NfvoActions(final NfvoOrchestrationV3 workflow, final VimResourceService vimResourceService, final NsOrchestrationAdapter orchestrationAdapter, final NsScaleStrategyV3 nsScaleStrategy,
			final NsBlueprintService blueprintService, final NsInstanceService nsInstanceService, final VnfInstanceGatewayService vnfInstancesService, final ManoClientFactory manoClientFactory,
			final Planner<Task> planner) {
		super(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy, planner);
		this.blueprintService = blueprintService;
		this.nsInstanceService = nsInstanceService;
		this.vnfInstancesService = vnfInstancesService;
		this.manoClientFactory = manoClientFactory;
	}

	public void heal(final UUID objectId) {
		final Blueprint<? extends VimTask, ? extends Instance> blueprint = orchestrationAdapter.getBluePrint(objectId);
		final NsdInstance nsi = nsInstanceService.findById(blueprint.getInstance().getId());
		final List<NsLiveInstance> vnfs = blueprintService.findByNsdInstanceAndClass(nsi, NsVnfInstantiateTask.class);
		vnfs.stream().forEach(nsli -> {
			final VnfInstance vnfInstance = vnfInstancesService.findById(UUID.fromString(nsli.getResourceId()));
			final NsVnfInstantiateTask t = (NsVnfInstantiateTask) nsli.getNsTask();
			final ManoClient mc = manoClientFactory.getClient(t.getServer());
			mc.vnfInstance().id(vnfInstance.getId()).heal(new VnfHealRequest());
		});
	}

	@Override
	protected void mergeVirtualLinks(final Instance vnfInstance, final Blueprint<?, ?> localPlan) {
		// Nothing to merge.
	}

	public void update(final UUID objectId) {
		instantiate(objectId);
	}

}
