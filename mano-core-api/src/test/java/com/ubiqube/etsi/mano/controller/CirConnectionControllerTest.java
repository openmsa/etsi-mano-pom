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

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.service.Patcher;
import com.ubiqube.etsi.mano.service.mapping.CirConnectionControllerMapping;
import com.ubiqube.etsi.mano.service.vim.CirConnectionManager;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class CirConnectionControllerTest {
	@Mock
	private final CirConnectionControllerMapping mapper = Mappers.getMapper(CirConnectionControllerMapping.class);
	@Mock
	private CirConnectionManager vimManager;
	@Mock
	private Patcher patcher;

	@Test
	void testConnectVim() {
		final CirConnectionController srv = new CirConnectionController(mapper, vimManager, patcher);
		srv.connectVim(null);
		assertTrue(true);
	}

	@Test
	void testDeleteVim() {
		final CirConnectionController srv = new CirConnectionController(mapper, vimManager, patcher);
		srv.deleteVim(UUID.randomUUID().toString());
		assertTrue(true);
	}

	@Test
	void testListVim() {
		final CirConnectionController srv = new CirConnectionController(mapper, vimManager, patcher);
		srv.listVim();
		assertTrue(true);
	}

	@Test
	void testPatchVim() {
		final CirConnectionController srv = new CirConnectionController(mapper, vimManager, patcher);
		srv.patchVim(null, null, null);
		assertTrue(true);
	}

	@Test
	void testPatchVim2() {
		final CirConnectionController srv = new CirConnectionController(mapper, vimManager, patcher);
		final ConnectionInformation conn = new ConnectionInformation();
		when(vimManager.findVimById(any())).thenReturn(conn);
		assertThrows(PreConditionException.class, () -> srv.patchVim(null, null, ""));
		assertTrue(true);
	}

	@Test
	void testPatchVim3() {
		final CirConnectionController srv = new CirConnectionController(mapper, vimManager, patcher);
		final ConnectionInformation conn = new ConnectionInformation();
		conn.setVersion(1);
		when(vimManager.findVimById(any())).thenReturn(conn);
		srv.patchVim(null, null, "1");
		assertTrue(true);
	}

	@Test
	void testRegisterVim() {
		final CirConnectionController srv = new CirConnectionController(mapper, vimManager, patcher);
		srv.registerVim(null);
		assertTrue(true);
	}

}
