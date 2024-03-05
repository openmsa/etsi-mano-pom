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
package com.ubiqube.etsi.mano.nfvo.service.plan.uow;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.NsVlProfile;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.VlProtocolData;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVirtualLinkVt;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vim.dummy.DummyVim;

class NsVlUowTest {

	private final Vim vim = new DummyVim();

	@Test
	void testExecute() {
		final NsVirtualLinkTask task = new NsVirtualLinkTask();
		final NsVirtualLink nsVl = new NsVirtualLink();
		final NsVlProfile nsProfile = new NsVlProfile();
		final VlProtocolData vpd = new VlProtocolData();
		nsProfile.setVlProtocolData(Set.of(vpd));
		nsVl.setNsVlProfile(nsProfile);
		task.setNsVirtualLink(nsVl);
		final VirtualTaskV3<NsVirtualLinkTask> vt = new NsVirtualLinkVt(task);
		final VimConnectionInformation vimConnection = new VimConnectionInformation();
		final NsVlUow ns = new NsVlUow(vt, vim, vimConnection);
		ns.execute(null);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final NsVirtualLinkTask task = new NsVirtualLinkTask();
		final VirtualTaskV3<NsVirtualLinkTask> vt = new NsVirtualLinkVt(task);
		final VimConnectionInformation vimConnection = new VimConnectionInformation();
		final NsVlUow ns = new NsVlUow(vt, vim, vimConnection);
		ns.rollback(null);
		assertTrue(true);
	}

}
