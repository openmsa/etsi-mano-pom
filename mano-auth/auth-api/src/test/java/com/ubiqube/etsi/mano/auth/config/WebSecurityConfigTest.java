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
package com.ubiqube.etsi.mano.auth.config;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;

import com.ubiqube.etsi.mano.auth.AuthException;
import com.ubiqube.etsi.mano.config.TestSecutiryConfig;

@ExtendWith(MockitoExtension.class)
class WebSecurityConfigTest {
	@Mock
	private HttpSecurity http;
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry amrmr;
	@Mock
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl au;
	@Mock
	private HeadersConfigurer<HttpSecurity> headers;
	@Mock
	private HeadersConfigurer<HttpSecurity>.FrameOptionsConfig foc;
	@Mock
	private CsrfConfigurer<HttpSecurity> csrf;

	@Test
	void testCsrf() throws Exception {
		final SecutiryConfig sc = new TestSecutiryConfig();
		final WebSecurityConfig wc = new WebSecurityConfig(sc);
		final ArgumentCaptor<Customizer<HeadersConfigurer<HttpSecurity>>> cust = ArgumentCaptor.forClass(Customizer.class);
		when(http.headers(cust.capture())).thenReturn(http);
		//
		final ArgumentCaptor<Customizer<CsrfConfigurer<HttpSecurity>>> cap01 = ArgumentCaptor.forClass(Customizer.class);
		when(http.csrf(cap01.capture())).thenReturn(http);
		//
		final ArgumentCaptor<Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>> authorizeHttpRequestsCustomizer = ArgumentCaptor.forClass(Customizer.class);
		when(http.authorizeHttpRequests(authorizeHttpRequestsCustomizer.capture())).thenReturn(http);
		when(amrmr.requestMatchers(anyString())).thenReturn(au);
		when(au.permitAll()).thenReturn(amrmr);
		wc.configure(http);
		when(amrmr.anyRequest()).thenReturn(au);
		cap01.getValue().customize(csrf);
		final ArgumentCaptor<Customizer<HeadersConfigurer<HttpSecurity>.FrameOptionsConfig>> cap02 = ArgumentCaptor.forClass(Customizer.class);
		when(headers.frameOptions(cap02.capture())).thenReturn(headers);
		cust.getValue().customize(headers);
		authorizeHttpRequestsCustomizer.getValue().customize(amrmr);
		cap02.getValue().customize(foc);
		assertTrue(true);
	}

	@Test
	void testThrow() throws Exception {
		final SecutiryConfig sc = new TestSecutiryConfig();
		sc.getSwaggerSecurityScheme(null);
		final WebSecurityConfig wc = new WebSecurityConfig(sc);
		final ArgumentCaptor<Customizer<CsrfConfigurer<HttpSecurity>>> cap01 = ArgumentCaptor.forClass(Customizer.class);
		when(http.csrf(cap01.capture())).thenThrow(Exception.class);
		assertThrows(AuthException.class, () -> wc.configure(http));

	}
}
