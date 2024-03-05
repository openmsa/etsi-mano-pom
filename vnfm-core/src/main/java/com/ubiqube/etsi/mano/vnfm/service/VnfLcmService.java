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
package com.ubiqube.etsi.mano.vnfm.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.OperateChanges;
import com.ubiqube.etsi.mano.dao.mano.OperationalStateType;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;
import com.ubiqube.etsi.mano.service.search.ManoSearch;
import com.ubiqube.etsi.mano.vnfm.controller.vnflcm.VnfLcmFactory;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfBlueprintJpa;

@Service
public class VnfLcmService {

	private final VnfBlueprintJpa vnfBlueprintJpa;

	private final VnfInstanceService vnfInstancesService;

	private final GrammarParser grammarParser;

	private final ManoSearch manoSearch;

	public VnfLcmService(final VnfBlueprintJpa planJpa, final VnfInstanceService vnfInstancesService, final GrammarParser grammarParser,
			final ManoSearch manoSearch) {
		this.vnfBlueprintJpa = planJpa;
		this.manoSearch = manoSearch;
		this.vnfInstancesService = vnfInstancesService;
		this.grammarParser = grammarParser;
	}

	public VnfBlueprint createIntatiateOpOcc(final VnfInstance vnfInstance) {
		return createIntatiateTerminateVnfBlueprint(vnfInstance, PlanOperationType.INSTANTIATE);
	}

	public VnfBlueprint createTerminateOpOcc(final VnfInstance vnfInstance) {
		return createIntatiateTerminateVnfBlueprint(vnfInstance, PlanOperationType.TERMINATE);
	}

	private VnfBlueprint createIntatiateTerminateVnfBlueprint(final VnfInstance vnfInstance, final PlanOperationType state) {
		final VnfBlueprint lcmOpOccs = VnfLcmFactory.createVnfBlueprint(state, vnfInstance.getId());
		return saveLcmOppOcc(lcmOpOccs, vnfInstance);
	}

	public VnfBlueprint createScaleToLevelOpOcc(final VnfInstance vnfInstance, final VnfScaleToLevelRequest scaleVnfToLevelRequest) {
		final VnfBlueprint lcmOpOccs = VnfLcmFactory.createVnfBlueprint(PlanOperationType.SCALE_TO_LEVEL, vnfInstance.getId());
		lcmOpOccs.getParameters().setInstantiationLevelId(scaleVnfToLevelRequest.getInstantiationLevelId());
		if (scaleVnfToLevelRequest.getScaleInfo() != null) {
			final Set<ScaleInfo> scaleStatus = scaleVnfToLevelRequest.getScaleInfo().stream()
					.map(x -> new ScaleInfo(x.getAspectId(), x.getScaleLevel()))
					.collect(Collectors.toSet());
			lcmOpOccs.getParameters().setScaleStatus(scaleStatus);
		}
		return saveLcmOppOcc(lcmOpOccs, vnfInstance);
	}

	public VnfBlueprint createScaleOpOcc(final VnfInstance vnfInstance, final VnfScaleRequest scaleVnfRequest) {
		final VnfBlueprint lcmOpOccs = VnfLcmFactory.createVnfBlueprint(PlanOperationType.SCALE, vnfInstance.getId());
		lcmOpOccs.getParameters().setNumberOfSteps(scaleVnfRequest.getNumberOfSteps());
		lcmOpOccs.getParameters().setScaleType(scaleVnfRequest.getType());
		lcmOpOccs.getParameters().setAspectId(scaleVnfRequest.getAspectId());
		lcmOpOccs.getParameters().setScaleStatus(scaleVnfRequest.getScaleInfo());
		lcmOpOccs.getParameters().setInstantiationLevelId(scaleVnfRequest.getInstantiationLevelId());
		final Set<ScaleInfo> scaleStatus = Set.of(new ScaleInfo(scaleVnfRequest.getAspectId(), scaleVnfRequest.getNumberOfSteps()));
		lcmOpOccs.getParameters().setScaleStatus(scaleStatus);
		return saveLcmOppOcc(lcmOpOccs, vnfInstance);
	}

