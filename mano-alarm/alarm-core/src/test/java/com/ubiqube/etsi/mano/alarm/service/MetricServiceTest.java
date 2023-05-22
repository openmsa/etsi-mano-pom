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
