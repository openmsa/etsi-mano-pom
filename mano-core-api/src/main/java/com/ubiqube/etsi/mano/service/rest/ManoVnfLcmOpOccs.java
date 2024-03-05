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
package com.ubiqube.etsi.mano.service.rest;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoVnfLcmOpOccs {

	private static final Logger LOG = LoggerFactory.getLogger(ManoVnfLcmOpOccs.class);

	private final ManoClient client;

	public ManoVnfLcmOpOccs(final ManoClient client, final UUID id) {
		this.client = client;
		client.setQueryType(ApiVersionType.SOL003_VNFLCM);
		client.setObjectId(id);
		client.setFragment("vnf_lcm_op_occs");
	}

	public ManoVnfLcmOpOccs(final ManoClient manoClient) {
		this(manoClient, null);
	}

	public List<VnfBlueprint> list() {
		return client.createQuery()
				.setInClassList(HttpGateway::getListVnfLcmOpOccs)
				.setOutClass(VnfBlueprint.class)
				.getList();
	}

	public VnfBlueprint find() {
		client.setFragment("vnf_lcm_op_occs/{id}");
		return client.createQuery()
				.setWireOutClass(HttpGateway::getVnfLcmOpOccs)
				.setOutClass(VnfBlueprint.class)
				.getSingle();
	}

	public VnfBlueprint waitOnboading() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				LOG.warn("Interrupted!", e);
				Thread.currentThread().interrupt();
			}
			final VnfBlueprint pkg = find();
			final OperationStatusType state = pkg.getOperationStatus();
			LOG.debug("state {}", state);
			if ((state != OperationStatusType.ROLLING_BACK) &&
					(state != OperationStatusType.PROCESSING) &&
					(state != OperationStatusType.STARTED) &&
					(state != OperationStatusType.STARTING)) {
				return pkg;
			}
		}
	}
}
