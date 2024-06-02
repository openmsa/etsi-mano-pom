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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VduProfile;
import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.jpa.CapiServerJpa;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsContainerDeployableVt;

@ExtendWith(MockitoExtension.class)
class OsCapiClusterUowTest {
	@Mock
	private OsClusterService osCluster;
	@Mock
	private CapiServerJpa capiServer;

	OsCapiClusterUow createService(final VirtualTaskV3<OsContainerDeployableTask> task, final VimConnectionInformation vci) {
		return new OsCapiClusterUow(task, osCluster, vci, capiServer);
	}

	@Test
	void testExecute() {
		final OsContainerDeployableTask nt = new OsContainerDeployableTask();
		nt.setToscaName("name");
		nt.setVnfInstId("123");
		final OsContainerDeployableUnit deploy = new OsContainerDeployableUnit();
		final VduProfile vduProfile = new VduProfile();
		deploy.setVduProfile(vduProfile);
		nt.setOsContainerDeployableUnit(deploy);
		final VirtualTaskV3<OsContainerDeployableTask> task = new OsContainerDeployableVt(nt);
		final VimConnectionInformation vci = new VimConnectionInformation();
		final CnfInformations cnfInfo = new CnfInformations();
		cnfInfo.setDnsServer("1.2.3.4");
		vci.setCnfInfo(cnfInfo);
		final OsCapiClusterUow srv = createService(task, vci);
		//
		final CapiServer capiSrv = new CapiServer();
		when(capiServer.findAll()).thenReturn(List.of(capiSrv));
		srv.execute(null);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final OsContainerDeployableTask nt = new OsContainerDeployableTask();
		nt.setToscaName("name");
		nt.setVnfInstId("123");
		final VirtualTaskV3<OsContainerDeployableTask> task = new OsContainerDeployableVt(nt);
		final VimConnectionInformation vci = new VimConnectionInformation();
		final OsCapiClusterUow srv = createService(task, vci);
		//
		final CapiServer capiSrv = new CapiServer();
		when(capiServer.findAll()).thenReturn(List.of(capiSrv));
		srv.rollback(null);
		assertTrue(true);
	}

}
