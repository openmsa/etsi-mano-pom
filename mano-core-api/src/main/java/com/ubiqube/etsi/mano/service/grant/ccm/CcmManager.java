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
package com.ubiqube.etsi.mano.service.grant.ccm;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.ai.KubernetesV1Auth;
import com.ubiqube.etsi.mano.dao.mano.ii.K8sInterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.StatusType;
import com.ubiqube.etsi.mano.service.mapping.VimConnectionInformationMapping;
import com.ubiqube.etsi.mano.vim.k8s.K8s;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;

@Service
public class CcmManager {
	private final K8sServerInfoJpa k8sServerInfoJpa;
	private final VimConnectionInformationMapping connectionInformationMapping;
	private final CcmServerService ccmServerService;

	public CcmManager(final K8sServerInfoJpa k8sServerInfoJpa, final VimConnectionInformationMapping connectionInformationMapping, final CcmServerService ccmServerService) {
		this.k8sServerInfoJpa = k8sServerInfoJpa;
		this.connectionInformationMapping = connectionInformationMapping;
		this.ccmServerService = ccmServerService;
	}

	public VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> getVimConnection(final GrantResponse grants, final VnfPackage vnfPackage) {
		final Optional<K8sServers> k8sInfo = k8sServerInfoJpa.findByVnfInstanceId(getSafeUUID(grants.getVnfInstanceId()));
		if (k8sInfo.isPresent()) {
			return connectionInformationMapping.mapFromTls(k8sInfo.get());
		}
		final K8s res = ccmServerService.createCluster(grants.getVnfInstanceId());
		final K8sServers ret = toK8sServers(res, grants.getVnfInstanceId());
		ret.setId(UUID.randomUUID());
		ret.setVnfInstanceId(UUID.fromString(grants.getVnfInstanceId()));
		final K8sServers r = k8sServerInfoJpa.save(ret);
		return mapToConnection(r);
	}

	private static VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> mapToConnection(final K8sServers r) {
		final KubernetesV1Auth ai = new KubernetesV1Auth();
		ai.setClientCertificateData(r.getUserCrt());
		ai.setClientKeyData(r.getUserKey());
		final K8sInterfaceInfo ii = new K8sInterfaceInfo();
		ii.setCertificateAuthorityData(r.getCaPem());
		ii.setEndpoint(r.getApiAddress());
		final VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> conn = new VimConnectionInformation<>();
		conn.setAccessInfo(ai);
		conn.setInterfaceInfo(ii);
		conn.setVimType("UBINFV.CISM.V_1");
		// Use endpoint to make repeatable ID.
		conn.setVimId(UUID.nameUUIDFromBytes(ii.getEndpoint().getBytes()).toString());
		return conn;
	}

	private static K8sServers toK8sServers(final K8s ret, final String name) {
		final K8sServers r = new K8sServers();
		r.setApiAddress(ret.getApiUrl());
		r.setCaPem(ret.getCaData());
		r.setMasterAddresses(List.of(ret.getApiUrl()));
		r.setStatus(StatusType.CREATE_COMPLETE);
		r.setToscaName(name);
		r.setUserCrt(ret.getClientCrt());
		r.setUserKey(ret.getClientKey());
		return r;
	}

	public void getTerminateCluster(final String vnfInstanceId) {
		ccmServerService.terminateCluster(vnfInstanceId);
		final Optional<K8sServers> k8s = k8sServerInfoJpa.findByVnfInstanceId(getSafeUUID(vnfInstanceId));
		k8s.ifPresent(k8sServerInfoJpa::delete);
	}

}
