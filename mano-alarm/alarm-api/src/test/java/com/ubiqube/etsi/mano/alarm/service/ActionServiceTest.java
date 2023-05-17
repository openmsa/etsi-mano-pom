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

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.ubiqube.etsi.mano.alarm.entities.AuthParamBasic;
import com.ubiqube.etsi.mano.alarm.entities.AuthParamOauth2;
import com.ubiqube.etsi.mano.alarm.entities.AuthType;
import com.ubiqube.etsi.mano.alarm.entities.AuthentificationInformations;
import com.ubiqube.etsi.mano.alarm.entities.OAuth2GrantType;
import com.ubiqube.etsi.mano.alarm.entities.Subscription;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;

@WireMockTest
class ActionServiceTest {

	ActionService create() {
		return new ActionService();
	}

	@Test
	void test(final WireMockRuntimeInfo wmRuntimeInfo) {
		stubFor(post(urlMatching("/")).willReturn(aResponse().withStatus(200)));
		final ActionService srv = create();
		final Alarm alarm = new Alarm();
		final Subscription subs = new Subscription();
		subs.setCallbackUri(wmRuntimeInfo.getHttpBaseUrl());
		final AuthentificationInformations auth = new AuthentificationInformations();
		auth.setAuthType(List.of(AuthType.BASIC));
		subs.setAuthentication(auth);
		alarm.setSubscription(subs);
		srv.doAction(alarm, false);
		assertTrue(true);
	}

	@Test
	void testBasic(final WireMockRuntimeInfo wmRuntimeInfo) {
		stubFor(post(urlMatching("/")).willReturn(aResponse().withStatus(200)));
		final ActionService srv = create();
		final Alarm alarm = new Alarm();
		final Subscription subs = new Subscription();
		subs.setCallbackUri(wmRuntimeInfo.getHttpBaseUrl());
		final AuthentificationInformations auth = new AuthentificationInformations();
		auth.setAuthType(List.of(AuthType.BASIC));
		final AuthParamBasic basic = new AuthParamBasic();
		basic.setUserName("user");
		basic.setPassword("pass");
		auth.setAuthParamBasic(basic);
		subs.setAuthentication(auth);
		alarm.setSubscription(subs);
		srv.doAction(alarm, false);
		assertTrue(true);
	}

	@Test
	void testOauth2(final WireMockRuntimeInfo wmRuntimeInfo) {
		stubFor(post(urlMatching("/")).willReturn(aResponse().withStatus(200)));
		stubFor(post(urlMatching("/auth")).willReturn(aResponse()
				.withBody("""
						                                          {
						                                                  "access_token":"eyJhbGciOiJSUzI1",
						                                                  "expires_in":3000,
						                                                  "refresh_expires_in":0,
						                                                  "token_type":"Bearer",
						                                                  "not-before-policy":0,
						                                                  "scope":"profile mano:ovi email"
						                                          }
						""")
				.withHeader("Content-Type", "application/json")
				.withStatus(200)));
		final ActionService srv = create();
		final Alarm alarm = new Alarm();
		final Subscription subs = new Subscription();
		subs.setCallbackUri(wmRuntimeInfo.getHttpBaseUrl());
		final AuthentificationInformations auth = new AuthentificationInformations();
		auth.setAuthType(List.of(AuthType.BASIC));
		final AuthParamOauth2 oauth2 = new AuthParamOauth2();
		oauth2.setGrantType(OAuth2GrantType.CLIENT_CREDENTIAL);
		oauth2.setTokenEndpoint(wmRuntimeInfo.getHttpBaseUrl() + "/auth");
		oauth2.setClientId("client");
		oauth2.setClientSecret("secret");
		auth.setAuthParamOauth2(oauth2);
		subs.setAuthentication(auth);
		alarm.setSubscription(subs);
		srv.doAction(alarm, false);
		assertTrue(true);
	}

}
