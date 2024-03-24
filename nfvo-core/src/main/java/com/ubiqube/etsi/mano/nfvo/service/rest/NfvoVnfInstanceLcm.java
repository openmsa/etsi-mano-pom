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
package com.ubiqube.etsi.mano.nfvo.service.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfInstanceLcm;
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
import com.ubiqube.etsi.mano.service.VnfmService;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;

import jakarta.validation.constraints.NotNull;

/**
 * This class Is preparing querying a VNFM using HHTP.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@ConditionalOnMissingBean(VnfmService.class)
@Service
public class NfvoVnfInstanceLcm implements VnfInstanceLcm {
	private final ManoClientFactory manoClientFactory;

	public NfvoVnfInstanceLcm(final ManoClientFactory manoClientFactory) {
		this.manoClientFactory = manoClientFactory;
	}

	@Override
	public List<VnfInstance> get(final Servers servers, final MultiValueMap<String, String> requestParams) {
		return manoClientFactory.getClient(servers)
				.vnfInstance()
				.list(requestParams);
	}

	@Override
	public VnfInstance post(final Servers servers, final String vnfdId, final String vnfInstanceName, final String vnfInstanceDescription) {
		return manoClientFactory.getClient(servers)
				.vnfInstance()
				.create(vnfdId, vnfInstanceName, vnfInstanceDescription);
	}

	@Override
	public void delete(final Servers servers, final UUID vnfInstanceId) {
		manoClientFactory.getClient(servers)
				.vnfInstance().id(vnfInstanceId)
				.delete();
	}

	@Override
	public VnfBlueprint instantiate(final Servers servers, final UUID vnfInstanceId, final VnfInstantiate instantiateVnfRequest) {
		return manoClientFactory.getClient(servers)
				.vnfInstance().id(vnfInstanceId)
				.instantiate(instantiateVnfRequest);
	}

	@Override
	public VnfBlueprint terminate(final Servers servers, final UUID vnfInstanceId, final CancelModeTypeEnum terminationType, final Integer gracefulTerminationTimeout) {
		return manoClientFactory.getClient(servers)
				.vnfInstance().id(vnfInstanceId)
				.terminate(terminationType, gracefulTerminationTimeout);
	}

	@Override
	public VnfBlueprint scaleToLevel(final Servers servers, final UUID vnfInstanceId, final VnfScaleToLevelRequest scaleVnfToLevelRequest) {
		return manoClientFactory.getClient(servers)
				.vnfInstance().id(vnfInstanceId)
				.scaleToLevel(scaleVnfToLevelRequest);
	}

	@Override
	public VnfBlueprint scale(final Servers servers, final UUID vnfInstanceId, final VnfScaleRequest scaleVnfRequest) {
		return manoClientFactory.getClient(servers)
				.vnfInstance().id(vnfInstanceId)
				.scale(scaleVnfRequest);
	}

	@Override
	public VnfBlueprint heal(final Servers servers, final UUID vnfInstanceId, final VnfHealRequest vnfHealRequest) {
		return manoClientFactory.getClient(servers)
				.vnfInstance().id(vnfInstanceId)
				.heal(vnfHealRequest);
	}

	@Override
	public VnfBlueprint operate(final Servers servers, final UUID vnfInstanceId, final VnfOperateRequest operateVnfRequest) {
		return manoClientFactory.getClient(servers)
				.vnfInstance().id(vnfInstanceId)
				.operate(operateVnfRequest);
	}

	@Override
	public VnfBlueprint vnfLcmOpOccsGet(final Servers servers, @NotNull final UUID id) {
		return manoClientFactory.getClient(servers)
				.vnfLcmOpOccs()
				.id(id)
				.find();
	}

	@Override
	public VnfBlueprint changeExtConn(final Servers servers, @NotNull final UUID vnfInstanceId, final ChangeExtVnfConnRequest cevcr) {
		return manoClientFactory.getClient(servers)
				.vnfInstance().id(vnfInstanceId)
				.changeExtConn(cevcr);
	}

	@Override
	public VnfInstance findById(final Servers servers, final String vnfInstance) {
		return manoClientFactory.getClient(servers)
				.vnfInstance().id(UUID.fromString(vnfInstance))
				.find();
	}

	@Override
	public List<VnfBlueprint> findByVnfInstanceId(final Servers servers, @NotNull final UUID id) {
		return manoClientFactory.getClient(servers)
				.vnfLcmOpOccs().list();
	}

}
