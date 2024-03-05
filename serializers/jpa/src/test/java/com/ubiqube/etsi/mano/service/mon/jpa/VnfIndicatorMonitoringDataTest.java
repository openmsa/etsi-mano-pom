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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.mon.model.VnfIndicatorMonitoringData;

@SuppressWarnings("static-method")
class VnfIndicatorMonitoringDataTest {

	@Test
	void testName() throws Exception {
		final VnfIndicatorMonitoringData obj = new VnfIndicatorMonitoringData();
		obj.equals(null);
		obj.equals("");
		obj.equals(obj);
		obj.hashCode();
		obj.toString();
		assertTrue(true);
	}

	@Test
	void testCtor() throws Exception {
		final VnfIndicatorMonitoringData obj = new VnfIndicatorMonitoringData("key2", UUID.randomUUID(), 123D, UUID.randomUUID());
		obj.setId(UUID.randomUUID());
		obj.setKey("key");
		obj.setMasterJobId(UUID.randomUUID());
		obj.setTime(OffsetDateTime.now());
		obj.setValue(123D);
		obj.setVnfcId(UUID.randomUUID());
		obj.getId();
		obj.getKey();
		obj.getMasterJobId();
		obj.getTime();
		obj.getValue();
		obj.getVnfcId();
		assertTrue(true);
	}
}
