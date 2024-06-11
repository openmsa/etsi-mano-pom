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
package com.ubiqube.etsi.mano.nfvo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.docker.DockerService;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.mapping.CirConnectionControllerMapping;
import com.ubiqube.etsi.mano.service.vim.CirConnectionManager;

@ExtendWith(MockitoExtension.class)
class CirRegistryUploaderTest {
	@Mock
	private VnfPackageRepository packageRepository;
	@Mock
	private DockerService dockerService;
	@Mock
	private CirConnectionManager vimManager;
	private final CirConnectionControllerMapping mapper = Mappers.getMapper(CirConnectionControllerMapping.class);

	CirRegistryUploader createService() {
		return new CirRegistryUploader(packageRepository, dockerService, vimManager, mapper);
	}

	@Test
	void test() {
		final CirRegistryUploader srv = createService();
		assertNotNull(srv);
		final VnfPackage vnfPackage = new VnfPackage();
		srv.uploadToRegistry(vnfPackage);
	}

	@Test
	void testEmpty() {
		final CirRegistryUploader srv = createService();
		assertNotNull(srv);
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setMciops(Set.of());
		srv.uploadToRegistry(vnfPackage);
	}

	@Test
	void testOne() {
		final CirRegistryUploader srv = createService();
		assertNotNull(srv);
		final VnfPackage vnfPackage = new VnfPackage();
		final McIops mc01 = new McIops();
		vnfPackage.setMciops(Set.of(mc01));
		assertThrows(GenericException.class, () -> srv.uploadToRegistry(vnfPackage));
	}

	@Test
	void testOne001() {
		final CirRegistryUploader srv = createService();
		assertNotNull(srv);
		final VnfPackage vnfPackage = new VnfPackage();
		final McIops mc01 = new McIops();
		final SoftwareImage sw01 = new SoftwareImage();
		sw01.setImagePath("/path");
		mc01.setArtifacts(Map.of("img0~", sw01));
		vnfPackage.setMciops(Set.of(mc01));
		final ConnectionInformation ci01 = new ConnectionInformation();
		when(vimManager.findAll()).thenReturn(List.of(ci01));
		final ManoResource mano = Mockito.mock(ManoResource.class);
		when(mano.getInputStream()).thenReturn(InputStream.nullInputStream());
		when(packageRepository.getBinary(any(), any())).thenReturn(mano);
		srv.uploadToRegistry(vnfPackage);
	}
}
