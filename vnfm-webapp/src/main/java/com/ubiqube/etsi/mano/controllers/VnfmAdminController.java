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
package com.ubiqube.etsi.mano.controllers;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.PackageUsageState;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;

@RestController
@RequestMapping("/vnfm-admin")
public class VnfmAdminController {
	private final VnfPackageJpa vnfPackageRepositoryJpa;
	private final VnfPackageRepository vnfPackageRepository;
	private final EventManager eventManager;

	public VnfmAdminController(final VnfPackageJpa vnfPackageRepositoryJpa, final VnfPackageRepository vnfPackageRepository, final EventManager eventManager) {
		super();
		this.vnfPackageRepositoryJpa = vnfPackageRepositoryJpa;
		this.vnfPackageRepository = vnfPackageRepository;
		this.eventManager = eventManager;
	}

	@DeleteMapping("/vnf-package/{vnfPkgId}")
	public ResponseEntity<Void> deleteVnfPackage(@PathVariable("vnfPkgId") final String vnfPkgId) {
		vnfPackageRepositoryJpa.deleteById(UUID.fromString(vnfPkgId));
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/vnf-package/onboard")
	public ResponseEntity<VnfPackage> onboardVnfPackage(@RequestParam("file") final MultipartFile file) throws IOException {
		final VnfPackage entity = new VnfPackage();
		entity.setOnboardingState(OnboardingStateType.CREATED);
		entity.setOperationalState(PackageOperationalState.DISABLED);
		entity.setUsageState(PackageUsageState.NOT_IN_USE);
		final VnfPackage ne = vnfPackageRepository.save(entity);
		vnfPackageRepository.storeBinary(ne.getId(), Constants.REPOSITORY_FILENAME_PACKAGE, file.getInputStream());
		eventManager.sendActionVnfm(ActionType.VNF_PKG_ONBOARD_DOWNLOAD_INSTANTIATE, ne.getId(), Map.of());
		return ResponseEntity.ok(ne);
	}
}
