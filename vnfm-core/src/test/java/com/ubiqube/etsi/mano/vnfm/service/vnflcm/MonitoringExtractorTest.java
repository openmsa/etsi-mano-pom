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
package com.ubiqube.etsi.mano.vnfm.service.vnflcm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.MonitoringParams;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfMonitoringParameter;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.MonitoringTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;

class MonitoringExtractorTest {

	MonitoringExtractor createService() {
		return new MonitoringExtractor();
	}

	VnfLiveInstance createDummyVli() {
		final VnfLiveInstance vli = new VnfLiveInstance();
		final VnfTask t = new HelmTask();
		vli.setTask(t);
		return vli;
	}

	@Test
	void test() {
		final MonitoringExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		srv.extract(null, params, List.of());
		final Set<VnfMonitoringParameter> mon = params.getVnfMonitoringParameter();
		assertNotNull(mon);
		assertTrue(mon.isEmpty());
	}

	@Test
	void test2() {
		final MonitoringExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		final VnfLiveInstance vli0 = new VnfLiveInstance();
		final MonitoringTask mt = new MonitoringTask();
		final MonitoringParams monP = new MonitoringParams();
		mt.setMonitoringParams(monP);
		vli0.setTask(mt);
		final VnfLiveInstance vli1 = createDummyVli();
		srv.extract(null, params, List.of(vli0, vli1));
		final Set<VnfMonitoringParameter> mon = params.getVnfMonitoringParameter();
		assertNotNull(mon);
		assertEquals(1, mon.size());
	}
}
