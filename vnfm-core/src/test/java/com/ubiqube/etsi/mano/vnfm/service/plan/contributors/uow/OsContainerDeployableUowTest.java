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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VduProfile;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Cnf;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsContainerDeployableVt;

@ExtendWith(MockitoExtension.class)
class OsContainerDeployableUowTest {
	@Mock
	private Vim vim;
	@Mock
	private Context3d context;
	@Mock
	private Cnf cnf;

	@Test
	void test() {
		final OsContainerDeployableTask nt = new OsContainerDeployableTask();
		final OsContainerDeployableUnit odu = new OsContainerDeployableUnit();
		final VduProfile profile = new VduProfile();
		odu.setVduProfile(profile);
		nt.setOsContainerDeployableUnit(odu);
		final VirtualTaskV3<OsContainerDeployableTask> vt = new OsContainerDeployableVt(nt);
		assertNotNull(vt.getType());
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final CnfInformations vnfInfo = new CnfInformations();
		vimConn.setCnfInfo(vnfInfo);
		final OsContainerDeployableUow3 uow = new OsContainerDeployableUow3(vt, vim, vimConn);
		when(vim.cnf(vimConn)).thenReturn(cnf);
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final OsContainerDeployableTask nt = new OsContainerDeployableTask();
		final VirtualTaskV3<OsContainerDeployableTask> vt = new OsContainerDeployableVt(nt);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final OsContainerDeployableUow3 uow = new OsContainerDeployableUow3(vt, vim, vimConn);
		when(vim.cnf(vimConn)).thenReturn(cnf);
		uow.rollback(context);
		assertTrue(true);
	}
}
