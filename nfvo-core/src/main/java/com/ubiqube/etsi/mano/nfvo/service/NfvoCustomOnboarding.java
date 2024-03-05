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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.input.CountingInputStream;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.AdditionalArtifact;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.vim.Checksum;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.DownloadResult;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;
import com.ubiqube.etsi.mano.service.pkg.vnf.CustomOnboarding;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NfvoCustomOnboarding implements CustomOnboarding {
	private final VnfPackageRepository repo;

	public NfvoCustomOnboarding(final VnfPackageRepository repo) {
		this.repo = repo;
	}

	@Override
	public void handleArtifacts(final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader) {
		final Set<String> cache = new HashSet<>();
		final ByteArrayOutputStream fos = new ByteArrayOutputStream();
		try (final ZipOutputStream zipOut = new ZipOutputStream(fos)) {
			vnfPackage.getAdditionalArtifacts()
					.forEach(x -> handleArtifact(cache, zipOut, vnfPackage, vnfPackageReader, x));
			vnfPackage.getOsContainer().forEach(x -> handleArtifact(cache, zipOut, vnfPackage, vnfPackageReader, x.getArtifacts()));
			vnfPackage.getMciops().forEach(x -> handleArtifact(cache, zipOut, vnfPackage, vnfPackageReader, x.getArtifacts()));
		} catch (final IOException e) {
			throw new GenericException("Unable to create Zip file.", e);
		}
		repo.storeBinary(Objects.requireNonNull(vnfPackage.getId()), Constants.REPOSITORY_ZIP_ARTIFACT, new ByteArrayInputStream(fos.toByteArray()));
	}

	private void handleArtifact(final Set<String> cache, final ZipOutputStream zipOut, final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader, final @Nullable Map<String, SoftwareImage> artifacts) {
		if (null == artifacts) {
			return;
		}
		artifacts.entrySet().forEach(x -> handleSw(cache, zipOut, vnfPackage, vnfPackageReader, x.getValue()));
	}

	private void handleSw(final Set<String> cache, final ZipOutputStream zipOut, final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader, final SoftwareImage value) {
		final UUID id = Objects.requireNonNull(vnfPackage.getId());
		if (cache.contains(value.getImagePath())) {
			return;
		}
		addEntry(zipOut, value.getImagePath());
		final DownloadResult hash = copyFile(zipOut, vnfPackageReader, id, value.getImagePath());
		setHash(value, hash);
		cache.add(value.getImagePath());
	}

	private void handleArtifact(final Set<String> cache, final ZipOutputStream zipOut, final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader, final AdditionalArtifact artifact) {
		final UUID id = Objects.requireNonNull(vnfPackage.getId());
		if (null == artifact.getArtifactPath()) {
			return;
		}
		if (!cache.contains(artifact.getArtifactPath())) {
			cache.add(artifact.getArtifactPath());
			addEntry(zipOut, artifact.getArtifactPath());
			final DownloadResult hash = copyFile(zipOut, vnfPackageReader, id, artifact.getArtifactPath());
			setHash(artifact, hash);
		}
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

	private static void setHash(final SoftwareImage value, final DownloadResult hash) {
		final Checksum chk = Optional.ofNullable(value.getChecksum()).orElseGet(Checksum::new);
		chk.setMd5(hash.md5String());
		chk.setSha256(hash.sha256String());
		chk.setSha512(hash.sha512String());
		if (null == value.getSize()) {
			value.setSize(hash.count());
		} else if (value.getSize().equals(hash.count())) {
			throw new GenericException("File size for [" + value.getImagePath() + "] doesn't match the given size: " + value.getSize() + ", but found: " + hash.count());
		}
		value.setChecksum(chk);
	}

	private static void setHash(final AdditionalArtifact artifact, final DownloadResult hash) {
		final Checksum chk = Optional.ofNullable(artifact.getChecksum()).orElseGet(Checksum::new);
		chk.setMd5(hash.md5String());
		chk.setSha256(hash.sha256String());
		chk.setSha512(hash.sha512String());
		artifact.setChecksum(chk);
	}

	private static void addEntry(final ZipOutputStream zipOut, final String path) {
		final ZipEntry zipEntry = new ZipEntry(path);
		try {
			zipOut.putNextEntry(zipEntry);
		} catch (final IOException e) {
			throw new GenericException("Unable to add entry: " + path, e);
		}
	}

	@SuppressWarnings("null")
	@NotNull
	private static String mkPath(final String path) {
		return Paths.get(Constants.REPOSITORY_FOLDER_ARTIFACTS, path).toString();
	}

	private DownloadResult copyFile(final ZipOutputStream zipOut, final VnfPackageReader vnfPackageReader, final UUID id, final String artifactPath) {
		DownloadResult ret = new DownloadResult(new byte[0], new byte[0], new byte[0], 0L);
		try (final InputStream tgtIn = vnfPackageReader.getFileInputStream(artifactPath);
				final CountingInputStream count = new CountingInputStream(tgtIn);
				final DigestInputStream inMd5 = new DigestInputStream(count, MessageDigest.getInstance("md5"));
				final DigestInputStream inSha256 = new DigestInputStream(inMd5, MessageDigest.getInstance("SHA-256"));
				final DigestInputStream tgt = new DigestInputStream(inSha256, MessageDigest.getInstance("SHA-512"));) {
			repo.storeBinary(id, mkPath(artifactPath), tgt);
			ret = new DownloadResult(inMd5.getMessageDigest().digest(), inSha256.getMessageDigest().digest(), tgt.getMessageDigest().digest(), count.getByteCount());
		} catch (final IOException | NoSuchAlgorithmException e) {
			throw new GenericException("Problem reading " + artifactPath, e);
		}
		try (final InputStream tgt = vnfPackageReader.getFileInputStream(artifactPath)) {
			tgt.transferTo(zipOut);
			zipOut.closeEntry();
		} catch (final IOException e) {
			throw new GenericException("Problem adding " + artifactPath + " to zip.", e);
		}
		return ret;
	}

	@Override
	public void handleArtifacts(final NsdPackage pkg, final NsPackageProvider packageReader) {
		// Probably nothing to do.
	}

}
