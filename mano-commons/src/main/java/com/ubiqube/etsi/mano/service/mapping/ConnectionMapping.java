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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mapstruct.Mapper;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.dao.mano.ai.KubernetesV1Auth;
import com.ubiqube.etsi.mano.dao.mano.ii.K8sInterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ii.OpenstackV3InterfaceInfo;
import com.ubiqube.etsi.mano.exception.GenericException;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Mapper
public interface ConnectionMapping {
	default InterfaceInfo mapToInterfaceInfo(@NotNull final String vimType, @Valid final Map<String, String> ii) {
		if (ii == null) {
			return null;
		}
		if ("ETSINFV.OPENSTACK_KEYSTONE.V_3".equals(vimType)) {
			return mapToOpenstackV3InterfaceInfo(ii);
		}
		if ("UBINFV.CISM.V_1".equals(vimType)) {
			return mapToK8sInterfaceInfo(ii);
		}
		if ("PAAS".equals(vimType)) {
			// XXX: This need works works.
			return new InterfaceInfo();
		}
		throw new GenericException("Unknown vimType: " + vimType);
	}

	K8sInterfaceInfo mapToK8sInterfaceInfo(@Valid Map<String, String> ii);

	OpenstackV3InterfaceInfo mapToOpenstackV3InterfaceInfo(@Valid Map<String, String> ii);

	default List<String> mapStringToList(final String value) {
		if (value == null) {
			return List.of();
		}
		return List.of(value);
	}

	KeystoneAuthV3 mapToKeystoneAuthV3(@Valid Map<String, String> ii);

	default AccessInfo mapToAccessInfo(@NotNull final String vimType, @Valid final Map<String, String> ai) {
		if (ai == null) {
			return null;
		}
		if ("ETSINFV.OPENSTACK_KEYSTONE.V_3".equals(vimType)) {
			return mapToKeystoneAuthV3(ai);
		}
		if ("UBINFV.CISM.V_1".equals(vimType)) {
			return mapToK8sAuth(ai);
		}
		if ("PAAS".equals(vimType)) {
			// XXX: This need works works.
			return new AccessInfo();
		}
		throw new GenericException("Vim type: " + vimType);
	}

	KubernetesV1Auth mapToK8sAuth(@Valid Map<String, String> ai);

	default Map<String, String> map(final InterfaceInfo value) {
		if (null == value) {
			return Map.of();
		}
		final Map<String, String> ret = new HashMap<>();
		ret.put("endpoint", value.getEndpoint());
		if (value.isNonStrictSsl()) {
			ret.put("non-strict-ssl", "true");
		}
		Optional.ofNullable(value.getNatHost()).ifPresent(x -> ret.put("nat-host", x));

		Optional.ofNullable(value.getConnectionTimeout()).ifPresent(x -> ret.put("connection-timeout", "" + x));
		Optional.ofNullable(value.getReadTimeout()).ifPresent(x -> ret.put("read-timeout", "" + x));
		ret.put("read-timeout", "" + value.getRetry());
		Optional.ofNullable(value.getRegionName()).ifPresent(x -> ret.put("regionName", x));
		Optional.ofNullable(value.getSdnEndpoint()).ifPresent(x -> ret.put("sdn-endpoint", x));
		return ret;
	}

	default Map<String, String> map(final KeystoneAuthV3 value) {
		if (null == value) {
			return Map.of();
		}
		final Map<String, String> ret = new HashMap<>();
		Optional.ofNullable(value.getUsername()).ifPresent(x -> ret.put("username", x));
		Optional.ofNullable(value.getPassword()).ifPresent(x -> ret.put("password", x));

		Optional.ofNullable(value.getUserDomain()).ifPresent(x -> ret.put("userDomain", x));
		Optional.ofNullable(value.getProject()).ifPresent(x -> ret.put("project", x));
		Optional.ofNullable(value.getRegion()).ifPresent(x -> ret.put("region", x));
//		Optional.ofNullable(value.getProjectId()).ifPresent(x -> ret.put("projectId", x));
		Optional.ofNullable(value.getProjectDomain()).ifPresent(x -> ret.put("projectDomain", x));
//		Optional.ofNullable(value.getProjectName()).ifPresent(x -> ret.put("projectName", x));
		return ret;
	}

	@Nullable
	default Map<String, String> map(final @Nullable com.ubiqube.etsi.mano.dao.mano.AccessInfo o) {
		if (null == o) {
			return null;
		}
		final Map<String, String> ret = new LinkedHashMap<>();
		if (o.getId() != null) {
			ret.put("id", o.getId().toString());
		}
		if (o instanceof final KeystoneAuthV3 ka3) {
			ret.put("password", ka3.getPassword());
			ret.put("project", ka3.getProject());
			ret.put("projectDomain", ka3.getProjectDomain());
			ret.put("projectId", ka3.getProjectId());
			ret.put("region", ka3.getRegion());
			ret.put("userDomain", ka3.getUserDomain());
			ret.put("userName", ka3.getUsername());
		} else if (o instanceof final KubernetesV1Auth ka1) {
			ret.put("client-certificate-data", ka1.getClientCertificateData());
			ret.put("client-key-data", ka1.getClientKeyData());
		}
		return ret;
	}

}
