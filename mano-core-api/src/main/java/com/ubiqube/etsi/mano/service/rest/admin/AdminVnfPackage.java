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
package com.ubiqube.etsi.mano.service.rest.admin;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriComponentsBuilder;

import com.ubiqube.etsi.mano.dao.mano.dto.VnfPackageDto;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

public class AdminVnfPackage {
	private final QueryParameters client;

	public AdminVnfPackage(final QueryParameters client) {
		this.client = client;
	}

	public List<VnfPackageDto> list(final String root) {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(root, "vnfm-admin/vnf-package");
		final ParameterizedTypeReference<List<VnfPackageDto>> myBean = new ParameterizedTypeReference<>() {
			//
		};
		return server.rest().get(uri, myBean, null);
	}

	public void delete(final String root, final UUID vnfPkgId) {
		client.setObjectId(vnfPkgId);
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(root, "vnfm-admin/vnf-package/{id}");
		server.rest().delete(uri, String.class, null);
	}

	public VnfPackageDto vnfmGetVnfPackage(final String root, final UUID vnfd) {
		client.setObjectId(vnfd);
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(root, "vnfm-admin/vnf-package/{id}");
		return server.rest().get(uri, VnfPackageDto.class, null);
	}

	private URI buildUri(final String urlRoot, final String url) {
		final Map<String, Object> uriParams = Optional.ofNullable(client.getObjectId()).map(x -> Map.of("id", (Object) x.toString())).orElseGet(Map::of);
		return UriComponentsBuilder.fromHttpUrl(urlRoot).pathSegment(url).buildAndExpand(uriParams).toUri();
	}

}
