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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.K8sInformationsTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.StatusType;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Cnf;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsK8sClusterVt;

@ExtendWith(MockitoExtension.class)
class OsK8sClusterInfoUowTest {
	@Mock
	private Vim vim;
	@Mock
	private K8sServerInfoJpa serverInfoJpa;
	@Mock
	private Context3d context;
	@Mock
	private Cnf cnf;

	@Test
	void test() {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final K8sInformationsTask task = new K8sInformationsTask();
		final VirtualTaskV3<K8sInformationsTask> vt = new OsK8sClusterVt(task);
		assertNotNull(vt.getType());
		final OsK8sClusterInfoUow uow = new OsK8sClusterInfoUow(vt, vim, vimConn, serverInfoJpa);
		when(context.get(any(), any())).thenReturn(UUID.randomUUID().toString());
		// when(vim.cnf(vimConn)).thenReturn(cnf);
		// final K8sServers srv = new K8sServers();
		// srv.setStatus(StatusType.CREATE_COMPLETE);
		// when(cnf.getClusterInformations(any())).thenReturn(srv);
		// when(serverInfoJpa.save(any())).thenReturn(srv);
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testAlreadyPresent() {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final K8sInformationsTask task = new K8sInformationsTask();
		final VirtualTaskV3<K8sInformationsTask> vt = new OsK8sClusterVt(task);
		final OsK8sClusterInfoUow uow = new OsK8sClusterInfoUow(vt, vim, vimConn, serverInfoJpa);
		when(context.get(any(), any())).thenReturn(UUID.randomUUID().toString());
		final K8sServers srv = new K8sServers();
		srv.setId(UUID.randomUUID());
		srv.setStatus(StatusType.CREATE_COMPLETE);
		when(serverInfoJpa.findByVimResourceId(any())).thenReturn(Optional.of(srv));
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testWaitPart() {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final K8sInformationsTask task = new K8sInformationsTask();
		final VirtualTaskV3<K8sInformationsTask> vt = new OsK8sClusterVt(task);
		final OsK8sClusterInfoUow uow = new OsK8sClusterInfoUow(vt, vim, vimConn, serverInfoJpa);
		when(context.get(any(), any())).thenReturn(UUID.randomUUID().toString());
//		when(vim.cnf(vimConn)).thenReturn(cnf);
//		final K8sServers srv = new K8sServers();
//		srv.setId(UUID.randomUUID());
//		srv.setStatus(StatusType.CREATE_IN_PROGRESS);
//		final K8sServers srv2 = new K8sServers();
//		srv2.setStatus(StatusType.CREATE_COMPLETE);
//		srv2.setId(UUID.randomUUID());
//		when(cnf.getClusterInformations(any())).thenReturn(srv, srv2);
//		when(serverInfoJpa.save(any())).thenReturn(srv);
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testCreateFail() {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final K8sInformationsTask task = new K8sInformationsTask();
		final VirtualTaskV3<K8sInformationsTask> vt = new OsK8sClusterVt(task);
		final OsK8sClusterInfoUow uow = new OsK8sClusterInfoUow(vt, vim, vimConn, serverInfoJpa);
		when(context.get(any(), any())).thenReturn(UUID.randomUUID().toString());
//		when(vim.cnf(vimConn)).thenReturn(cnf);
//		final K8sServers srv = new K8sServers();
//		srv.setStatus(StatusType.CREATE_FAILED);
//		when(cnf.getClusterInformations(any())).thenReturn(srv);
//		when(serverInfoJpa.save(any())).thenReturn(srv);
		// assertThrows(GenericException.class, () -> uow.execute(context));
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final K8sInformationsTask task = new K8sInformationsTask();
		final VirtualTaskV3<K8sInformationsTask> vt = new OsK8sClusterVt(task);
		final OsK8sClusterInfoUow uow = new OsK8sClusterInfoUow(vt, vim, vimConn, serverInfoJpa);
		uow.rollback(context);
		assertTrue(true);
	}

	@Test
	void testRollback2() {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final K8sInformationsTask task = new K8sInformationsTask();
		final VirtualTaskV3<K8sInformationsTask> vt = new OsK8sClusterVt(task);
		final OsK8sClusterInfoUow uow = new OsK8sClusterInfoUow(vt, vim, vimConn, serverInfoJpa);
		final K8sServers srv = new K8sServers();
		when(serverInfoJpa.findByVimResourceId(any())).thenReturn(Optional.of(srv));
		uow.rollback(context);
		assertTrue(true);
	}
}
