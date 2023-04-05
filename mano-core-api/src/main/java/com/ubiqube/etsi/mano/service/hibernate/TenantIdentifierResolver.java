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
package com.ubiqube.etsi.mano.service.hibernate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {

	private static final Logger LOG = LoggerFactory.getLogger(TenantIdentifierResolver.class);

	@Override
	public void customize(final Map<String, Object> hibernateProperties) {
		// hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER,
		// this);
	}

	@Override
	public String resolveCurrentTenantIdentifier() {
		final Optional<Authentication> auth = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
		if (auth.isPresent()) {
			final Object principal = auth.get().getPrincipal();
			if (principal instanceof final Jwt jwt) {
				return handleJwt(jwt);
			}
			return auth.get().getName();
		}
		return "unknown";
	}

	private static String handleJwt(final Jwt jwt) {
		final List<String> obj = jwt.getClaim("vimId");
		return obj.get(0);
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		final Optional<Authentication> auth = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
		LOG.info("validateExistingCurrentSessions: {}", auth.isPresent());
		return auth.isPresent();
	}

}
