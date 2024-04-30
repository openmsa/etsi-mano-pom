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
package com.ubiqube.etsi.mano.service.rest.nspkg;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.ManoQueryBuilder;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

import jakarta.annotation.Nonnull;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoNsPackage {
	@Nonnull
	private final QueryParameters client;

	public ManoNsPackage(final QueryParameters manoClient) {
		this.client = manoClient;
		client.setQueryType(ApiVersionType.SOL005_NSD);
		client.setFragment("/ns_descriptors");
	}

	public ManoNsPackageId id(final UUID id) {
		return new ManoNsPackageId(client, id);
	}

	public List<NsdPackage> list() {
		final ManoQueryBuilder<Object, NsdPackage> q = client.createQuery();
		return q
				.setInClassList(HttpGateway::getNsdPackageClassList)
				.setOutClass(HttpGateway::mapToNsdPackage)
				.getList();
	}

	public NsdPackage create(final Map<String, Object> userDefinedData) {
		final BiFunction<HttpGateway, Object, Object> f2 = (httpGateway, r) -> httpGateway.createNsdPackageRequest(userDefinedData);
		return (NsdPackage) client.createQuery()
				.setWireInClass(f2)
				.setWireOutClass(HttpGateway::getNsdPackageClass)
				.setOutClass(HttpGateway::mapToNsdPackage)
				.post();
	}

}
