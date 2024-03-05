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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Port;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.VnfPortVt;

@ExtendWith(MockitoExtension.class)
class VnfPortUowTest {
	@Mock
	private Vim vim;
	@Mock
	private Context3d context;
	@Mock
	private com.ubiqube.etsi.mano.service.vim.Network network;

	@Test
	void test() {
		final VnfPortTask nt = new VnfPortTask();
		final VnfLinkPort vnfLinkPort = new VnfLinkPort();
		nt.setVnfLinkPort(vnfLinkPort);
		final VirtualTaskV3<VnfPortTask> vt = new VnfPortVt(nt);
		assertNotNull(vt.getType());
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfPortUow uow = new VnfPortUow(vt, vim, vimConn);
		when(context.get(eq(Network.class), any())).thenReturn("");
		when(vim.network(vimConn)).thenReturn(network);
		final Port port = new Port();
		port.setId(UUID.randomUUID());
		when(network.createPort(any(), any(), any(), any(), any())).thenReturn(port);
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testExternal() {
		final VnfPortTask nt = new VnfPortTask();
		final VnfLinkPort vnfLinkPort = new VnfLinkPort();
		nt.setVnfLinkPort(vnfLinkPort);
		final ExtManagedVirtualLinkDataEntity exter = new ExtManagedVirtualLinkDataEntity();
		nt.setExternal(exter);
		final VirtualTaskV3<VnfPortTask> vt = new VnfPortVt(nt);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfPortUow uow = new VnfPortUow(vt, vim, vimConn);
		when(vim.network(vimConn)).thenReturn(network);
		final Port port = new Port();
		port.setId(UUID.randomUUID());
		when(network.createPort(any(), any(), any(), any(), any())).thenReturn(port);
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final VnfPortTask nt = new VnfPortTask();
		final VirtualTaskV3<VnfPortTask> vt = new VnfPortVt(nt);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfPortUow uow = new VnfPortUow(vt, vim, vimConn);
		when(vim.network(vimConn)).thenReturn(network);
		uow.rollback(context);
		assertTrue(true);
	}
}
