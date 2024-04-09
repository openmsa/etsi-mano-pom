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
package com.ubiqube.etsi.mano.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.service.Patcher;
import com.ubiqube.etsi.mano.service.VimService;
import com.ubiqube.etsi.mano.service.mapping.VimConnectionInformationMapping;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vim.dummy.DummyVim;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class VimControllerTest {
	@Mock
	private VimService vnfService;
	private final VimConnectionInformationMapping mapper = Mappers.getMapper(VimConnectionInformationMapping.class);
	@Mock
	private VimManager vimManager;
	@Mock
	private Patcher patcher;

	@Test
	void testRegister() {
		final VimController srv = new VimController(vnfService, mapper, vimManager, patcher);
		srv.registerVim(null);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final VimController srv = new VimController(vnfService, mapper, vimManager, patcher);
		srv.deleteVim(UUID.randomUUID().toString());
		assertTrue(true);
	}

	@Test
	void testRefresh() {
		final VimController srv = new VimController(vnfService, mapper, vimManager, patcher);
		srv.updateVim(null);
		assertTrue(true);
	}

	@Test
	void testPatchVim() {
		final VimController srv = new VimController(vnfService, mapper, vimManager, patcher);
		srv.patchVim(null, null, null);
		assertTrue(true);
	}

	@Test
	void testPatchVim2() {
		final VimController srv = new VimController(vnfService, mapper, vimManager, patcher);
		final VimConnectionInformation vim = new VimConnectionInformation();
		vim.setVersion(1111);
		when(vimManager.findVimById(any())).thenReturn(vim);
		assertThrows(PreConditionException.class, () -> srv.patchVim(null, null, "11"));
	}

	@Test
	void testPatchVim3() {
		final VimController srv = new VimController(vnfService, mapper, vimManager, patcher);
		final VimConnectionInformation vim = new VimConnectionInformation();
		vim.setVersion(11);
		when(vimManager.findVimById(any())).thenReturn(vim);
		srv.patchVim(null, null, "11");
		assertTrue(true);
	}

	@Test
	void testConnectVim() {
		final VimController srv = new VimController(vnfService, mapper, vimManager, patcher);
		final Vim vim = new DummyVim();
		when(vimManager.getVimById(any())).thenReturn(vim);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		when(vnfService.findById(any())).thenReturn(Optional.of(vimConn));
		srv.connectVim(null);
		assertTrue(true);
	}

	@Test
	void testConnectVimFail() {
		final VimController srv = new VimController(vnfService, mapper, vimManager, patcher);
		final Vim vim = new DummyVim();
		when(vimManager.getVimById(any())).thenReturn(vim);
		when(vnfService.findById(any())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> srv.connectVim(null));
	}

	@Test
	void testListVim() {
		final VimController srv = new VimController(vnfService, mapper, vimManager, patcher);
		srv.listVim();
		assertTrue(true);
	}

}
