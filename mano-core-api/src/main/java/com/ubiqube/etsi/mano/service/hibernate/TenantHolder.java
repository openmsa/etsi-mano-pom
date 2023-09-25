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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantHolder {

	private static final Logger LOG = LoggerFactory.getLogger(TenantHolder.class);

	private static ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

	private TenantHolder() {
		//
	}

	public static void setTenantId(final String tenantId) {
		LOG.debug("Setting tenantId to {}", tenantId);
		currentTenant.set(tenantId);
	}

	public static String getTenantId() {
		return currentTenant.get();
	}

	public static void clear() {
		currentTenant.remove();
	}
}
