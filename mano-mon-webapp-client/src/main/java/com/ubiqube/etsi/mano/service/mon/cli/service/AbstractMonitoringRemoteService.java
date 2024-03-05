/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
package com.ubiqube.etsi.mano.service.mon.cli.service;

import org.springframework.context.annotation.Bean;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.ubiqube.etsi.mano.service.mon.cli.MetricsRemoteService;
import com.ubiqube.etsi.mano.service.mon.cli.MonPollingRemoteService;
import com.ubiqube.etsi.mano.service.mon.cli.MonSearchRemoteService;

public abstract class AbstractMonitoringRemoteService {

	@Bean
	MonPollingRemoteService createMonPollingRemoteService() {
		final HttpServiceProxyFactory proxyFactory = createProxyFactory();
		return proxyFactory.createClient(MonPollingRemoteService.class);
	}

	@Bean
	MetricsRemoteService createMetricsRemoteService() {
		final HttpServiceProxyFactory proxyFactory = createProxyFactory();
		return proxyFactory.createClient(MetricsRemoteService.class);
	}

	MonSearchRemoteService createMonSearchRemoteService() {
		final HttpServiceProxyFactory proxyFactory = createProxyFactory();
		return proxyFactory.createClient(MonSearchRemoteService.class);
	}

	abstract HttpServiceProxyFactory createProxyFactory();
}
