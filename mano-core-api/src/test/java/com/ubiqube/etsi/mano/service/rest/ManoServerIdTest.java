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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.service.HttpGateway;

@ExtendWith(MockitoExtension.class)
class ManoServerIdTest {
	@Mock
	private ManoClient manoClient;
	@Mock
	private HttpGateway httpGateway;
	@Mock
	private FluxRest fluxRest;

	@Test
	void testDelete() throws Exception {
		final ManoServerId srv = new ManoServerId(manoClient, UUID.randomUUID());
		final ServerAdapter serverAdapter = createAdapter();
		when(manoClient.getServer()).thenReturn(serverAdapter);
		when(fluxRest.delete(any(), any(), any())).thenReturn(serverAdapter);
		when(manoClient.getObjectId()).thenReturn(UUID.randomUUID());
		srv.delete("http://localhost/");
		assertTrue(true);
	}

	@Test
	void testFindNoBody() throws Exception {
		final ManoServerId srv = new ManoServerId(manoClient, UUID.randomUUID());
		final ServerAdapter serverAdapter = createAdapter();
		when(manoClient.getServer()).thenReturn(serverAdapter);
		when(manoClient.getObjectId()).thenReturn(UUID.randomUUID());
		srv.find("http://localhost/");
		assertTrue(true);
	}

	@Test
	void testFindWithBody() throws Exception {
		final ManoServerId srv = new ManoServerId(manoClient, UUID.randomUUID());
		final ServerAdapter serverAdapter = createAdapter();
		when(manoClient.getServer()).thenReturn(serverAdapter);
		when(manoClient.getObjectId()).thenReturn(UUID.randomUUID());
		final ResponseEntity<Servers> resp = ResponseEntity.status(200).body(Servers.builder().url(URI.create("http://localhost/")).build());
		when(fluxRest.getWithReturn(any(), eq(Servers.class), any())).thenReturn(resp);
		srv.find("http://localhost/");
		assertTrue(true);
	}

	@Test
	void testWaitForServer() throws Exception {
		final ManoServerId srv = new ManoServerId(manoClient, UUID.randomUUID());
		final ServerAdapter serverAdapter = createAdapter();
		when(manoClient.getServer()).thenReturn(serverAdapter);
		when(manoClient.getObjectId()).thenReturn(UUID.randomUUID());
		final ResponseEntity<Servers> resp = ResponseEntity.status(200).body(Servers.builder()
				.serverStatus(PlanStatusType.FAILED)
				.url(URI.create("http://localhost/"))
				.build());
		when(fluxRest.getWithReturn(any(), eq(Servers.class), any())).thenReturn(resp);
		srv.waitForServer("http://localhost/");
		assertTrue(true);
	}

	private ServerAdapter createAdapter() {
		final Servers server = Servers.builder()
				.url(URI.create("http://localhost/"))
				.build();
		return new ServerAdapter(httpGateway, server, fluxRest);
	}
}
