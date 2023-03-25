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
package com.ubiqube.etsi.mano.auth.basic.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;

import com.ubiqube.etsi.mano.auth.AuthException;
import com.ubiqube.etsi.mano.auth.config.Http403EntryPoint;
import com.ubiqube.etsi.mano.auth.config.SecurityType;

@ExtendWith(MockitoExtension.class)
class BasicAuthTest {

	private Http403EntryPoint http403;
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry http;
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl authUrl;
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry AutMngrReqMatchReg;
	@Mock
	private HttpSecurity httpSecurity;
	@Mock
	private HttpBasicConfigurer<HttpSecurity> httpBasic;
	@Mock
	private CsrfConfigurer<HttpSecurity> csrf;

	@Test
	void testSecurityType() {
		final BasicAuth ba = new BasicAuth(http403);
		assertEquals(SecurityType.BASIC, ba.getSecurityType());
	}

	@Test
	void testSwagger() {
		final BasicAuth ba = new BasicAuth(http403);
		assertNotNull(ba.getSwaggerSecurityScheme(null));
	}

	@Test
	void testConfigure() throws Exception {
		final BasicAuth ba = new BasicAuth(http403);
		when(http.anyRequest()).thenReturn(authUrl);
		when(authUrl.authenticated()).thenReturn(AutMngrReqMatchReg);
		when(AutMngrReqMatchReg.and()).thenReturn(httpSecurity);
		final ArgumentCaptor<Customizer<HttpBasicConfigurer<HttpSecurity>>> arg = ArgumentCaptor.forClass(Customizer.class);
		when(httpSecurity.httpBasic(arg.capture())).thenReturn(httpSecurity);
		final ArgumentCaptor<Customizer<CsrfConfigurer<HttpSecurity>>> arg2 = ArgumentCaptor.forClass(Customizer.class);
		when(httpSecurity.csrf(arg2.capture())).thenReturn(httpSecurity);
		ba.configure(http);
		arg.getValue().customize(httpBasic);
		arg2.getValue().customize(csrf);
		assertTrue(true);
	}

	@Test
	void testConfigureError() throws Exception {
		final BasicAuth ba = new BasicAuth(http403);
		when(http.anyRequest()).thenReturn(authUrl);
		assertThrows(AuthException.class, () -> ba.configure(http));
	}
}
