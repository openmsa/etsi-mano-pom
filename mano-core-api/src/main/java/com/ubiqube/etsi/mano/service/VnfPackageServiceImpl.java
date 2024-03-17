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
package com.ubiqube.etsi.mano.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;

import jakarta.annotation.Nonnull;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfPackageServiceImpl implements VnfPackageService {
	private final VnfPackageRepository vnfPackageRepository;

	private final VnfPackageJpa vnfPackageJpa;

	private final VnfInstanceJpa vnfInstanceJpa;

	public VnfPackageServiceImpl(final VnfPackageJpa vnfPackageJpa, final VnfInstanceJpa vnfInstanceJpa,
			final VnfPackageRepository vnfPackageRepository) {
		this.vnfPackageJpa = vnfPackageJpa;
		this.vnfInstanceJpa = vnfInstanceJpa;
		this.vnfPackageRepository = vnfPackageRepository;
	}

	@Override
	public @Nonnull VnfPackage findById(final UUID vnfPkgId) {
		final VnfPackage ret = vnfPackageJpa.findById(vnfPkgId).orElseThrow(() -> new NotFoundException("VNF Package: " + vnfPkgId + " not found."));
		final int i = vnfInstanceJpa.countByVnfPkgId(vnfPkgId);
		ret.setUsageState(i == 0 ? UsageStateEnum.NOT_IN_USE : UsageStateEnum.IN_USE);
		return ret;
	}

	@Override
	public VnfPackage save(final @Nonnull VnfPackage vnfPackage) {
		return vnfPackageRepository.save(vnfPackage);
	}

	@Override
	public Optional<VnfPackage> findByVnfdIdOpt(final String descriptorId) {
		return vnfPackageJpa.findByVnfdId(descriptorId);
	}

	@Override
	public Optional<VnfPackage> findByVnfdIdFlavorIdVnfdVersion(final String descriptorId, final String flavorId, final String versionId) {
		return vnfPackageJpa.findByVnfdIdAndFlavorIdAndVnfdVersion(descriptorId, flavorId, versionId);
	}

	@Override
	public Optional<VnfPackage> findByVnfdIdAndSoftwareVersion(final String name, final String version) {
		return vnfPackageJpa.findByVnfdIdAndVnfSoftwareVersion(name, version);
	}

	@Override
	public VnfPackage findByVnfdId(final String id) {
		return vnfPackageJpa.findByVnfdIdAndOnboardingState(id, OnboardingStateType.ONBOARDED).orElseThrow(() -> new NotFoundException("Could not find vnfdId: " + id + ", or it is not ONBOARDED."));
	}

	@Override
	public void delete(final UUID id) {
		vnfPackageJpa.deleteById(id);
	}

}
