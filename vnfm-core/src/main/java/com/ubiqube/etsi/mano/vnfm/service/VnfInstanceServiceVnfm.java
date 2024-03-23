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
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.service.VnfInstanceGatewayService;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.vnflcm.VnfLcmExtractor;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfInstanceServiceVnfm implements VnfInstanceGatewayService {

	private final VnfInstanceJpa vnfInstanceJpa;

	private final VnfLiveInstanceJpa vnfLiveInstanceJpa;

	private final List<VnfLcmExtractor> extractors;

	public VnfInstanceServiceVnfm(final VnfInstanceJpa vnfInstanceJpa, final VnfLiveInstanceJpa vnfLiveInstanceJpa, final List<VnfLcmExtractor> extractors) {
		this.vnfInstanceJpa = vnfInstanceJpa;
		this.vnfLiveInstanceJpa = vnfLiveInstanceJpa;
		this.extractors = extractors;
	}

	@Override
	public VnfInstance findById(final UUID id) {
		final VnfInstance inst = vnfInstanceJpa.findById(id).orElseThrow(() -> new NotFoundException("Could not find VNF instance: " + id));
		final BlueprintParameters vnfInfo = Objects.requireNonNull(inst.getInstantiatedVnfInfo());
		final List<VnfLiveInstance> vli = vnfLiveInstanceJpa.findByVnfInstance(inst);
		extractors.forEach(x -> x.extract(inst, vnfInfo, vli));
		inst.setInstantiationState(isLive(id));
		return inst;
	}

	private InstantiationState isLive(final UUID id) {
		return vnfLiveInstanceJpa.findByVnfInstanceId(id).isEmpty() ? InstantiationState.NOT_INSTANTIATED : InstantiationState.INSTANTIATED;
	}

}
