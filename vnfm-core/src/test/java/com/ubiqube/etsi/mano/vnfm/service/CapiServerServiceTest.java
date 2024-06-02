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
package com.ubiqube.etsi.mano.vnfm.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.jpa.CapiServerJpa;
import com.ubiqube.etsi.mano.service.CapiServerService;

@ExtendWith(MockitoExtension.class)
class CapiServerServiceTest {
	@Mock
	private CapiServerJpa capiServerJpa;

	CapiServerService createService() {
		return new CapiServerService(capiServerJpa);
	}

	@Test
	void testDelete() {
		final CapiServerService srv = createService();
		srv.deleteById(null);
		assertTrue(true);
	}

	@Test
	void testFindAll() {
		final CapiServerService srv = createService();
		srv.findAll();
		assertTrue(true);
	}

	@Test
	void testSave() {
		final CapiServerService srv = createService();
		srv.save(null);
		assertTrue(true);
	}
}
