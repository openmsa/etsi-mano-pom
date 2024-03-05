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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.service.event.EventManager;

@ExtendWith(MockitoExtension.class)
class VnfmRegisterServiceTest {
	@Mock
	private EventManager eventManager;
	@Mock
	private ServersJpa serversJpa;

	@Test
	void testEmptyList() throws Exception {
		final VnfmRegisterService srv = new VnfmRegisterService(eventManager, serversJpa);
		when(serversJpa.findByServerStatusIn(any())).thenReturn(List.of());
		srv.run(null);
		assertTrue(true);
	}

	@Test
	void testOk() throws Exception {
		final VnfmRegisterService srv = new VnfmRegisterService(eventManager, serversJpa);
		final Servers ser = new Servers();
		when(serversJpa.findByServerStatusIn(any())).thenReturn(List.of(ser));
		srv.run(null);
		assertTrue(true);
	}
}
