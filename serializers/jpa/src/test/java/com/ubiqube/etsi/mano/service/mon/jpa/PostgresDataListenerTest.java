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
package com.ubiqube.etsi.mano.service.mon.jpa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.JmsMetricHolder;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.jms.PostgresDataListener;
import com.ubiqube.etsi.mano.service.mon.repository.MonitoringDataJpa;

@ExtendWith(MockitoExtension.class)
class PostgresDataListenerTest {
	@Mock
	private MonitoringDataJpa monitoringJpa;

	@Test
	void testName() throws Exception {
		final PostgresDataListener postgresDataListener = new PostgresDataListener(monitoringJpa);
		final List<MonitoringDataSlim> metrics = List.of();
		final JmsMetricHolder results = new JmsMetricHolder(metrics);
		postgresDataListener.onGnocchiData(results);
		assertTrue(true);
	}

	@Test
	void testNotEmpty() throws Exception {
		final PostgresDataListener postgresDataListener = new PostgresDataListener(monitoringJpa);
		final TelemetryMetricsResult tmp = new TelemetryMetricsResult("masterJobId", "vnfcId", "key", 123D, null, OffsetDateTime.now(), true);
		final List<MonitoringDataSlim> metrics = List.of(tmp);
		final JmsMetricHolder results = new JmsMetricHolder(metrics);
		postgresDataListener.onGnocchiData(results);
		assertTrue(true);
	}
}
