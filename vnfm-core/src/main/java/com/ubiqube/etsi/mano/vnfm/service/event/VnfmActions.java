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

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.OperationalStateType;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;
import com.ubiqube.etsi.mano.service.VimResourceService;
import com.ubiqube.etsi.mano.service.event.AbstractGenericActionV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfBlueprintService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;
import com.ubiqube.etsi.mano.vnfm.service.graph.VnfWorkflow;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfmActions extends AbstractGenericActionV3 {

	private static final Logger LOG = LoggerFactory.getLogger(VnfmActions.class);

	private final VimManager vimManager;

	private final VnfInstanceService vnfInstancesService;

	private final VnfBlueprintService blueprintService;

	private final VnfLiveInstanceJpa vnfLiveInstanceJpa;

	private final VnfInstanceServiceVnfm vnfInstanceServiceVnfm;

	public VnfmActions(final VimManager vimManager, final VnfOrchestrationAdapter orchestrationAdapter, final VnfInstanceService vnfInstancesService,
			final VnfBlueprintService blueprintService, final VimResourceService vimResourceService, final VnfLiveInstanceJpa vnfLiveInstanceJpa,
			final VnfInstanceServiceVnfm vnfInstanceServiceVnfm, final VnfWorkflow workflow, final Planner<Task> planv2) {
		super(workflow, vimResourceService, orchestrationAdapter, new NsScaleStrategyV3(), planv2);
		this.vimManager = vimManager;
		this.vnfInstancesService = vnfInstancesService;
		this.blueprintService = blueprintService;
		this.vnfLiveInstanceJpa = vnfLiveInstanceJpa;
		this.vnfInstanceServiceVnfm = vnfInstanceServiceVnfm;
	}

	public void vnfOperate(final UUID blueprintId) {
		actionShield(blueprintId, (x, actionParameter) -> {
			final Vim vim = vimManager.getVimById(actionParameter.vimConnection.getId());
			if (actionParameter.blueprint.getOperateChanges().getTerminationType() == OperationalStateType.STARTED) {
				LOG.info("Restarting {}", x.getId());
				vim.startServer(actionParameter.vimConnection, x.getVimResourceId());
			} else {
				LOG.info("Stopping {}", x.getId());
				vim.stopServer(actionParameter.vimConnection, x.getVimResourceId());
			}
		});
	}

	public void vnfHeal(final UUID blueprintId) {
		actionShield(blueprintId, (x, actionParameter) -> {
			LOG.info("Rebooting: {}", x.getVimResourceId());
			final Vim vim = vimManager.getVimById(actionParameter.vimConnection.getId());
			vim.rebootServer(actionParameter.vimConnection, x.getVimResourceId());
		});
	}

	private void actionShield(final UUID blueprintId, final BiConsumer<VnfTask, ActionParameter> func) {
		final VnfBlueprint blueprint = blueprintService.findById(blueprintId);
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(blueprint.getVnfInstance().getId());
		try {
			final VimConnectionInformation vimConnection = vnfInstance.getVimConnectionInfo().iterator().next();
			blueprint.getTasks().forEach(x -> func.accept(x, new ActionParameter(blueprint, vnfInstance, vimConnection)));
			completeOperation(blueprint, vnfInstance, OperationStatusType.COMPLETED);
		} catch (final RuntimeException e) {
			LOG.error("", e);
			completeOperation(blueprint, vnfInstance, OperationStatusType.FAILED);
		}
	}

	private void completeOperation(final VnfBlueprint blueprint, final VnfInstance vnfInstance, final OperationStatusType oState) {
		blueprint.setOperationStatus(oState);
		blueprint.setStateEnteredTime(OffsetDateTime.now());
		blueprintService.save(blueprint);
		vnfInstance.setLockedBy(null);
		vnfInstancesService.save(vnfInstance);
	}

	public void vnfChangeVnfConn(final UUID blueprintId) {
		final VnfBlueprint blueprint = blueprintService.findById(blueprintId);
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(blueprint.getVnfInstance().getId());
		final Set<ExtVirtualLinkDataEntity> evl = blueprint.getChangeExtVnfConnRequest().getExtVirtualLinks();
		final List<VnfLiveInstance> vli = evl.stream()
				.flatMap(x -> x.getExtCps().stream())
				.flatMap(y -> vnfLiveInstanceJpa.findByTaskVnfInstanceAndToscaName(vnfInstance, y.getCpdId()).stream())
				.toList();
		LOG.debug("{}", vli.get(0).getTask());
	}

	@Override
	protected void mergeVirtualLinks(final Instance vnfInstance, final Blueprint<?, ?> localPlan) {
		final VnfBlueprint vp = (VnfBlueprint) localPlan;
		vnfInstance.setExtManagedVirtualLinks(vp.getParameters().getExtManagedVirtualLinks());
		vnfInstance.setExtVirtualLinks(vp.getParameters().getExtVirtualLinkInfo());
	}

	private record ActionParameter(VnfBlueprint blueprint, VnfInstance vnfInstance, VimConnectionInformation vimConnection) {
		// Nothing.
	}
}
