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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.PmJobsJpa;
import com.ubiqube.etsi.mano.service.SearchableService;

@ExtendWith(MockitoExtension.class)
class NfvoPmControllerImplTest {
	@Mock
	private SearchableService searchService;
	@Mock
	private PmJobsJpa pmJobsJpa;

	@Test
	void testDelete() throws Exception {
		final NfvoPmControllerImpl srv = new NfvoPmControllerImpl(searchService, pmJobsJpa);
		final PmJob pmj = new PmJob();
		when(pmJobsJpa.findById(any())).thenReturn(Optional.of(pmj));
		srv.deleteById(null);
		assertTrue(true);
	}

	@Test
	void testDelete_Fail() throws Exception {
		final NfvoPmControllerImpl srv = new NfvoPmControllerImpl(searchService, pmJobsJpa);
		assertThrows(NotFoundException.class, () -> srv.deleteById(null));
	}

	@Test
	void testGetById() throws Exception {
		final NfvoPmControllerImpl srv = new NfvoPmControllerImpl(searchService, pmJobsJpa);
		final PmJob pmj = new PmJob();
		when(pmJobsJpa.findById(any())).thenReturn(Optional.of(pmj));
		srv.getById(null);
		assertTrue(true);
	}

	@Test
	void testGetById_fail() throws Exception {
		final NfvoPmControllerImpl srv = new NfvoPmControllerImpl(searchService, pmJobsJpa);
		assertThrows(NotFoundException.class, () -> srv.getById(null));
	}

	@Test
	void testSave() throws Exception {
		final NfvoPmControllerImpl srv = new NfvoPmControllerImpl(searchService, pmJobsJpa);
		srv.save(null);
		assertTrue(true);
	}

	@Test
	void testSearch() throws Exception {
		final NfvoPmControllerImpl srv = new NfvoPmControllerImpl(searchService, pmJobsJpa);
		srv.search(null, null, null, null, null);
		assertTrue(true);
	}
}
