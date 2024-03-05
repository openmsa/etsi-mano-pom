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
package com.ubiqube.etsi.mano.service.mon;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.mon.cli.MetricsRemoteService;
import com.ubiqube.etsi.mano.service.mon.cli.MonPollingRemoteService;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;

@ExtendWith(MockitoExtension.class)
class DummyExternalMonitoringTest {
	@Mock
	private MonPollingRemoteService remoteService;
	@Mock
	private MetricsRemoteService metricsRemoteService;
	@Mock
	private ResponseEntity<BatchPollingJob> resp;

	@Test
	void testCreateBatch() {
		final DummyExternalMonitoring srv = new DummyExternalMonitoring(remoteService, metricsRemoteService);
		final VimConnectionInformation vim = new VimConnectionInformation();
		vim.setVimId(UUID.randomUUID().toString());
		when(remoteService.register(any())).thenReturn(resp);
		final BatchPollingJob bpj = new BatchPollingJob();
		when(resp.getBody()).thenReturn(bpj);
		srv.createBatch(null, Set.of("val"), 4L, vim);
		assertTrue(true);
	}

	@Test
	void testDeleteResource() {
		final DummyExternalMonitoring srv = new DummyExternalMonitoring(remoteService, metricsRemoteService);
		final String id = UUID.randomUUID().toString();
		srv.deleteResources(id);
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final DummyExternalMonitoring srv = new DummyExternalMonitoring(remoteService, metricsRemoteService);
		srv.searchMetric(null);
		assertTrue(true);
	}
}
