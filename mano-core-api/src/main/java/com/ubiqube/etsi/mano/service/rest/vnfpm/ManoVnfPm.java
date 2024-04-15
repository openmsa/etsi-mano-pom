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

import java.util.function.BiFunction;

import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

/**
 * Sol003 VNF PM.
 *
 * @author olivier
 *
 */
public class ManoVnfPm {
	private final QueryParameters client;

	public ManoVnfPm(final QueryParameters manoClient) {
		this.client = manoClient;
		manoClient.setFragment("pm_jobs");
		manoClient.setQueryType(ApiVersionType.SOL003_VNFPM);
	}

	public ManoThreshold threshold() {
		return new ManoThreshold(client);
	}

	public PmJob create(final PmJob pmJob) {
		final BiFunction<HttpGateway, Object, Object> request = (final HttpGateway httpGateway, Object r) -> httpGateway.createVnfPmJobRequest(pmJob);
		return (PmJob) client.createQuery()
				.setWireInClass(request)
				.setWireOutClass(HttpGateway::getVnfPmJobClass)
				.setOutClass(HttpGateway::mapToPmJob)
				.post();
	}
}
