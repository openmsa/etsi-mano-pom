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
