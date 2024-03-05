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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.ubiqube.etsi.mano.service.mon.cli.MetricsRemoteService;
import com.ubiqube.etsi.mano.service.mon.cli.MonPollingRemoteService;
import com.ubiqube.etsi.mano.service.mon.cli.MonSearchRemoteService;
import com.ubiqube.etsi.mano.service.mon.cli.MonitoringProperty;

import io.micrometer.observation.ObservationRegistry;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(ObservationRegistry.class)
public class MonitoringRemoteService {

	private static final Logger LOG = LoggerFactory.getLogger(MonitoringRemoteService.class);
	private final MonitoringProperty properties;

	public MonitoringRemoteService(final MonitoringProperty properties) {
		LOG.debug("Starting monitoring remote service.");
		this.properties = properties;
	}

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

	@Bean
	MonSearchRemoteService createMonSearchRemoteService2() {
		final HttpServiceProxyFactory proxyFactory = createProxyFactory();
		return proxyFactory.createClient(MonSearchRemoteService.class);
	}

	HttpServiceProxyFactory createProxyFactory() {
		final Builder webBuilder = WebClient.builder()
				.baseUrl(properties.getUrl());
		final WebClient client = webBuilder
				.build();
		return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
	}

}
