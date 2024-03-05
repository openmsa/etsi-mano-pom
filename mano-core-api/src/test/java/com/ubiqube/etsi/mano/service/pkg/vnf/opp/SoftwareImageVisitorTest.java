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
package com.ubiqube.etsi.mano.service.pkg.vnf.opp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.vim.Checksum;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.service.pkg.vnf.DownloaderService;

@ExtendWith(MockitoExtension.class)
class SoftwareImageVisitorTest {
	@Mock
	private DownloaderService downloadService;

	@Test
	void testBasic() throws Exception {
		final SoftwareImageVisitor sv = new SoftwareImageVisitor(downloadService);
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setOsContainer(Set.of());
		vnfPackage.setMciops(Set.of());
		sv.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void testComputeWithNoCheckSum() throws Exception {
		final SoftwareImageVisitor sv = new SoftwareImageVisitor(downloadService);
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setOsContainer(Set.of());
		vnfPackage.setMciops(Set.of());
		//
		final VnfCompute comp01 = new VnfCompute();
		final SoftwareImage img = new SoftwareImage();
		comp01.setSoftwareImage(img);
		vnfPackage.setVnfCompute(Set.of(comp01));
		sv.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void testComputeWithCheckSumNoHash() throws Exception {
		final SoftwareImageVisitor sv = new SoftwareImageVisitor(downloadService);
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setOsContainer(Set.of());
		vnfPackage.setMciops(Set.of());
		//
		final VnfCompute comp01 = new VnfCompute();
		final SoftwareImage img = new SoftwareImage();
		img.setChecksum(new Checksum());
		img.setImagePath("/etc/passwd");
		comp01.setSoftwareImage(img);
		vnfPackage.setVnfCompute(Set.of(comp01));
		sv.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void testComputeWithCheckSum() throws Exception {
		final SoftwareImageVisitor sv = new SoftwareImageVisitor(downloadService);
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setOsContainer(Set.of());
		vnfPackage.setMciops(Set.of());
		//
		final VnfCompute comp01 = new VnfCompute();
		final SoftwareImage img = new SoftwareImage();
		final Checksum chk = new Checksum();
		chk.setHash("hash");
		img.setChecksum(chk);
		img.setImagePath("http://0.0.0.0/");
		comp01.setSoftwareImage(img);
		vnfPackage.setVnfCompute(Set.of(comp01));
		sv.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void testStorageWithCheckSum() throws Exception {
		final SoftwareImageVisitor sv = new SoftwareImageVisitor(downloadService);
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setOsContainer(Set.of());
		vnfPackage.setMciops(Set.of());
		//
		final SoftwareImage img = new SoftwareImage();
		final Checksum chk = new Checksum();
		chk.setHash("hash");
		img.setChecksum(chk);
		img.setImagePath("http://0.0.0.0/");
		final VnfStorage sto01 = new VnfStorage();
		sto01.setSoftwareImage(img);
		vnfPackage.setVnfStorage(Set.of(sto01));
		sv.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void testOsContainerWithCheckSum() throws Exception {
		final SoftwareImageVisitor sv = new SoftwareImageVisitor(downloadService);
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setOsContainer(Set.of());
		vnfPackage.setMciops(Set.of());
		//
		final SoftwareImage img = new SoftwareImage();
		final Checksum chk = new Checksum();
		chk.setHash("hash");
		img.setChecksum(chk);
		img.setImagePath("http://0.0.0.0/");
		final OsContainer osc01 = new OsContainer();
		osc01.setArtifacts(new HashMap<>());
		osc01.getArtifacts().put("test", img);
		vnfPackage.setOsContainer(Set.of(osc01));
		sv.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void testMciopsWithCheckSum() throws Exception {
		final SoftwareImageVisitor sv = new SoftwareImageVisitor(downloadService);
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setOsContainer(Set.of());
		vnfPackage.setMciops(Set.of());
		//
		final SoftwareImage img = new SoftwareImage();
		final Checksum chk = new Checksum();
		chk.setHash("hash2");
		img.setChecksum(chk);
		img.setImagePath("0.0.0.0/");
		final McIops mci01 = new McIops();
		mci01.setArtifacts(new HashMap<>());
		mci01.getArtifacts().put("test", img);
		vnfPackage.setMciops(Set.of(mci01));
		sv.visit(vnfPackage);
		assertTrue(true);
	}
}
