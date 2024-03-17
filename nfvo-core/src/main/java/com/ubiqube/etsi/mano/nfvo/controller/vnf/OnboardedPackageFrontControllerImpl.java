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

import static com.ubiqube.etsi.mano.Constants.VNF_SEARCH_DEFAULT_EXCLUDE_FIELDS;
import static com.ubiqube.etsi.mano.Constants.VNF_SEARCH_MANDATORY_FIELDS;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.controller.MetaStreamResource;
import com.ubiqube.etsi.mano.controller.vnf.OnboardedPackageFrontController;
import com.ubiqube.etsi.mano.controller.vnf.VnfPackageManagement;
import com.ubiqube.etsi.mano.dao.mano.AdditionalArtifact;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.utils.SpringUtils;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class OnboardedPackageFrontControllerImpl implements OnboardedPackageFrontController {
	private final VnfPackageManagement vnfManagement;

	private final VnfPackageService vnfPackageService;

	private final VnfPackageRepository vnfPackageRepository;

	public OnboardedPackageFrontControllerImpl(final VnfPackageManagement vnfManagement, final VnfPackageService vnfPackageService, final VnfPackageRepository vnfPackageRepository) {
		this.vnfManagement = vnfManagement;
		this.vnfPackageService = vnfPackageService;
		this.vnfPackageRepository = vnfPackageRepository;
	}

	@Override
	public ResponseEntity<Resource> onboardedGetContentByVnfdId(final String vnfdId, final String accept, final @Nullable String includeSignature) {
		final VnfPackage vnfPkg = vnfPackageService.findByVnfdId(vnfdId);
		final ManoResource content = vnfManagement.onboardedVnfPackagesVnfdIdVnfdGet(vnfPkg.getVnfdId(), accept, includeSignature);
		if (null == includeSignature) {
			return returnDownloadable(content);
		}
		return handleSignature(vnfPkg.getId(), "vnfd.sig", content);
	}

	@Override
	public ResponseEntity<Resource> onboardedGetVnfdByVnfdId(final String vnfdId, final @Nullable String includeSignatures) {
		final VnfPackage vnfPkg = vnfPackageService.findByVnfdId(vnfdId);
		final ManoResource content = vnfPackageRepository.getBinary(vnfPkg.getId(), Constants.REPOSITORY_FILENAME_VNFD);
		if (null == includeSignatures) {
			return returnDownloadable(content);
		}
		return handleSignature(vnfPkg.getId(), "vnfd.sig", content);
	}

	@Override
	public ResponseEntity<Resource> onboardedGetArtifact(final HttpServletRequest request, final String vnfdId, final @Nullable String includeSignatures) {
		final UUID vnfPkgId = vnfPackageService.findByVnfdId(vnfdId).getId();
		final String path = SpringUtils.extractParams(request);
		final File f = new File(Constants.REPOSITORY_FOLDER_ARTIFACTS, path);
		final ManoResource content = vnfPackageRepository.getBinary(vnfPkgId, f.toString());
		if (null == includeSignatures) {
			return returnDownloadable(content);
		}
		return handleSignature(vnfPkgId, path, content);
	}

	private ResponseEntity<Resource> handleSignature(final UUID safeUUID, final String path, final ManoResource content) {
		final VnfPackage pkg = vnfPackageService.findById(safeUUID);
		final AdditionalArtifact artifact = findArtifact(path, pkg.getAdditionalArtifacts());
		if (null == artifact.getSignature()) {
			return returnDownloadable(content);
		}
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (final ZipOutputStream zipOut = new ZipOutputStream(bos)) {
			addEntry(zipOut, path);
			try (final InputStream tgt = content.getInputStream()) {
				tgt.transferTo(zipOut);
				zipOut.closeEntry();
			}
			final File f = new File(Constants.REPOSITORY_FOLDER_ARTIFACTS, artifact.getSignature());
			final ManoResource sig = vnfPackageRepository.getBinary(safeUUID, f.toString());
			addEntry(zipOut, artifact.getSignature());
			try (final InputStream tgt = sig.getInputStream()) {
				tgt.transferTo(zipOut);
				zipOut.closeEntry();
			}
		} catch (final IOException e) {
			throw new GenericException("Problem adding " + path + " to zip.", e);
		}
		final ManoResource ret = new ByteArrayResource(bos.toByteArray(), artifact.getArtifactPath() + ".zip");
		return returnDownloadable(ret);
	}

	private static void addEntry(final ZipOutputStream zipOut, final String path) {
		final ZipEntry zipEntry = new ZipEntry(path);
		try {
			zipOut.putNextEntry(zipEntry);
		} catch (final IOException e) {
			throw new GenericException("Unable to add entry: " + path, e);
		}
	}

	private static ResponseEntity<Resource> returnDownloadable(final ManoResource content) {
		final MetaStreamResource res = new MetaStreamResource(content);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaTypeFactory
						.getMediaType(res)
						.orElse(MediaType.APPLICATION_OCTET_STREAM))
				.body(res);
	}

	private static AdditionalArtifact findArtifact(final String p, final Set<AdditionalArtifact> aa) {
		return aa.stream().filter(x -> x.getArtifactPath().equals(p)).findFirst().orElseThrow(() -> new GenericException("Cannot find artifact " + p));
	}

	@Override
	public <U> ResponseEntity<U> onboardedFindById(final String vnfdId, final Class<U> clazz, final Consumer<U> makeLinks) {
		final U vnfPkgInfo = vnfManagement.vnfPackagesVnfPkgVnfdIdGet(vnfdId, clazz);
		makeLinks.accept(vnfPkgInfo);
		return new ResponseEntity<>(vnfPkgInfo, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Resource> onboardedGetArtifactByVnfdId(final String vnfdId) {
		final VnfPackage vnfPkg = vnfPackageService.findByVnfdId(vnfdId);
		final ManoResource content = vnfPackageRepository.getBinary(vnfPkg.getId(), Constants.REPOSITORY_ZIP_ARTIFACT);
		return returnDownloadable(content);
	}

	@Override
	public ResponseEntity<Resource> onboardedGetManifestByVnfd(final String vnfdId, final @Nullable String includeSignature) {
		final VnfPackage vnfPkg = vnfPackageService.findByVnfdId(vnfdId);
		final ManoResource content = vnfPackageRepository.getBinary(vnfPkg.getId(), Constants.REPOSITORY_ZIP_ARTIFACT);
		return returnDownloadable(content);
	}

	@Override
	public <U> ResponseEntity<String> onboardedSearch(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final Consumer<U> makeLinks) {
		return vnfManagement.searchOnboarded(requestParams, clazz, VNF_SEARCH_DEFAULT_EXCLUDE_FIELDS, VNF_SEARCH_MANDATORY_FIELDS, makeLinks);
	}

}
