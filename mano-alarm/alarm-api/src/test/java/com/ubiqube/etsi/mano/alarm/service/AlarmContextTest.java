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
