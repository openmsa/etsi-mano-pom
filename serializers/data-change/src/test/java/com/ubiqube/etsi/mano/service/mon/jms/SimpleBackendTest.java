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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;

/**
 *
 * @author Olivier Vignaud
 *
 */
@SuppressWarnings("static-method")
class SimpleBackendTest {

	@Test
	void test() {
		final SimpleBackend srv = new SimpleBackend();
		final MonitoringDataSlim res = srv.getLastMetrics(null, null);
		assertNull(res);
		final MonitoringDataSlim r2 = new TelemetryMetricsResult("mk", "res", "key", 11d, null, OffsetDateTime.now(), false);
		srv.updateMetric(r2);
		final MonitoringDataSlim r3 = srv.getLastMetrics("key", "mk");
		assertNotNull(r3);
		assertEquals(11d, r3.getValue());
		assertTrue(true);
	}

}
