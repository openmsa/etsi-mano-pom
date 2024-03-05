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
package com.ubiqube.etsi.mano.service.event.images;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.vim.Checksum;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VirtualFileSystem;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageManager;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;
import com.ubiqube.etsi.mano.service.vim.Storage;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;

import jakarta.validation.constraints.NotNull;

@ExtendWith(MockitoExtension.class)
class SoftwareImageTest {
	@Mock
	private ManoResource mr;
	@Mock
	private Vim vim;
	@Mock
	private VimManager vimManager;
	@Mock
	private VnfPackageRepository repository;
	@Mock
	private VnfPackageManager packageManager;
	@Mock
	private PackageDescriptor<VnfPackageReader> pdesc;
	@Mock
	private @NotNull Storage storage;

	private static SoftwareImage withHash256Si() {
		final SoftwareImage si = new SoftwareImage();
		si.setName("good1");
		final Checksum checksum = new Checksum();
		checksum.setAlgorithm("SHA-256");
		checksum.setHash("01ba4719c80b6fe911b091a7c05124b64eeece964e09c058ef8f9805daca546b");
		si.setChecksum(checksum);
		return si;
	}

	private static SoftwareImage noAlg384Si() {
		final SoftwareImage si = new SoftwareImage();
		si.setName("good");
		si.setImagePath("/my-image.qcow2");
		final Checksum checksum = new Checksum();
		checksum.setAlgorithm("SHA-384");
		checksum.setHash("ec664e889ed6c1b2763cacf7899d95b7f347373eb982e523419feea3aa362d891b3bf025f292267a5854049091789c3e");
		si.setChecksum(checksum);
		return si;
	}

	private static SoftwareImage withHash256BadSi() {
		final SoftwareImage si = new SoftwareImage();
		si.setName("bad");
		final Checksum checksum = new Checksum();
		checksum.setAlgorithm("SHA-256");
		checksum.setHash("01ba4719c80b6fe911b091a7c05124b64eeece964e09c058ef8f9805daca546b");
		si.setChecksum(checksum);
		return si;
	}

	@Test
	void testOk() {
		final SoftwareImageService sis = new SoftwareImageService(repository, vimManager, packageManager);
		final List<SoftwareImage> sws = List.of(withHash256Si());
		final SoftwareImage swIn = noAlg384Si();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfPackage pkg = new VnfPackage();
		pkg.setPackageProvider("provider");
		when(repository.get(any())).thenReturn(pkg);
		when(packageManager.getProviderFor(anyString())).thenReturn(pdesc);
		when(repository.getBinary(any(), eq(Constants.REPOSITORY_FILENAME_PACKAGE))).thenReturn(mr);
		when(vimManager.getVimById(any())).thenReturn(vim);
		when(pdesc.getFileSystem(any())).thenReturn(new myVfs());
		when(vim.storage(any())).thenReturn(storage);
		final SoftwareImage img = sis.getImage(sws, swIn, vimConn, UUID.randomUUID());
		assertNotNull(img);
	}

	@Test
	void testEqual() {
		final SoftwareImageService sis = new SoftwareImageService(repository, vimManager, packageManager);
		final List<SoftwareImage> sws = List.of(withHash256BadSi());
		final SoftwareImage swIn = withHash256BadSi();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final SoftwareImage img = sis.getImage(sws, swIn, vimConn, UUID.randomUUID());
		assertNotNull(img);
	}

	@Test
	void testCreate() {
		final SoftwareImageService sis = new SoftwareImageService(repository, vimManager, packageManager);
		final List<SoftwareImage> sws = List.of(withHash256Si());
		final SoftwareImage swIn = noAlg384Si();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		// Mock
		lenient().when(packageManager.getProviderFor(anyString())).thenReturn(pdesc);
		final VnfPackage pkg = new VnfPackage();
		pkg.setPackageProvider("provider");
		when(repository.get(any())).thenReturn(pkg);
		when(repository.getBinary(any(), any())).thenReturn(mr);
		when(vimManager.getVimById(any())).thenReturn(vim);
		when(pdesc.getFileSystem(any())).thenReturn(new myVfs());
		when(vim.storage(any())).thenReturn(storage);
		final SoftwareImage img = sis.getImage(sws, swIn, vimConn, UUID.randomUUID());
		assertNotNull(img);
	}

	class myVfs implements VirtualFileSystem {

		@Override
		public InputStream getInputStream(final String imagePath) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
