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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.v2.DnsZoneTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.DnsZoneVt;

@ExtendWith(MockitoExtension.class)
class DnsZoneSystemTest {
	@Mock
	private VimManager vimManager;
	@Mock
	private OrchestrationServiceV3<DnsZoneTask> orcService;

	@Test
	void test() {
		final DnsZoneSystem srv = new DnsZoneSystem(vimManager);
		final DnsZoneTask nt = new DnsZoneTask();
		final VirtualTaskV3<DnsZoneTask> task = new DnsZoneVt(nt);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		srv.getImplementation(orcService, task, vimConn);
		assertNotNull(srv.getType());
		assertNotNull(srv.getVimType());
	}

}
