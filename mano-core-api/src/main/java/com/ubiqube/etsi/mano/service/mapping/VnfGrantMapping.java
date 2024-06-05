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
package com.ubiqube.etsi.mano.service.mapping;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.ubiqube.etsi.mano.dao.mano.GrantInformationExt;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VnfGrantMapping {

	// Ignore Add
	@Mapping(target = "addResources", ignore = true)
	@Mapping(target = "available", ignore = true)
	@Mapping(target = "computeReservationId", ignore = true)
	@Mapping(target = "dstVnfdId", ignore = true)
	@Mapping(target = "extManagedVirtualLinks", ignore = true)
	@Mapping(target = "extVirtualLinks", ignore = true)
	@Mapping(target = "flavourId", ignore = true)
	@Mapping(target = "instanceLink", ignore = true)
	@Mapping(target = "instantiationLevelId", ignore = true)
	@Mapping(target = "lcmLink", ignore = true)
	@Mapping(target = "mciopRepositoryInfo", ignore = true)
	@Mapping(target = "networkReservationId", ignore = true)
	@Mapping(target = "paasAssets", ignore = true)
	@Mapping(target = "placementConstraints", ignore = true)
	@Mapping(target = "removeResources", ignore = true)
	@Mapping(target = "storageReservationId", ignore = true)
	@Mapping(target = "targetScaleLevelInfo", ignore = true)
	@Mapping(target = "tempResources", ignore = true)
	@Mapping(target = "updateResources", ignore = true)
	@Mapping(target = "vimAssets", ignore = true)
	@Mapping(target = "vimConstraints", ignore = true)
	@Mapping(target = "vnfInstanceId", source = "vnfInstance.id")
	@Mapping(target = "vnfLcmOpOccId", source = "id")
	@Mapping(target = "vnfdId", source = "vnfInstance.vnfdId")
	GrantResponse mapToGrantResponse(VnfBlueprint o);

	default Set<GrantInformationExt> mapVnfTasks(final Set<VnfTask> o) {
		if (o == null) {
			return Set.of();
		}
		final Predicate<? super VimTask> isManoClass = x -> (x.getType() == ResourceTypeEnum.COMPUTE) ||
				(x.getType() == ResourceTypeEnum.LINKPORT) ||
				(x.getType() == ResourceTypeEnum.STORAGE) ||
				(x.getType() == ResourceTypeEnum.VL);
		return o.stream()
				.filter(isManoClass)
				.map(this::map)
				.collect(Collectors.toSet());
	}

	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "mcioConstraints", ignore = true)
	@Mapping(target = "reservationId", source = "vimReservationId")
	@Mapping(target = "resourceDefinitionId", ignore = true)
	@Mapping(target = "resourceId", source = "vimResourceId")
	@Mapping(target = "resourceTemplateId", source = "toscaName")
	@Mapping(target = "secondaryResourceTemplateId", ignore = true)
	@Mapping(target = "snapshotResDef", ignore = true)
	@Mapping(target = "vduId", ignore = true)
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	@Mapping(target = "vimLevelResourceType", ignore = true)
	@Mapping(target = "vnfdId", ignore = true)
	GrantInformationExt map(VnfTask o);

	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "mcioConstraints", ignore = true)
	@Mapping(target = "reservationId", source = "vimReservationId")
	@Mapping(target = "resourceDefinitionId", ignore = true)
	@Mapping(target = "resourceId", source = "vimResourceId")
	@Mapping(target = "resourceTemplateId", source = "toscaName")
	@Mapping(target = "secondaryResourceTemplateId", ignore = true)
	@Mapping(target = "snapshotResDef", ignore = true)
	@Mapping(target = "vduId", ignore = true)
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	@Mapping(target = "vimLevelResourceType", ignore = true)
	@Mapping(target = "vnfdId", ignore = true)
	GrantInformationExt mapToVnfInstantiatedStorage(StorageTask src);

	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "mcioConstraints", ignore = true)
	@Mapping(target = "reservationId", source = "vimReservationId")
	@Mapping(target = "resourceDefinitionId", ignore = true)
	@Mapping(target = "resourceId", source = "vimResourceId")
	@Mapping(target = "resourceTemplateId", source = "toscaName")
	@Mapping(target = "secondaryResourceTemplateId", ignore = true)
	@Mapping(target = "snapshotResDef", ignore = true)
	@Mapping(target = "vduId", ignore = true)
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	@Mapping(target = "vimLevelResourceType", ignore = true)
	@Mapping(target = "vnfdId", ignore = true)
	GrantInformationExt mapToVnfInstantiatedCompute(ComputeTask o);

	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "mcioConstraints", ignore = true)
	@Mapping(target = "reservationId", source = "vimReservationId")
	@Mapping(target = "resourceDefinitionId", ignore = true)
	@Mapping(target = "resourceId", source = "vimResourceId")
	@Mapping(target = "resourceTemplateId", source = "toscaName")
	@Mapping(target = "secondaryResourceTemplateId", ignore = true)
	@Mapping(target = "snapshotResDef", ignore = true)
	@Mapping(target = "vduId", ignore = true)
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	@Mapping(target = "vimLevelResourceType", ignore = true)
	@Mapping(target = "vnfdId", ignore = true)
	GrantInformationExt mapToVnfInstantiatedVirtualLink(NetworkTask o);

	default Set<String> map(final String value) {
		if (null == value) {
			return Set.of();
		}
		return Set.of(value);
	}
}
