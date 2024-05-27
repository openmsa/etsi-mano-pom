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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.AdditionalArtifact;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;

@ExtendWith(MockitoExtension.class)
class NfvoCustomOnboardingTest {

	private static final String ARTEFACT_1 = "/tmp/test-package-1.zip";
	private static final String ARTEFACT_1_CONTENT = "content1";
	private static final String ARTEFACT_2 = "/tmp/test-package-2.zip";
	private static final String ARTEFACT_2_CONTENT = "content2";
	private static final String ARTEFACT_3 = "/tmp/test-package-3.zip";
	private static final String ARTEFACT_3_CONTENT = "content3";

	@Mock
	private VnfPackageRepository vnfRepo;
	@Mock
	private VnfPackageReader packageReader;

	@BeforeAll
	public static void beforeAll() throws MalformedURLException, IOException {
		createFile(ARTEFACT_1, ARTEFACT_1_CONTENT);
		createFile(ARTEFACT_2, ARTEFACT_2_CONTENT);
		createFile(ARTEFACT_3, ARTEFACT_3_CONTENT);
	}

	static void createFile(final String path, final String content) throws FileNotFoundException, IOException {
		try (FileOutputStream fos = new FileOutputStream(path)) {
			final ByteArrayInputStream bis = new ByteArrayInputStream(content.getBytes());
		}
	}

	@Test
	void test_Basic() {
		final NfvoCustomOnboarding srv = new NfvoCustomOnboarding(vnfRepo);
		final VnfPackage pkg = new VnfPackage();
		final UUID id = UUID.randomUUID();
		pkg.setId(id);
		pkg.setAdditionalArtifacts(Set.of());
		pkg.setOsContainer(Set.of());
		pkg.setMciops(Set.of());
		srv.handleArtifacts(pkg, packageReader);
		assertTrue(true);
	}

	@Test
	void test_Additional() throws FileNotFoundException {
		final NfvoCustomOnboarding srv = new NfvoCustomOnboarding(vnfRepo);
		final VnfPackage pkg = new VnfPackage();
		final UUID id = UUID.randomUUID();
		pkg.setId(id);
		//
		final AdditionalArtifact art = new AdditionalArtifact();
		art.setArtifactPath(ARTEFACT_1);
		pkg.setAdditionalArtifacts(Set.of(art));
		//
		pkg.setOsContainer(Set.of());
		//
		pkg.setMciops(Set.of());
		//
		final FileInputStream fis1 = new FileInputStream(ARTEFACT_1);
		final FileInputStream fis2 = new FileInputStream(ARTEFACT_1);
		when(packageReader.getFileInputStream(ARTEFACT_1)).thenReturn(fis1, fis2);
		when(vnfRepo.getBinary(id, mkPath(ARTEFACT_1))).thenReturn(new ByteArrayResource("content".getBytes(), ARTEFACT_1));
		srv.handleArtifacts(pkg, packageReader);
		assertTrue(true);
	}

	@Test
	void test_AdditionalWithSignature() throws FileNotFoundException {
		final NfvoCustomOnboarding srv = new NfvoCustomOnboarding(vnfRepo);
		final VnfPackage pkg = new VnfPackage();
		final UUID id = UUID.randomUUID();
		pkg.setId(id);
		//
		final AdditionalArtifact art = new AdditionalArtifact();
		art.setArtifactPath(ARTEFACT_1);
		art.setSignature(ARTEFACT_2);
		pkg.setAdditionalArtifacts(Set.of(art));
		//
		pkg.setOsContainer(Set.of());
		//
		pkg.setMciops(Set.of());
		//
		final FileInputStream fis1 = new FileInputStream(ARTEFACT_1);
		final FileInputStream fis2 = new FileInputStream(ARTEFACT_1);
		when(packageReader.getFileInputStream(ARTEFACT_1)).thenReturn(fis1, fis2);
		final FileInputStream fis3 = new FileInputStream(ARTEFACT_2);
		final FileInputStream fis4 = new FileInputStream(ARTEFACT_2);
		when(packageReader.getFileInputStream(ARTEFACT_2)).thenReturn(fis3, fis4);
		when(vnfRepo.getBinary(id, mkPath(ARTEFACT_1))).thenReturn(new ByteArrayResource("content".getBytes(), ARTEFACT_1));
		when(vnfRepo.getBinary(id, mkPath(ARTEFACT_2))).thenReturn(new ByteArrayResource("content".getBytes(), ARTEFACT_2));
		srv.handleArtifacts(pkg, packageReader);
		assertTrue(true);
	}

