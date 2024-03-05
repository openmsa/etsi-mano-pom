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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ConfigurableApplicationContext;

import com.ubiqube.etsi.mano.service.mon.cli.MonitoringProperty;

import io.micrometer.observation.ObservationRegistry;

@ExtendWith(MockitoExtension.class)
class MonitoringRemoteObservabilityServiceTest {
	@Mock
	private ConfigurableApplicationContext configurableApp;
	@Mock
	private ObservationRegistry obsReg;

	@Test
	void testCreateMetricsRemoteService() {
		final MonitoringProperty props = new MonitoringProperty();
		when(configurableApp.getBean(ObservationRegistry.class)).thenReturn(obsReg);
		final MonitoringRemoteObservabilityService srv = new MonitoringRemoteObservabilityService(configurableApp, props);
		srv.createMetricsRemoteService();
		assertTrue(true);
	}

	@Test
	void testCreateMonPollingRemoteService() {
		final MonitoringProperty props = new MonitoringProperty();
		when(configurableApp.getBean(ObservationRegistry.class)).thenReturn(obsReg);
		final MonitoringRemoteObservabilityService srv = new MonitoringRemoteObservabilityService(configurableApp, props);
		srv.createMonPollingRemoteService();
		assertTrue(true);
	}

	@Test
	void testCreateProxyFactory() {
		final MonitoringProperty props = new MonitoringProperty();
		when(configurableApp.getBean(ObservationRegistry.class)).thenReturn(obsReg);
		final MonitoringRemoteObservabilityService srv = new MonitoringRemoteObservabilityService(configurableApp, props);
		srv.createProxyFactory();
		assertTrue(true);
	}
}
