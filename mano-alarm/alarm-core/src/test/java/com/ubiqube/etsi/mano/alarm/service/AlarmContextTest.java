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
package com.ubiqube.etsi.mano.alarm.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.alarm.AlarmException;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.jms.MetricChange;

class AlarmContextTest {

	@Test
	void test() {
		final AlarmContext srv = new AlarmContext();
		final MonitoringDataSlim latest = new TelemetryMetricsResult();
		latest.setValue(123d);
		final MetricChange mc = new MetricChange(latest, null);
		final MetricKey mk = MetricKey.of(latest, "test");
		srv.put(mk, mc);
		srv.getEvaluationContext();
		srv.get("test");
		assertThrows(AlarmException.class, () -> srv.get("bad"));
		srv.replace("test", 0);
		assertTrue(true);
	}

}
