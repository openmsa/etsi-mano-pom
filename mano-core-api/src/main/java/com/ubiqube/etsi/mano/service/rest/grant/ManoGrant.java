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
package com.ubiqube.etsi.mano.service.rest.grant;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

import jakarta.annotation.Nonnull;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoGrant {

	private static final Logger LOG = LoggerFactory.getLogger(ManoGrant.class);
	private static final Pattern UUID_REGEXP = Pattern.compile("(?<uuid>[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12})$");
	@Nonnull
	private final QueryParameters client;
	private UUID id;

	public ManoGrant(final QueryParameters manoClient) {
		this.client = manoClient;
		client.setFragment("grants");
		manoClient.setQueryType(ApiVersionType.SOL003_GRANT);
	}

	public ManoGrant(final QueryParameters manoClient, final UUID id) {
		this.client = manoClient;
		client.setObjectId(id);
		client.setFragment("grants/{id}");
		manoClient.setQueryType(ApiVersionType.SOL003_GRANT);
		this.id = id;
	}

	public ManoGrant id(final UUID id) {
		return new ManoGrant(client, id);
	}

	public GrantResponse find() {
		final ResponseEntity<?> resp = client.createQuery()
				.setWireOutClass(HttpGateway::getGrantResponse)
				.setOutClass(HttpGateway::mapToGrantResponse)
				.getRaw();
		return buildResponse(resp, Objects.requireNonNull(id));
	}

	public GrantResponse create(final GrantResponse grant) {
		final BiFunction<HttpGateway, Object, Object> request = (final HttpGateway httpGateway, final Object r) -> httpGateway.createGrantRequest(grant);
		final ResponseEntity<?> resp = client.createQuery()
				.setWireInClass(request)
				.setWireOutClass(HttpGateway::getGrantResponse)
				.setOutClass(HttpGateway::mapToGrantResponse)
				.postRaw();
		GrantResponse respCreate = handleLocation(resp);
		final ManoGrant manoId = new ManoGrant(client, respCreate.getId());
		while (Boolean.FALSE.equals(respCreate.getAvailable())) {
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				LOG.debug("", e);
				Thread.currentThread().interrupt();
			}
			respCreate = manoId.find();
		}
		return respCreate;
	}

	private static GrantResponse handleLocation(final ResponseEntity<?> resp) {
		final Optional<List<String>> loc = Optional.ofNullable(resp.getHeaders().get("Location"));
		if (!loc.isPresent()) {
			throw new GenericException("Grant post received a ACCEPTED response with no Location header");
		}
		final Matcher m = UUID_REGEXP.matcher(loc.get().get(0));
		m.find();
		final String uuid = m.group("uuid");
		final GrantResponse grants = new GrantResponse();
		grants.setId(UUID.fromString(uuid));
		grants.setAvailable(Boolean.FALSE);
		return grants;
	}

	private GrantResponse buildResponse(final ResponseEntity<?> resp, final UUID grantId) {
		GrantResponse grants = new GrantResponse();
		if (resp.getStatusCode().value() == 202) {
			grants.setId(grantId);
			grants.setAvailable(Boolean.FALSE);
		} else {
			grants = (GrantResponse) client.createQuery()
					.setWireOutClass(HttpGateway::getGrantResponse)
					.setOutClass(HttpGateway::mapToGrantResponse)
					.getSingle();
			grants.setAvailable(true);
		}
		return grants;
	}

}
