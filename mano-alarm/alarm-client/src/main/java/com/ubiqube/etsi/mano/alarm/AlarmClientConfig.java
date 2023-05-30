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
package com.ubiqube.etsi.mano.alarm;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.DefaultClientRequestObservationConvention;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.ubiqube.etsi.mano.alarm.controller.AlarmClient;

import io.micrometer.observation.ObservationRegistry;
import jakarta.annotation.Nonnull;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Configuration
public class AlarmClientConfig {
	@Nonnull
	private final DefaultClientRequestObservationConvention oc;
	@Nonnull
	private final ObservationRegistry observationRegistry;

	public AlarmClientConfig(final ConfigurableApplicationContext configurableApplicationContext) {
		oc = new DefaultClientRequestObservationConvention("http.client.requests");
		observationRegistry = configurableApplicationContext.getBean(ObservationRegistry.class);
	}

	@Bean
	AlarmClient createAlarmClient() {
		final HttpServiceProxyFactory proxyFactory = createProxyFactory();
		return proxyFactory.createClient(AlarmClient.class);
	}

	private HttpServiceProxyFactory createProxyFactory() {
		final Builder webBuilder = WebClient.builder()
				.baseUrl("http://mano-alarm:8080/");
		webBuilder.observationConvention(oc);
		webBuilder.observationRegistry(observationRegistry);
		final WebClient client = webBuilder
				.build();
		return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
	}
}
