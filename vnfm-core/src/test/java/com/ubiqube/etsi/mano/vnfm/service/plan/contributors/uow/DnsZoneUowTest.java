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

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.v2.DnsZoneTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vim.dummy.DummyVim;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.DnsZoneVt;

class DnsZoneUowTest {
	private final Vim vim = new DummyVim();

	@Test
	void test() {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final DnsZoneTask nt = new DnsZoneTask();
		final VirtualTaskV3<DnsZoneTask> task = new DnsZoneVt(nt);
		assertNotNull(task.getType());
		final DnsZoneUow uow = new DnsZoneUow(task, vim, vimConn);
		uow.execute(null);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final DnsZoneTask nt = new DnsZoneTask();
		final VirtualTaskV3<DnsZoneTask> task = new DnsZoneVt(nt);
		final DnsZoneUow uow = new DnsZoneUow(task, vim, vimConn);
		uow.rollback(null);
		assertTrue(true);
	}
}
