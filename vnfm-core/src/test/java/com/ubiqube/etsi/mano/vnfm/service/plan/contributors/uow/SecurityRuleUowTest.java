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

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vnfm.SecurityRuleTask;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Network;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.SecurityRuleVt;

@ExtendWith(MockitoExtension.class)
class SecurityRuleUowTest {
	@Mock
	private Vim vim;
	@Mock
	private Context3d context;
	@Mock
	private Network network;

	@Test
	void test() {
		final SecurityRuleTask task = new SecurityRuleTask();
		final VirtualTaskV3<SecurityRuleTask> vt = new SecurityRuleVt(task);
		assertNotNull(vt.getType());
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final SecurityRuleUow uow = new SecurityRuleUow(vt, vim, vimConn);
		when(vim.network(vimConn)).thenReturn(network);
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final SecurityRuleTask task = new SecurityRuleTask();
		final VirtualTaskV3<SecurityRuleTask> vt = new SecurityRuleVt(task);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final SecurityRuleUow uow = new SecurityRuleUow(vt, vim, vimConn);
		uow.rollback(context);
		assertTrue(true);
	}
}
