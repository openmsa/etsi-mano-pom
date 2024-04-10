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
package com.ubiqube.etsi.mano.nfvo.controller.vnf;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.controller.vnf.VnfPackageManagement;
import com.ubiqube.etsi.mano.dao.mano.AdditionalArtifact;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.VnfPackageService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class OnboardedPackageFrontControllerImplTest {
	@Mock
	private VnfPackageManagement vnfManagement;
	@Mock
	private VnfPackageService vnfPackageService;
	@Mock
	private VnfPackageRepository vnfPackageRepo;
	@Mock
	private ManoResource manoResource;
	@Mock
	private ManoResource manoResource2;
	@Mock
	private HttpServletRequest req;

	@Test
	void testName() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final Consumer cons = c -> {
			//
		};
		final UUID id = UUID.randomUUID();
		srv.onboardedFindById(id.toString(), x -> "", cons);
		assertTrue(true);
	}

	@Test
	void testOnboardedSearch() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		srv.onboardedSearch(null, null, null, null);
		assertTrue(true);
	}

	@Test
	void testOnboardedGetArtifactByVnfdId() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final String id = UUID.randomUUID().toString();
		final VnfPackage vnfPackage = new VnfPackage();
		when(vnfPackageService.findByVnfdId(id)).thenReturn(vnfPackage);
		srv.onboardedGetArtifactByVnfdId(id);
		assertTrue(true);
	}

	@Test
	void testOnboardedGetManifestByVnfd() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final String id = UUID.randomUUID().toString();
		final VnfPackage vnfPackage = new VnfPackage();
		when(vnfPackageService.findByVnfdId(id)).thenReturn(vnfPackage);
		srv.onboardedGetManifestByVnfd(id, null);
		assertTrue(true);
	}

	@Test
	void testOnboardedGetVnfdByVnfdId() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final String id = UUID.randomUUID().toString();
		final VnfPackage vnfPackage = new VnfPackage();
		when(vnfPackageService.findByVnfdId(id)).thenReturn(vnfPackage);
		srv.onboardedGetVnfdByVnfdId(id, null);
		assertTrue(true);
	}

	@Test
	void testOnboardedGetContentByVnfdId() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		when(vnfPackageService.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		srv.onboardedGetContentByVnfdId(id.toString(), null, null);
		assertTrue(true);
	}

	@Test
	void testOnboardedGetContentByVnfdId002_NoSign() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		final AdditionalArtifact arte = new AdditionalArtifact();
		arte.setArtifactPath("vnfd.sig");
		vnfPackage.setAdditionalArtifacts(Set.of(arte));
		when(vnfPackageService.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		when(vnfPackageService.findById(id)).thenReturn(vnfPackage);
		srv.onboardedGetContentByVnfdId(id.toString(), null, "false");
		assertTrue(true);
	}

	@Test
	void testOnboardedGetContentByVnfdId002() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		final AdditionalArtifact arte = new AdditionalArtifact();
		arte.setArtifactPath("vnfd.sig");
		arte.setSignature("");
		vnfPackage.setAdditionalArtifacts(Set.of(arte));
		when(vnfPackageService.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		when(vnfPackageService.findById(id)).thenReturn(vnfPackage);
		when(vnfManagement.onboardedVnfPackagesVnfdIdVnfdGet(any(), any(), any())).thenReturn(manoResource);
		when(manoResource.getInputStream()).thenReturn(InputStream.nullInputStream());
		when(vnfPackageRepo.getBinary(any(), any())).thenReturn(manoResource2);
		when(manoResource2.getInputStream()).thenReturn(InputStream.nullInputStream());
		srv.onboardedGetContentByVnfdId(id.toString(), null, "false");
		assertTrue(true);
	}

	@Test
	void testOnboardedGetArtifact_NoSig() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		final AdditionalArtifact arte = new AdditionalArtifact();
		arte.setArtifactPath("vnfd.sig");
		vnfPackage.setAdditionalArtifacts(Set.of(arte));
		when(vnfPackageService.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		srv.onboardedGetArtifact(req, id.toString(), null);
		assertTrue(true);
	}

	@Test
	void testOnboardedGetArtifact_WithSig() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		final AdditionalArtifact arte = new AdditionalArtifact();
		arte.setArtifactPath("");
		vnfPackage.setAdditionalArtifacts(Set.of(arte));
		when(vnfPackageService.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		//
		when(vnfPackageService.findById(id)).thenReturn(vnfPackage);
		when(vnfPackageRepo.getBinary(any(), any())).thenReturn(manoResource2);
		srv.onboardedGetArtifact(req, id.toString(), "");
		assertTrue(true);
	}

	@Test
	void testOnboardedGetArtifact_WithSig_FailNoArtifact() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		final AdditionalArtifact arte = new AdditionalArtifact();
		arte.setArtifactPath("ghgh");
		vnfPackage.setAdditionalArtifacts(Set.of(arte));
		when(vnfPackageService.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		//
		when(vnfPackageService.findById(id)).thenReturn(vnfPackage);
		when(vnfPackageRepo.getBinary(any(), any())).thenReturn(manoResource2);
		final String strId = id.toString();
		assertThrows(GenericException.class, () -> srv.onboardedGetArtifact(req, strId, ""));
	}

	@Test
	void testOnboardedGetVnfdByVnfdId_NoSig() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		final AdditionalArtifact arte = new AdditionalArtifact();
		arte.setArtifactPath("vnfd.sig");
		vnfPackage.setAdditionalArtifacts(Set.of(arte));
		when(vnfPackageService.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		srv.onboardedGetVnfdByVnfdId(id.toString(), null);
		assertTrue(true);
	}

	@Test
	void testOnboardedGetVnfdByVnfdId_WithSig() throws Exception {
		final OnboardedPackageFrontControllerImpl srv = new OnboardedPackageFrontControllerImpl(vnfManagement, vnfPackageService, vnfPackageRepo);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		final AdditionalArtifact arte = new AdditionalArtifact();
		arte.setArtifactPath("vnfd.sig");
		vnfPackage.setAdditionalArtifacts(Set.of(arte));
		when(vnfPackageService.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		//
		when(vnfPackageService.findById(id)).thenReturn(vnfPackage);
		when(vnfPackageRepo.getBinary(any(), any())).thenReturn(manoResource2);
		srv.onboardedGetVnfdByVnfdId(id.toString(), "sig");
		assertTrue(true);
	}
}
