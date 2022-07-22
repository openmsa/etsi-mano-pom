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
package com.ubiqube.etsi.mano.nfvo.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.AdditionalArtifact;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;
import com.ubiqube.etsi.mano.service.pkg.vnf.CustomOnboarding;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NfvoCustomOnboarding implements CustomOnboarding {
	private final VnfPackageRepository repo;

	public NfvoCustomOnboarding(final VnfPackageRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public void handleArtifacts(final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader) {
		final Set<String> cache = new HashSet<>();
		final ByteArrayOutputStream fos = new ByteArrayOutputStream();
		try (final ZipOutputStream zipOut = new ZipOutputStream(fos)) {
			vnfPackage.getAdditionalArtifacts()
					.forEach(x -> handleArtifact(cache, zipOut, vnfPackage, vnfPackageReader, x));
		} catch (final IOException e) {
			throw new GenericException("Unable to create Zip file.", e);
		}
		repo.storeBinary(vnfPackage.getId(), Constants.REPOSITORY_ZIP_ARTIFACT, new ByteArrayInputStream(fos.toByteArray()));
	}

	private void handleArtifact(final Set<String> cache, final ZipOutputStream zipOut, final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader, final AdditionalArtifact artifact) {
		final UUID id = vnfPackage.getId();
		addEntry(zipOut, artifact.getArtifactPath());
		copyFile(zipOut, vnfPackageReader, id, artifact.getArtifactPath());
		if ((artifact.getSignature() != null) && !cache.contains(artifact.getSignature())) {
			cache.add(artifact.getSignature());
			addEntry(zipOut, artifact.getSignature());
			copyFile(zipOut, vnfPackageReader, id, artifact.getSignature());
		}
		if ((artifact.getCertificate() != null) && !cache.contains(artifact.getCertificate())) {
			cache.add(artifact.getCertificate());
			addEntry(zipOut, artifact.getCertificate());
			copyFile(zipOut, vnfPackageReader, id, artifact.getCertificate());
		}
	}

	private static void addEntry(final ZipOutputStream zipOut, final String path) {
		final ZipEntry zipEntry = new ZipEntry(path);
		try {
			zipOut.putNextEntry(zipEntry);
		} catch (final IOException e) {
			throw new GenericException("Unable to add entry: " + path, e);
		}
	}

	private static String mkPath(final String path) {
		return Paths.get(Constants.REPOSITORY_FOLDER_ARTIFACTS, path).toString();
	}

	private void copyFile(final ZipOutputStream zipOut, final VnfPackageReader vnfPackageReader, final UUID id, final String artifactPath) {
		try (final InputStream tgt = vnfPackageReader.getFileInputStream(artifactPath)) {
			if (null == tgt) {
				throw new GenericException("Unable to find " + artifactPath);
			}
			repo.storeBinary(id, mkPath(artifactPath), tgt);
		} catch (final IOException e) {
			throw new GenericException("Problem reading " + artifactPath, e);
		}
		try (final InputStream tgt = vnfPackageReader.getFileInputStream(artifactPath)) {
			tgt.transferTo(zipOut);
			zipOut.closeEntry();
		} catch (final IOException e) {
			throw new GenericException("Problem adding " + artifactPath + " to zip.", e);
		}
	}

	@Override
	public void handleArtifacts(final NsdPackage pkg, final NsPackageProvider packageReader) {
		// Probably nothing to do.
	}

}
