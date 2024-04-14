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
import org.mapstruct.MappingTarget;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.alarm.ResourceHandle;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstanceDto;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.VnfInstanceDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NsInstanceDtoMapping {
	@Mapping(target = "flavourId", source = "flavorId")
	@Mapping(target = "nsInstanceDescription", ignore = true)
	@Mapping(target = "nsInstanceName", ignore = true)
	@Mapping(target = "nsScaleStatus", ignore = true)
	@Mapping(target = "nsState", ignore = true)
	@Mapping(target = "nsdInfoId", source = "id")
	@Mapping(target = "virtualLinkInfo", ignore = true)
	@Mapping(target = "vnfInstance", ignore = true)
	@Mapping(target = "vnffgInfo", ignore = true)
	NsInstanceDto map(NsdPackage o);

	@Mapping(target = "flavourId", ignore = true)
	@Mapping(target = "nsState", ignore = true)
	@Mapping(target = "nsdId", ignore = true)
	@Mapping(target = "nsdInfoId", ignore = true)
	@Mapping(target = "virtualLinkInfo", ignore = true)
	void map(NsdInstance ret, @MappingTarget NsInstanceDto dto);

	@Mapping(target = "vimId", ignore = true)
	@Mapping(target = "vnfPkgId", source = "vnfPkg.id")
	VnfInstanceDto map(VnfInstance x);

	@Mapping(target = "containerNamespace", ignore = true)
	@Mapping(target = "vimLevelAdditionalResourceInfo", ignore = true)
	ResourceHandle map(NsLiveInstance x);

}
