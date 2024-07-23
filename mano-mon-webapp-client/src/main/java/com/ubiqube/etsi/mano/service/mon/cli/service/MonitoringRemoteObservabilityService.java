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

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.DefaultClientRequestObservationConvention;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.ubiqube.etsi.mano.service.mon.cli.MonitoringProperty;

import io.micrometer.observation.ObservationRegistry;
import jakarta.annotation.Nonnull;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(ObservationRegistry.class)
public class MonitoringRemoteObservabilityService extends AbstractMonitoringRemoteService {
	@Nonnull
	private final DefaultClientRequestObservationConvention oc;
	@Nonnull
	private final ObservationRegistry observationRegistry;
	private final MonitoringProperty properties;

	public MonitoringRemoteObservabilityService(final ConfigurableApplicationContext configurableApplicationContext, final MonitoringProperty properties) {
		oc = new DefaultClientRequestObservationConvention("http.client.requests");
		observationRegistry = configurableApplicationContext.getBean(ObservationRegistry.class);
		this.properties = properties;
	}

	@Override
	HttpServiceProxyFactory createProxyFactory() {
		final Builder webBuilder = WebClient.builder()
				.baseUrl(properties.getUrl());
		webBuilder.observationConvention(oc);
		webBuilder.observationRegistry(observationRegistry);
		final WebClient client = webBuilder
				.build();
		return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
	}

}
