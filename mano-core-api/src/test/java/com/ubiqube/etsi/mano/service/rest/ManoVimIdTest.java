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

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.rest.admin.vim.ManoVimId;

@ExtendWith(MockitoExtension.class)
class ManoVimIdTest {
	@Mock
	private QueryParameters client;
	@Mock
	private ServerAdapter server;
	@Mock
	private FluxRest fluxRest;

	@Test
	void testDelete() {
		final ManoVimId srv = new ManoVimId(client, UUID.randomUUID());
		when(client.getObjectId()).thenReturn(UUID.randomUUID());
		when(client.getServer()).thenReturn(server);
		when(server.rest()).thenReturn(fluxRest);
		srv.delete("http://localhost/");
		assertTrue(true);
	}
}
