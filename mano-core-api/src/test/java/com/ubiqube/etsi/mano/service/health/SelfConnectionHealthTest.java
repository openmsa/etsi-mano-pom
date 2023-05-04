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

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.core.env.Environment;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@ExtendWith(MockitoExtension.class)
@WireMockTest
class SelfbuilderConnectionHealthTest {
	@Mock
	private Environment env;
	@Mock
	private Builder builder;

	@Test
	void test(final WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		stubForEnv(wmRuntimeInfo);
		//
		when(builder.up()).thenReturn(builder);
		when(builder.down()).thenReturn(builder);
		//
		stubForOAuth2();
		stubFor(get(urlPathMatching("/admin/sink/self")).willReturn(aResponse()
				.withBody("{}")
				.withStatus(200)));
		final SelfConnectionHealth srv = new SelfConnectionHealth(env);
		srv.getHealth(true);
		srv.doHealthCheck(builder);
		assertTrue(true);
	}

	@Test
	void testOk(final WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		stubForEnv(wmRuntimeInfo);
		//
		when(builder.up()).thenReturn(builder);
		//
		stubForOAuth2();
		stubFor(get(urlPathMatching("/admin/sink/self")).willReturn(aResponse()
				.withStatus(204)));
		final SelfConnectionHealth srv = new SelfConnectionHealth(env);
		srv.getHealth(true);
		srv.doHealthCheck(builder);
		assertTrue(true);
	}

	private void stubForEnv(final WireMockRuntimeInfo wmRuntimeInfo) {
		when(env.getProperty("mano.swagger-o-auth2")).thenReturn(wmRuntimeInfo.getHttpBaseUrl()+"/auth/realms/mano-realm/protocol/openid-connect/token");
		when(env.getProperty("keycloak.resource")).thenReturn("res");
		when(env.getProperty("keycloak.credentials.secret")).thenReturn("sec");
		when(env.getProperty("mano.frontend-url")).thenReturn(wmRuntimeInfo.getHttpBaseUrl());
	}

	private static void stubForOAuth2() {
		stubFor(post(urlPathMatching("/auth/realms/mano-realm/protocol/openid-connect/token")).willReturn(aResponse()
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
				.withStatus(200)
				.withHeader("Content-Type", "application/json")));
	}

}
