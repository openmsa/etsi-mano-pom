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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ubiqube.etsi.mano.controller.MetaStreamResource;
import com.ubiqube.etsi.mano.controller.vnf.VnfPackageFrontController;
import com.ubiqube.etsi.mano.controller.vnf.VnfPackageManagement;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.ExternalArtifactsAccessConfig;
import com.ubiqube.etsi.mano.dao.mano.pkg.UploadUriParameters;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.service.auth.model.AuthType;
import com.ubiqube.etsi.mano.utils.SpringUtils;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnfPackageFrontControllerImpl implements VnfPackageFrontController {
	private final VnfPackageManagement vnfManagement;
	private final VnfPackageController vnfPackageController;

	public VnfPackageFrontControllerImpl(final VnfPackageManagement vnfManagement, final VnfPackageController vnfPackageController) {
		this.vnfManagement = vnfManagement;
		this.vnfPackageController = vnfPackageController;
	}

	@Override
	public ResponseEntity<Resource> getArtifactPath(final HttpServletRequest request, final UUID vnfPkgId, final @Nullable String includeSignature) {
		final String artifactPath = SpringUtils.extractParams(request);
		return vnfManagement.vnfPackagesVnfPkgIdArtifactsArtifactPathGet(vnfPkgId, artifactPath);
	}

	@Override
	public <U> ResponseEntity<U> findById(final UUID vnfPkgId, final Function<VnfPackage, U> mapper, final Consumer<U> makeLinks) {
		final VnfPackage vnfPackage = vnfManagement.vnfPackagesVnfPkgIdGet(vnfPkgId);
		final FailureDetails error = vnfPackage.getOnboardingFailureDetails();
		final Long res = Optional.ofNullable(error).map(x -> x.getStatus()).orElse(0L);
		if (res == 0L) {
			vnfPackage.setOnboardingFailureDetails(null);
		}
		final U vnfPkgInfo = mapper.apply(vnfPackage);
		makeLinks.accept(vnfPkgInfo);
		return ResponseEntity.ok().eTag("" + vnfPackage.getVersion()).body(vnfPkgInfo);
	}

	@Override
	public <U> ResponseEntity<U> findByIdReadOnly(final UUID vnfPkgId, final Function<VnfPackage, U> mapper, final Consumer<U> makeLinks) {
		final U vnfPkgInfo = vnfManagement.vnfPackagesVnfPkgIdGet(vnfPkgId, mapper);
		makeLinks.accept(vnfPkgInfo);
		return new ResponseEntity<>(vnfPkgInfo, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Resource> getManifest(final UUID vnfPkgId, final @Nullable String includeSignature) {
		final ManoResource ms = vnfManagement.getPackageManifest(vnfPkgId, includeSignature);
		final MetaStreamResource res = new MetaStreamResource(ms);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypeFactory
				.getMediaType(res).orElse(MediaType.APPLICATION_OCTET_STREAM))
				.body(res);
	}

	@Override
	public ResponseEntity<Resource> getContent(final UUID vnfPkgId) {
		return vnfManagement.vnfPackagesVnfPkgIdPackageContentGet(vnfPkgId);
	}

	@Override
	public ResponseEntity<Resource> getVfnd(final UUID vnfPkgId, final String contentType, final @Nullable String includeSignature) {
		final ManoResource ms = vnfManagement.vnfPackagesVnfPkgIdVnfdGet(vnfPkgId, contentType, includeSignature != null);
		final MetaStreamResource res = new MetaStreamResource(ms);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaTypeFactory
						.getMediaType(res)
						.orElse(MediaType.APPLICATION_OCTET_STREAM))
				.body(res);
	}

	@Override
	public ResponseEntity<Resource> getSelectArtifacts(final HttpServletRequest request, final UUID vnfPkgId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResponseEntity<Void> deleteById(final UUID vnfPkgId) {
		vnfPackageController.vnfPackagesVnfPkgIdDelete(vnfPkgId);
		return ResponseEntity.noContent().build();
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<VnfPackage, U> mapper, final Consumer<U> makeLinks, final Class<?> frontClass) {
		return vnfManagement.search(requestParams, mapper, VNF_SEARCH_DEFAULT_EXCLUDE_FIELDS, VNF_SEARCH_MANDATORY_FIELDS, makeLinks, frontClass);
	}

	@Override
	public <U> ResponseEntity<U> create(final Map<String, String> userDefinedData, final Function<VnfPackage, U> mapper, final Consumer<U> makeLinks, final Function<U, String> getSelfLink) {
		final VnfPackage vnfPackage = vnfPackageController.vnfPackagesPost(userDefinedData);
		final U vnfPkgInfo = mapper.apply(vnfPackage);
		makeLinks.accept(vnfPkgInfo);
		final String link = getSelfLink.apply(vnfPkgInfo);
		return ResponseEntity.created(URI.create(link)).body(vnfPkgInfo);
	}

	@Override
	public <U> ResponseEntity<U> getExternalArtifacts(final UUID id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> putExternalArtifactAccessConfig(final ExternalArtifactsAccessConfig body, final UUID id, final Function<ExternalArtifactsAccessConfig, U> mapper) {
		final ExternalArtifactsAccessConfig res = vnfManagement.putExternalArtifact(body, id);
		return ResponseEntity.ok(mapper.apply(res));
	}

	@Override
	public ResponseEntity<Void> putContent(final UUID id, final String accept, final @Nullable MultipartFile file) {
		if (null == file) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'file' multi part parameter MUST be present.");
		}
		try (InputStream is = file.getInputStream()) {
			vnfPackageController.vnfPackagesVnfPkgIdPackageContentPut(id, is, accept);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		return ResponseEntity.accepted().build();
	}

	@Override
	public ResponseEntity<Void> uploadFromUri(final @Nullable UploadUriParameters body, final UUID id, final String contentType) {
		if ((null == body.getAuthType()) && (body.getUsername() != null)) {
			body.setAuthType(AuthType.BASIC);
		}
		vnfPackageController.vnfPackagesVnfPkgIdPackageContentUploadFromUriPost(id, contentType, body);
		return ResponseEntity.accepted().build();
	}

	@Override
	public <U> ResponseEntity<U> modify(final String body, final UUID vnfPkgId, final @Nullable String ifMatch, final Function<VnfPackage, U> mapper, final Consumer<U> makeLinks) {
		final VnfPackage vnfPackage = vnfPackageController.vnfPackagesVnfPkgIdPatch(vnfPkgId, body, ifMatch);
		final U vnfPkgInfo = mapper.apply(vnfPackage);
		makeLinks.accept(vnfPkgInfo);
		return new ResponseEntity<>(vnfPkgInfo, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Resource> searchArtifact(final UUID safeUUID, final @Nullable String includeSignatures, final @Nullable String excludeAllManoArtifacts, final @Nullable String excludeAllNonManoArtifacts, final @Nullable String selectNonManoArtifactSets) {
		throw new UnsupportedOperationException();
	}

}
