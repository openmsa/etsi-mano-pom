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
package com.ubiqube.etsi.mano.service.rest;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.reactive.function.client.DefaultClientRequestObservationConvention;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;

import io.micrometer.observation.ObservationRegistry;

public class TracingFluxRest extends FluxRest {

	public TracingFluxRest(final Servers server, final ConfigurableApplicationContext configurableApplicationContext) {
		super(server);
		final DefaultClientRequestObservationConvention oc = new DefaultClientRequestObservationConvention("http.client.requests");
		webBuilder.observationConvention(oc);
		final ObservationRegistry observationRegistry = configurableApplicationContext.getBean(ObservationRegistry.class);
		webBuilder.observationRegistry(observationRegistry);
	}
}
