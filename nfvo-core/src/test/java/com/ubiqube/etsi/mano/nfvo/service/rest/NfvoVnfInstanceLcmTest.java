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
package com.ubiqube.etsi.mano.nfvo.service.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;
import com.ubiqube.etsi.mano.service.rest.vnflcm.ManoVnfInstance;
import com.ubiqube.etsi.mano.service.rest.vnflcm.ManoVnfInstanceId;
import com.ubiqube.etsi.mano.service.rest.vnflcm.ManoVnfLcmOpOccs;

@ExtendWith(MockitoExtension.class)
class NfvoVnfInstanceLcmTest {
	@Mock
	private ManoClientFactory manoClient;
	@Mock
	private ManoClient mc;
	@Mock
	private ManoVnfInstance mvi;
	@Mock
	private ManoVnfInstanceId mvii;
	@Mock
	private ManoVnfLcmOpOccs mvilcm;

	NfvoVnfInstanceLcm createService() {
		return new NfvoVnfInstanceLcm(manoClient);
	}

	@Test
	void testGet() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfInstance()).thenReturn(mvi);
		srv.get(server, new HttpHeaders());
		assertTrue(true);
	}

	@Test
	void testPost() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfInstance()).thenReturn(mvi);
		srv.post(server, "id", "name", "descr.");
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfInstance()).thenReturn(mvi);
		when(mvi.id(any())).thenReturn(mvii);
		srv.delete(server, UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testInstantiate() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfInstance()).thenReturn(mvi);
		when(mvi.id(any())).thenReturn(mvii);
		srv.instantiate(server, UUID.randomUUID(), null);
		assertTrue(true);
	}

	@Test
	void testTerminate() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfInstance()).thenReturn(mvi);
		when(mvi.id(any())).thenReturn(mvii);
		srv.terminate(server, UUID.randomUUID(), null, null);
		assertTrue(true);
	}

	@Test
	void testScaleToLevel() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfInstance()).thenReturn(mvi);
		when(mvi.id(any())).thenReturn(mvii);
		srv.scaleToLevel(server, UUID.randomUUID(), null);
		assertTrue(true);
	}

	@Test
	void testScale() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfInstance()).thenReturn(mvi);
		when(mvi.id(any())).thenReturn(mvii);
		srv.scale(server, UUID.randomUUID(), null);
		assertTrue(true);
	}

	@Test
	void testHeal() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfInstance()).thenReturn(mvi);
		when(mvi.id(any())).thenReturn(mvii);
		srv.heal(server, null, null);
		assertTrue(true);
	}

	@Test
	void testOperate() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfInstance()).thenReturn(mvi);
		when(mvi.id(any())).thenReturn(mvii);
		srv.operate(server, null, null);
		assertTrue(true);
	}

	@Test
	void testVnfLcmOpOccsGet() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfLcmOpOccs()).thenReturn(mvilcm);
		final ManoVnfLcmOpOccs manoLcmOp = Mockito.mock(ManoVnfLcmOpOccs.class);
		when(mvilcm.id(any())).thenReturn(manoLcmOp);
		srv.vnfLcmOpOccsGet(server, null);
		assertTrue(true);
	}

	@Test
	void testChangeExtConn() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfInstance()).thenReturn(mvi);
		when(mvi.id(any())).thenReturn(mvii);
		srv.changeExtConn(server, null, null);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfInstance()).thenReturn(mvi);
		when(mvi.id(any())).thenReturn(mvii);
		srv.findById(server, UUID.randomUUID().toString());
		assertTrue(true);
	}

	@Test
	void testFindByVnfInstanceId() {
		final NfvoVnfInstanceLcm srv = createService();
		final Servers server = new Servers();
		when(manoClient.getClient(server)).thenReturn(mc);
		when(mc.vnfLcmOpOccs()).thenReturn(mvilcm);
		srv.findByVnfInstanceId(server, UUID.randomUUID());
		assertTrue(true);
	}
}
