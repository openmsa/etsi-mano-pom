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
import java.util.function.Function;

import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoVnfInstance {
	private final ManoClient client;

	public ManoVnfInstance(final ManoClient manoClient) {
		this.client = manoClient;
		manoClient.setFragment("vnf_instances");
		manoClient.setQueryType(ApiVersionType.SOL003_VNFLCM);
	}

	public ManoVnfInstanceId id(final UUID vnfInstanceId) {
		return new ManoVnfInstanceId(client, vnfInstanceId);
	}

	public List<VnfInstance> list(final MultiValueMap<String, String> requestParams) {
		return client.createQuery()
				.setInClassList(HttpGateway::getVnfInstanceListParam)
				.setOutClass(VnfInstance.class)
				.getList();
	}

	public <T> T create(final String vnfdId, final String vnfInstanceName, final String vnfInstanceDescription) {
		final Function<HttpGateway, Object> request = (final HttpGateway httpGateway) -> httpGateway.createVnfInstanceRequest(vnfdId, vnfInstanceName, vnfInstanceDescription);
		return client.createQuery(request)
				.setWireOutClass(HttpGateway::getVnfInstanceClass)
				.setOutClass(VnfInstance.class)
				.post();
	}

	public ManoVnfInstanceSubscription subscription() {
		return new ManoVnfInstanceSubscription(client);
	}
}
