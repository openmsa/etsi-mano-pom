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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.HttpGateway;

@ExtendWith(MockitoExtension.class)
class ManoVimTest {
	@Mock
	private ManoClient manoClient;
	@Mock
	private HttpGateway httpGateway;
	@Mock
	private FluxRest fluxRest;

	@Test
	void testName() throws Exception {
		final ManoVim mv = new ManoVim(manoClient);
		final VimConnectionInformation vim = new VimConnectionInformation();
		final Servers server = Servers.builder().url(URI.create("http://localhost/")).build();
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdapter);
		when(fluxRest.post(any(), any(), any(), any())).thenReturn(vim);
		mv.register(vim, "http://localhost/");
		assertTrue(true);
	}
}
