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
package com.ubiqube.etsi.mano.service.grant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.GrantInformationExt;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.docker.DockerApiException;
import com.ubiqube.etsi.mano.docker.DockerService;
import com.ubiqube.etsi.mano.jpa.ConnectionInformationJpa;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.event.TestFactory;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class GrantContainerActionTest {
	@Mock
	private ConnectionInformationJpa connJpa;
	@Mock
	private DockerService dockerService;
	@Mock
	private VnfPackageService vnfPkgService;
	@Mock
	private VnfPackageRepository vnfRepository;

	@Test
	void testHAndleGrant() {
		final GrantContainerAction srv = createService();
		final GrantResponse grant = new GrantResponse();
		grant.setVnfdId(UUID.randomUUID().toString());
		final VnfPackage vnfPkg = TestFactory.createVnfPkg(UUID.randomUUID());
		when(vnfPkgService.findByVnfdId(any())).thenReturn(vnfPkg);
		srv.handleGrant(grant);
		assertTrue(true);
	}

	private GrantContainerAction createService() {
		return new GrantContainerAction(connJpa, dockerService, vnfPkgService, vnfRepository);
	}

	@Test
	void testHAndleGrantOsContainer() {
		final GrantContainerAction srv = createService();
		final GrantResponse grant = new GrantResponse();
		grant.setVnfdId(UUID.randomUUID().toString());
		final VnfPackage vnfPkg = TestFactory.createVnfPkg(UUID.randomUUID());
		final OsContainer osc = new OsContainer();
		osc.setName("osc1");
		final SoftwareImage si1 = new SoftwareImage();
		osc.setArtifacts(Map.of("art1", si1));
		vnfPkg.getOsContainer().add(osc);
		when(vnfPkgService.findByVnfdId(any())).thenReturn(vnfPkg);
		final ConnectionInformation conn = new ConnectionInformation();
		conn.setName("conn1");
		conn.setUrl(URI.create("http://localhost/"));
		final AuthentificationInformations auth = AuthentificationInformations.builder()
				.authParamBasic(AuthParamBasic.builder()
						.build())
				.build();
		conn.setAuthentification(auth);
		final ManoResource manoRes = new ByteArrayResource("".getBytes(), "");
		when(vnfRepository.getBinary(any(), any())).thenReturn(manoRes);
		when(connJpa.findByConnType(ConnectionType.OCI)).thenReturn(List.of(conn));
		srv.handleGrant(grant);
		assertTrue(true);
	}

	@Test
	void testHAndleGrantOsContainerDockerException() throws IOException {
		final GrantContainerAction srv = createService();
		final GrantResponse grant = new GrantResponse();
		grant.setVnfdId(UUID.randomUUID().toString());
		final VnfPackage vnfPkg = TestFactory.createVnfPkg(UUID.randomUUID());
		final OsContainer osc = new OsContainer();
		osc.setName("osc1");
		final SoftwareImage si1 = new SoftwareImage();
		osc.setArtifacts(Map.of("art1", si1));
		vnfPkg.getOsContainer().add(osc);
		when(vnfPkgService.findByVnfdId(any())).thenReturn(vnfPkg);
		final ConnectionInformation conn = new ConnectionInformation();
		conn.setName("conn1");
		conn.setUrl(URI.create("http://localhost/"));
		final AuthentificationInformations auth = AuthentificationInformations.builder()
				.authParamBasic(AuthParamBasic.builder()
						.build())
				.build();
		conn.setAuthentification(auth);
		final ManoResource manoRes = Mockito.mock(ManoResource.class);
		final InputStream is = Mockito.mock(InputStream.class);
		doThrow(DockerApiException.class).when(is).close();
		when(manoRes.getInputStream()).thenReturn(is);
		when(vnfRepository.getBinary(any(), any())).thenReturn(manoRes);
		when(connJpa.findByConnType(ConnectionType.OCI)).thenReturn(List.of(conn));
		srv.handleGrant(grant);
		assertTrue(true);
	}

	@Test
	void testHAndleGrantVimConn() {
		final GrantContainerAction srv = createService();
		final GrantResponse grant = new GrantResponse();
		final GrantInformationExt gie = new GrantInformationExt();
		gie.setType(ResourceTypeEnum.OS_CONTAINER);
		grant.getAddResources().add(gie);
		grant.setVnfdId(UUID.randomUUID().toString());
		final VnfPackage vnfPkg = TestFactory.createVnfPkg(UUID.randomUUID());
		when(vnfPkgService.findByVnfdId(any())).thenReturn(vnfPkg);
		final ConnectionInformation conn = new ConnectionInformation();
		conn.setName("conn1");
		final AuthentificationInformations auth = AuthentificationInformations.builder()
				.authType(List.of())
				.authParamBasic(AuthParamBasic.builder().build())
				.build();
		conn.setAuthentification(auth);
		conn.setUrl(URI.create("http://localhost/"));
		when(connJpa.findByConnType(any())).thenReturn(List.of(conn));
		srv.handleGrant(grant);
		assertTrue(true);
	}

	@Test
	void testHAndleGrantUploadHelm() {
		final GrantContainerAction srv = createService();
		final GrantResponse grant = new GrantResponse();
		grant.setVnfdId(UUID.randomUUID().toString());
		final VnfPackage vnfPkg = TestFactory.createVnfPkg(UUID.randomUUID());
		final McIops mciops = new McIops();
		final SoftwareImage si1 = new SoftwareImage();
		si1.setName("arteName");
		si1.setVersion("123");
		mciops.setArtifacts(Map.of("arte", si1));
		vnfPkg.getMciops().add(mciops);
		when(vnfPkgService.findByVnfdId(any())).thenReturn(vnfPkg);
		final ConnectionInformation conn = new ConnectionInformation();
		conn.setName("conn1");
		conn.setUrl(URI.create("http://localhost/"));
		when(connJpa.findByConnType(ConnectionType.HELM)).thenReturn(List.of(conn));
		srv.handleGrant(grant);
		assertTrue(true);
	}
}
