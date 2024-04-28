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
package com.ubiqube.etsi.mano.service.rest.admin;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;

import com.ubiqube.etsi.mano.dao.mano.dto.VnfPackageDto;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;
import com.ubiqube.etsi.mano.service.rest.admin.srv.ManoServer;
import com.ubiqube.etsi.mano.service.rest.admin.vim.ManoVim;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoAdmin {

	private final QueryParameters client;

	public ManoAdmin(final QueryParameters manoClient) {
		this.client = manoClient;
	}

	public ManoServer server() {
		return new ManoServer(client);
	}

	public ManoVim vim() {
		return new ManoVim(client);
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
