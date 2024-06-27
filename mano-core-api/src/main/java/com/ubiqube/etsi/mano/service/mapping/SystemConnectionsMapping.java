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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.web.bind.annotation.PostMapping;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SystemConnectionsMapping {
	@Mapping(target = "moduleName", ignore = true)
	@Mapping(target = "interfaceInfo", ignore = true)
	@Mapping(target = "accessInfo", ignore = true)
	SystemConnections map(VimConnectionInformation o);

	@PostMapping
	default void map(VimConnectionInformation o, @MappingTarget SystemConnections sc) {
		if (null == o)
			return;
		if ("ETSINFV.OPENSTACK_KEYSTONE.V_3".equals(o.getVimType())) {
			sc.setInterfaceInfo(mapToOpenstackV3InterfaceInfo(o.getInterfaceInfo()));
			sc.setAccessInfo(mapToOpenstackV3Ai(o.getAccessInfo()));

		} else if ("UBINFV.CISM.V_1".equals(o.getVimType())) {
			sc.setInterfaceInfo(mapToK8sInterfaceInfo(o.getInterfaceInfo()));
			sc.setAccessInfo(mapToK8sAi(o.getAccessInfo()));
		} else if ("PAAS".equals(o.getVimType())) {
			// XXX: This need works works.
		}
	}

	@Mapping(target = "id", ignore = true)
	AccessInfo mapToOpenstackV3Ai(AccessInfo accessInfo);

	@Mapping(target = "id", ignore = true)
	InterfaceInfo mapToOpenstackV3InterfaceInfo(InterfaceInfo interfaceInfo);

	@Mapping(target = "id", ignore = true)
	AccessInfo mapToK8sAi(AccessInfo accessInfo);

	@Mapping(target = "id", ignore = true)
	InterfaceInfo mapToK8sInterfaceInfo(InterfaceInfo interfaceInfo);

}