	public VnfBlueprint createOperateOpOcc(final VnfInstance vnfInstance, final VnfOperateRequest operateVnfRequest) {
		final VnfBlueprint lcmOpOccs = VnfLcmFactory.createVnfBlueprint(PlanOperationType.OPERATE, vnfInstance.getId());
		final OperateChanges opChanges = lcmOpOccs.getOperateChanges();
		opChanges.setTerminationType(OperationalStateType.fromValue(operateVnfRequest.getChangeStateTo().toString()));
		opChanges.setGracefulTerminationTimeout(operateVnfRequest.getGracefulStopTimeout());
		final List<VnfLiveInstance> instantiatedCompute = vnfInstancesService.getLiveComputeInstanceOf(vnfInstance);
		instantiatedCompute.forEach(x -> {
			final VnfTask affectedCompute = copyInstantiedResource(x, new ComputeTask(), lcmOpOccs);
			lcmOpOccs.addTask(affectedCompute);
		});
		return saveLcmOppOcc(lcmOpOccs, vnfInstance);
	}

	public VnfBlueprint createHealOpOcc(final VnfInstance vnfInstance, final VnfHealRequest vnfHealRequest) {
		final VnfBlueprint lcmOpOccs = VnfLcmFactory.createVnfBlueprint(PlanOperationType.HEAL, vnfInstance.getId());
		lcmOpOccs.setHealCause(vnfHealRequest.getCause());
		final List<VnfLiveInstance> instantiatedCompute = vnfInstancesService.getLiveComputeInstanceOf(vnfInstance);
		instantiatedCompute.forEach(x -> {
			final VnfTask affectedCompute = copyInstantiedResource(x, new ComputeTask(), lcmOpOccs);
			lcmOpOccs.addTask(affectedCompute);
		});
		return saveLcmOppOcc(lcmOpOccs, vnfInstance);
	}

	public List<VnfBlueprint> query(final String filter) {
		final GrammarNodeResult nodes = grammarParser.parse(filter);
		return manoSearch.getCriteria(nodes.getNodes(), VnfBlueprint.class);
	}

	public VnfBlueprint findById(final UUID id) {
		return vnfBlueprintJpa.findById(id).orElseThrow(() -> new NotFoundException("Could not find VNF LCM operation: " + id));
	}

	public List<VnfBlueprint> findByVnfInstanceId(final UUID id) {
		return vnfBlueprintJpa.findByVnfInstanceId(id);
	}

	public VnfBlueprint createChangeExtCpOpOcc(final VnfInstance vnfInstance, final ChangeExtVnfConnRequest cevcr) {
		final VnfBlueprint lcmOpOccs = VnfLcmFactory.createVnfBlueprint(PlanOperationType.CHANGE_EXTERNAL_VNF_CONNECTIVITY, vnfInstance.getId());
		lcmOpOccs.setChangeExtVnfConnRequest(cevcr);
		return saveLcmOppOcc(lcmOpOccs, vnfInstance);
	}

	private VnfBlueprint saveLcmOppOcc(final VnfBlueprint blueprint, final VnfInstance vnfInstance) {
		final VnfBlueprint bp = vnfBlueprintJpa.save(blueprint);
		vnfInstance.setLockedBy(bp.getId());
		vnfInstancesService.save(vnfInstance);
		return bp;
	}

	private static <T extends VnfTask> T copyInstantiedResource(final VnfLiveInstance x, final T task, final VnfBlueprint blueprint) {
		task.setChangeType(ChangeType.REMOVED);
		task.setStatus(PlanStatusType.STARTED);
		task.setBlueprint(blueprint);
		task.setStartDate(LocalDateTime.now());
		task.setToscaName(x.getTask().getToscaName());
		task.setVimResourceId(x.getResourceId());
		return task;
	}

	public VnfBlueprint save(final VnfBlueprint lcm) {
		return vnfBlueprintJpa.save(lcm);
	}

	public void deleteByVnfInstance(final VnfInstance vnfInstance) {
		vnfBlueprintJpa.deleteByVnfInstance(vnfInstance);
	}
}
