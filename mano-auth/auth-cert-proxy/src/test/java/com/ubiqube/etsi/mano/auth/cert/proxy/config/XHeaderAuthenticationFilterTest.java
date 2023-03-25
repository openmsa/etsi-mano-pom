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

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class XHeaderAuthenticationFilterTest {
	@Mock
	private HttpServletRequest req;
	@Mock
	private HttpServletResponse res;
	@Mock
	private FilterChain chain;

	@Test
	void test() throws ServletException, IOException {
		final XHeaderAuthenticationFilter filter = new XHeaderAuthenticationFilter();
		filter.doFilter(req, res, chain);
	}

	@Test
	void test001() throws ServletException, IOException {
		final XHeaderAuthenticationFilter filter = new XHeaderAuthenticationFilter();
		when(req.getHeader("x-ssl-client-verify")).thenReturn("0");
		when(req.getHeader("x-ssl-client-dn")).thenReturn("/C=US/ST=Isere/L=Grenoble/O=TestUnit/CN=test/emailAddress=abc@test.com");
		filter.doFilter(req, res, chain);
	}

	@Test
	void test002() throws ServletException, IOException {
		final XHeaderAuthenticationFilter filter = new XHeaderAuthenticationFilter();
		when(req.getHeader("x-ssl-client-verify")).thenReturn("0");
		when(req.getHeader("x-ssl-client-dn")).thenReturn("C=US,ST=Isere,L=Grenoble,O=TestUnit,CN=test,emailAddress=abc@test.com");
		filter.doFilter(req, res, chain);
	}
}
