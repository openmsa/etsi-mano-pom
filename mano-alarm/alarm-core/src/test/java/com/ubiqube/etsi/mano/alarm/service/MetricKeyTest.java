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
package com.ubiqube.etsi.mano.alarm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Metrics;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;

/**
 *
 * @author Olivier Vignaud
 *
 */
@SuppressWarnings("static-method")
class MetricKeyTest {

	@Test
	void test() {
		final Metrics m = new Metrics();
		m.setObjectId("objectId");
		m.setKey("key");
		m.setLabel("label");
		final MetricKey res = MetricKey.of(m);
		assertNotNull(res);
		assertNotNull(res.objectId());
		assertEquals("key", res.key());
		assertEquals("label", res.alarmKey());
	}

	@Test
	void testOf2() {
		final MonitoringDataSlim m = new TelemetryMetricsResult();
		final MetricKey res = MetricKey.of(m, "label");
		assertNotNull(res);
	}
}
