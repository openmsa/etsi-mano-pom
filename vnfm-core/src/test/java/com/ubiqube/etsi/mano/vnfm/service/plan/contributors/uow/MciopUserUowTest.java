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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.MciopUserTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Cnf;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.MciopUserVt;

@ExtendWith(MockitoExtension.class)
class MciopUserUowTest {
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
		final MciopUserTask nt = new MciopUserTask();
		final VirtualTaskV3<MciopUserTask> vt = new MciopUserVt(nt);
		assertNotNull(vt.getType());
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final MciopUserUow uow = new MciopUserUow(vt, vim, vimConn, serverInfoJpa, "cn");
		when(context.get(eq(OsK8sInformationsNode.class), any())).thenReturn(UUID.randomUUID().toString());
		final K8sServers k8s = new K8sServers();
		when(serverInfoJpa.findById(any())).thenReturn(Optional.of(k8s));
		when(vim.cnf(vimConn)).thenReturn(cnf);
		when(cnf.sign(any(), eq(k8s))).thenReturn(k8s);
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testNotFound() {
		final MciopUserTask nt = new MciopUserTask();
		final VirtualTaskV3<MciopUserTask> vt = new MciopUserVt(nt);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final MciopUserUow uow = new MciopUserUow(vt, vim, vimConn, serverInfoJpa, "cn");
		when(context.get(eq(OsK8sInformationsNode.class), any())).thenReturn(UUID.randomUUID().toString());
		assertThrows(GenericException.class, () -> uow.execute(context));
	}

	@Test
	void testRollback() {
		final MciopUserTask nt = new MciopUserTask();
		final VirtualTaskV3<MciopUserTask> vt = new MciopUserVt(nt);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final MciopUserUow uow = new MciopUserUow(vt, vim, vimConn, serverInfoJpa, "cn");
		uow.rollback(context);
		assertTrue(true);
	}
}
