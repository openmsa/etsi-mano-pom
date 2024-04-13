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
package com.ubiqube.etsi.mano.nfvo.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.service.pkg.bean.NsInformations;
import org.mapstruct.Mapping;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NsdPackageMapping {

	@Mapping(target = "archiveSecurityOption", ignore = true)
	@Mapping(target = "artifacts", ignore = true)
	@Mapping(target = "audit", ignore = true)
	@Mapping(target = "autoHealEnabled", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "nestedNsdInfoIds", ignore = true)
	@Mapping(target = "nsInstance", ignore = true)
	@Mapping(target = "nsSaps", ignore = true)
	@Mapping(target = "nsVirtualLinks", ignore = true)
	@Mapping(target = "nsVnfIndicator", ignore = true)
	@Mapping(target = "nsdExtInvariantId", ignore = true)
	@Mapping(target = "nsdOnboardingState", ignore = true)
	@Mapping(target = "nsdOperationalState", ignore = true)
	@Mapping(target = "nsdUsageState", ignore = true)
	@Mapping(target = "onboardingFailureDetails", ignore = true)
	@Mapping(target = "overwriteDescId", ignore = true)
	@Mapping(target = "pnfdInfoIds", ignore = true)
	@Mapping(target = "serviceAvailabilityLevel", ignore = true)
	@Mapping(target = "signingCertificate", ignore = true)
	@Mapping(target = "userDefinedData", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "vnfPkgIds", ignore = true)
	@Mapping(target = "vnffgs", ignore = true)
	void map(NsInformations src, @MappingTarget NsdPackage dst);
}
