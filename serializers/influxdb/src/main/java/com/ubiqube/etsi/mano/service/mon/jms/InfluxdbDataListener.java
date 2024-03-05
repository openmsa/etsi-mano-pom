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

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.ubiqube.etsi.mano.mon.api.BusHelper;
import com.ubiqube.etsi.mano.service.mon.data.JmsMetricHolder;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class InfluxdbDataListener {

	private static final Logger LOG = LoggerFactory.getLogger(InfluxdbDataListener.class);

	private final InfluxDBClient influxClient;

	public InfluxdbDataListener(final InfluxDBClient influxClient) {
		this.influxClient = influxClient;
	}

	@JmsListener(destination = BusHelper.TOPIC_SERIALZE_DATA, subscription = "mano.monitoring.gnocchi.data", concurrency = "1", containerFactory = "serialzeDataFactory")
	public void onData(final JmsMetricHolder action) {
		LOG.info("influxdb-Receive: {}", action);
		final List<Point> points = action.getMetrics().stream().map(x -> Point.measurement(x.getMasterJobId())
				.addField("value", x.getValue())
				.addTag("key", x.getKey())
				.addTag("status", "success")
				.addTag("vnf-instance-id", x.getMasterJobId())
				.time(Instant.now(), WritePrecision.MS))
				.toList();
		try (WriteApi client = influxClient.getWriteApi()) {
			client.writePoints(points);
		}

	}
}
