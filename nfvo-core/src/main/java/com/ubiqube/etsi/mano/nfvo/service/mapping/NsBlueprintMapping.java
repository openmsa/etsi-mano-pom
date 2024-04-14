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

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstantiate;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import org.mapstruct.Mapping;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NsBlueprintMapping {

	@Mapping(target = "additionalParams", ignore = true)
	@Mapping(target = "audit", ignore = true)
	@Mapping(target = "automaticInvocation", ignore = true)
	@Mapping(target = "cancelMode", ignore = true)
	@Mapping(target = "cancelPending", ignore = true)
	@Mapping(target = "cirConnectionInfo", ignore = true)
	@Mapping(target = "error", ignore = true)
	@Mapping(target = "mciopConnectionInfo", ignore = true)
	@Mapping(target = "operation", ignore = true)
	@Mapping(target = "operationStatus", ignore = true)
	@Mapping(target = "stateEnteredTime", ignore = true)
	@Mapping(target = "tenantId", ignore = true)
	@Mapping(target = "extManagedVirtualLinks", ignore = true)
	@Mapping(target = "grantsRequestId", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "lcmCoordinations", ignore = true)
	@Mapping(target = "nsInstance", ignore = true)
	@Mapping(target = "operationParams", ignore = true)
	@Mapping(target = "parameters", ignore = true)
	@Mapping(target = "rejectedLcmCoordinations", ignore = true)
	@Mapping(target = "tasks", ignore = true)
	@Mapping(target = "vimConnections", ignore = true)
	@Mapping(target = "warnings", ignore = true)
	@Mapping(target = "zoneGroups", ignore = true)
	@Mapping(target = "zones", ignore = true)
	void map(NsInstantiate req, @MappingTarget NsBlueprint nsLcm);

	@Mapping(target = "audit", ignore = true)
	@Mapping(target = "blueprints", ignore = true)
	@Mapping(target = "cirConnectionInfo", ignore = true)
	@Mapping(target = "extManagedVirtualLinks", ignore = true)
	@Mapping(target = "extVirtualLinks", ignore = true)
	@Mapping(target = "extensions", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "instantiatedVnfInfo", ignore = true)
	@Mapping(target = "instantiationState", ignore = true)
	@Mapping(target = "lockedBy", ignore = true)
	@Mapping(target = "mciopRepositoryInfo", ignore = true)
	@Mapping(target = "metadata", ignore = true)
	@Mapping(target = "nsInstance", ignore = true)
	@Mapping(target = "pmJobs", ignore = true)
	@Mapping(target = "tenantId", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "vimConnectionInfo", ignore = true)
	@Mapping(target = "vnfConfigurableProperties", ignore = true)
	@Mapping(target = "vnfInstanceDescription", ignore = true)
	@Mapping(target = "vnfInstanceName", ignore = true)
	@Mapping(target = "additionalAffinityOrAntiAffinityRule", ignore = true)
	@Mapping(target = "blueprint", ignore = true)
	@Mapping(target = "instanceFlavourId", ignore = true)
	@Mapping(target = "monitoringParameter", ignore = true)
	@Mapping(target = "nestedNsInstance", ignore = true)
	@Mapping(target = "nsInstanceDescription", ignore = true)
	@Mapping(target = "nsInstanceName", ignore = true)
	@Mapping(target = "nsScaleStatus", ignore = true)
	@Mapping(target = "nsdInfo", ignore = true)
	@Mapping(target = "priority", ignore = true)
	@Mapping(target = "sapInfo", ignore = true)
	@Mapping(target = "vnfInstance", ignore = true)
	@Mapping(target = "vnfPkgIds", ignore = true)
	@Mapping(target = "vnfSnapshotInfoIds", ignore = true)
	@Mapping(target = "vnffgInfo", ignore = true)
	@Mapping(target = "vnffgs", ignore = true)
	@Mapping(target = "wanConnectionInfo", ignore = true)
	void map(NsInstantiate req, @MappingTarget NsdInstance nsInstanceDb);

}
