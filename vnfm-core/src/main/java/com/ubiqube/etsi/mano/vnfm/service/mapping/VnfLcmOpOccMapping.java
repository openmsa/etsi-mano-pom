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
import org.mapstruct.ValueMapping;

import com.ubiqube.etsi.mano.dao.mano.InstantiationStatusType;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedCompute;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedStorage;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedVirtualLink;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VnfLcmOpOccMapping {
	@Mapping(target = "aliasName", ignore = true)
	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "endTime", ignore = true)
	@Mapping(target = "instantiationLevel", ignore = true)
	@Mapping(target = "manoResourceId", ignore = true)
	@Mapping(target = "metadata", ignore = true)
	@Mapping(target = "removedInstantiated", ignore = true)
	@Mapping(target = "reservationId", ignore = true)
	@Mapping(target = "resourceDefinitionId", ignore = true)
	@Mapping(target = "resourceId", source = "vimResourceId")
	@Mapping(target = "startTime", ignore = true)
	@Mapping(target = "vduId", ignore = true)
	@Mapping(target = "vimConnectionInformation.vimId", source = "vimConnectionId")
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	@Mapping(target = "vimLevelResourceType", ignore = true)
	@Mapping(target = "vnfLcmOpOccs", ignore = true)
	@Mapping(target = "vnfdId", ignore = true)
	@Mapping(target = "virtualStorageDescId", source = "toscaName")
	VnfInstantiatedStorage mapToVnfInstantiatedStorage(StorageTask src);

	@ValueMapping(source = "REMOVED", target = MappingConstants.THROW_EXCEPTION)
	InstantiationStatusType map(PlanStatusType o);

	@Mapping(target = "aliasName", ignore = true)
	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "endTime", ignore = true)
	@Mapping(target = "instantiationLevel", ignore = true)
	@Mapping(target = "manoResourceId", ignore = true)
	@Mapping(target = "metadata", ignore = true)
	@Mapping(target = "removedInstantiated", ignore = true)
	@Mapping(target = "reservationId", ignore = true)
	@Mapping(target = "resourceDefinitionId", ignore = true)
	@Mapping(target = "resourceId", source = "vimResourceId")
	@Mapping(target = "startTime", ignore = true)
	@Mapping(target = "vduId", ignore = true)
	@Mapping(target = "vimConnectionInformation.vimId", source = "vimConnectionId")
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	@Mapping(target = "vimLevelResourceType", ignore = true)
	@Mapping(target = "vnfLcmOpOccs", ignore = true)
	@Mapping(target = "vnfdId", ignore = true)
	@Mapping(target = "addedStorageResourceIds", ignore = true)
	@Mapping(target = "affectedVnfcCpIds", ignore = true)
	@Mapping(target = "removedStorageResourceIds", ignore = true)
	@Mapping(target = "storageResourceIds", ignore = true)
	@Mapping(target = "vnfcCpInfo", ignore = true)
	VnfInstantiatedCompute mapToVnfInstantiatedCompute(ComputeTask o);

	@Mapping(target = "virtualLinkDescId", source = "vnfVl.toscaName")
	@Mapping(target = "aliasName", ignore = true)
	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "endTime", ignore = true)
	@Mapping(target = "manoResourceId", ignore = true)
	@Mapping(target = "metadata", ignore = true)
	@Mapping(target = "removedInstantiated", ignore = true)
	@Mapping(target = "reservationId", source = "vimResourceId")
	@Mapping(target = "resourceDefinitionId", ignore = true)
	@Mapping(target = "resourceId", ignore = true)
	@Mapping(target = "startTime", ignore = true)
	@Mapping(target = "vduId", ignore = true)
	@Mapping(target = "vimConnectionInformation.vimId", source = "vimConnectionId")
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	@Mapping(target = "vimLevelResourceType", ignore = true)
	@Mapping(target = "vnfLcmOpOccs", ignore = true)
	@Mapping(target = "vnfdId", ignore = true)
	@Mapping(target = "instantiationLevel", ignore = true)
	@Mapping(target = "vnfLinkPortIds", ignore = true)
	VnfInstantiatedVirtualLink mapToVnfInstantiatedVirtualLink(NetworkTask o);
}
