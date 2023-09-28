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
package com.ubiqube.etsi.mano.service.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ConfigurableApplicationContext;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;

import io.micrometer.observation.ObservationRegistry;

@ExtendWith(MockitoExtension.class)
class TracingFluxRestTest {
	@Mock
	private ConfigurableApplicationContext appCtx;
	@Mock
	private ObservationRegistry obsReg;

	@Test
	void test() {
		when(appCtx.getBean(ObservationRegistry.class)).thenReturn(obsReg);

		final Servers server = Servers.builder()
				.url(URI.create("http://localhost/"))
				.build();
		final TracingFluxRest srv = new TracingFluxRest(server, appCtx);
		assertNotNull(srv);
	}

}
