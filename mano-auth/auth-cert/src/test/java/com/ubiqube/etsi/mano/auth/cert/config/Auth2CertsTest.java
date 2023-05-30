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
package com.ubiqube.etsi.mano.auth.cert.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.X509Configurer;

import com.ubiqube.etsi.mano.auth.AuthException;
import com.ubiqube.etsi.mano.auth.config.SecurityType;

@ExtendWith(MockitoExtension.class)
class Auth2CertsTest {
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry http;
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl authUrl;
	@Mock
	private HttpSecurity httpSecurity;
	@Mock
	private HttpSecurity x509;
	@Mock
	private X509Configurer<HttpSecurity> x509Conf;

	@Test
	void testConfigure() throws Exception {
		final Auth2Certs ac = new Auth2Certs();
		final ArgumentCaptor<Customizer<X509Configurer<HttpSecurity>>> arg = ArgumentCaptor.forClass(Customizer.class);
		when(httpSecurity.x509(arg.capture())).thenReturn(x509);
		ac.configure(httpSecurity);
		when(x509Conf.subjectPrincipalRegex(anyString())).thenReturn(x509Conf);
		arg.getValue().customize(x509Conf);
		assertTrue(true);
	}

	@Test
	void testConfigureError() throws Exception {
		final Auth2Certs ac = new Auth2Certs();
		when(httpSecurity.x509(any())).thenThrow(RuntimeException.class);
		assertThrows(AuthException.class, () -> ac.configure(httpSecurity));
	}

	@Test
	void testSwagger() throws Exception {
		final Auth2Certs ac = new Auth2Certs();
		ac.getSwaggerSecurityScheme(null);
		assertTrue(true);
	}

	@Test
	void testScutiryType() throws Exception {
		final Auth2Certs ac = new Auth2Certs();
		assertEquals(SecurityType.CERT, ac.getSecurityType());
		assertTrue(true);
	}
}
