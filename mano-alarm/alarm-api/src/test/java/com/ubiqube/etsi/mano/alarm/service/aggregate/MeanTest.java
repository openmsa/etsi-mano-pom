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
package com.ubiqube.etsi.mano.alarm.service.aggregate;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Aggregates;
import com.ubiqube.etsi.mano.alarm.service.AlarmContext;
import com.ubiqube.etsi.mano.alarm.service.MetricKey;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.jms.MetricChange;

@SuppressWarnings("static-method")
class MeanTest {

	@Test
	void test() {
		final Mean m = new Mean();
		m.getName();
		final AlarmContext ctx = new AlarmContext();
		final Aggregates aggregate = new Aggregates();
		m.apply(ctx, aggregate);
		assertTrue(true);
	}

	@Test
	void test2() {
		final Mean m = new Mean();
		m.getName();
		final AlarmContext ctx = new AlarmContext();
		final MonitoringDataSlim latest = new TelemetryMetricsResult("", "", "vnf", 123d, null, OffsetDateTime.now(), true);
		final MetricChange mc = new MetricChange(latest, null);
		final MetricKey key = MetricKey.of(latest, "vnf");
		ctx.put(key, mc);
		final Aggregates aggregate = new Aggregates();
		aggregate.setParameters(List.of("vnf"));
		m.apply(ctx, aggregate);
		assertTrue(true);
	}

}
