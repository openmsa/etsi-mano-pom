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
package com.ubiqube.etsi.mano.service.mon.jpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.mon.model.MonitoringData;

class MonitoringDataTest {

	@SuppressWarnings("static-method")
	@Test
	void testName() {
		final MonitoringData md = new MonitoringData();
		md.setId(UUID.randomUUID());
		md.setKey("");
		md.setMasterJobId("");
		md.setStatus(true);
		md.setText("");
		md.setTime(OffsetDateTime.now());
		md.setValue(123d);
		md.setResourceId("");
		md.getId();
		md.getKey();
		md.getMasterJobId();
		md.getText();
		md.getTime();
		md.getValue();
		md.isStatus();
		assertNotNull(md.getResourceId());
	}
}
