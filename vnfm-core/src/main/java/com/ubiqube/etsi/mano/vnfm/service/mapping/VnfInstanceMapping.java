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
package com.ubiqube.etsi.mano.vnfm.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VnfInstanceMapping {

	@Mapping(target = "blueprints", ignore = true)
	@Mapping(target = "cirConnectionInfo", ignore = true)
	@Mapping(target = "extManagedVirtualLinks", ignore = true)
	@Mapping(target = "extVirtualLinks", ignore = true)
	@Mapping(target = "extensions", ignore = true)
	@Mapping(target = "instantiatedVnfInfo", ignore = true)
	@Mapping(target = "instantiationState", ignore = true)
	@Mapping(target = "lockedBy", ignore = true)
	@Mapping(target = "mciopRepositoryInfo", ignore = true)
	@Mapping(target = "metadata", ignore = true)
	@Mapping(target = "pmJobs", ignore = true)
	@Mapping(target = "tenantId", ignore = true)
	@Mapping(target = "vimConnectionInfo", ignore = true)
	@Mapping(target = "vnfConfigurableProperties", ignore = true)
	@Mapping(target = "vnfInstanceDescription", ignore = true)
	@Mapping(target = "vnfInstanceName", ignore = true)
	@Mapping(target = "extCpInfo", ignore = true)
	@Mapping(target = "versionDependency", ignore = true)
	@Mapping(target = "vnfPkg", ignore = true)
	@Mapping(target = "nsInstance", ignore = true)
	void map(VnfPackage src, @MappingTarget VnfInstance dst);
}
