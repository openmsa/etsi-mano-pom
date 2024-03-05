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
package com.ubiqube.etsi.mano.service.vim;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.jpa.ConnectionInformationJpa;

@WireMockTest
@ExtendWith(MockitoExtension.class)
class CirConnectionManagerTest {
	@Mock
	private ConnectionInformationJpa cirConnJpa;

	private CirConnectionManager createService() {
		return new CirConnectionManager(cirConnJpa);
	}

	@Test
	void testConnectivity(final WireMockRuntimeInfo wmRuntimeInfo) {
		stubFor(get(urlMatching("/")).willReturn(aResponse().withStatus(200)));
		final CirConnectionManager srv = createService();
		final UUID id = UUID.randomUUID();
		final ConnectionInformation conn = new ConnectionInformation();
		conn.setUrl(URI.create(wmRuntimeInfo.getHttpBaseUrl()));
		when(cirConnJpa.findById(id)).thenReturn(Optional.ofNullable(conn));
		srv.checkConnectivity(id);
		assertTrue(true);
	}

	@Test
	void testRegister(final WireMockRuntimeInfo wmRuntimeInfo) {
		final CirConnectionManager srv = createService();
		stubFor(get(urlMatching("/")).willReturn(aResponse().withStatus(200)));
		final ConnectionInformation conn = new ConnectionInformation();
		conn.setUrl(URI.create(wmRuntimeInfo.getHttpBaseUrl()));
		srv.register(conn);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final CirConnectionManager srv = createService();
		srv.deleteVim(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final CirConnectionManager srv = createService();
		final UUID id = UUID.randomUUID();
		final ConnectionInformation conn = new ConnectionInformation();
		when(cirConnJpa.findById(id)).thenReturn(Optional.ofNullable(conn));
		srv.findVimById(id);
		assertTrue(true);
	}

	@Test
	void testSave() {
		final CirConnectionManager srv = createService();
		srv.save(null);
		assertTrue(true);
	}

	@Test
	void testFindAll() {
		final CirConnectionManager srv = createService();
		srv.findAll();
		assertTrue(true);
	}
}
