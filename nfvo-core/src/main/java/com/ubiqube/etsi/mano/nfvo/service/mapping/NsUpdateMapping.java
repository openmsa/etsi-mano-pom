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
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeExtVnfConnectivityData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeVnfFlavourData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ExtManagedVirtualLinkData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ExtVirtualLinkData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.InstantiateVnfData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.NfpData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.OperateVnfData;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NsUpdateMapping {

	@Mapping(target = "additionalParams", source = "additionalParam")
	VnfOperateRequest map(OperateVnfData operateData);

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
	@Mapping(target = "grantsRequestId", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "lcmCoordinations", ignore = true)
	@Mapping(target = "nsFlavourId", ignore = true)
	@Mapping(target = "nsInstance", ignore = true)
	@Mapping(target = "nsInstantiationLevelId", ignore = true)
	@Mapping(target = "operationParams", ignore = true)
	@Mapping(target = "parameters", ignore = true)
	@Mapping(target = "rejectedLcmCoordinations", ignore = true)
	@Mapping(target = "tasks", ignore = true)
	@Mapping(target = "vimConnections", ignore = true)
	@Mapping(target = "warnings", ignore = true)
	@Mapping(target = "zoneGroups", ignore = true)
	@Mapping(target = "zones", ignore = true)
	NsBlueprint map(InstantiateVnfData o);

	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "extManagedMultisiteVirtualLinkId", ignore = true)
	@Mapping(target = "grants", ignore = true)
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	@Mapping(target = "vimLevelResourceType", ignore = true)
	@Mapping(target = "vnfInstance", ignore = true)
	@Mapping(target = "vnfLinkPorts", ignore = true)
	@Mapping(target = "vnfNetAttDefResource", ignore = true)
	@Mapping(target = "vnfdId", ignore = true)
	ExtManagedVirtualLinkDataEntity map(ExtManagedVirtualLinkData o);

	ChangeVnfFlavourData map(ChangeVnfFlavourData o);

	@Mapping(target = "audit", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "vimConnectionInfo", ignore = true)
	ChangeExtVnfConnRequest map(ChangeExtVnfConnectivityData o);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "instances", ignore = true)
	@Mapping(target = "toscaName", ignore = true)
	NfpDescriptor map(NfpData o);

	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "currentVnfExtCpData", ignore = true)
	@Mapping(target = "extCps", ignore = true)
	@Mapping(target = "extLinkPorts", ignore = true)
	@Mapping(target = "extNetAttDefResource", ignore = true)
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	@Mapping(target = "vimLevelResourceType", ignore = true)
	@Mapping(target = "vnfInstance", ignore = true)
	ExtVirtualLinkDataEntity map(ExtVirtualLinkData o);

}
