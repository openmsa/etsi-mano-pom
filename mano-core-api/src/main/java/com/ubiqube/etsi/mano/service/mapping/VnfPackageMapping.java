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
package com.ubiqube.etsi.mano.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.service.pkg.bean.ProviderData;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VnfPackageMapping {

	@Mapping(target = "additionalArtifacts", ignore = true)
	@Mapping(target = "affinityRules", ignore = true)
	@Mapping(target = "audit", ignore = true)
	@Mapping(target = "checksum", ignore = true)
	@Mapping(target = "compatibleSpecificationVersions", ignore = true)
	@Mapping(target = "defaultInstantiationLevel", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "mciopId", ignore = true)
	@Mapping(target = "mciops", ignore = true)
	@Mapping(target = "nfvoId", ignore = true)
	@Mapping(target = "nonManoArtifactSetId", ignore = true)
	@Mapping(target = "nsInstance", ignore = true)
	@Mapping(target = "nsdPackages", ignore = true)
	@Mapping(target = "onboardingFailureDetails", ignore = true)
	@Mapping(target = "onboardingState", ignore = true)
	@Mapping(target = "operationalState", ignore = true)
	@Mapping(target = "osContainer", ignore = true)
	@Mapping(target = "osContainerDeployableUnits", ignore = true)
	@Mapping(target = "osContainerDesc", ignore = true)
	@Mapping(target = "overloadedAttribute", ignore = true)
	@Mapping(target = "overwriteDescId", ignore = true)
	@Mapping(target = "packageProvider", ignore = true)
	@Mapping(target = "packageSecurityOption", ignore = true)
	@Mapping(target = "repositories", ignore = true)
	@Mapping(target = "securityGroups", ignore = true)
	@Mapping(target = "server", ignore = true)
	@Mapping(target = "signingCertificate", ignore = true)
	@Mapping(target = "softwareImages", ignore = true)
	@Mapping(target = "uploadUriParameters", ignore = true)
	@Mapping(target = "usageState", ignore = true)
	@Mapping(target = "userDefinedData", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "vimCapabilities", ignore = true)
	@Mapping(target = "virtualCp", ignore = true)
	@Mapping(target = "virtualLinks", ignore = true)
	@Mapping(target = "vnfCompute", ignore = true)
	@Mapping(target = "vnfExtCp", ignore = true)
	@Mapping(target = "vnfIndicator", ignore = true)
	@Mapping(target = "vnfInstantiationLevels", ignore = true)
	@Mapping(target = "vnfLinkPort", ignore = true)
	@Mapping(target = "vnfStorage", ignore = true)
	@Mapping(target = "vnfVl", ignore = true)
	@Mapping(target = "vnfdContentType", ignore = true)
	@Mapping(target = "vnfdExtInvariantId", ignore = true)
	@Mapping(target = "vnfdId", source = "descriptorId")
	@Mapping(target = "vnfmInfo281", ignore = true)
	VnfPackage map(ProviderData o);

	@Mapping(target = "additionalArtifacts", ignore = true)
	@Mapping(target = "affinityRules", ignore = true)
	@Mapping(target = "audit", ignore = true)
	@Mapping(target = "checksum", ignore = true)
	@Mapping(target = "compatibleSpecificationVersions", ignore = true)
	@Mapping(target = "defaultInstantiationLevel", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "mciopId", ignore = true)
	@Mapping(target = "mciops", ignore = true)
	@Mapping(target = "nfvoId", ignore = true)
	@Mapping(target = "nonManoArtifactSetId", ignore = true)
	@Mapping(target = "nsInstance", ignore = true)
	@Mapping(target = "nsdPackages", ignore = true)
	@Mapping(target = "onboardingFailureDetails", ignore = true)
	@Mapping(target = "onboardingState", ignore = true)
	@Mapping(target = "operationalState", ignore = true)
	@Mapping(target = "osContainer", ignore = true)
	@Mapping(target = "osContainerDeployableUnits", ignore = true)
	@Mapping(target = "osContainerDesc", ignore = true)
	@Mapping(target = "overloadedAttribute", ignore = true)
	@Mapping(target = "overwriteDescId", ignore = true)
	@Mapping(target = "packageProvider", ignore = true)
	@Mapping(target = "packageSecurityOption", ignore = true)
	@Mapping(target = "repositories", ignore = true)
	@Mapping(target = "securityGroups", ignore = true)
	@Mapping(target = "server", ignore = true)
	@Mapping(target = "signingCertificate", ignore = true)
	@Mapping(target = "softwareImages", ignore = true)
	@Mapping(target = "uploadUriParameters", ignore = true)
	@Mapping(target = "usageState", ignore = true)
	@Mapping(target = "userDefinedData", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "vimCapabilities", ignore = true)
	@Mapping(target = "virtualCp", ignore = true)
	@Mapping(target = "virtualLinks", ignore = true)
	@Mapping(target = "vnfCompute", ignore = true)
	@Mapping(target = "vnfExtCp", ignore = true)
	@Mapping(target = "vnfIndicator", ignore = true)
	@Mapping(target = "vnfInstantiationLevels", ignore = true)
	@Mapping(target = "vnfLinkPort", ignore = true)
	@Mapping(target = "vnfStorage", ignore = true)
	@Mapping(target = "vnfVl", ignore = true)
	@Mapping(target = "vnfdContentType", ignore = true)
	@Mapping(target = "vnfdExtInvariantId", ignore = true)
	@Mapping(target = "vnfdId", source = "descriptorId")
	@Mapping(target = "vnfmInfo281", ignore = true)
	void map(ProviderData o, @MappingTarget VnfPackage dst);

	@Mapping(target = "aspectId", source = "name")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "vnfdId", ignore = true)
	com.ubiqube.etsi.mano.dao.mano.ScaleInfo map(com.ubiqube.etsi.mano.service.pkg.bean.ScaleInfo o);
}
