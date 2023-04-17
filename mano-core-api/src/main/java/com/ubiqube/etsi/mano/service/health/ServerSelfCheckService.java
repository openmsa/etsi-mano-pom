/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.service.health;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;

import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.rest.model.AuthType;
import com.ubiqube.etsi.mano.service.rest.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.rest.model.OAuth2GrantType;
import com.ubiqube.etsi.mano.service.rest.model.ServerConnection;

@Component
@Profile("!test")
public class ServerSelfCheckService {
	private static final String OAUTH2 = "OAuth2";
	private static final String OK = "\033[1;32mOK\033[0m";
	private static final String WARN = "\033[1;33mWARN\033[0m";
	private static final Logger LOG = LoggerFactory.getLogger(ServerSelfCheckService.class);

	private final String secret;

	private final String authUrl;

	private final String user;

	private final String frontendUrl;

	private final List<Supplier<Result>> checkFunctions;

	public ServerSelfCheckService(final Environment env) {
		authUrl = env.getProperty("mano.swagger-o-auth2");
		user = env.getProperty("keycloak.resource");
		secret = env.getProperty("keycloak.credentials.secret");
		frontendUrl = Objects.requireNonNull(env.getProperty("mano.frontend-url"));
		checkFunctions = new ArrayList<>();
		checkFunctions.add(this::checkOAuth2);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReadyEvent() {
		if ((secret == null) || (authUrl == null) || (user == null)) {
			LOG.warn("Skipping auth credential checks");
		}
		LOG.info("Checking Server configuration:");
		checkFunctions.forEach(this::displayResult);
	}

	private void displayResult(final Supplier<Result> supp) {
		final Result res = supp.get();
		final String f = res.func();
		final String code = res.code();
		final String errors = res.errors();
		if (OK.equals(res.code())) {
			LOG.info("  - {}: {}", f, code);
		} else {
			LOG.info("  - {}: {}, {}", f, code, errors);
		}
	}

	private Result checkOAuth2() {
		final FluxRest fr = createFluxRest();
		final UriComponents uri = fr.uriBuilder().path("/admin/sink/self").build();
		final ResponseEntity<String> res = fr.getWithReturn(uri.toUri(), String.class, null);
		if (res == null) {
			return new Result(OAUTH2, WARN, "Sink URL return null.");
		}
		if (res.getStatusCode() == HttpStatus.NO_CONTENT) {
			return new Result(OAUTH2, OK, null);
		}
		return new Result(OAUTH2, WARN, "Unexpected status code: " + res.getStatusCode());
	}

	private FluxRest createFluxRest() {
		final ServerConnection server = ServerConnection.serverBuilder()
				.url(frontendUrl.replace("/sol003", ""))
				.authentification(AuthentificationInformations.builder()
						.authType(List.of(AuthType.OAUTH2_CLIENT_CREDENTIALS))
						.authParamOauth2(AuthParamOauth2.builder()
								.clientId(user)
								.clientSecret(secret)
								.grantType(OAuth2GrantType.CLIENT_CREDENTIAL)
								.tokenEndpoint(authUrl)
								.build())
						.build())
				.build();
		return new FluxRest(server);
	}
}

record Result(String func, String code, String errors) {
	//
}
