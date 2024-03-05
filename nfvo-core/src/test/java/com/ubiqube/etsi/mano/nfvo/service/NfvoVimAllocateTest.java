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
package com.ubiqube.etsi.mano.nfvo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.vim.VimManager;

@ExtendWith(MockitoExtension.class)
class NfvoVimAllocateTest {
	@Mock
	private VimManager vimManager;

	@Test
	void testCheckVim() throws Exception {
		final NfvoVimAllocate srv = new NfvoVimAllocate(vimManager);
		final Blueprint plan = new NsBlueprint();
		assertThrows(GenericException.class, () -> srv.allocate(plan));
	}

	@Test
	void testOk() throws Exception {
		final NfvoVimAllocate srv = new NfvoVimAllocate(vimManager);
		final NsBlueprint plan = new NsBlueprint();
		final NsVnfTask task = new NsVnfTask();
		plan.addTask(task);
		final NsVnfTask task2 = new NsVnfTask();
		task2.setVimConnectionId("");
		plan.addTask(task2);
		final VimConnectionInformation vim = new VimConnectionInformation();
		when(vimManager.findAllVimconnections()).thenReturn(List.of(vim));
		srv.allocate(plan);
		assertTrue(true);
	}
}
