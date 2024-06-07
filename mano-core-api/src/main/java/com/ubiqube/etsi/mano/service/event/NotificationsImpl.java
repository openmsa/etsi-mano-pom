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
package com.ubiqube.etsi.mano.service.event;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

import jakarta.annotation.Nullable;

@Service
public class NotificationsImpl implements Notifications {
	/** Logger instance. */
	private static final Logger LOG = LoggerFactory.getLogger(NotificationsImpl.class);
	/** JSON mapper. */
	private final ObjectMapper mapper;

	public NotificationsImpl(final ObjectMapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * Send a notification Object to the _uri
	 *
	 * @param obj    The JSON Onject.
	 * @param uri    The complete URL.
	 * @param server A Servers object.
	 */
	@Override
	public void doNotification(final Object obj, final URI uri, final ServerAdapter server) {
		String content;
		try {
			content = mapper.writeValueAsString(obj);
		} catch (final JsonProcessingException e) {
			throw new GenericException(e);
		}
		LOG.debug("Message :\n{}", content);
		sendRequest(content, server, uri, null);
	}

	private static void sendRequest(final String content, final ServerAdapter server, final URI uri, @Nullable final String version) {
		final var rest = server.rest();
		LOG.info("Sending to {}", uri);
		rest.post(uri, content, Void.class, version);
		LOG.debug("Event Sent to {}", uri);
	}

	@Override
	public void check(final ServerAdapter server, final URI uri) {
		final var rest = server.rest();
		doRealCheck(rest, uri);
	}

	private static void doRealCheck(final FluxRest rest, final URI uri) {
		final ResponseEntity<Void> status = rest.getWithReturn(uri, Void.class, null);
		if ((null == status) || (status.getStatusCode() != HttpStatus.NO_CONTENT)) {
			final String code = getStatucCode(status);
			LOG.error("Status response must be 204 by was: {} <=> {}", code, uri);
			throw new GenericException("HttpClient got an error: " + code + ", must be 204");
		}
	}

	private static String getStatucCode(@Nullable final ResponseEntity<Void> status) {
		return Optional.ofNullable(status)
				.map(ResponseEntity::getStatusCode)
				.map(HttpStatusCode::toString)
				.orElse("[No Code]");
	}

	@Override
	public void check(final AuthentificationInformations authentication, final URI callbackUri) {
		final Servers server = new Servers(authentication, callbackUri);
		final FluxRest rest = new FluxRest(server);
		doRealCheck(rest, callbackUri);
	}

}
