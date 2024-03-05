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

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.PackageBase;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.nfvo.service.NsdPackageService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.OrchestrationAdapter;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.graph.WorkflowEvent;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NsOrchestrationAdapter implements OrchestrationAdapter<NsTask, NsdInstance> {
	private final NsLiveInstanceJpa liveInstanceJpa;
	private final NsBlueprintService blueprintService;
	private final NsInstanceService instanceService;
	private final NsdPackageService packageService;
	private final EventManager eventManager;

	public NsOrchestrationAdapter(final NsLiveInstanceJpa nsLiveInstanceJpa, final NsBlueprintService nsBlueprintService, final NsInstanceService nsInstanceService, final NsdPackageService nsdPackageService, final EventManager eventManager) {
		this.liveInstanceJpa = nsLiveInstanceJpa;
		this.blueprintService = nsBlueprintService;
		this.instanceService = nsInstanceService;
		this.packageService = nsdPackageService;
		this.eventManager = eventManager;
	}

	@Override
	public void createLiveInstance(final Instance instance, final String il, final Task task, final Blueprint<? extends Task, ? extends Instance> blueprint) {
		final NsLiveInstance nli = new NsLiveInstance(null, (NsTask) task, (NsBlueprint) blueprint, (NsdInstance) instance);
		liveInstanceJpa.save(nli);
	}

	@Override
	public void deleteLiveInstance(final UUID removedLiveInstanceId) {
		liveInstanceJpa.deleteById(removedLiveInstanceId);
	}

	@Override
	public @Nonnull Blueprint<NsTask, NsdInstance> getBluePrint(final UUID blueprintId) {
		return blueprintService.findById(blueprintId);
	}

	@Override
	public @Nonnull Instance getInstance(final UUID blueprintId) {
		return instanceService.findById(blueprintId);
	}

	@Override
	public PackageBase getPackage(final Instance instance) {
		final NsdInstance inst = (NsdInstance) instance;
		return packageService.findById(inst.getNsdInfo().getId());
	}

	@Override
	public Instance getInstance(final Blueprint<NsTask, NsdInstance> blueprint) {
		final NsBlueprint blue = (NsBlueprint) blueprint;
		return blue.getInstance();
	}

	@Override
	public @NotNull Blueprint<NsTask, NsdInstance> save(final Blueprint blueprint) {
		return blueprintService.save((NsBlueprint) blueprint);
	}

	@Override
	public Instance save(final Instance instance) {
		final NsdInstance nsi = (NsdInstance) instance;
		final long c = liveInstanceJpa.countByNsInstance(nsi);
		nsi.setInstantiationState(c > 0 ? InstantiationState.INSTANTIATED : InstantiationState.NOT_INSTANTIATED);
		return instanceService.save(nsi);
	}

	@Override
	public Blueprint<NsTask, NsdInstance> updateState(final Blueprint localPlan, final OperationStatusType processing) {
		return blueprintService.updateState((NsBlueprint) localPlan, OperationStatusType.PROCESSING);
	}

	@Override
	public void fireEvent(final WorkflowEvent instantiateProcessing, final UUID id) {
		final NotificationEvent notificationEvent = convert(instantiateProcessing);
		eventManager.sendNotification(notificationEvent, id, Map.of());
	}

	private static NotificationEvent convert(final WorkflowEvent instantiateProcessing) {
		return switch (instantiateProcessing) {
		case INSTANTIATE_PROCESSING -> NotificationEvent.NS_INSTANCE_CREATE;
		case INSTANTIATE_SUCCESS -> NotificationEvent.NS_INSTANTIATE;
		case INSTANTIATE_FAILED -> NotificationEvent.NS_INSTANTIATE;
		case SCALE_FAILED -> NotificationEvent.NS_SCALE;
		case SCALE_SUCCESS -> NotificationEvent.NS_SCALE;
		case SCALETOLEVEL_FAILED -> NotificationEvent.NS_SCALE;
		case SCALETOLEVEL_SUCCESS -> NotificationEvent.NS_SCALE;
		case TERMINATE_FAILED -> NotificationEvent.NS_TERMINATE;
		case TERMINATE_SUCCESS -> NotificationEvent.NS_TERMINATE;
		default -> throw new GenericException("Unknow event: " + instantiateProcessing);
		};
	}

}
