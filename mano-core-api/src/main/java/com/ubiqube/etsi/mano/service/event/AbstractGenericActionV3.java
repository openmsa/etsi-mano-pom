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

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.PackageBase;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.orchestrator.ExecutionGraph;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.orchestrator.v3.PreExecutionGraphV3;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;
import com.ubiqube.etsi.mano.service.VimResourceService;
import com.ubiqube.etsi.mano.service.graph.WorkflowEvent;

/**
 *
 * @author olivier
 *
 */
public abstract class AbstractGenericActionV3 {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractGenericActionV3.class);

	private final WorkflowV3 workflow;

	private final VimResourceService vimResourceService;

	protected final OrchestrationAdapter<?, ?> orchestrationAdapter;

	private final NsScaleStrategyV3 nsScaleStrategy;

	private final Planner<Task> planv2;

	protected AbstractGenericActionV3(final WorkflowV3 workflow, final VimResourceService vimResourceService, final OrchestrationAdapter<?, ?> orchestrationAdapter, final NsScaleStrategyV3 nsScaleStrategy,
			final Planner<Task> planv2) {
		this.workflow = workflow;
		this.vimResourceService = vimResourceService;
		this.orchestrationAdapter = orchestrationAdapter;
		this.nsScaleStrategy = nsScaleStrategy;
		this.planv2 = planv2;
	}

	public final void instantiate(final UUID blueprintId) {
		instantiateShield(blueprintId, WorkflowEvent.INSTANTIATE_SUCCESS, WorkflowEvent.INSTANTIATE_FAILED);
	}

	public ExecutionGraph getExecutionGraph(final NsdInstance vnfInstance) {
		final NsBlueprint blueprint = new NsBlueprint();
		blueprint.setNsInstance(vnfInstance);
		return getExecutionGraph(vnfInstance, blueprint);
	}

	public ExecutionGraph getExecutionGraph(final Instance vnfInstance, final Blueprint<? extends VimTask, ? extends Instance> blueprint) {
		blueprint.setVimConnections(vnfInstance.getVimConnectionInfo());
		if (null == blueprint.getParameters().getInstantiationLevelId()) {
			blueprint.getParameters().setInstantiationLevelId(vnfInstance.getInstantiatedVnfInfo().getInstantiationLevelId());
		}
		final PackageBase vnfPkg = orchestrationAdapter.getPackage(vnfInstance);
		final Set<ScaleInfo> newScale = merge(blueprint, vnfInstance);
		blueprint.getParameters().setScaleStatus(newScale);
		final PreExecutionGraphV3<Task> prePlan = workflow.setWorkflowBlueprint(vnfPkg, blueprint);
		//
		workflow.refresh(prePlan, blueprint);
		blueprint.getTasks().forEach(x -> {
			if (Optional.ofNullable(x.getVimConnectionId()).isEmpty()) {
				x.setVimConnectionId(vnfInstance.getVimConnectionInfo().iterator().next().getVimId());
			}
		});
		return planv2.implement(prePlan);
	}

	public ExecutionGraph getExecutionGraph(final VnfInstance vnfInstance) {
		final VnfBlueprint blueprint = new VnfBlueprint();
		blueprint.setVnfInstance(vnfInstance);
		return getExecutionGraph(vnfInstance, blueprint);
	}

	private final void instantiateInnerv2(final Blueprint<? extends VimTask, ? extends Instance> blueprint, final Instance vnfInstance) {
		if (null == blueprint.getParameters().getInstantiationLevelId()) {
			blueprint.getParameters().setInstantiationLevelId(vnfInstance.getInstantiatedVnfInfo().getInstantiationLevelId());
		}
		final PackageBase vnfPkg = orchestrationAdapter.getPackage(vnfInstance);
		final Set<ScaleInfo> newScale = merge(blueprint, vnfInstance);
		final PreExecutionGraphV3<?> prePlan = workflow.setWorkflowBlueprint(vnfPkg, blueprint);
		Blueprint localPlan = orchestrationAdapter.save(blueprint);
		orchestrationAdapter.fireEvent(WorkflowEvent.INSTANTIATE_PROCESSING, vnfInstance.getId());
		vimResourceService.allocate(localPlan);
		localPlan = orchestrationAdapter.updateState(localPlan, OperationStatusType.PROCESSING);
		//
		workflow.refresh(prePlan, localPlan);
		final OrchExecutionResults<Task> res = workflow.execute(prePlan, localPlan);
		localPlan = orchestrationAdapter.getBluePrint(localPlan.getId());
		setLiveSatus(res);
		orchestrationAdapter.consolidate(vnfInstance);
		//
		setResultLcmInstance(localPlan, res);
		if (OperationStatusType.COMPLETED == localPlan.getOperationStatus()) {
			setInstanceStatus(vnfInstance, localPlan, newScale);
		}
		// XXX ??? error duplicate key in NSD.
		copyVimConnections(vnfInstance, localPlan);
		mergeVirtualLinks(vnfInstance, localPlan);
		vnfInstance.setLockedBy(null);
		orchestrationAdapter.save(vnfInstance);
		LOG.info("Saving LCM OP OCCS.");
		localPlan = orchestrationAdapter.save(localPlan);
		// XXX Send COMPLETED event.
		LOG.info("Instance {} / LCM {} Finished.", vnfInstance.getId(), localPlan.getId());
	}

	protected abstract void mergeVirtualLinks(Instance vnfInstance, Blueprint<?, ?> localPlan);

	private static void copyVimConnections(final Instance vnfInstance, final Blueprint<?, ?> localPlan) {
		vnfInstance.setVimConnectionInfo(new LinkedHashSet<>());
		localPlan.getVimConnections().forEach(vnfInstance::addVimConnectionInfo);
		vnfInstance.setCismConnectionInfo(new LinkedHashSet<>());
		localPlan.getCismConnections().forEach(vnfInstance::addCismConnectionInfo);
	}

	/**
	 * Move this function to scale strategy.
	 *
	 * @param instance
	 * @param localPlan
	 * @param newScale
	 */
	private void setInstanceStatus(final Instance instance, final Blueprint<? extends VimTask, ? extends Instance> localPlan, final Set<ScaleInfo> newScale) {
		Optional.ofNullable(localPlan.getParameters().getScaleStatus())
				.map(x -> x.stream()
						.map(y -> new ScaleInfo(y.getAspectId(), y.getScaleLevel()))
						.collect(Collectors.toSet()))
				.ifPresent(x -> setScaleStatus(instance, x, localPlan.getParameters().getScaleType()));
		if (localPlan.getOperation() == PlanOperationType.INSTANTIATE) {
			instance.getInstantiatedVnfInfo().setNsStepStatus(copy(localPlan.getParameters().getNsStepStatus()));
			instance.getInstantiatedVnfInfo().setScaleStatus(copy(localPlan.getParameters().getScaleStatus()));
		}
		Optional.ofNullable(localPlan.getParameters().getNsScale()).ifPresent(x -> nsScaleStrategy.remapNsScale(x, instance));
		LOG.info("Saving Instance.");
		instance.getInstantiatedVnfInfo().setInstantiationLevelId(localPlan.getParameters().getInstantiationLevelId());
		if (null != localPlan.getParameters().getFlavourId()) {
			instance.getInstantiatedVnfInfo().setFlavourId(localPlan.getParameters().getFlavourId());
		}
	}

	private static void setScaleStatus(final Instance instance, final Set<ScaleInfo> si, final ScaleTypeEnum scaleTypeEnum) {
		si.stream().forEach(x -> instance.getInstantiatedVnfInfo().getScaleStatus().stream()
				.filter(y -> y.getAspectId().equals(x.getAspectId()))
				.findFirst()
				.ifPresent(y -> y.setScaleLevel(getNewStep(scaleTypeEnum, y.getScaleLevel(), x.getScaleLevel()))));
	}

	private static int getNewStep(final ScaleTypeEnum scaleTypeEnum, final int orig, final int adder) {
		if (scaleTypeEnum == ScaleTypeEnum.IN) {
			final int i = orig - adder;
			return i < 0 ? 0 : i;
		}
		return orig + adder;
	}

	private static Set<ScaleInfo> copy(final Set<ScaleInfo> nsStepStatus) {
		return nsStepStatus.stream().map(x -> new ScaleInfo(x.getAspectId(), x.getScaleLevel())).collect(Collectors.toSet());
	}

	private static Set<ScaleInfo> merge(final Blueprint<? extends VimTask, ? extends Instance> plan, final Instance instance) {
		final Set<ScaleInfo> tmp = instance.getInstantiatedVnfInfo().getScaleStatus().stream()
				.filter(x -> notIn(x.getAspectId(), plan.getParameters().getScaleStatus()))
				.map(x -> new ScaleInfo(x.getAspectId(), x.getScaleLevel()))
				.collect(Collectors.toSet());
		tmp.addAll(plan.getParameters().getScaleStatus());
		return tmp;
	}

	private static boolean notIn(final String aspectId, final Set<? extends ScaleInfo> scaleInfos) {
		return scaleInfos.stream().noneMatch(x -> x.getAspectId().equals(aspectId));
	}

	private static void setResultLcmInstance(final Blueprint<?, ?> blueprint, final OrchExecutionResults<?> res) {
		if (res.getErrored().isEmpty()) {
			blueprint.setOperationStatus(OperationStatusType.COMPLETED);
		} else {
			blueprint.setOperationStatus(OperationStatusType.FAILED);
		}
		blueprint.setStateEnteredTime(OffsetDateTime.now());
	}

	public final void terminate(final UUID blueprintId) {
		instantiateShield(blueprintId, WorkflowEvent.TERMINATE_SUCCESS, WorkflowEvent.TERMINATE_FAILED);
	}

	public final void scaleToLevel(final UUID blueprintId) {
		instantiateShield(blueprintId, WorkflowEvent.SCALETOLEVEL_SUCCESS, WorkflowEvent.SCALETOLEVEL_FAILED);
	}

	public final void scale(final UUID blueprintId) {
		instantiateShield(blueprintId, WorkflowEvent.SCALE_SUCCESS, WorkflowEvent.SCALE_FAILED);
	}

	public final void jujuInstantiate(final UUID blueprintId) {

	}

	public final void jujuTerminate(final UUID blueprintId) {

	}

	private void instantiateShield(final UUID blueprintId, final WorkflowEvent success, final WorkflowEvent failure) {
		final Blueprint<? extends VimTask, ? extends Instance> blueprint = orchestrationAdapter.getBluePrint(blueprintId);
		final Instance vnfInstance = orchestrationAdapter.getInstance(blueprint.getInstance().getId());
		try {
			instantiateInnerv2(blueprint, vnfInstance);
			LOG.info("{} {} {} Success...", vnfInstance.getVnfInstanceName(), success, blueprintId);
			orchestrationAdapter.fireEvent(success, blueprintId);
		} catch (final RuntimeException e) {
			LOG.error("{} {} Failed", vnfInstance.getVnfInstanceName(), failure, e);
			onFailure(failure, blueprint, vnfInstance, e);
		}
	}

	private final void onFailure(final WorkflowEvent workflowEvent, final Blueprint<?, ?> blueprintOrig, final Instance instance, final Exception e) {
		final Blueprint<?, ?> blueprint = orchestrationAdapter.getBluePrint(blueprintOrig.getId());
		blueprint.setOperationStatus(OperationStatusType.FAILED);
		blueprint.setError(new FailureDetails(500L, e.getMessage()));
		blueprint.setStateEnteredTime(OffsetDateTime.now());
		orchestrationAdapter.save(blueprint);
		instance.setLockedBy(null);
		orchestrationAdapter.save(instance);
		orchestrationAdapter.fireEvent(workflowEvent, blueprint.getId());
	}

	private void setLiveSatus(final OrchExecutionResults<Task> res) {
		LOG.info("Creating / deleting live instances.");
		res.getSuccess().forEach(x -> {
			final Task rhe = x.getTask().getVirtualTask().getTemplateParameters();
			final ChangeType ct = rhe.getChangeType();
			if (ct == ChangeType.ADDED) {
				if ((null == rhe.getId()) || (null == rhe.getVimResourceId())) {
					LOG.warn("No vim resource or database id for: {}", x.getTask().getVirtualTask().getTemplateParameters().getToscaName());
				}
			} else if ((ct == ChangeType.REMOVED) && (null != rhe.getId()) && (null != rhe.getRemovedLiveInstance())) {
				LOG.info("Removing {} = {}", rhe.getAlias(), rhe.getId());
				orchestrationAdapter.deleteLiveInstance(rhe.getRemovedLiveInstance());
			}
		});
	}

}