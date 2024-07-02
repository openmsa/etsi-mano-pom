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

import java.net.URI;
import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ServerAdapter {
	@NotNull
	private final HttpGateway httpGateway;
	@NotNull
	private final FluxRest rest;
	@NotNull
	private final Servers server;

	public ServerAdapter(final HttpGateway httpGateway, final Servers server, final FluxRest fluxRest) {
		this.httpGateway = httpGateway;
		this.server = server;
		rest = fluxRest;
	}

	public Servers getServer() {
		return server;
	}

	public HttpGateway httpGateway() {
		return httpGateway;
	}

	public URI getUriFor(final ApiVersionType type, final String urlPart, final Map<String, Object> params) {
		final String endOfUri = UriComponentsBuilder
				.fromUriString(httpGateway.getUrlFor(type)).pathSegment(urlPart).build().toString();
		return rest.uriBuilder()
				.pathSegment(endOfUri)
				.buildAndExpand(params)
				.toUri()
				.normalize();
	}

	public FluxRest rest() {
		return rest;
	}

	public URI getUriFor(final ApiVersionType type, final String urlPart) {
		return getUriFor(type, urlPart, Map.of());
	}
}
