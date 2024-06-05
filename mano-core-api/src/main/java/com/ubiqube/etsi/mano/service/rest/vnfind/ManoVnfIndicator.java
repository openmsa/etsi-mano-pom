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
package com.ubiqube.etsi.mano.service.rest.vnfind;

import java.util.List;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

import jakarta.annotation.Nonnull;

public class ManoVnfIndicator {
	@Nonnull
	private final QueryParameters client;

	public ManoVnfIndicator(final QueryParameters manoClient) {
		this.client = manoClient;
		manoClient.setFragment("vnfind");
		manoClient.setQueryType(ApiVersionType.SOL003_VNFIND);
	}

	public ManoVnfIndicatorSubscription subscription() {
		return new ManoVnfIndicatorSubscription(client);
	}

	public List<Object> list() {
		return client.createQuery()
				.setInClassList(HttpGateway::getVnfIndicatorClassList)
				.setOutClass(HttpGateway::mapToVnfIndicator)
				.getList();
	}

	public ManoVnfIndicatorId id(final UUID id) {
		return new ManoVnfIndicatorId(client, id);
	}

}
