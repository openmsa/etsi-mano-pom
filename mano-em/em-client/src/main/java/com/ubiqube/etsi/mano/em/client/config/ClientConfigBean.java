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
package com.ubiqube.etsi.mano.em.client.config;

import java.util.Optional;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.DefaultClientRequestObservationConvention;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.ubiqube.etsi.mano.em.client.vnfind.VnfIndRemoteService;
import com.ubiqube.etsi.mano.em.client.vnfind.VnfIndSubscriptionRemoteService;
import com.ubiqube.etsi.mano.vnfm.property.EmProperty;

import io.micrometer.observation.ObservationRegistry;
import jakarta.validation.constraints.NotNull;

@Component
public class ClientConfigBean {
	@NotNull
	private final DefaultClientRequestObservationConvention oc;
	@NotNull
	private final ObservationRegistry observationRegistry;
	private final EmProperty conf;

	public ClientConfigBean(final ConfigurableApplicationContext configurableApplicationContext, final EmProperty conf) {
		oc = new DefaultClientRequestObservationConvention("http.client.requests");
		observationRegistry = configurableApplicationContext.getBean(ObservationRegistry.class);
		this.conf = conf;
	}

	@Bean
	VnfIndRemoteService vnfIndRemoteService() {
		final HttpServiceProxyFactory proxyFactory = createProxyFactory();
		return proxyFactory.createClient(VnfIndRemoteService.class);
	}

	@Bean
	VnfIndSubscriptionRemoteService vnfIndSubscriptionRemoteService() {
		final HttpServiceProxyFactory proxyFactory = createProxyFactory();
		return proxyFactory.createClient(VnfIndSubscriptionRemoteService.class);
	}

	private HttpServiceProxyFactory createProxyFactory() {
		final Builder webBuilder = WebClient.builder()
				.baseUrl(conf.getUrl());
		Optional.ofNullable(conf.getVersion()).ifPresent(x -> webBuilder.defaultHeader("Version", x));
		webBuilder.observationConvention(oc);
		webBuilder.observationRegistry(observationRegistry);
		final WebClient client = webBuilder
				.build();
		return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
	}
}
