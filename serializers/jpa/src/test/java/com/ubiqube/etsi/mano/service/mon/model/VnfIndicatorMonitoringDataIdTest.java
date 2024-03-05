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

import java.util.UUID;

import org.junit.jupiter.api.Test;

class VnfIndicatorMonitoringDataIdTest {

	@Test
	void test() {
		final VnfIndicatorMonitoringDataId srv = new VnfIndicatorMonitoringDataId();
		srv.setKey(null);
		srv.setVnfcId(null);
		srv.getKey();
		srv.getVnfcId();
		srv.equals(srv);
		srv.equals(null);
		srv.equals("");
		srv.hashCode();
		final VnfIndicatorMonitoringDataId srv2 = new VnfIndicatorMonitoringDataId();
		srv.equals(srv2);
		srv2.setKey("");
		srv.equals(srv2);
		srv2.setKey(null);
		srv2.setVnfcId(UUID.randomUUID());
		srv.equals(srv2);
		assertTrue(true);
	}

}
