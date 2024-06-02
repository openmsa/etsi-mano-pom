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
package com.ubiqube.etsi.mano.vnfm.service.system;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.K8sInformationsTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.jpa.CapiServerJpa;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi.OsCapiClusterInfoUow;

import jakarta.annotation.Nonnull;

@Service
public class OsCapiInformationsSystem extends AbstractVimSystemV3<K8sInformationsTask> {
	@Nonnull
	private final OsClusterService osClusterService;
	@Nonnull
	private final K8sServerInfoJpa serverInfoJpa;
	@Nonnull
	private final CapiServerJpa capiServerJpa;

	protected OsCapiInformationsSystem(final VimManager vimManager, final K8sServerInfoJpa serverInfoJpa, final OsClusterService osClusterService, final CapiServerJpa capiServerJpa) {
		super(vimManager);
		this.osClusterService = osClusterService;
		this.serverInfoJpa = serverInfoJpa;
		this.capiServerJpa = capiServerJpa;
	}

	@Override
	public String getVimType() {
		return "CAPI";
	}

	@Override
	public Class<? extends Node> getType() {
		return OsK8sInformationsNode.class;
	}

	@Override
	protected SystemBuilder<UnitOfWorkV3<K8sInformationsTask>> getImplementation(final OrchestrationServiceV3<K8sInformationsTask> orchestrationService, final VirtualTaskV3<K8sInformationsTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		return orchestrationService.systemBuilderOf(new OsCapiClusterInfoUow(virtualTask, osClusterService, serverInfoJpa, capiServerJpa));
	}

}
