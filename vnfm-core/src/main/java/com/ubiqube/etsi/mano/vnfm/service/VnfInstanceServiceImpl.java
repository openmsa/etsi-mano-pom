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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.service.Patcher;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfInstanceServiceImpl implements VnfInstanceService {

	private final VnfInstanceJpa vnfInstanceJpa;

	private final VnfLiveInstanceJpa vnfLiveInstanceJpa;

	private final Patcher patcher;

	private final EventManager eventManager;

	private final SearchableService searchableService;

	public VnfInstanceServiceImpl(final VnfInstanceJpa vnfInstanceJpa, final VnfLiveInstanceJpa vnfLiveInstance,
			final Patcher patcher, final EventManager eventManager, final SearchableService searchableService) {
		this.vnfInstanceJpa = vnfInstanceJpa;
		this.vnfLiveInstanceJpa = vnfLiveInstance;
		this.patcher = patcher;
		this.eventManager = eventManager;
		this.searchableService = searchableService;
	}

	@Override
	public VnfInstance findBVnfInstanceyVnfPackageId(final NsdInstance nsdInstance, final UUID vnfPackageId) {
		return vnfInstanceJpa.findByVnfPkg_IdAndNsInstance_Id(vnfPackageId, nsdInstance.getId()).orElseThrow(() -> new NotFoundException("Could not find vnf=" + vnfPackageId + " nsInstance=" + nsdInstance.getId()));
	}

	@Override
	public VnfInstance save(final VnfInstance vnfInstance) {
		return vnfInstanceJpa.save(vnfInstance);
	}

	@Override
	@Transactional
	public void delete(final UUID vnfInstanceId) {
		final VnfInstance entity = vnfInstanceJpa.findById(vnfInstanceId).orElseThrow(() -> new NotFoundException("Vnf Instance " + vnfInstanceId + " not found."));
		vnfInstanceJpa.delete(entity);
	}

	@Override
	public VnfLiveInstance findLiveInstanceByInstantiated(final UUID id) {
		return vnfLiveInstanceJpa.findByTaskId(id);
	}

	@Override
	public VnfLiveInstance save(final VnfLiveInstance vli) {
		return vnfLiveInstanceJpa.save(vli);
	}

	@Override
	public Optional<VnfLiveInstance> findLiveInstanceById(final UUID removedInstantiated) {
		return vnfLiveInstanceJpa.findById(removedInstantiated);
	}

	@Override
	public void deleteLiveInstanceById(final UUID id) {
		vnfLiveInstanceJpa.deleteById(id);
	}

	@Override
	public List<VnfLiveInstance> getLiveComputeInstanceOf(final VnfInstance vnfInstance) {
		return vnfLiveInstanceJpa.findByVnfInstanceAndTaskVnfComputeNotNull(vnfInstance);
	}

	@Override
	public List<VnfInstance> query(final String filter) {
		return searchableService.query(VnfInstance.class, filter);
	}

	@Override
	public boolean isInstantiate(final UUID id) {
		return 0 == vnfInstanceJpa.countByVnfPkgId(id);
	}

	@Override
	public VnfInstance vnfLcmPatch(final VnfInstance vnfInstance, final String body, final @Nullable String ifMatch) {
		if ((ifMatch != null) && !ifMatch.equals(vnfInstance.getVersion() + "")) {
			throw new PreConditionException(ifMatch + " does not match " + vnfInstance.getVersion());
		}
		patcher.patch(body, vnfInstance);
		eventManager.sendNotification(NotificationEvent.VNF_INSTANCE_CHANGED, vnfInstance.getId(), Map.of());
		return vnfInstanceJpa.save(vnfInstance);
	}

	@Override
	public List<VnfLiveInstance> findByResourceIdIn(final List<String> objectInstanceIds) {
		return vnfLiveInstanceJpa.findByResourceIdIn(objectInstanceIds);
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<VnfInstance, U> mapper, final @Nullable String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink) {
		return searchableService.search(VnfInstance.class, requestParams, mapper, excludeDefaults, mandatoryFields, makeLink, List.of());
	}

	@Override
	public VnfInstance findById(@NotNull final UUID safeUUID) {
		return vnfInstanceJpa.findById(safeUUID).orElseThrow(() -> new GenericException("Unable to find VNF instance: " + safeUUID));
	}

	@Override
	public List<VnfLiveInstance> findByVnfInstanceId(final UUID id) {
		return vnfLiveInstanceJpa.findByVnfInstanceId(id);
	}
}
