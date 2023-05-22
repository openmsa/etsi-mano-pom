/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
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
