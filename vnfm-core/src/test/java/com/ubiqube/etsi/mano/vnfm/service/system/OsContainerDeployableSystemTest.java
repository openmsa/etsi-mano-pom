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

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsContainerDeployableVt;

@ExtendWith(MockitoExtension.class)
class OsContainerDeployableSystemTest {
	@Mock
	private Vim vim;
	@Mock
	private VimManager vimManager;
	@Mock
	private K8sServerInfoJpa k8sService;
	@Mock
	private OrchestrationServiceV3<OsContainerDeployableTask> orchService;

	@Test
	void test() {
		final OsContainerDeployableSystem srv = new OsContainerDeployableSystem(vim, vimManager);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final OsContainerDeployableTask nt = new OsContainerDeployableTask();
		final VirtualTaskV3<OsContainerDeployableTask> vt = new OsContainerDeployableVt(nt);
		srv.getImplementation(orchService, vt, vimConn);
		assertNotNull(srv.getType());
		assertNotNull(srv.getVimType());
	}

}
