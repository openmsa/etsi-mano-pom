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
package com.ubiqube.etsi.mano.service.event;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

@ExtendWith(MockitoExtension.class)
class NotificationsImplTest {
	@Mock
	private ObjectMapper mapper;
	@Mock
	private HttpGateway httpGateway;
	@Mock
	private FluxRest fluxRest;

	private final Servers server = Servers.builder().url(URI.create("http://localhost/")).build();

	@Test
	void testCheckFail() throws Exception {
		final NotificationsImpl srv = new NotificationsImpl(mapper);
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateway, server, fluxRest);
		final ResponseEntity<Object> resp = ResponseEntity.status(200).build();
		when(fluxRest.getWithReturn(any(), any(), any())).thenReturn(resp);
		assertThrows(GenericException.class, () -> srv.check(serverAdapter, URI.create("http://localhost/")));
	}

	@Test
	void testCheckFailNull() throws Exception {
		final NotificationsImpl srv = new NotificationsImpl(mapper);
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateway, server, fluxRest);
		when(fluxRest.getWithReturn(any(), any(), any())).thenReturn(null);
		assertThrows(GenericException.class, () -> srv.check(serverAdapter, URI.create("http://localhost/")));
	}

	@Test
	void testCheckSuccess() throws Exception {
		final NotificationsImpl srv = new NotificationsImpl(mapper);
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateway, server, fluxRest);
		final ResponseEntity<Object> resp = ResponseEntity.status(204).build();
		when(fluxRest.getWithReturn(any(), any(), any())).thenReturn(resp);
		srv.check(serverAdapter, URI.create("http://localhost/"));
		assertTrue(true);
	}

	@Test
	void testDoNotification() {
		final NotificationsImpl srv = new NotificationsImpl(mapper);
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateway, server, fluxRest);
		srv.doNotification(null, URI.create("http://localhost/"), serverAdapter);
		assertTrue(true);
	}
}
