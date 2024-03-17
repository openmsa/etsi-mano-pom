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

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;

public interface VnfPackageService {

	VnfPackage findById(final UUID vnfPkgId);

	VnfPackage save(final VnfPackage vnfPackage);

	Optional<VnfPackage> findByVnfdIdOpt(final String descriptorId);

	Optional<VnfPackage> findByVnfdIdFlavorIdVnfdVersion(final String descriptorId, final String flavorId, final String versionId);

	Optional<VnfPackage> findByVnfdIdAndSoftwareVersion(final String name, final String version);

	VnfPackage findByVnfdId(final String id);

	void delete(UUID id);

}
