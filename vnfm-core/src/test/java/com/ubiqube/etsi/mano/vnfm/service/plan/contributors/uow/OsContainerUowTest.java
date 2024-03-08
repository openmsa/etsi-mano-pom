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
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.JujuInformations;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.JujuCloudService;
import com.ubiqube.etsi.mano.service.vim.Cnf;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsContainerVt;

@ExtendWith(MockitoExtension.class)
class OsContainerUowTest {
	@Mock
	private Vim vim;
	@Mock
	private Context3d context;
	@Mock
	private Cnf cnf;
	@Mock
	private JujuCloudService jujuCloudService;
	@Mock
	private VnfPackageRepository vnfRepo;

	@Test
	void testExecute() {
		final OsContainerTask task = new OsContainerTask();
		final OsContainer cont = new OsContainer();
		task.setOsContainer(cont);
		final VirtualTaskV3<OsContainerTask> vt = new OsContainerVt(task);
		assertNotNull(vt.getType());
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final CnfInformations cnf1 = new CnfInformations();
		vimConn.setCnfInfo(cnf1);
		final OsContainerUow uow = new OsContainerUow(vt, vim, vimConn, jujuCloudService, vnfRepo);
		when(vim.cnf(vimConn)).thenReturn(cnf);
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final OsContainerTask task = new OsContainerTask();
		final OsContainer osc = new OsContainer();
		final SoftwareImage sw = new SoftwareImage();
		final Map<String, SoftwareImage> map = Map.of("img", sw);
		osc.setArtifacts(map);
		task.setOsContainer(osc);
		final VirtualTaskV3<OsContainerTask> vt = new OsContainerVt(task);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final JujuInformations juju = new JujuInformations();
		juju.setRegion("region");
		vimConn.setJujuInfo(juju);
		final OsContainerUow uow = new OsContainerUow(vt, vim, vimConn, jujuCloudService, vnfRepo);
		when(vim.cnf(vimConn)).thenReturn(cnf);
		uow.rollback(context);
		assertTrue(true);
	}
}