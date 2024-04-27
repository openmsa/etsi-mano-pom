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
package com.ubiqube.etsi.mano.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;
import com.ubiqube.etsi.mano.orchestrator.ExecutionGraph;
import com.ubiqube.etsi.mano.orchestrator.dump.ExecutionResult;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.mapping.VnfPackageDtoMapping;
import com.ubiqube.etsi.mano.vnfm.dto.VnfPackageDto;
import com.ubiqube.etsi.mano.vnfm.service.event.VnfmActions;

@RestController
@RequestMapping("/vnfm-admin")
public class VnfmAdminController {
	private final VnfPackageJpa vnfPackageRepositoryJpa;
	private final VnfPackageRepository vnfPackageRepository;
	private final EventManager eventManager;
	private final VnfPackageDtoMapping mapper;
	private final VnfmActions vnfmActions;
	private final VnfInstanceJpa vnfInstanceJpa;

	public VnfmAdminController(final VnfPackageJpa vnfPackageRepositoryJpa, final VnfPackageRepository vnfPackageRepository, final EventManager eventManager,
			final VnfPackageDtoMapping mapper, VnfmActions vnfmActions, VnfInstanceJpa vnfInstanceJpa) {
		this.vnfPackageRepositoryJpa = vnfPackageRepositoryJpa;
		this.vnfPackageRepository = vnfPackageRepository;
		this.eventManager = eventManager;
		this.mapper = mapper;
		this.vnfmActions = vnfmActions;
		this.vnfInstanceJpa = vnfInstanceJpa;
	}

	@DeleteMapping("/vnf-package/{vnfPkgId}")
	public ResponseEntity<Void> deleteVnfPackage(@PathVariable("vnfPkgId") final String vnfPkgId) {
		vnfPackageRepositoryJpa.deleteById(UUID.fromString(vnfPkgId));
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/vnf-package")
	public ResponseEntity<List<VnfPackageDto>> findAll() {
		final Iterable<VnfPackage> all = vnfPackageRepositoryJpa.findAll();
		final List<VnfPackage> ret = new ArrayList<>();
		all.forEach(ret::add);
		return ResponseEntity.ok(ret.stream().map(mapper::map).toList());
	}

	@PostMapping("/vnf-package/onboard")
	public ResponseEntity<VnfPackageDto> onboardVnfPackage(@RequestParam("file") final MultipartFile file) throws IOException {
		final VnfPackage entity = new VnfPackage();
		entity.setOnboardingState(OnboardingStateType.CREATED);
		entity.setOperationalState(PackageOperationalState.DISABLED);
		entity.setUsageState(UsageStateEnum.NOT_IN_USE);
		final VnfPackage ne = vnfPackageRepository.save(entity);
		vnfPackageRepository.storeBinary(ne.getId(), Constants.REPOSITORY_FILENAME_PACKAGE, file.getInputStream());
		eventManager.sendActionVnfm(ActionType.VNF_PKG_ONBOARD_DOWNLOAD_INSTANTIATE, ne.getId(), Map.of());
		return ResponseEntity.ok(mapper.map(ne));
	}

	@GetMapping("/vnf-lcm/graph/{id}")
	public ResponseEntity<ExecutionResult> getVnfLcmGraph(@PathVariable("id") UUID id) {
		final Optional<VnfInstance> inst = vnfInstanceJpa.findById(id);
		ExecutionGraph res = vnfmActions.getExecutionGraph(inst.orElseThrow());
		return ResponseEntity.ok(res.dump());
	}

}
