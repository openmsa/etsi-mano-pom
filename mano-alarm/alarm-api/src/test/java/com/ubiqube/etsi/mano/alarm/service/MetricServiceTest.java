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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Metrics;
import com.ubiqube.etsi.mano.service.mon.cli.MonSearchRemoteService;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {
	@Mock
	private MonSearchRemoteService searchService;

	@Test
	void test() {
		final MetricService srv = new MetricService(searchService);
		final Metrics met = new Metrics();
		final ResponseEntity<List<MonitoringDataSlim>> resp = ResponseEntity.ok(List.of());
		when(searchService.findByObjectIdAndKey(any(), any())).thenReturn(resp);
		srv.findLatest(met);
		assertTrue(true);
	}

}
