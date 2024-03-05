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
package com.ubiqube.etsi.mano.mon.core.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.mon.data.MonConnInformation;
import com.ubiqube.etsi.mano.service.mon.dto.ConnInfo;
import com.ubiqube.etsi.mano.service.mon.dto.PollingJob;

@ExtendWith(MockitoExtension.class)
class MonApiImplTest {
	@Mock
	private ConnectionInformationService connRepo;
	@Mock
	private PollingJobService pollingJobRepo;

	@Test
	void testRegister() {
		final MonApiImpl api = new MonApiImpl(pollingJobRepo, connRepo);
		final PollingJob pj = new PollingJob();
		pj.setResourceId("r");
		final ConnInfo connInfo = new ConnInfo();
		pj.setConnection(connInfo);
		api.register(pj);
		assertTrue(true);
	}

	@Test
	void testRegisterAllready() {
		final MonApiImpl api = new MonApiImpl(pollingJobRepo, connRepo);
		final PollingJob pj = new PollingJob();
		final ConnInfo connInfo = new ConnInfo();
		pj.setConnection(connInfo);
		pj.setResourceId("r");
		final MonConnInformation monConn = new MonConnInformation();
		when(connRepo.findByConnId(any())).thenReturn(Optional.of(monConn));
		api.register(pj);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final MonApiImpl api = new MonApiImpl(pollingJobRepo, connRepo);
		api.delete(null);
		assertTrue(true);
	}

	@Test
	void testList() {
		final MonApiImpl api = new MonApiImpl(pollingJobRepo, connRepo);
		api.list();
		assertTrue(true);
	}

	@Test
	void testlistConnections() {
		final MonApiImpl api = new MonApiImpl(pollingJobRepo, connRepo);
		api.listConnections();
		assertTrue(true);
	}

	@Test
	void testDeleteConnection() {
		final MonApiImpl api = new MonApiImpl(pollingJobRepo, connRepo);
		api.deleteConnection(null);
		assertTrue(true);
	}
}
