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
package com.ubiqube.etsi.mano.nfvo.controller.nssp;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class VnfSnapshotPackagesFrontControllerImplTest {

	@Test
	void testCreate() {
		final VnfSnapshotPackagesFrontControllerImpl srv = new VnfSnapshotPackagesFrontControllerImpl();
		assertThrows(UnsupportedOperationException.class, () -> srv.create(srv));
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final VnfSnapshotPackagesFrontControllerImpl srv = new VnfSnapshotPackagesFrontControllerImpl();
		assertThrows(UnsupportedOperationException.class, () -> srv.delete(null));
		assertTrue(true);
	}

	@Test
	void testGetArtifact() {
		final VnfSnapshotPackagesFrontControllerImpl srv = new VnfSnapshotPackagesFrontControllerImpl();
		assertThrows(UnsupportedOperationException.class, () -> srv.getArtifact(null, null, null));
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final VnfSnapshotPackagesFrontControllerImpl srv = new VnfSnapshotPackagesFrontControllerImpl();
		assertThrows(UnsupportedOperationException.class, () -> srv.search(null, null, null));
		assertTrue(true);
	}

}
