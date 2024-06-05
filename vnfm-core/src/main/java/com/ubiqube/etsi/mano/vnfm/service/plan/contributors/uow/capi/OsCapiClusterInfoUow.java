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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.K8sInformationsTask;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.StatusType;
import com.ubiqube.etsi.mano.jpa.CapiServerJpa;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.vim.k8s.K8s;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.AbstractVnfmUow;

public class OsCapiClusterInfoUow extends AbstractVnfmUow<K8sInformationsTask> {
	private final OsClusterService osClusterService;
	private final K8sInformationsTask task;
	private final K8sServerInfoJpa serverInfoJpa;
	private final CapiServerJpa capiServerJpa;
	private final CapiServerMapping mapper = Mappers.getMapper(CapiServerMapping.class);

	public OsCapiClusterInfoUow(final VirtualTaskV3<K8sInformationsTask> task, final OsClusterService osClusterService, final K8sServerInfoJpa serverInfoJpa, final CapiServerJpa capiServerJpa) {
		super(task, OsK8sInformationsNode.class);
		this.task = task.getTemplateParameters();
		this.osClusterService = osClusterService;
		this.serverInfoJpa = serverInfoJpa;
		this.capiServerJpa = capiServerJpa;
	}

	@Override
	public String execute(final Context3d context) {
		final String srv = context.get(OsContainerDeployableNode.class, task.getToscaName());
		final Optional<K8sServers> obj = serverInfoJpa.findByVimResourceId(srv);
		if (obj.isPresent()) {
			return obj.get().getId().toString();
		}
		final CapiServer capiSrv = capiServerJpa.findAll().iterator().next();
		final K8s k8sConfig = mapper.map(capiSrv);
		final K8s res = osClusterService.getKubeConfig(k8sConfig, "default", buildClusterName(task.getToscaName(), task.getVnfInstId()));
		final K8sServers ret = toK8sServers(res);
		ret.setId(UUID.fromString(srv));
		final K8sServers r = serverInfoJpa.save(ret);
		return r.getId().toString();
	}

	private K8sServers toK8sServers(final K8s ret) {
		final K8sServers r = new K8sServers();
		r.setApiAddress(ret.getApiUrl());
		r.setCaPem(ret.getCaData());
		r.setMasterAddresses(List.of(ret.getApiUrl()));
		r.setStatus(StatusType.CREATE_COMPLETE);
		r.setToscaName(task.getToscaName());
		r.setUserCrt(ret.getClientCrt());
		r.setUserKey(ret.getClientKey());
		return r;
	}

	@Override
	public String rollback(final Context3d context) {
		final Optional<K8sServers> obj = serverInfoJpa.findByVimResourceId(task.getVimResourceId());
		if (obj.isPresent()) {
			serverInfoJpa.deleteById(obj.get().getId());
		}
		return null;
	}

	private static String buildClusterName(final String toscaName, final String vnfInstId) {
		final String newName = toscaName.toLowerCase().replace('_', '-');
		return "%s-%s".formatted(newName, vnfInstId);
	}

}
