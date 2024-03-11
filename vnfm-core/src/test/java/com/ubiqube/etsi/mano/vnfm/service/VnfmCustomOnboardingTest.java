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
package com.ubiqube.etsi.mano.vnfm.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.AdditionalArtifact;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;
import com.ubiqube.etsi.mano.service.rest.vnfpkg.ManoOnboarded;
import com.ubiqube.etsi.mano.service.rest.vnfpkg.ManoVnfPackage;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;

@ExtendWith(MockitoExtension.class)
class VnfmCustomOnboardingTest {
	@Mock
	private VnfPackageRepository vnfPackageRepository;
	@Mock
	private ManoClientFactory factory;
	@Mock
	private ManoClient client;
	@Mock
	private ManoVnfPackage manoVnfPkg;
	@Mock
	private ManoOnboarded manoOnboarded;

	@Test
	void testVnf() {
		final VnfmCustomOnboarding srv = new VnfmCustomOnboarding(vnfPackageRepository, factory);
		final VnfPackage vnfPkg = TestFactory.createVnfPkg(UUID.randomUUID());
		final AdditionalArtifact add1 = new AdditionalArtifact();
		vnfPkg.setAdditionalArtifacts(Set.of(add1));
		srv.handleArtifacts(vnfPkg, null);
		assertTrue(true);
	}

	@Test
	void testVnf2() {
		final VnfmCustomOnboarding srv = new VnfmCustomOnboarding(vnfPackageRepository, factory);
		final VnfPackage vnfPkg = TestFactory.createVnfPkg(UUID.randomUUID());
		vnfPkg.setVnfdId(vnfPkg.getId().toString());
		final AdditionalArtifact add1 = new AdditionalArtifact();
		add1.setArtifactPath("test");
		vnfPkg.setAdditionalArtifacts(Set.of(add1));
		when(factory.getClient(any())).thenReturn(client);
		when(client.vnfPackage()).thenReturn(manoVnfPkg);
		when(manoVnfPkg.onboarded(any())).thenReturn(manoOnboarded);
		srv.handleArtifacts(vnfPkg, null);
		assertTrue(true);
	}

	@Test
	void testNsd() {
		final VnfmCustomOnboarding srv = new VnfmCustomOnboarding(vnfPackageRepository, factory);
		final NsdPackage vnfPkg = new NsdPackage();
		srv.handleArtifacts(vnfPkg, null);
		assertTrue(true);
	}
}
