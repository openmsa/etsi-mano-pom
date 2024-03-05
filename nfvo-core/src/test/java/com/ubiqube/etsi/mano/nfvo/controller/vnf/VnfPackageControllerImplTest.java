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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.UploadUriParameters;
import com.ubiqube.etsi.mano.exception.ConflictException;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.nfvo.service.NsdPackageVnfPackageService;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.Patcher;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.EventManager;

@ExtendWith(MockitoExtension.class)
class VnfPackageControllerImplTest {
	@Mock
	private Patcher patcher;
	@Mock
	private EventManager eventManager;
	@Mock
	private VnfPackageService vnfPackageSrv;
	@Mock
	private VnfPackageRepository vnfPkgRepo;
	@Mock
	private NsdPackageVnfPackageService nsdVnfPkgService;

	@Test
	void testName() throws Exception {
		final VnfPackageControllerImpl srv = new VnfPackageControllerImpl(patcher, eventManager, vnfPackageSrv, vnfPkgRepo, nsdVnfPkgService);
		srv.vnfPackagesPost(Map.of());
		assertTrue(true);
	}

	@Test
	void testVnfPackagesVnfPkgIdDelete() throws Exception {
		final VnfPackageControllerImpl srv = new VnfPackageControllerImpl(patcher, eventManager, vnfPackageSrv, vnfPkgRepo, nsdVnfPkgService);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setOperationalState(PackageOperationalState.DISABLED);
		vnfPkg.setUsageState(UsageStateEnum.NOT_IN_USE);
		vnfPkg.setVnfdId("");
		when(vnfPackageSrv.findById(id)).thenReturn(vnfPkg);
		srv.vnfPackagesVnfPkgIdDelete(id);
		assertTrue(true);
	}

	@Test
	void testVnfPackagesVnfPkgIdDelete_InUse() throws Exception {
		final VnfPackageControllerImpl srv = new VnfPackageControllerImpl(patcher, eventManager, vnfPackageSrv, vnfPkgRepo, nsdVnfPkgService);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setOperationalState(PackageOperationalState.DISABLED);
		vnfPkg.setUsageState(UsageStateEnum.NOT_IN_USE);
		vnfPkg.setVnfdId("");
		when(vnfPackageSrv.findById(id)).thenReturn(vnfPkg);
		final NsdPackageVnfPackage pk = new NsdPackageVnfPackage();
		final NsdPackage nsdPkg = new NsdPackage();
		nsdPkg.setId(id);
		pk.setNsdPackage(nsdPkg);
		when(nsdVnfPkgService.findByVnfdId(anyString())).thenReturn(Set.of(pk));
		assertThrows(ConflictException.class, () -> srv.vnfPackagesVnfPkgIdDelete(id));
	}

	@Test
	void testVnfPackagesVnfPkgIdPatch() throws Exception {
		final VnfPackageControllerImpl srv = new VnfPackageControllerImpl(patcher, eventManager, vnfPackageSrv, vnfPkgRepo, nsdVnfPkgService);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setVnfdId(id.toString());
		vnfPkg.setOperationalState(PackageOperationalState.DISABLED);
		when(vnfPackageSrv.findById(id)).thenReturn(vnfPkg);
		srv.vnfPackagesVnfPkgIdPatch(id, "{}", null);
		assertTrue(true);
	}

	@Test
	void testVnfPackagesVnfPkgIdPatch_Match() throws Exception {
		final VnfPackageControllerImpl srv = new VnfPackageControllerImpl(patcher, eventManager, vnfPackageSrv, vnfPkgRepo, nsdVnfPkgService);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setVersion(1);
		when(vnfPackageSrv.findById(id)).thenReturn(vnfPkg);
		srv.vnfPackagesVnfPkgIdPatch(id, "{}", "1");
		assertTrue(true);
	}

	@Test
	void testVnfPackagesVnfPkgIdPatch_MatchFail() throws Exception {
		final VnfPackageControllerImpl srv = new VnfPackageControllerImpl(patcher, eventManager, vnfPackageSrv, vnfPkgRepo, nsdVnfPkgService);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setVersion(1);
		when(vnfPackageSrv.findById(id)).thenReturn(vnfPkg);
		assertThrows(PreConditionException.class, () -> srv.vnfPackagesVnfPkgIdPatch(id, "{}", "66"));
	}

	@Test
	void testVnfPackagesVnfPkgIdPackageContentPut() throws Exception {
		final VnfPackageControllerImpl srv = new VnfPackageControllerImpl(patcher, eventManager, vnfPackageSrv, vnfPkgRepo, nsdVnfPkgService);
		final UUID id = UUID.randomUUID();
		final InputStream is = InputStream.nullInputStream();
		final VnfPackage vnfPkg = new VnfPackage();
		when(vnfPackageSrv.findById(id)).thenReturn(vnfPkg);
		srv.vnfPackagesVnfPkgIdPackageContentPut(id, is, "application/json");
		assertTrue(true);
	}

	@Test
	void testVnfPackagesVnfPkgIdPackageContentUploadFromUriPost() throws Exception {
		final VnfPackageControllerImpl srv = new VnfPackageControllerImpl(patcher, eventManager, vnfPackageSrv, vnfPkgRepo, nsdVnfPkgService);
		final UUID id = UUID.randomUUID();
		final InputStream is = InputStream.nullInputStream();
		final VnfPackage vnfPkg = new VnfPackage();
		when(vnfPackageSrv.findById(id)).thenReturn(vnfPkg);
		final UploadUriParameters params = new UploadUriParameters();
		srv.vnfPackagesVnfPkgIdPackageContentUploadFromUriPost(id, "", params);
		assertTrue(true);
	}

}
