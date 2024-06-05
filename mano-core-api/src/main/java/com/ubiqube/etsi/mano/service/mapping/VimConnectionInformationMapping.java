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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
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
	default VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> map(final VimConnectionInfoDto o) {
		if (o == null) {
			return null;
		}
		final VimConnectionInformation ret = new VimConnectionInformation<>();
		ret.setCnfInfo(o.getCnfInfo());
		ret.setExtra(o.getExtra());
		ret.setJujuInfo(o.getJujuInfo());
		ret.setVimCapabilities(o.getVimCapabilities());
		ret.setVimId(o.getVimId());
		ret.setVimType(o.getVimType());
		final AccessInfo ai = switch (ret.getVimType()) {
		case "OPENSTACK_V3" -> mapToKeystoneAuthV3(o.getAccessInfo(), new KeystoneAuthV3());
		default -> throw new IllegalArgumentException("Unexpected value: " + ret.getVimType());
		};
		ret.setAccessInfo(ai);
		return ret;
	}

	KeystoneAuthV3 mapToKeystoneAuthV3(Map<String, String> accessInfo, @MappingTarget KeystoneAuthV3 keystoneAuthV3);

	default List<VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo>> mapAsList(final List<VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo>> vimConnectionInfo) {
		// Doesn't works with `toList()`
		return vimConnectionInfo.stream().map(this::map).collect(Collectors.toList());
	}

	default VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> map(final VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> o) {
		final VimConnectionInformation<InterfaceInfo, AccessInfo> ret = new VimConnectionInformation<>();
		ret.setCnfInfo(o.getCnfInfo());
		ret.setExtra(o.getExtra());
		ret.setJujuInfo(o.getJujuInfo());
		ret.setVimCapabilities(o.getVimCapabilities());
		ret.setVimId(o.getVimId());
		ret.setVimType(o.getVimType());
		final AccessInfo ai = switch (ret.getVimType()) {
		case "OPENSTACK_V3" -> mapToKeystoneAuthV3((KeystoneAuthV3) o.getAccessInfo());
		default -> throw new IllegalArgumentException("Unexpected value: " + ret.getVimType());
		};
		ret.setAccessInfo(ai);
		return ret;
	}

	KeystoneAuthV3 mapToKeystoneAuthV3(final KeystoneAuthV3 o);

	default UUID mapUuid(final String str) {
		if (null == str) {
			return null;
		}
		return UUID.fromString(str);
	}

	@Mapping(target = "accessInfo", source = ".")
	@Mapping(target = "extra", ignore = true)
	@Mapping(target = "interfaceInfo.endpoint", source = "apiAddress")
	@Mapping(target = "vimType", constant = "UBINFV.CISM.V_1")
	@Mapping(target = "vimId", constant = "130bdaa5-672f-437a-96ae-690c6ac3751f")
	// UBINFV.CISM.V_1
	VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> mapFromTls(K8sServers tls);

	@Mapping(target = "clientCertificateData", source = "userCrt")
	@Mapping(target = "clientKeyData", source = "userKey")
	KubernetesV1Auth map(K8sServers o);
}
