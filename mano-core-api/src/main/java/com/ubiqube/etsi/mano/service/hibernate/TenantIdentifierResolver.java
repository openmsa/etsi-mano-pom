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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.hibernate;

import java.util.Map;
import java.util.Optional;

import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import com.ubiqube.etsi.mano.auth.config.TenantHolder;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String>, HibernatePropertiesCustomizer {

	private static final Logger LOG = LoggerFactory.getLogger(TenantIdentifierResolver.class);

	@Override
	public void customize(final Map<String, Object> hibernateProperties) {
		hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
	}

	@Override
	public String resolveCurrentTenantIdentifier() {
		return Optional.ofNullable(TenantHolder.getTenantId()).orElse("BOOTSTRAP");
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		final Optional<String> tenant = Optional.ofNullable(TenantHolder.getTenantId());
		LOG.info("validateExistingCurrentSessions: {}", tenant.isPresent());
		return tenant.isPresent();
	}

}
