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
package com.ubiqube.etsi.mano.service.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.service.HttpGateway;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class ManoServerTest {
	@Mock
	private ManoClient manoClient;
	@Mock
	private HttpGateway httpGateway;
	@Mock
	private FluxRest fluxRest;
	@Mock
	private MapperFacade mapper;

	@Test
	void testCreate() throws Exception {
		final ManoServer ms = new ManoServer(manoClient);
		final ServerAdapter sa = createAdapter();
		when(manoClient.getServer()).thenReturn(sa);
		ms.create(null, "http://localhost/");
		assertTrue(true);
	}

	@Test
	void testList() throws Exception {
		final ManoServer ms = new ManoServer(manoClient);
		final ServerAdapter sa = createAdapter();
		when(manoClient.getServer()).thenReturn(sa);
		when(manoClient.getObjectId()).thenReturn(UUID.randomUUID());
		ms.list(mapper, "http://localhost/");
		assertTrue(true);
	}

	private ServerAdapter createAdapter() {
		final Servers server = Servers.builder()
				.url(URI.create("http://localhost/"))
				.build();
		return new ServerAdapter(httpGateway, server, fluxRest);
	}

}
