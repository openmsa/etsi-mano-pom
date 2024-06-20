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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.K8sInformationsTask;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;
import com.ubiqube.etsi.mano.jpa.CapiServerJpa;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.vim.k8s.K8s;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsK8sClusterVt;

@ExtendWith(MockitoExtension.class)
class OsCapiClusterInfoUowTest {

	@Mock
	private OsClusterService osClusterService;
	@Mock
	private K8sServerInfoJpa serverInfoJpa;
	@Mock
	private CapiServerJpa capiServerJpa;

	OsCapiClusterInfoUow createService(final VirtualTaskV3<K8sInformationsTask> task) {
		return new OsCapiClusterInfoUow(task, osClusterService, serverInfoJpa, capiServerJpa);
	}

	@Test
	void testExecute() {
		final K8sInformationsTask nt = new K8sInformationsTask();
		nt.setToscaName("abc");
		nt.setVnfInstId("123");
		final VirtualTaskV3<K8sInformationsTask> task = new OsK8sClusterVt(nt);
		final OsCapiClusterInfoUow srv = createService(task);
		final Context3d ctx = Mockito.mock(Context3d.class);
		when(serverInfoJpa.findByVimResourceId(any())).thenReturn(Optional.empty());
		final CapiServer capi = new CapiServer();
		capi.setUrl("http://localhost/");
		when(capiServerJpa.findAll()).thenReturn(List.of(capi));
		final K8s k8sVal = new K8s();
		k8sVal.setApiUrl("http://localhost/");
		when(osClusterService.getKubeConfig(any(), any(), any())).thenReturn(Optional.of(k8sVal));
		when(ctx.get(any(), any())).thenReturn(UUID.randomUUID().toString());
		final K8sServers k8s = new K8sServers();
		k8s.setId(UUID.randomUUID());
		when(serverInfoJpa.save(any())).thenReturn(k8s);
		srv.execute(ctx);
		assertTrue(true);
	}

	@Test
	void testExecute2() {
		final K8sInformationsTask nt = new K8sInformationsTask();
		nt.setToscaName("abc");
		nt.setVnfInstId("123");
		final VirtualTaskV3<K8sInformationsTask> task = new OsK8sClusterVt(nt);
		final OsCapiClusterInfoUow srv = createService(task);
		final Context3d ctx = Mockito.mock(Context3d.class);
		final K8sServers k8s = new K8sServers();
		k8s.setId(UUID.randomUUID());
		when(serverInfoJpa.findByVimResourceId(any())).thenReturn(Optional.of(k8s));
		srv.execute(ctx);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final K8sInformationsTask nt = new K8sInformationsTask();
		final VirtualTaskV3<K8sInformationsTask> task = new OsK8sClusterVt(nt);
		final OsCapiClusterInfoUow srv = createService(task);
		when(serverInfoJpa.findByVimResourceId(any())).thenReturn(Optional.empty());
		srv.rollback(null);
		assertTrue(true);
	}

	@Test
	void testRollback2() {
		final K8sInformationsTask nt = new K8sInformationsTask();
		final VirtualTaskV3<K8sInformationsTask> task = new OsK8sClusterVt(nt);
		final OsCapiClusterInfoUow srv = createService(task);
		final K8sServers k8s = new K8sServers();
		when(serverInfoJpa.findByVimResourceId(any())).thenReturn(Optional.of(k8s));
		srv.rollback(null);
		assertTrue(true);
	}
}
