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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoServerId {

	private static final Logger LOG = LoggerFactory.getLogger(ManoServerId.class);

	private final ManoClient client;

	public ManoServerId(final ManoClient client, final UUID id) {
		this.client = client;
		client.setObjectId(id);
	}

	public void delete(final String root) {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(root, "admin/server/{id}");
		server.rest().delete(uri, Object.class, null);
	}

	public @Nullable Servers find(final String root) {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(root, "admin/server/{id}");
		final ResponseEntity<Servers> resp = server.rest().getWithReturn(uri, Servers.class, null);
		if (resp == null) {
			return null;
		}
		return resp.getBody();
	}

	public Servers waitForServer(final String root) {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				LOG.warn("Interrupted!", e);
				Thread.currentThread().interrupt();
			}
			final Servers srv = find(root);
			if (srv == null) {
				throw new GenericException("Unable to get server: " + root);
			}
			final PlanStatusType state = srv.getServerStatus();
			LOG.debug("state {}", state);
			if ((state == PlanStatusType.FAILED) || (state == PlanStatusType.SUCCESS)) {
				return srv;
			}
		}
	}

	private URI buildUri(final String urlRoot, final String url) {
		final Map<String, Object> uriParams = Optional.ofNullable(client.getObjectId()).map(x -> Map.of("id", (Object) x.toString())).orElseGet(Map::of);
		return UriComponentsBuilder.fromHttpUrl(urlRoot).pathSegment(url).buildAndExpand(uriParams).toUri();
	}
}
