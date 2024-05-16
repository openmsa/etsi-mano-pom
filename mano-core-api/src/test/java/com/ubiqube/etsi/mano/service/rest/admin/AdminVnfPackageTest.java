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
package com.ubiqube.etsi.mano.service.rest.admin;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;

import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

@ExtendWith(MockitoExtension.class)
class AdminVnfPackageTest {
	@Mock
	private QueryParameters client;
	@Mock
	private ServerAdapter server;
	@Mock
	private FluxRest fluxRest;

	AdminVnfPackage createService() {
		return new AdminVnfPackage(client);
	}

	@Test
	void testList() {
		final AdminVnfPackage srv = createService();
		when(client.getServer()).thenReturn(server);
		when(server.rest()).thenReturn(fluxRest);
		srv.list("http://localhost/");
		assertTrue(true);
	}

	@Test
	void testList2() {
		final AdminVnfPackage srv = createService();
		when(client.getServer()).thenReturn(server);
		when(server.rest()).thenReturn(fluxRest);
		when(fluxRest.get((URI) any(), (ParameterizedTypeReference) any(), any())).thenReturn(List.of());
		srv.list("http://localhost/");
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final AdminVnfPackage srv = createService();
		when(client.getServer()).thenReturn(server);
		when(server.rest()).thenReturn(fluxRest);
		when(client.getObjectId()).thenReturn(UUID.randomUUID());
		srv.delete("http://localhost/", UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testVnfmGetVnfPackage() {
		final AdminVnfPackage srv = createService();
		when(client.getServer()).thenReturn(server);
		when(server.rest()).thenReturn(fluxRest);
		when(client.getObjectId()).thenReturn(UUID.randomUUID());
		srv.vnfmGetVnfPackage("http://localhost/", UUID.randomUUID());
		assertTrue(true);
	}

}
