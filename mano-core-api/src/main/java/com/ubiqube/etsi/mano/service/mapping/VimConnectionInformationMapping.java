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

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.ubiqube.etsi.mano.dao.mano.Connection;
import com.ubiqube.etsi.mano.dao.mano.ai.KubernetesV1Auth;
import com.ubiqube.etsi.mano.dao.mano.dto.VimConnectionInfoDto;
import com.ubiqube.etsi.mano.dao.mano.ii.K8sInterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VimConnectionInformationMapping {
	@Mapping(target = "audit", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "tenantId", ignore = true)
	@Mapping(target = "version", ignore = true)
	VimConnectionInformation map(VimConnectionInfoDto o);

	default List<VimConnectionInformation> mapAsList(final List<VimConnectionInformation> vimConnectionInfo) {
		return vimConnectionInfo.stream().map(this::map).toList();
	}

	VimConnectionInformation map(VimConnectionInformation x);

	default UUID mapUuid(final String str) {
		if (null == str) {
			return null;
		}
		return UUID.fromString(str);
	}

	@Mapping(target = "accessInfo", source = ".")
	@Mapping(target = "extra", ignore = true)
	@Mapping(target = "interfaceInfo.endpoint", source = "apiAddress")
	// UBINFV:KUBERNETES_TLS:V_1
	Connection<K8sInterfaceInfo, KubernetesV1Auth> mapFromTls(K8sServers tls);

	@Mapping(target = "clientCertificateData", source = "userCrt")
	@Mapping(target = "clientKeyData", source = "userKey")
	KubernetesV1Auth map(K8sServers o);
}
