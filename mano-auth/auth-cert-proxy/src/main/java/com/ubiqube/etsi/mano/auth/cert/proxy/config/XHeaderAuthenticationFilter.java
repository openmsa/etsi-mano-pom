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
/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.etsi.mano.auth.cert.proxy.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bouncycastle.asn1.x500.X500Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ubiqube.etsi.mano.auth.cert.common.CertUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class XHeaderAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(XHeaderAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
		LOG.debug("{}", request.getHeaderNames());
		final String verified = Optional.ofNullable(request.getHeader("x-ssl-client-verify")).orElse("1");
		if (!"0".equals(verified) && !"SUCCESS".equals(verified)) {
			filterChain.doFilter(request, response);
			return;
		}
		final String dn = request.getHeader("x-ssl-client-dn");
		final X500Name n = new X500Name(sanitize(dn));
		final UserDetails user = CertUtils.createUserDetails(n);
		final Authentication auth = new XHeaderAuthToken(user.getUsername(), user.getPassword(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		filterChain.doFilter(request, response);
	}

	private static String sanitize(final String dn) {
		if (!dn.startsWith("/")) {
			return dn;
		}
		final String tmp = dn.substring(1);
		final String[] spl = tmp.split("/");
		return Arrays.stream(spl)
				.map(x -> x.replace(",", "\\,"))
				.collect(Collectors.joining(","));
	}
}
