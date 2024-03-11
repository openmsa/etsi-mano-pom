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
package com.ubiqube.etsi.mano.service.rest.vnfpm;

import java.util.UUID;
import java.util.function.Function;

import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.ManoClient;

/**
 * Sol003 threshold.
 *
 * @author olivier
 *
 */
public class ManoThreshold {

	private final ManoClient client;

	public ManoThreshold(final ManoClient manoClient) {
		this.client = manoClient;
		manoClient.setFragment("threshold");
		manoClient.setQueryType(ApiVersionType.SOL003_VNFPM);
	}

	public Threshold create(final Threshold req) {
		final Function<HttpGateway, Object> request = (final HttpGateway httpGateway) -> httpGateway.createVnfThresholdRequest(req);
		return client.createQuery(request)
				.setWireOutClass(HttpGateway::getVnfInstanceClass)
				.setOutClass(Threshold.class)
				.post();
	}

	public ManoThresholdId id(final UUID id) {
		return new ManoThresholdId(client, id);
	}
}
