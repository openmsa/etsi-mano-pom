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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;

import jakarta.annotation.Nullable;

public interface VnfInstanceService {

	VnfInstance findBVnfInstanceyVnfPackageId(final NsdInstance nsdInstance, final UUID vnfPackageId);

	VnfInstance save(final VnfInstance vnfInstance);

	void delete(final UUID vnfInstanceId);

	VnfLiveInstance findLiveInstanceByInstantiated(final UUID id);

	VnfLiveInstance save(final VnfLiveInstance vli);

	Optional<VnfLiveInstance> findLiveInstanceById(final UUID vnfLiveInstance);

	void deleteLiveInstanceById(final UUID id);

	List<VnfLiveInstance> getLiveComputeInstanceOf(VnfInstance vnfInstance);

	List<VnfInstance> query(final String filter);

	boolean isInstantiate(UUID id);

	VnfInstance vnfLcmPatch(VnfInstance vnfInstance, String body, @Nullable String ifMatch);

	<U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Class<U> clazz, @Nullable final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink);

	List<VnfLiveInstance> findByResourceIdIn(List<String> objectInstanceIds);

	VnfInstance findById(UUID id);

	List<VnfLiveInstance> findByVnfInstanceId(final UUID id);
}
