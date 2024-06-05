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
package com.ubiqube.etsi.mano.vnfm.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.ubiqube.etsi.mano.dao.mano.VimResource;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfcResourceInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VnfcResourceInfoEntityMapping {
	@Mapping(target = "certificateContentId", ignore = true)
	@Mapping(target = "computeResource", source = ".")
	@Mapping(target = "metadata", ignore = true)
	@Mapping(target = "reservationId", ignore = true)
	@Mapping(target = "storageResourceIds", ignore = true)
	@Mapping(target = "vduId", ignore = true)
	@Mapping(target = "vnfcCpInfo", ignore = true)
	@Mapping(target = "vnfdId", ignore = true)
	@Mapping(target = "zoneId", ignore = true)
	VnfcResourceInfoEntity map(VnfLiveInstance o);

	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "resourceProviderId", ignore = true)
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	@Mapping(target = "vimLevelResourceType", ignore = true)
	VimResource mapVimResource(VnfLiveInstance o);

	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "resourceId", ignore = true)
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	@Mapping(target = "vimLevelResourceType", ignore = true)
	VimResource map(VnfTask task);
}
