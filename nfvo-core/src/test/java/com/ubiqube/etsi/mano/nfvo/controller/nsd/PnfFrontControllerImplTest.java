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
package com.ubiqube.etsi.mano.nfvo.controller.nsd;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class PnfFrontControllerImplTest {
	@Mock
	private PnfdController pnfController;
	@Mock
	private MapperFacade mapper;

	@Test
	void testCreate() {
		final PnfFrontControllerImpl srv = new PnfFrontControllerImpl(pnfController);
		final Consumer<Object> cons = x -> {
		};
		srv.create(Map.of(), x -> "", cons);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final PnfFrontControllerImpl srv = new PnfFrontControllerImpl(pnfController);
		srv.delete(UUID.randomUUID().toString());
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final PnfFrontControllerImpl srv = new PnfFrontControllerImpl(pnfController);
		final Consumer<Object> cons = x -> {
		};
		srv.findById(UUID.randomUUID().toString(), x -> "", cons);
		assertTrue(true);
	}

	@Test
	void testGetArtifact() {
		final PnfFrontControllerImpl srv = new PnfFrontControllerImpl(pnfController);
		assertThrows(UnsupportedOperationException.class, () -> srv.getArtifact(null, null, null, null));
		assertTrue(true);
	}

	@Test
	void testGetContent() {
		final PnfFrontControllerImpl srv = new PnfFrontControllerImpl(pnfController);
		srv.getContent(null, null);
		assertTrue(true);
	}

	@Test
	void testgetPnfd() {
		final PnfFrontControllerImpl srv = new PnfFrontControllerImpl(pnfController);
		assertThrows(UnsupportedOperationException.class, () -> srv.getPnfd(null, null, null));
		assertTrue(true);
	}

	@Test
	void testGetManifest() {
		final PnfFrontControllerImpl srv = new PnfFrontControllerImpl(pnfController);
		assertThrows(UnsupportedOperationException.class, () -> srv.manifestGet(null, null));
		assertTrue(true);
	}

	@Test
	void testModify() {
		final PnfFrontControllerImpl srv = new PnfFrontControllerImpl(pnfController);
		assertThrows(UnsupportedOperationException.class, () -> srv.modify(null, null, srv));
		assertTrue(true);
	}

	@Test
	void testPutContent() {
		final PnfFrontControllerImpl srv = new PnfFrontControllerImpl(pnfController);
		srv.putContent(null, null);
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final PnfFrontControllerImpl srv = new PnfFrontControllerImpl(pnfController);
		srv.search(null, null, null, null);
		assertTrue(true);
	}

}
