/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.nfvo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsPackageManager;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsPackageOnboardingImpl;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;
import com.ubiqube.etsi.mano.utils.TemporaryFileSentry;

@Controller
@RequestMapping("/admin")
public class AdminNfvoController {

	private final NsPackageManager packageManager;
	private final NsPackageOnboardingImpl nsPackageOnboardingImpl;

	public AdminNfvoController(NsPackageManager packageManager, final NsPackageOnboardingImpl nsPackageOnboardingImpl) {
		super();
		this.packageManager = packageManager;
		this.nsPackageOnboardingImpl = nsPackageOnboardingImpl;
	}

	@PostMapping(value = "/validate/ns")
	public ResponseEntity<Void> validateNs(@RequestParam("file") MultipartFile file) {
		final NsdPackage nsPackage = new NsdPackage();
		nsPackage.setId(UUID.randomUUID());
		try (InputStream fis = file.getInputStream(); 
				TemporaryFileSentry tfs = new TemporaryFileSentry()) {
			final Path p = tfs.get();
            FileUtils.copyInputStreamToFile(fis, p.toFile());
			ManoResource data = new ByteArrayResource(IOUtils.toByteArray(fis), p.toFile().getName());
			final PackageDescriptor<NsPackageProvider> packageProvider = packageManager.getProviderFor(data);
			if (null != packageProvider) {
				try (InputStream is = data.getInputStream()) {
					nsPackageOnboardingImpl.mapNsPackage(packageProvider.getNewReaderInstance(is, nsPackage.getId()), nsPackage);
				}
			}
		} catch (IOException e) {
			throw new GenericException(e);
		}
		return ResponseEntity.accepted().build();
	}

}
