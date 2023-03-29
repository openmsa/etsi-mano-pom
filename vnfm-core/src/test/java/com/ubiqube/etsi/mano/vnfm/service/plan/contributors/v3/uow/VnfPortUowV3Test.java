/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.uow;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.VnfPortVt;

@ExtendWith(MockitoExtension.class)
class VnfPortUowV3Test {
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
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfPortUowV3 uow = new VnfPortUowV3(vt, vim, vimConn);
		when(context.get(eq(Network.class), any())).thenReturn("");
		when(vim.network(vimConn)).thenReturn(network);
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
		final VnfPortUowV3 uow = new VnfPortUowV3(vt, vim, vimConn);
		when(vim.network(vimConn)).thenReturn(network);
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final VnfPortTask nt = new VnfPortTask();
		final VirtualTaskV3<VnfPortTask> vt = new VnfPortVt(nt);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfPortUowV3 uow = new VnfPortUowV3(vt, vim, vimConn);
		when(vim.network(vimConn)).thenReturn(network);
		uow.rollback(context);
		assertTrue(true);
	}
}