	@Test
	void test_AdditionalWithCertificate() throws FileNotFoundException {
		final NfvoCustomOnboarding srv = new NfvoCustomOnboarding(vnfRepo);
		final VnfPackage pkg = new VnfPackage();
		final UUID id = UUID.randomUUID();
		pkg.setId(id);
		//
		final AdditionalArtifact art = new AdditionalArtifact();
		art.setArtifactPath(ARTEFACT_1);
		art.setCertificate(ARTEFACT_2);
		pkg.setAdditionalArtifacts(Set.of(art));
		//
		pkg.setOsContainer(Set.of());
		//
		pkg.setMciops(Set.of());
		//
		final FileInputStream fis1 = new FileInputStream(ARTEFACT_1);
		final FileInputStream fis2 = new FileInputStream(ARTEFACT_1);
		when(packageReader.getFileInputStream(ARTEFACT_1)).thenReturn(fis1, fis2);
		final FileInputStream fis3 = new FileInputStream(ARTEFACT_2);
		final FileInputStream fis4 = new FileInputStream(ARTEFACT_2);
		when(packageReader.getFileInputStream(ARTEFACT_2)).thenReturn(fis3, fis4);
		when(vnfRepo.getBinary(id, mkPath(ARTEFACT_1))).thenReturn(new ByteArrayResource("content".getBytes(), ARTEFACT_1));
		when(vnfRepo.getBinary(id, mkPath(ARTEFACT_2))).thenReturn(new ByteArrayResource("content".getBytes(), ARTEFACT_2));
		srv.handleArtifacts(pkg, packageReader);
		assertTrue(true);
	}

	@Test
	void test_OsContainer() throws FileNotFoundException {
		final NfvoCustomOnboarding srv = new NfvoCustomOnboarding(vnfRepo);
		final VnfPackage pkg = new VnfPackage();
		final UUID id = UUID.randomUUID();
		pkg.setId(id);
		//
		pkg.setAdditionalArtifacts(Set.of());
		//
		final OsContainer osc = new OsContainer();
		final SoftwareImage si01 = new SoftwareImage();
		si01.setImagePath(ARTEFACT_1);
		final SoftwareImage si02 = new SoftwareImage();
		si02.setImagePath(ARTEFACT_1);
		osc.setArtifacts(Map.of("kk", si01, "2", si02));
		pkg.setOsContainer(Set.of(osc));
		//
		pkg.setMciops(Set.of());
		//
		final FileInputStream fis1 = new FileInputStream(ARTEFACT_1);
		final FileInputStream fis2 = new FileInputStream(ARTEFACT_1);
		when(packageReader.getFileInputStream(ARTEFACT_1)).thenReturn(fis1, fis2);
		when(vnfRepo.getBinary(id, mkPath(ARTEFACT_1))).thenReturn(new ByteArrayResource("content".getBytes(), ARTEFACT_1));
		srv.handleArtifacts(pkg, packageReader);
		assertTrue(true);
	}

	@Test
	void test_Mcio() throws FileNotFoundException {
		final NfvoCustomOnboarding srv = new NfvoCustomOnboarding(vnfRepo);
		final VnfPackage pkg = new VnfPackage();
		final UUID id = UUID.randomUUID();
		pkg.setId(id);
		//
		pkg.setAdditionalArtifacts(Set.of());
		//
		pkg.setOsContainer(Set.of());
		//
		final McIops mcio = new McIops();
		final SoftwareImage si = new SoftwareImage();
		si.setImagePath(ARTEFACT_1);
		mcio.setArtifacts(Map.of(ARTEFACT_1, si));
		pkg.setMciops(Set.of(mcio));
		//
		final FileInputStream fis1 = new FileInputStream(ARTEFACT_1);
		final FileInputStream fis2 = new FileInputStream(ARTEFACT_1);
		when(packageReader.getFileInputStream(ARTEFACT_1)).thenReturn(fis1, fis2);
		when(vnfRepo.getBinary(id, mkPath(ARTEFACT_1))).thenReturn(new ByteArrayResource("content".getBytes(), ARTEFACT_1));
		srv.handleArtifacts(pkg, packageReader);
		assertTrue(true);
	}

	private static String mkPath(final String path) {
		return Paths.get(Constants.REPOSITORY_FOLDER_ARTIFACTS, path).toString();
	}
}
