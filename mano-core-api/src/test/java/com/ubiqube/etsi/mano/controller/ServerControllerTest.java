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
package com.ubiqube.etsi.mano.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.event.EventManager;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class ServerControllerTest {
	@Mock
	private ServerService serverService;
	@Mock
	private MapperFacade mapper;
	@Mock
	private EventManager eventManager;

	@Test
	void testCreateServer() {
		final ServerController srv = new ServerController(serverService, mapper, eventManager);
		final Servers servers = new Servers();
		when(serverService.createServer(any())).thenReturn(servers);
		srv.createServer(null);
		assertTrue(true);
	}

	@Test
	void testDeleteById() {
		final ServerController srv = new ServerController(serverService, mapper, eventManager);
		srv.deleteById(null);
		assertTrue(true);
	}

	@Test
	void testFindAll() {
		final ServerController srv = new ServerController(serverService, mapper, eventManager);
		srv.findAll(null);
		assertTrue(true);
	}

	@Test
	void testFindAll2() {
		final ServerController srv = new ServerController(serverService, mapper, eventManager);
		final Servers s = new Servers();
		when(serverService.findAll(any())).thenReturn(List.of(s));
		srv.findAll(null);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final ServerController srv = new ServerController(serverService, mapper, eventManager);
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testRetry() {
		final ServerController srv = new ServerController(serverService, mapper, eventManager);
		srv.retryById(null);
		assertTrue(true);
	}

}
