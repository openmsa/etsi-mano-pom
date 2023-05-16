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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.DefaultClientRequestObservationConvention;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.ubiqube.etsi.mano.service.mon.cli.MonSearchRemoteService;
import com.ubiqube.etsi.mano.service.mon.cli.MonitoringProperty;

import io.micrometer.observation.ObservationRegistry;

/**
 *
 * @author Olivier Vignaud
 *
 */
@SpringBootApplication
public class AlarmApplication {

	public static void main(final String[] args) {
		final SpringApplication app = new SpringApplication(AlarmApplication.class);
		app.setApplicationStartup(new BufferingApplicationStartup(2048));
		app.run(args);
	}

	HttpServiceProxyFactory createProxyFactory(final ConfigurableApplicationContext configurableApplicationContext, final MonitoringProperty properties) {
		final DefaultClientRequestObservationConvention oc = new DefaultClientRequestObservationConvention("http.client.requests");
		final Builder webBuilder = WebClient.builder()
				.baseUrl(properties.getUrl());
		webBuilder.observationConvention(oc);
		final ObservationRegistry observationRegistry = configurableApplicationContext.getBean(ObservationRegistry.class);
		webBuilder.observationRegistry(observationRegistry);
		final WebClient client = webBuilder
				.build();
		return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
	}

	@Bean
	MonSearchRemoteService createMonSearchRemoteService2(final ConfigurableApplicationContext configurableApplicationContext, final MonitoringProperty properties) {
		final HttpServiceProxyFactory proxyFactory = createProxyFactory(configurableApplicationContext, properties);
		return proxyFactory.createClient(MonSearchRemoteService.class);
	}

}
