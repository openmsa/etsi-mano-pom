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

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.auth.AuthException;
import com.ubiqube.etsi.mano.auth.config.Http403EntryPoint;
import com.ubiqube.etsi.mano.auth.config.SecurityType;
import com.ubiqube.etsi.mano.auth.config.SecutiryConfig;
import com.ubiqube.etsi.mano.config.properties.ManoProperties;

import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class BasicAuth implements SecutiryConfig {

	private final Http403EntryPoint http403EntryPoint;

	public BasicAuth(final Http403EntryPoint http403EntryPoint) {
		this.http403EntryPoint = http403EntryPoint;
	}

	/**
	 * All request must be authenticated, No login page.
	 */
	@Override
	public void configure(final HttpSecurity http) {
		try {
			http.httpBasic(basic -> basic.authenticationEntryPoint(http403EntryPoint))
					.csrf(csrf -> csrf.disable());
		} catch (final Exception e) {
			throw new AuthException(e);
		}
	}

	@Override
	public SecurityScheme getSwaggerSecurityScheme(final ManoProperties oauth2Params) {
		return new SecurityScheme().type(Type.HTTP).scheme("basic");
	}

	@Override
	public SecurityType getSecurityType() {
		return SecurityType.BASIC;
	}

}
