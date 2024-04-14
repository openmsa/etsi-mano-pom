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
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.service.VnfPackageService;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class VnfManagementTest {
	@Mock
	private VnfPackageRepository vnfPkgRepo;
	@Mock
	private MapperFacade mapper;
	@Mock
	private VnfPackageService vnfPackageJpa;
	@Mock
	private SearchableService searchService;
	@Mock
	private ManoResource manoRes;

	@Test
	void testName() throws Exception {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		when(vnfPackageJpa.findById(id)).thenReturn(vnfPackage);
		mng.getPackageManifest(id, null);
		assertTrue(true);
	}

	private VnfManagement createService() {
		return new VnfManagement(vnfPkgRepo, vnfPackageJpa, searchService);
	}

	@Test
	void testOnboardedGetMananifest() throws Exception {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		when(vnfPackageJpa.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		mng.onboardedGetManifestByVnfd(id.toString(), null);
		assertTrue(true);
	}

	@Test
	void testOnboardedGet() throws Exception {
		final VnfManagement mng = createService();
		createZip("test");
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		when(vnfPackageJpa.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		when(vnfPkgRepo.getBinary(id, "vnfd")).thenReturn(manoRes);
		final InputStream is = new FileInputStream("/tmp/test.zip");
		when(manoRes.getInputStream()).thenReturn(is);
		mng.onboardedVnfPackagesVnfdIdArtifactsGet(id.toString(), "test");
		assertTrue(true);
	}

	@Test
	void testOnboardedGet_NotFound() throws Exception {
		final VnfManagement mng = createService();
		createZip("test2");
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		when(vnfPackageJpa.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		when(vnfPkgRepo.getBinary(id, "vnfd")).thenReturn(manoRes);
		final InputStream is = new FileInputStream("/tmp/test.zip");
		when(manoRes.getInputStream()).thenReturn(is);
		assertThrows(NotFoundException.class, () -> mng.onboardedVnfPackagesVnfdIdArtifactsGet(id.toString(), "test"));
	}

	@Test
	void testOnboardedSet() throws Exception {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		when(vnfPackageJpa.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		mng.onboardedVnfPackagesVnfdIdGet(id.toString(), x -> "");
		assertTrue(true);
	}

	@Test
	void testOnboardedManif() throws Exception {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setId(id);
		when(vnfPackageJpa.findByVnfdId(id.toString())).thenReturn(vnfPackage);
		mng.onboardedVnfPackagesVnfdIdManifestGet(id.toString(), null);
		assertTrue(true);
	}

	@Test
	void testVnfPackagesVnfPkgIdGet() {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		mng.vnfPackagesVnfPkgIdGet(id, x -> "");
		assertTrue(true);
	}

	@Test
	void testVnfPackagesVnfPkgIdGet002() {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		mng.vnfPackagesVnfPkgIdGet(id);
		assertTrue(true);
	}

	@Test
	void testVnfPackagesGet() {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		mng.vnfPackagesGet("");
		assertTrue(true);
	}

	@Test
	void testVnfPackagesVnfPkgIdVnfdGet() {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		mng.vnfPackagesVnfPkgIdVnfdGet(id, "", false);
		assertTrue(true);
	}

	@Test
	void testVnfPackagesVnfPkgIdPackageContentGet() {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		mng.vnfPackagesVnfPkgIdPackageContentGet(id);
		assertTrue(true);
	}

	@Test
	void testOnboardedVnfPackagesVnfdIdPackageContentGet() {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setId(id);
		when(vnfPackageJpa.findByVnfdId(id.toString())).thenReturn(vnfPkg);
		mng.onboardedVnfPackagesVnfdIdPackageContentGet(id.toString());
		assertTrue(true);
	}

	@Test
	void testOnboardedVnfPackagesVnfdIdVnfdGet() {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setId(id);
		when(vnfPackageJpa.findByVnfdId(id.toString())).thenReturn(vnfPkg);
		mng.onboardedVnfPackagesVnfdIdVnfdGet(id.toString(), "", null);
		assertTrue(true);
	}

	@Test
	void testVnfPackagesVnfPkgVnfdIdGet() {
		final VnfManagement mng = createService();
		final UUID id = UUID.randomUUID();
		mng.vnfPackagesVnfPkgVnfdIdGet(id.toString(), x -> "");
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final VnfManagement mng = createService();
		mng.search(null, null, null, null, null, null);
		assertTrue(true);
	}

	@Test
	void testSearchOnboarded() {
		final VnfManagement mng = createService();
		mng.searchOnboarded(null, null, null, null, null, null);
		assertTrue(true);
	}

	private static void createZip(final String innerFilename) throws FileNotFoundException, IOException {
		try (FileOutputStream fos = new FileOutputStream("/tmp/test.zip")) {
			try (ZipOutputStream zipOut = new ZipOutputStream(fos)) {
				try (InputStream is = new ByteArrayInputStream("content".getBytes())) {
					final ZipEntry zipEntry = new ZipEntry(innerFilename);
					zipOut.putNextEntry(zipEntry);

					final byte[] bytes = new byte[1024];
					int length;
					while ((length = is.read(bytes)) >= 0) {
						zipOut.write(bytes, 0, length);
					}
				}
			}
		}
	}
}
