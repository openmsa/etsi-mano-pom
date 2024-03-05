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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.service.ServerService;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class ManoClientFactoryTest {
	@Mock
	private MapperFacade mapper;
	@Mock
	private ServerService server;

	@Test
	void testGetClient() {
		final ManoClientFactory srv = new ManoClientFactory(mapper, server);
		srv.getClient();
		assertTrue(true);
	}

	@Test
	void testgetClient2() {
		final ManoClientFactory srv = new ManoClientFactory(mapper, server);
		final Servers servers = new Servers();
		srv.getClient(servers);
		assertTrue(true);
	}

}
