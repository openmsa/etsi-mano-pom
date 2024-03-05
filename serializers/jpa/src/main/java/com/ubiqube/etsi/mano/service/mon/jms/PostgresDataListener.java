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
package com.ubiqube.etsi.mano.service.mon.jms;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.mon.api.BusHelper;
import com.ubiqube.etsi.mano.service.mon.data.JmsMetricHolder;
import com.ubiqube.etsi.mano.service.mon.model.MonitoringData;
import com.ubiqube.etsi.mano.service.mon.repository.MonitoringDataJpa;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class PostgresDataListener {
	private static final String POSTGRESQL_RECEIVE = "Postgresql-Receive: {}";

	private static final Logger LOG = LoggerFactory.getLogger(PostgresDataListener.class);

	private final MonitoringDataJpa monitoringDataJpa;

	public PostgresDataListener(final MonitoringDataJpa monitoringDataJpa) {
		this.monitoringDataJpa = monitoringDataJpa;
	}

	@JmsListener(destination = BusHelper.TOPIC_SERIALZE_DATA, subscription = "mano.monitoring.gnocchi.data", concurrency = "1", containerFactory = "serialzeDataFactory")
	public void onGnocchiData(final JmsMetricHolder results) {
		LOG.trace(POSTGRESQL_RECEIVE, results);
		final List<MonitoringData> metrics = results.getMetrics().stream()
				.map(x -> new MonitoringData(x.getKey(), x.getMasterJobId(), x.getTime(), x.getValue(), x.getText(), x.getResourceId(), true))
				.toList();
		monitoringDataJpa.saveAll(metrics);
	}

}
