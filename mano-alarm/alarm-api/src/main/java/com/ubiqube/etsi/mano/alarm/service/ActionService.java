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
package com.ubiqube.etsi.mano.alarm.service;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.alarm.entities.Subscription;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.rest.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.rest.model.AuthType;
import com.ubiqube.etsi.mano.service.rest.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.rest.model.OAuth2GrantType;
import com.ubiqube.etsi.mano.service.rest.model.ServerConnection;

import jakarta.annotation.Nullable;

/**
 * This service should be Async
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class ActionService {
	@SuppressWarnings("static-method")
	public void doAction(final Alarm alarm) {
		final Subscription subs = alarm.getSubscription();
		final ServerConnection server = ServerConnection.serverBuilder()
				.authentification(buildAuth(subs.getAuthentication()))
				.ignoreSsl(true)
				.url(subs.getCallbackUri())
				.build();
		final FluxRest fr = new FluxRest(server);
		final URI uri = URI.create(alarm.getSubscription().getCallbackUri());
		fr.post(uri, alarm, Alarm.class, null);
	}

	private static AuthentificationInformations buildAuth(final com.ubiqube.etsi.mano.alarm.entities.AuthentificationInformations authentication) {
		return AuthentificationInformations.builder()
				.authParamBasic(map(authentication.getAuthParamBasic()))
				.authParamOauth2(map(authentication.getAuthParamOauth2()))
				.authType(map(authentication.getAuthType()))
				.build();
	}

	private static List<AuthType> map(final List<com.ubiqube.etsi.mano.alarm.entities.AuthType> authType) {
		return authType.stream().map(x -> AuthType.fromValue(x.toString())).toList();
	}

	@Nullable
	private static AuthParamOauth2 map(@Nullable final com.ubiqube.etsi.mano.alarm.entities.AuthParamOauth2 authParamOauth2) {
		if (null == authParamOauth2) {
			return null;
		}
		return AuthParamOauth2.builder()
				.clientId(authParamOauth2.getClientId())
				.clientSecret(authParamOauth2.getClientSecret())
				.grantType(OAuth2GrantType.fromValue(authParamOauth2.getGrantType().toString()))
				.o2AuthTlsCert(authParamOauth2.getO2AuthTlsCert())
				.o2IgnoreSsl(authParamOauth2.getO2IgnoreSsl())
				.o2Password(authParamOauth2.getO2Password())
				.o2Username(authParamOauth2.getO2Username())
				.tokenEndpoint(authParamOauth2.getTokenEndpoint())
				.build();
	}

	@Nullable
	private static AuthParamBasic map(@Nullable final com.ubiqube.etsi.mano.alarm.entities.AuthParamBasic authParamBasic) {
		if (null == authParamBasic) {
			return null;
		}
		return AuthParamBasic.builder()
				.password(authParamBasic.getPassword())
				.userName(authParamBasic.getUserName())
				.build();
	}

}
