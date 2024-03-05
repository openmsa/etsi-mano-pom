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
package com.ubiqube.etsi.mano.service.mon.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Olivier Vignaud
 *
 */
class MonitoringDataTest {

	@Test
	void test() {
		MonitoringData srv = new MonitoringData();
		srv = new MonitoringData(null, null, null, null, null, null, false);
		srv.setId(null);
		srv.setKey(null);
		srv.setMasterJobId(null);
		srv.setResourceId(null);
		srv.setStatus(false);
		srv.setText(null);
		srv.setTime(null);
		srv.setValue(null);
		srv.getId();
		srv.getKey();
		srv.getMasterJobId();
		srv.getResourceId();
		srv.getText();
		srv.getTime();
		srv.getValue();
		srv.isStatus();
		assertTrue(true);
	}

}
