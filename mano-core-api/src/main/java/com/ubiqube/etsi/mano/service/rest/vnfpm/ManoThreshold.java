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
import java.util.function.BiFunction;

import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

import jakarta.annotation.Nonnull;

/**
 * Sol003 threshold.
 *
 * @author olivier
 *
 */
public class ManoThreshold {
	@Nonnull
	private final QueryParameters client;

	public ManoThreshold(final QueryParameters manoClient) {
		this.client = manoClient;
		manoClient.setFragment("threshold");
		manoClient.setQueryType(ApiVersionType.SOL003_VNFPM);
	}

	public Threshold create(final Threshold req) {
		final BiFunction<HttpGateway, Object, Object> request = (final HttpGateway httpGateway, final Object r) -> httpGateway.createVnfThresholdRequest(req);
		return (Threshold) client.createQuery()
				.setWireInClass(request)
				.setWireOutClass(HttpGateway::getVnfThresholdClass)
				.setOutClass(HttpGateway::mapToThreshold)
				.post();
	}

	public ManoThresholdId id(final UUID id) {
		return new ManoThresholdId(client, id);
	}
}
