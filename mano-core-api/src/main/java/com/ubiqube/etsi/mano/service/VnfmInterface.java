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
package com.ubiqube.etsi.mano.service;

import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;

public interface VnfmInterface {

	VnfInstance createVnfInstance(Servers servers, String vnfdId, String vnfInstanceDescription, String vnfInstanceName);

	VnfBlueprint vnfInstatiate(Servers servers, String vnfInstanceId, VnfInstantiate instantiateVnfRequest);

	VnfBlueprint vnfLcmOpOccsGet(Servers servers, UUID id);

	VnfBlueprint vnfTerminate(Servers servers, String nsInstanceId);

	VnfBlueprint vnfScale(Servers servers, UUID vnfInstanceId, VnfScaleRequest vnfScaleRequest);

	VnfBlueprint vnfHeal(Servers servers, UUID vnfInstanceId, VnfHealRequest vnfHealRequest);

	VnfInstance getVnfInstance(Servers servers, String vnfInstance);

	void delete(Servers servers, String vnfInstance);

}
