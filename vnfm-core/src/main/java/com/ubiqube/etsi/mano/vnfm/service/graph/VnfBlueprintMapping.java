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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.model.VnfInstantiate;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VnfBlueprintMapping {

	@Mapping(target = "audit", ignore = true)
	@Mapping(target = "automaticInvocation", ignore = true)
	@Mapping(target = "cancelMode", ignore = true)
	@Mapping(target = "cancelPending", ignore = true)
	@Mapping(target = "cirConnectionInfo", ignore = true)
	@Mapping(target = "error", ignore = true)
	@Mapping(target = "mciopConnectionInfo", ignore = true)
	@Mapping(target = "operation", ignore = true)
	@Mapping(target = "operationStatus", ignore = true)
	@Mapping(target = "startTime", ignore = true)
	@Mapping(target = "stateEnteredTime", ignore = true)
	@Mapping(target = "tenantId", ignore = true)
	@Mapping(target = "affectedVipCps", ignore = true)
	@Mapping(target = "changeExtVnfConnRequest", ignore = true)
	@Mapping(target = "changedExtConnectivity", ignore = true)
	@Mapping(target = "changedInfo", ignore = true)
	@Mapping(target = "grantId", ignore = true)
	@Mapping(target = "grantsRequestId", ignore = true)
	@Mapping(target = "healCause", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "isAutomaticInvocation", ignore = true)
	@Mapping(target = "isCancelPending", ignore = true)
	@Mapping(target = "lcmCoordinations", ignore = true)
	@Mapping(target = "modificationsTriggeredByVnfPkgChange", ignore = true)
	@Mapping(target = "operateChanges", ignore = true)
	@Mapping(target = "operationParams", ignore = true)
	@Mapping(target = "parameters", ignore = true)
	@Mapping(target = "rejectedLcmCoordinations", ignore = true)
	@Mapping(target = "resourceChanges", ignore = true)
	@Mapping(target = "tasks", ignore = true)
	@Mapping(target = "vimConnections", source = "vimConnectionInfo")
	@Mapping(target = "vnfInstance", ignore = true)
	@Mapping(target = "vnfSnapshotInfoId", ignore = true)
	@Mapping(target = "warnings", ignore = true)
	@Mapping(target = "zoneGroups", ignore = true)
	@Mapping(target = "zones", ignore = true)
	void map(VnfInstantiate src, @MappingTarget VnfBlueprint dst);
}
