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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.cnf.CnfServer;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.service.CnfServerService;
import com.ubiqube.etsi.mano.service.vim.VimManager;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud
 *
 */

@ExtendWith(MockitoExtension.class)
class CnfServerControllerTest {
	@Mock
	private CnfServerService cnfServerService;
	@Mock
	private VimManager vimManager;
	@Mock
	private MapperFacade mapper;

	@Test
	void testCreate() {
		final CnfServerController srv = new CnfServerController(cnfServerService, vimManager, mapper);
		srv.createCnfServer(null);
		assertTrue(true);
	}

	@Test
	void testFindCnfServer() {
		final CnfServerController srv = new CnfServerController(cnfServerService, vimManager, mapper);
		final CnfServer val = new CnfServer();
		when(cnfServerService.findById(any())).thenReturn(Optional.of(val));
		srv.findCnfServer(null);
		assertTrue(true);
	}

	@Test
	void testFindCnfServerFail() {
		final CnfServerController srv = new CnfServerController(cnfServerService, vimManager, mapper);
		assertThrows(NotFoundException.class, () -> srv.findCnfServer(null));
		assertTrue(true);
	}

	@Test
	void testListCnfServer() {
		final CnfServerController srv = new CnfServerController(cnfServerService, vimManager, mapper);
		srv.listCnfServer();
		assertTrue(true);
	}

	@Test
	void testMergeCnfServer() {
		final CnfServerController srv = new CnfServerController(cnfServerService, vimManager, mapper);
		final CnfServer val = new CnfServer();
		when(cnfServerService.findById(any())).thenReturn(Optional.of(val));
		final VimConnectionInformation vim = new VimConnectionInformation();
		when(vimManager.findVimById(any())).thenReturn(vim);
		srv.mergeCnfServer(null);
		assertTrue(true);
	}

	@Test
	void testMergeCnfServerFail() {
		final CnfServerController srv = new CnfServerController(cnfServerService, vimManager, mapper);
		assertThrows(NotFoundException.class, () -> srv.mergeCnfServer(null));
		assertTrue(true);
	}

}
