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
package com.ubiqube.etsi.mano.controller.nfv.cim;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.InterfaceInfoService;
import com.ubiqube.etsi.mano.service.sol009.PeerEntityService;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class ManoEntityControllerTest {
	@Mock
	private PeerEntityService peerEntityService;
	@Mock
	private InterfaceInfoService interfaceInfoService;

	@Test
	void testChangeStatus() {
		final ManoEntityController srv = createService();
		assertThrows(UnsupportedOperationException.class, () -> srv.changeStatus(srv));
	}

	private ManoEntityController createService() {
		return new ManoEntityController(peerEntityService, interfaceInfoService);
	}

	@Test
	void testInterfaceChangeState() {
		final ManoEntityController srv = createService();
		assertThrows(UnsupportedOperationException.class, () -> srv.interfaceChangeState(null, srv));
	}

	@Test
	void testInterfaceFindById() {
		final ManoEntityController srv = createService();
		assertThrows(UnsupportedOperationException.class, () -> srv.interfaceFindById(null, null));
	}

	@Test
	void testInterfacePatch() {
		final ManoEntityController srv = createService();
		assertThrows(UnsupportedOperationException.class, () -> srv.interfacePatch(null, srv, null));
	}

	@Test
	void testPatch() {
		final ManoEntityController srv = createService();
		assertThrows(UnsupportedOperationException.class, () -> srv.patch(srv, null));
		assertTrue(true);
	}

	@Test
	void testFind() {
		final ManoEntityController srv = createService();
		srv.find(x -> "");
		assertTrue(true);
	}

	@Test
	void testInterfaceSearch() {
		final ManoEntityController srv = createService();
		srv.interfaceSearch(null, x -> "");
		assertTrue(true);
	}

}
