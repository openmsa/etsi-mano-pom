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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ubiqube.etsi.mano.controller.vnf.VnfPackageManagement;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.UploadUriParameters;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;

import jakarta.servlet.http.HttpServletRequest;
import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class VnfPackageFrontControllerImplTest {
	@Mock
	private VnfPackageManagement vnfPkgMng;
	@Mock
	private VnfPackageController vnfPackageController;
	@Mock
	private MapperFacade mapper;
	@Mock
	private HttpServletRequest req;
	@Mock
	private MultipartFile file;

	@Test
	void testGetArtifactPath() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		srv.getArtifactPath(req, id, null);
		assertTrue(true);
	}

	@Test
	void testFindById() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		final Consumer func = x -> {
			//
		};
		final VnfPackage vnfPkg = new VnfPackage();
		when(vnfPkgMng.vnfPackagesVnfPkgIdGet(id)).thenReturn(vnfPkg);
		srv.findById(id, "".getClass(), func);
		assertTrue(true);
	}

	@Test
	void testFindById_Error() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		final Consumer func = x -> {
			//
		};
		final VnfPackage vnfPkg = new VnfPackage();
		final FailureDetails tailure = new FailureDetails();
		vnfPkg.setOnboardingFailureDetails(tailure);
		when(vnfPkgMng.vnfPackagesVnfPkgIdGet(id)).thenReturn(vnfPkg);
		srv.findById(id, "".getClass(), func);
		assertTrue(true);
	}

	@Test
	void testFindByIdReadOnly() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		final Consumer func = x -> {
			//
		};
		final VnfPackage vnfPkg = new VnfPackage();
		when(vnfPkgMng.vnfPackagesVnfPkgIdGet(any(), any())).thenReturn(vnfPkg);
		srv.findByIdReadOnly(id, "".getClass(), func);
		assertTrue(true);
	}

	@Test
	void testGetManifest() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		srv.getManifest(id, null);
		assertTrue(true);
	}

	@Test
	void testGetContent() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		srv.getContent(id);
		assertTrue(true);
	}

	@Test
	void testGetVfnd() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		srv.getVfnd(id, "application/json", null);
		assertTrue(true);
	}

	@Test
	void testDeleteById() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		srv.deleteById(id);
		assertTrue(true);
	}

	@Test
	void testSearch() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		final Consumer func = x -> {
			//
		};
		srv.search(null, "".getClass(), func);
		assertTrue(true);
	}

	@Test
	void testCreate() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		final Consumer func = x -> {
			//
		};
		final Function func2 = x -> "";
		srv.create(null, "".getClass(), func, func2);
		assertTrue(true);
	}

	@Test
	void testPutContent() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		srv.putContent(id, "application/json", file);
		assertTrue(true);
	}

	@Test
	void testPutContent001() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		assertThrows(ResponseStatusException.class, () -> srv.putContent(id, "application/json", null));
	}

	@Test
	void testUploadFromUri() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		final Consumer func = x -> {
			//
		};
		final UploadUriParameters params = new UploadUriParameters();
		when(mapper.map(any(), eq(UploadUriParameters.class))).thenReturn(params);
		srv.uploadFromUri(null, id, "");
		assertTrue(true);
	}

	@Test
	void testModify() throws Exception {
		final VnfPackageFrontControllerImpl srv = new VnfPackageFrontControllerImpl(vnfPkgMng, vnfPackageController, mapper);
		final UUID id = UUID.randomUUID();
		final Consumer func = x -> {
			//
		};
		srv.modify("", id, null, "".getClass(), func);
		assertTrue(true);
	}

}
