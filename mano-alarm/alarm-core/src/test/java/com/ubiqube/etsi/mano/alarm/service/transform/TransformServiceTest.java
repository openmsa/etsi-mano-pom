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
package com.ubiqube.etsi.mano.alarm.service.transform;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Transform;
import com.ubiqube.etsi.mano.alarm.service.AlarmContext;
import com.ubiqube.etsi.mano.alarm.service.MetricKey;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.jms.MetricChange;

class TransformServiceTest {

	@Test
	void test() {
		final TransformService srv = new TransformService(List.of());
		srv.checkErrors(List.of());
		assertTrue(true);
	}

	@Test
	void test2() {
		final Cpuz cpuz = new Cpuz();
		final TransformService srv = new TransformService(List.of(cpuz));
		final Transform t = new Transform();
		t.setFunction("cpuz");
		srv.checkErrors(List.of(t));
		t.setFunction("bad");
		srv.checkErrors(List.of(t));
		assertTrue(true);
	}

	@Test
	void test3() {
		final Cpuz cpuz = new Cpuz();
		final TransformService srv = new TransformService(List.of(cpuz));
		final Transform t = new Transform();
		t.setFunction("cpuz");
		t.setValue("vnf");
		t.setParameters(Map.of("cpu", "2"));
		final AlarmContext ctx = new AlarmContext();
		final MonitoringDataSlim latest = new TelemetryMetricsResult("", "", "vnf", 123d, null, OffsetDateTime.now(), true);
		final MonitoringDataSlim old = new TelemetryMetricsResult("", "", "vnf", 123d, null, OffsetDateTime.now().minus(12, ChronoUnit.MINUTES), true);
		final MetricChange mc = new MetricChange(latest, old);
		final MetricKey key = MetricKey.of(latest, "vnf");
		ctx.put(key, mc);
		srv.transform(ctx, t);
		assertTrue(true);
	}
}
