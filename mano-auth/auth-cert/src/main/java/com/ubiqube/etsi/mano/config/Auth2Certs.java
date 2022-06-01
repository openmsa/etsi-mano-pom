/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.config;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.ubiqube.etsi.mano.config.properties.ManoProperties;

import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Component
public class Auth2Certs implements SecutiryConfig {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Override
	public void configure(final ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry http) throws Exception {
		http.and()
				.x509()
				.subjectPrincipalRegex("CN=(.*?)(?:,|$)")
				.authenticationUserDetailsService(new UserDetailSsl());
	}

	@Override
	public SecurityScheme getSwaggerSecurityScheme(final ManoProperties oauth2Params) {
		return new SecurityScheme().type(Type.MUTUALTLS).scheme("Mutual");
	}

	@Override
	public SecurityType getSecurityType() {
		return SecurityType.CERT;
	}
}
