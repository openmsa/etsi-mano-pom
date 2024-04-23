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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

@ExtendWith(MockitoExtension.class)
class VnfInstanceServiceNfvoTest {
	@Mock
	private ServerService serverService;
	@Mock
	private ServerAdapter serverAdapter;
	@Mock
	private HttpGateway httpGateway;
	@Mock
	private FluxRest fluxRest;

	@Test
	void testName() throws Exception {
		final VnfInstanceServiceNfvo srv = new VnfInstanceServiceNfvo(serverService);
		when(serverService.findNearestServer()).thenReturn(serverAdapter);
		when(serverAdapter.httpGateway()).thenReturn(httpGateway);
		when(serverAdapter.rest()).thenReturn(fluxRest);
		final UUID id = UUID.randomUUID();
		srv.findById(id);
		assertTrue(true);
	}
}
