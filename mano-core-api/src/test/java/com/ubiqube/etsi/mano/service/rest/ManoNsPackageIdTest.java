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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.service.rest.nspkg.ManoNsPackageId;

@ExtendWith(MockitoExtension.class)
class ManoNsPackageIdTest {
	@Mock
	private QueryParameters client;
	@Mock
	private ManoQueryBuilder query;

	@Test
	void testFind() {
		final ManoNsPackageId srv = new ManoNsPackageId(client, UUID.randomUUID());
		when(client.createQuery()).thenReturn(query);
		when(query.setWireOutClass(any())).thenReturn(query);
		when(query.setOutClass(any())).thenReturn(query);
		srv.find();
		assertTrue(true);
	}

	@Test
	void testOnboard() {
		final ManoNsPackageId srv = new ManoNsPackageId(client, UUID.randomUUID());
		final String accept = null;
		final Path path = Paths.get("/");
		when(client.createQuery()).thenReturn(query);
		srv.onboard(path, accept);
		assertTrue(true);
	}

	@Test
	void testWaitOnboarding() {
		final ManoNsPackageId srv = new ManoNsPackageId(client, UUID.randomUUID());
		when(client.createQuery()).thenReturn(query);
		when(query.setWireOutClass(any())).thenReturn(query);
		when(query.setOutClass(any())).thenReturn(query);
		final NsdPackage nsdPacakge = new NsdPackage();
		when(query.getSingle()).thenReturn(nsdPacakge);
		srv.waitForOnboarding();
		assertTrue(true);
	}

}
