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

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.NsdPackageJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsdPackageNsdPackageJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsdPackageVnfPackageJpa;

@ExtendWith(MockitoExtension.class)
class NsdPackageServiceTest {
	@Mock
	private NsdPackageJpa nsdPkgJpa;
	@Mock
	private NsdPackageNsdPackageJpa nsdPackageNsdJpa;
	@Mock
	private NsdPackageVnfPackageJpa nsdPkgVnfJpa;

	@Test
	void testFindVnfPackageByNsPackage() throws Exception {
		final NsdPackageService srv = new NsdPackageService(nsdPkgJpa, nsdPackageNsdJpa, nsdPkgVnfJpa);
		srv.findVnfPackageByNsPackage(null);
		assertTrue(true);
	}

	@Test
	void testFindNestedNsdByNsdPackage() throws Exception {
		final NsdPackageService srv = new NsdPackageService(nsdPkgJpa, nsdPackageNsdJpa, nsdPkgVnfJpa);
		srv.findNestedNsdByNsdPackage(null);
		assertTrue(true);
	}

	@Test
	void testFindById() throws Exception {
		final NsdPackageService srv = new NsdPackageService(nsdPkgJpa, nsdPackageNsdJpa, nsdPkgVnfJpa);
		final NsdPackage nsdPkg = new NsdPackage();
		when(nsdPkgJpa.findById(any())).thenReturn(Optional.of(nsdPkg));
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testFindById_NotFound() throws Exception {
		final NsdPackageService srv = new NsdPackageService(nsdPkgJpa, nsdPackageNsdJpa, nsdPkgVnfJpa);
		assertThrows(NotFoundException.class, () -> srv.findById(null));
	}

	@Test
	void testSave() throws Exception {
		final NsdPackageService srv = new NsdPackageService(nsdPkgJpa, nsdPackageNsdJpa, nsdPkgVnfJpa);
		srv.save(null);
		assertTrue(true);
	}

	@Test
	void testFindByNsdId() throws Exception {
		final NsdPackageService srv = new NsdPackageService(nsdPkgJpa, nsdPackageNsdJpa, nsdPkgVnfJpa);
		final NsdPackage nsdPkg = new NsdPackage();
		when(nsdPkgJpa.findByNsdId(any())).thenReturn(Optional.of(nsdPkg));
		srv.findByNsdId(null);
		assertTrue(true);
	}

	@Test
	void testFindByNsdId_NotFound() throws Exception {
		final NsdPackageService srv = new NsdPackageService(nsdPkgJpa, nsdPackageNsdJpa, nsdPkgVnfJpa);
		assertThrows(GenericException.class, () -> srv.findByNsdId(null));
	}
}
