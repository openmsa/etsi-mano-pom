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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.K8sInformationsTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.StatusType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;

import jakarta.annotation.Nullable;

public class OsK8sClusterInfoUow extends AbstractVnfmUow<K8sInformationsTask> {

	private static final Logger LOG = LoggerFactory.getLogger(OsK8sClusterInfoUow.class);

	private final VimConnectionInformation vimConnectionInformation;
	private final Vim vim;
	private final K8sInformationsTask task;
	private final K8sServerInfoJpa serverInfoJpa;

	public OsK8sClusterInfoUow(final VirtualTaskV3<K8sInformationsTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation,
			final K8sServerInfoJpa serverInfoJpa) {
		super(task, OsK8sInformationsNode.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
		this.serverInfoJpa = serverInfoJpa;
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final String srv = context.get(OsContainerDeployableNode.class, task.getToscaName());
		final Optional<K8sServers> obj = serverInfoJpa.findByVimResourceId(srv);
		if (obj.isPresent()) {
			return obj.get().getId().toString();
		}
		if (vimConnectionInformation.getCnfInfo() != null) {
			K8sServers status = vim.cnf(vimConnectionInformation).getClusterInformations(srv);
			while (status.getStatus() == StatusType.CREATE_IN_PROGRESS) {
				try {
					Thread.sleep(1_000L);
				} catch (final InterruptedException e) {
					LOG.error("", e);
					Thread.currentThread().interrupt();
				}
				status = vim.cnf(vimConnectionInformation).getClusterInformations(srv);
				LOG.debug("Fetched OsContainer status: {} / {}", task.getToscaName(), status.getStatus());
			}
			status.setToscaName(task.getToscaName());
			status.setId(UUID.fromString(srv));
			final K8sServers n = serverInfoJpa.save(status);
			if (status.getStatus() == StatusType.CREATE_FAILED) {
				throw new GenericException("Create Failed: " + task.getToscaName());
			}
			return n.getId().toString();
		}
		return null;
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final Optional<K8sServers> obj = serverInfoJpa.findByVimResourceId(task.getVimResourceId());
        obj.ifPresent(k8sServers -> serverInfoJpa.deleteById(k8sServers.getId()));
		return null;
	}

}
