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
package com.ubiqube.etsi.mano.service.pkg.vnf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.UploadUriParameters;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;

@ExtendWith(MockitoExtension.class)
class VnfPackageOnboardingImplTest {
	@Mock
	private VnfPackageRepository vnfPackageRepository;
	@Mock
	private EventManager eventManager;
	@Mock
	private VnfPackageManager packageManager;
	@Mock
	private VnfPackageService vnfPackageService;
	@Mock
	private VnfOnboardingMapperService onboardingMapper;

	@Test
	void testOk() throws Exception {
		final VnfPackageOnboardingImpl srv = new VnfPackageOnboardingImpl(vnfPackageRepository, eventManager, packageManager, vnfPackageService, onboardingMapper);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setId(id);
		final PackageDescriptor<VnfPackageReader> packageProvider = new TestPackageDescriptor();
		final ManoResource data = new ByteArrayResource("".getBytes(), null);
		//
		when(vnfPackageRepository.getBinary(id, Constants.REPOSITORY_FILENAME_PACKAGE)).thenReturn(data);
		when(vnfPackageService.findById(id)).thenReturn(vnfPkg);
		when(vnfPackageService.save(vnfPkg)).thenReturn(vnfPkg);

		when(packageManager.getProviderFor(data)).thenReturn(packageProvider);
		srv.vnfPackagesVnfPkgIdPackageContentPut(id.toString());
		assertTrue(true);
	}

	@Test
	void testEnsireFail() throws Exception {
		final VnfPackageOnboardingImpl srv = new VnfPackageOnboardingImpl(vnfPackageRepository, eventManager, packageManager, vnfPackageService, onboardingMapper);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setId(id);
		final ManoResource data = new ByteArrayResource("".getBytes(), null);
		//
		when(vnfPackageRepository.getBinary(id, Constants.REPOSITORY_FILENAME_PACKAGE)).thenReturn(data);
		when(vnfPackageService.findById(id)).thenReturn(vnfPkg);
		when(vnfPackageService.save(vnfPkg)).thenReturn(vnfPkg);

		final VnfPackage res = srv.vnfPackagesVnfPkgIdPackageContentPut(id.toString());
		assertEquals(OnboardingStateType.ERROR, res.getOnboardingState());
	}

	@Test
	void testFromUriOk() throws Exception {
		final VnfPackageOnboardingImpl srv = new VnfPackageOnboardingImpl(vnfPackageRepository, eventManager, packageManager, vnfPackageService, onboardingMapper);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setId(id);
		final UploadUriParameters uriParams = new UploadUriParameters();
		uriParams.setAddressInformation(URI.create("http://nexus.ubiqube.com/repository/local-helm/index.yaml"));
		vnfPkg.setUploadUriParameters(uriParams);
		final PackageDescriptor<VnfPackageReader> packageProvider = new TestPackageDescriptor();
		//
		// when(vnfPackageRepository.getBinary(id,
		// Constants.REPOSITORY_FILENAME_PACKAGE)).thenReturn(data);
		when(vnfPackageService.findById(id)).thenReturn(vnfPkg);
		when(vnfPackageService.save(vnfPkg)).thenReturn(vnfPkg);

		when(packageManager.getProviderFor((ManoResource) any())).thenReturn(packageProvider);
		srv.vnfPackagesVnfPkgIdPackageContentUploadFromUriPost(id.toString());
		assertTrue(true);
	}

}
