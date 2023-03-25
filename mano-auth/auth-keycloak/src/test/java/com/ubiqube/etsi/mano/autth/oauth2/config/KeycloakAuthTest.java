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
package com.ubiqube.etsi.mano.autth.oauth2.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import com.ubiqube.etsi.mano.auth.AuthException;
import com.ubiqube.etsi.mano.auth.config.Http403EntryPoint;
import com.ubiqube.etsi.mano.auth.config.SecurityType;
import com.ubiqube.etsi.mano.config.properties.ManoProperties;

@ExtendWith(MockitoExtension.class)
class KeycloakAuthTest {
	@Mock
	private Http403EntryPoint http403;
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry http;
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl authUrl;
	@Mock
	private HttpSecurity httpSecurity;
	@Mock
	private OAuth2ResourceServerConfigurer<HttpSecurity> oauth2;
	@Mock
	private OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwtConf;

	@Test
	void testConfigure() throws Exception {
		final KeycloakAuth kca = new KeycloakAuth(http403);
		when(http.anyRequest()).thenReturn(authUrl);
		when(authUrl.authenticated()).thenReturn(http);
		when(http.and()).thenReturn(httpSecurity);
		final ArgumentCaptor<Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>>> cust = ArgumentCaptor.forClass(Customizer.class);
		when(httpSecurity.oauth2ResourceServer(cust.capture())).thenReturn(httpSecurity);
		kca.configure(http);
		final ArgumentCaptor<Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer>> cust2 = ArgumentCaptor.forClass(Customizer.class);
		when(oauth2.jwt(cust2.capture())).thenReturn(oauth2);
		cust.getValue().customize(oauth2);
		cust2.getValue().customize(jwtConf);
		assertTrue(true);
	}

	@Test
	void testConfigureError() throws Exception {
		final KeycloakAuth kca = new KeycloakAuth(http403);
		when(http.anyRequest()).thenReturn(authUrl);
		when(authUrl.authenticated()).thenReturn(http);
		assertThrows(AuthException.class, () -> kca.configure(http));
	}

	@Test
	void testSwagger() throws Exception {
		final KeycloakAuth kca = new KeycloakAuth(http403);
		kca.getSwaggerSecurityScheme(new ManoProperties());
		assertTrue(true);
	}

	@Test
	void testSecurityType() throws Exception {
		final KeycloakAuth kca = new KeycloakAuth(http403);
		assertEquals(SecurityType.OAUTH2, kca.getSecurityType());
	}
}
