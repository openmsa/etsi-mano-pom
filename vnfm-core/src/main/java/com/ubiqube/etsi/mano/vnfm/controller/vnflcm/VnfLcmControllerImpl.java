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
package com.ubiqube.etsi.mano.vnfm.controller.vnflcm;

import static com.ubiqube.etsi.mano.Constants.ensureFailedTemp;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfLcmService;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfLcmControllerImpl implements VnfLcmController {
	private final VnfLcmService vnfLcmOpOccsRepository;
	private final VnfInstanceService vnfInstanceService;
	private final SearchableService searchableService;

	public VnfLcmControllerImpl(final VnfLcmService vnfLcmOpOccsRepository, final VnfInstanceService vnfInstanceService, final SearchableService searchableService) {
		this.vnfLcmOpOccsRepository = vnfLcmOpOccsRepository;
		this.vnfInstanceService = vnfInstanceService;
		this.searchableService = searchableService;
	}

	@Override
	public VnfBlueprint vnfLcmOpOccsVnfLcmOpOccIdGet(final UUID id) {
		return vnfLcmOpOccsRepository.findById(id);
	}

	@Override
	public void failed(final UUID id) {
		final VnfBlueprint lcm = vnfLcmOpOccsRepository.findById(id);
		final VnfInstance instance = lcm.getVnfInstance();
		ensureFailedTemp(lcm);
		instance.setLockedBy(null);
		vnfInstanceService.save(instance);
		lcm.setOperationStatus(OperationStatusType.FAILED);
		vnfLcmOpOccsRepository.save(lcm);
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<VnfBlueprint, U> mapper, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink, final Class<?> frontClass) {
		return searchableService.search(VnfBlueprint.class, requestParams, mapper, excludeDefaults, mandatoryFields, makeLink, List.of(), frontClass);
	}
}
