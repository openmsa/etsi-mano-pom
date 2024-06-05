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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.magnum;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Cnf;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vim.dummy.DummyCnf;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsContainerVt;

@ExtendWith(MockitoExtension.class)
class OsMagnumContainerUowTest {
	@Mock
	private Vim vim;

	OsMagnumContainerUow createService(final VirtualTaskV3<OsContainerTask> task, final VimConnectionInformation vimConnectionInformation) {
		return new OsMagnumContainerUow(task, vim, vimConnectionInformation);
	}

	@Test
	void testExecute() {
		final OsContainerTask nt = new OsContainerTask();
		nt.setOsContainer(new OsContainer());
		final VirtualTaskV3<OsContainerTask> task = new OsContainerVt(nt);
		final VimConnectionInformation vci = new VimConnectionInformation();
		vci.setCnfInfo(new CnfInformations());
		final OsMagnumContainerUow srv = createService(task, vci);
		final Cnf cnf = new DummyCnf();
		when(vim.cnf(vci)).thenReturn(cnf);
		srv.execute(null);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final OsContainerTask nt = new OsContainerTask();
		nt.setOsContainer(new OsContainer());
		final VirtualTaskV3<OsContainerTask> task = new OsContainerVt(nt);
		final VimConnectionInformation vci = new VimConnectionInformation();
		vci.setCnfInfo(new CnfInformations());
		final OsMagnumContainerUow srv = createService(task, vci);
		final Cnf cnf = new DummyCnf();
		when(vim.cnf(vci)).thenReturn(cnf);
		srv.rollback(null);
		assertTrue(true);
	}
}
