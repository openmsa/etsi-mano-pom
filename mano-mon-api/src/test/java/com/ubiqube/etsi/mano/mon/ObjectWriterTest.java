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
package com.ubiqube.etsi.mano.mon;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.JmsMetricHolder;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.jms.MetricChange;

class ObjectWriterTest {

	private final ObjectMapper om;

	public ObjectWriterTest() {
		om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
	}

	@Test
	void testName() throws Exception {
		final TelemetryMetricsResult m = new TelemetryMetricsResult("master", "resource", "key", 12D, null, OffsetDateTime.now(), true);
		final String str = om.writeValueAsString(m);
		System.out.println(str);
		final MonitoringDataSlim obj = om.readValue(str, MonitoringDataSlim.class);
		assertNotNull(obj);
	}

	@Test
	void testMetricHolder() throws JsonProcessingException {
		final TelemetryMetricsResult m = new TelemetryMetricsResult("master", "resource", "key", 12D, null, OffsetDateTime.now(), true);
		final JmsMetricHolder jmh = new JmsMetricHolder(List.of(m));
		final String str = om.writeValueAsString(jmh);
		System.out.println(str);
		final JmsMetricHolder obj = om.readValue(str, JmsMetricHolder.class);
		assertNotNull(obj);
	}

	@Test
	void testMetricChange() throws JsonProcessingException {
		final MonitoringDataSlim mc1 = new TelemetryMetricsResult("master", "resource", "key", 12D, null, OffsetDateTime.now(), true);
		final MonitoringDataSlim mc2 = new TelemetryMetricsResult("master2", "resource2", "key2", 212D, null, OffsetDateTime.now(), true);
		final MetricChange mc = new MetricChange(mc1, mc2);
		final String str = om.writeValueAsString(mc);
		System.out.println(str);
		final MetricChange obj = om.readValue(str, MetricChange.class);
		assertNotNull(obj);
	}
}
