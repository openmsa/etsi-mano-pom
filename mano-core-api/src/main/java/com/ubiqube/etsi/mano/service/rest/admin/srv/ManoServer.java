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
package com.ubiqube.etsi.mano.service.rest.admin.srv;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriComponentsBuilder;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoServer {

	private final QueryParameters client;

	public ManoServer(final QueryParameters client) {
		this.client = client;
	}

	public List<Servers> list(final String root) {
		final ParameterizedTypeReference<List<Servers>> res = new ParameterizedTypeReference<>() {
			// Nothing.
		};
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(root, "admin/server");
		return server.rest().get(uri, res, null);
	}

	private URI buildUri(final String urlRoot, final String url) {
		final Map<String, Object> uriParams = Optional.ofNullable(client.getObjectId()).map(x -> Map.of("id", (Object) x.toString())).orElseGet(Map::of);
		return UriComponentsBuilder.fromHttpUrl(urlRoot).pathSegment(url).buildAndExpand(uriParams).toUri();
	}

	public @Nullable Servers create(final Servers srv, final String root) {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(root, "admin/server");
		return server.rest().post(uri, srv, Servers.class, null);
	}

	public ManoServerId id(final UUID id) {
		return new ManoServerId(client, id);
	}

}
