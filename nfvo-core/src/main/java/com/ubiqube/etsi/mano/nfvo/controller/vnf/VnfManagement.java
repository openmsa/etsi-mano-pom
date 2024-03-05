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

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.controller.MetaStreamResource;
import com.ubiqube.etsi.mano.controller.vnf.VnfPackageManagement;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.grammar.BooleanExpression;
import com.ubiqube.etsi.mano.grammar.GrammarLabel;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarOperandType;
import com.ubiqube.etsi.mano.grammar.GrammarValue;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.service.VnfPackageService;

import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;

/**
 * This implementation cover VNFO + NFVM & VNFO only.
 *
 * @author ovi@ubiqube.com
 *
 */
@Service
public class VnfManagement implements VnfPackageManagement {
	private static final Logger LOG = LoggerFactory.getLogger(VnfManagement.class);

	private final VnfPackageRepository vnfPackageRepository;
	private final VnfPackageService vnfPackageService;
	private final MapperFacade mapper;
	private final SearchableService searchableService;

	public VnfManagement(final VnfPackageRepository vnfPackageRepository, final MapperFacade mapper, final VnfPackageService vnfPackageService, final SearchableService searchableService) {
		this.vnfPackageRepository = vnfPackageRepository;
		this.mapper = mapper;
		this.vnfPackageService = vnfPackageService;
		this.searchableService = searchableService;
		LOG.info("Starting VNF Package Management For NFVO+VNFM or NFVO Only Management.");
	}

	@Override
	public <U> U vnfPackagesVnfPkgIdGet(final UUID vnfPkgId, final Class<U> u) {
		final VnfPackage vnfPackage = vnfPackageRepository.get(vnfPkgId);
		return mapper.map(vnfPackage, u);
	}

	@Override
	public VnfPackage vnfPackagesVnfPkgIdGet(final UUID vnfPkgId) {
		return vnfPackageRepository.get(vnfPkgId);
	}

	@Override
	public List<VnfPackage> vnfPackagesGet(final String filter) {
		return vnfPackageRepository.query(filter);
	}

	/**
	 * We should not reply a Response here.
	 *
	 * @param vnfPkgId
	 * @param artifactPath
	 * @return A Resource.
	 */
	@Override
	public ResponseEntity<Resource> vnfPackagesVnfPkgIdArtifactsArtifactPathGet(final UUID vnfPkgId, final String artifactPath) {
		vnfPackageRepository.get(vnfPkgId);
		final ManoResource content = vnfPackageRepository.getBinary(vnfPkgId, "vnfd");
		final ZipInputStream zis = new ZipInputStream(content.getInputStream());
		ZipEntry entry = null;
		try {
			while ((entry = zis.getNextEntry()) != null) {
				if (entry.isDirectory()) {
					continue;
				}
				if (entry.getName().equals(artifactPath)) {
					final MetaStreamResource res = new MetaStreamResource(content);
					return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
							.contentType(MediaTypeFactory.getMediaType(res).orElse(MediaType.APPLICATION_OCTET_STREAM))
							.body(res);
				}
			}
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		throw new NotFoundException(new StringBuilder("VNF package artifact not found for vnfPack with id: ")
				.append(vnfPkgId).append(" artifactPath: ").append(artifactPath).toString());
	}

	@Override
	public ManoResource vnfPackagesVnfPkgIdVnfdGet(final UUID vnfPkgId, final String contentType, final boolean includeSignature) {
		return vnfPackageRepository.getBinary(vnfPkgId, Constants.REPOSITORY_FILENAME_VNFD);
	}

	@Override
	public ResponseEntity<Resource> vnfPackagesVnfPkgIdPackageContentGet(final UUID vnfPkgId) {
		vnfPackageRepository.get(vnfPkgId);
		final ManoResource content = vnfPackageRepository.getBinary(vnfPkgId, Constants.REPOSITORY_FILENAME_PACKAGE);
		final MetaStreamResource res = new MetaStreamResource(content);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypeFactory
				.getMediaType(res).orElse(MediaType.APPLICATION_OCTET_STREAM))
				.body(res);
	}

	@Override
	public ManoResource getPackageManifest(final UUID vnfPkgId, @Valid final String includeSignatures) {
		final VnfPackage vnfPackage = vnfPackageService.findById(vnfPkgId);
		return getPackageManifest(vnfPackage, includeSignatures);
	}

	@Override
	public ManoResource onboardedVnfPackagesVnfdIdManifestGet(final UUID vnfdId, @Valid final String includeSignatures) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(vnfdId);
		return getPackageManifest(vnfPackage, includeSignatures);
	}

	@Override
	public ResponseEntity<Resource> onboardedVnfPackagesVnfdIdPackageContentGet(final UUID vnfdId) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(vnfdId);
		return vnfPackagesVnfPkgIdPackageContentGet(Objects.requireNonNull(vnfPackage.getId()));
	}

	@Override
	public ManoResource onboardedVnfPackagesVnfdIdVnfdGet(final UUID vnfdId, final String contentType, @Valid final String includeSignatures) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(vnfdId);
		return vnfPackagesVnfPkgIdVnfdGet(Objects.requireNonNull(vnfPackage.getId()), contentType, null != includeSignatures);
	}

	@Override
	public <U> U onboardedVnfPackagesVnfdIdGet(final UUID vnfdId, final Class<U> clazz) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(vnfdId);
		return mapper.map(vnfPackage, clazz);
	}

	@Override
	public ManoResource onboardedGetManifestByVnfd(final UUID vnfdId, @Valid final String includeSignature) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(vnfdId);
		return getPackageManifest(vnfPackage, includeSignature);
	}

	private ManoResource getPackageManifest(final VnfPackage vnfPackage, @Valid final String includeSignature) {
		return vnfPackageRepository.getBinary(vnfPackage.getId(), Constants.REPOSITORY_FILENAME_MANIFEST);
	}

	@Override
	public ResponseEntity<Resource> onboardedVnfPackagesVnfdIdArtifactsGet(final UUID vnfdId, final String artifactPath) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(vnfdId);
		return vnfPackagesVnfPkgIdArtifactsArtifactPathGet(Objects.requireNonNull(vnfPackage.getId()), artifactPath);
	}

	@Override
	public <U> U vnfPackagesVnfPkgVnfdIdGet(final UUID vnfdId, final Class<U> clazz) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(vnfdId);
		return mapper.map(vnfPackage, clazz);
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink) {
		return searchableService.search(VnfPackage.class, requestParams, clazz, excludeDefaults, mandatoryFields, makeLink, List.of());
	}

	@Override
	public <U> ResponseEntity<String> searchOnboarded(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLinks) {
		final GrammarNode onBoardedNode = new BooleanExpression(new GrammarLabel("onboardingState"), GrammarOperandType.EQ, new GrammarValue("ONBOARDED"));
		return searchableService.search(VnfPackage.class, requestParams, clazz, excludeDefaults, mandatoryFields, makeLinks, List.of(onBoardedNode));

	}
}
