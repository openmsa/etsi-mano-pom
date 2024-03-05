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
package com.ubiqube.etsi.mano.controller.vnflcm;

import java.util.List;
import java.util.UUID;

import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;

import jakarta.annotation.Nullable;

/**
 * NFVO+VNFM &amp; VNFM Implementation.
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public interface VnfInstanceLcm {

	List<VnfInstance> get(@Nullable Servers servers, final MultiValueMap<String, String> queryParameters);

	VnfInstance post(@Nullable Servers servers, final String vnfdId, final String vnfInstanceName, final String vnfInstanceDescription);

	void delete(@Nullable Servers servers, final UUID vnfInstanceId);

	VnfBlueprint instantiate(@Nullable Servers servers, final UUID vnfInstanceId, final VnfInstantiate instantiateVnfRequest);

	VnfBlueprint terminate(@Nullable Servers servers, final UUID vnfInstanceId, @Nullable final CancelModeTypeEnum terminationType, @Nullable final Integer gracefulTerminationTimeout);

	VnfBlueprint scaleToLevel(@Nullable Servers servers, final UUID vnfInstanceId, final VnfScaleToLevelRequest scaleVnfToLevelRequest);

	VnfBlueprint scale(@Nullable Servers servers, final UUID vnfInstanceId, final VnfScaleRequest scaleVnfRequest);

	VnfBlueprint heal(@Nullable Servers servers, final UUID vnfInstanceId, final VnfHealRequest healVnfRequest);

	VnfBlueprint operate(@Nullable Servers servers, final UUID vnfInstanceId, final VnfOperateRequest operateVnfRequest);

	VnfBlueprint vnfLcmOpOccsGet(@Nullable Servers servers, UUID id);

	VnfBlueprint changeExtConn(@Nullable Servers servers, UUID vnfInstanceId, ChangeExtVnfConnRequest cevcr);

	VnfInstance findById(@Nullable Servers servers, String vnfInstance);

	List<VnfBlueprint> findByVnfInstanceId(@Nullable Servers servers, UUID id);

}
