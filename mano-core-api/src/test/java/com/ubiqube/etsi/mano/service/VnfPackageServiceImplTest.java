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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;

@ExtendWith(MockitoExtension.class)
class VnfPackageServiceImplTest {
	@Mock
	private VnfPackageJpa vnfPackageJpa;
	@Mock
	private VnfInstanceJpa vnfInstanceJpa;
	@Mock
	private VnfPackageRepository vnfPackageRepository;

	@Test
	void testFindByIdFailed() throws Exception {
		final VnfPackageServiceImpl srv = new VnfPackageServiceImpl(vnfPackageJpa, vnfInstanceJpa, vnfPackageRepository);
		final UUID id = UUID.randomUUID();
		assertThrows(NotFoundException.class, () -> srv.findById(id));
	}

	@Test
	void testFindByIdOk() throws Exception {
		final VnfPackageServiceImpl srv = new VnfPackageServiceImpl(vnfPackageJpa, vnfInstanceJpa, vnfPackageRepository);
		final UUID id = UUID.randomUUID();
		final Optional<VnfPackage> optPkg = Optional.of(new VnfPackage());
		when(vnfPackageJpa.findById(id)).thenReturn(optPkg);
		srv.findById(id);
		assertTrue(true);
	}

	@Test
	void testSave() throws Exception {
		final VnfPackageServiceImpl srv = new VnfPackageServiceImpl(vnfPackageJpa, vnfInstanceJpa, vnfPackageRepository);
		srv.save(null);
		assertTrue(true);
	}

	@Test
	void testByDescriptorId() throws Exception {
		final VnfPackageServiceImpl srv = new VnfPackageServiceImpl(vnfPackageJpa, vnfInstanceJpa, vnfPackageRepository);
		srv.findByVnfdIdOpt("");
		assertTrue(true);
	}

	@Test
	void testByDescriptorIdAndSoftwareVersion() throws Exception {
		final VnfPackageServiceImpl srv = new VnfPackageServiceImpl(vnfPackageJpa, vnfInstanceJpa, vnfPackageRepository);
		srv.findByVnfdIdAndSoftwareVersion(null, null);
		assertTrue(true);
	}

	@Test
	void testByDescriptorIdFlavorIdVnfdVersion() throws Exception {
		final VnfPackageServiceImpl srv = new VnfPackageServiceImpl(vnfPackageJpa, vnfInstanceJpa, vnfPackageRepository);
		srv.findByVnfdIdFlavorIdVnfdVersion(null, null, null);
		assertTrue(true);
	}

	@Test
	void testByVnfdIdFailed() throws Exception {
		final VnfPackageServiceImpl srv = new VnfPackageServiceImpl(vnfPackageJpa, vnfInstanceJpa, vnfPackageRepository);
		final String id = UUID.randomUUID().toString();
		assertThrows(NotFoundException.class, () -> srv.findByVnfdId(id));
	}

	@Test
	void testByVnfdId() throws Exception {
		final VnfPackageServiceImpl srv = new VnfPackageServiceImpl(vnfPackageJpa, vnfInstanceJpa, vnfPackageRepository);
		final String id = UUID.randomUUID().toString();
		final Optional<VnfPackage> optPkg = Optional.of(new VnfPackage());
		when(vnfPackageJpa.findByVnfdIdAndOnboardingState(id.toString(), OnboardingStateType.ONBOARDED)).thenReturn(optPkg);
		srv.findByVnfdId(id);
		assertTrue(true);
	}

	@Test
	void testDelete() throws Exception {
		final VnfPackageServiceImpl srv = new VnfPackageServiceImpl(vnfPackageJpa, vnfInstanceJpa, vnfPackageRepository);
		srv.delete(UUID.randomUUID());
		assertTrue(true);
	}
}
