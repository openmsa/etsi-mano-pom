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
package com.ubiqube.etsi.mano.controller.vnf;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.repository.ManoResource;

import jakarta.validation.Valid;

public interface VnfPackageManagement {
	/**
	 * Only for read.
	 *
	 * @param <U>      The json class.
	 * @param vnfPkgId A Id.
	 * @param u        The json class.
	 * @return A Json instance.
	 */
	<U> U vnfPackagesVnfPkgIdGet(UUID vnfPkgId, Class<U> u);

	/**
	 * Only for write.
	 *
	 * @param vnfPkgId An Id.
	 * @return A VnfPackage instance.
	 */
	VnfPackage vnfPackagesVnfPkgIdGet(UUID vnfPkgId);

	List<VnfPackage> vnfPackagesGet(String filter);

	/**
	 *
	 * @param vnfPkgId
	 * @param artifactPath
	 * @return A resource.
	 */
	ResponseEntity<Resource> vnfPackagesVnfPkgIdArtifactsArtifactPathGet(UUID vnfPkgId, String artifactPath);

	ManoResource vnfPackagesVnfPkgIdVnfdGet(UUID vnfPkgId, String contentType, boolean includeSignature);

	ResponseEntity<Resource> vnfPackagesVnfPkgIdPackageContentGet(UUID vnfPkgId);

	ManoResource getPackageManifest(UUID vnfPkgId, String includeSignatures);

	ManoResource onboardedVnfPackagesVnfdIdManifestGet(String vnfdId, String includeSignatures);

	ResponseEntity<Resource> onboardedVnfPackagesVnfdIdPackageContentGet(String vnfdId);

	ManoResource onboardedVnfPackagesVnfdIdVnfdGet(String vnfdId, String contentType, String includeSignatures);

	<U> U onboardedVnfPackagesVnfdIdGet(String vnfdId, Class<U> clazz);

	<U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, Class<U> clazz, String excludeDefaults, Set<String> mandatoryFields, Consumer<U> makeLink);

	ManoResource onboardedGetManifestByVnfd(String vnfdId, @Valid String includeSignature);

	ResponseEntity<Resource> onboardedVnfPackagesVnfdIdArtifactsGet(String vnfdId, final String artifactPath);

	<U> U vnfPackagesVnfPkgVnfdIdGet(String vnfPkgId, Class<U> clazz);

	<U> ResponseEntity<String> searchOnboarded(MultiValueMap<String, String> requestParams, Class<U> clazz, String excludeDefaults, Set<String> mandatoryFields, Consumer<U> makeLinks);

}