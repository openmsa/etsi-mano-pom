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
package com.ubiqube.etsi.mano.vnfm.service.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VlProfileEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.VlProtocolData;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.NetWorkVt;

@ExtendWith(MockitoExtension.class)
class VimNetworkSystemTest {
	@Mock
	private Vim vim;
	@Mock
	private VimManager vimManager;
	@Mock
	private OrchestrationServiceV3<NetworkTask> orch;

	@Test
	void test() {
		final VimNetworkSystem srv = new VimNetworkSystem(vim, vimManager);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final NetworkTask nt = new NetworkTask();
		final VnfVl vnfVl = new VnfVl();
		final VlProfileEntity profile = new VlProfileEntity();
		final VlProtocolData vp1 = new VlProtocolData();
		profile.setVirtualLinkProtocolData(Set.of(vp1));
		vnfVl.setVlProfileEntity(profile);
		nt.setVnfVl(vnfVl);
		final VirtualTaskV3<NetworkTask> vt = new NetWorkVt(nt);
		srv.getImplementation(orch, vt, vimConn);
		srv.getType();
		srv.getVimType();
		assertTrue(true);
	}

}
