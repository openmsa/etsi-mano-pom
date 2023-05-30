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
package com.ubiqube.etsi.mano.auth.cert.proxy.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

import com.ubiqube.etsi.mano.auth.config.SecurityType;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("static-method")
class CertProxySecutiryConfigTest {
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry http;
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl authUrl;
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry match;
	@Mock
	private HttpSecurity httpSecurity;

	@Test
	void test() {
		final CertProxySecutiryConfig cert = new CertProxySecutiryConfig();
		when(httpSecurity.addFilterBefore(any(), any())).thenReturn(httpSecurity);
		cert.configure(httpSecurity);
		assertTrue(true);
	}

	@Test
	void testSwagger() throws Exception {
		final CertProxySecutiryConfig cert = new CertProxySecutiryConfig();
		cert.getSwaggerSecurityScheme(null);
		assertTrue(true);
	}

	@Test
	void testServerType() throws Exception {
		final CertProxySecutiryConfig cert = new CertProxySecutiryConfig();
		assertEquals(SecurityType.CERT, cert.getSecurityType());
	}
}
