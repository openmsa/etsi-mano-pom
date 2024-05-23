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

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vnfm.jpa.CapiServerJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi.OsCapiClusterUow;

import jakarta.annotation.Nonnull;

@Service
public class OsCapiContainerDeployable extends AbstractVimSystemV3<OsContainerDeployableTask> {
	@Nonnull
	private final OsClusterService osCluster;
	@Nonnull
	private final CapiServerJpa capiServerJpa;

	protected OsCapiContainerDeployable(final VimManager vimManager, final CapiServerJpa capiServerJpa, final OsClusterService osCluster) {
		super(vimManager);
		this.osCluster = osCluster;
		this.capiServerJpa = capiServerJpa;
	}

	@Override
	public String getVimType() {
		return "CAPI";
	}

	@Override
	public Class<? extends Node> getType() {
		return OsContainerDeployableNode.class;
	}

	@Override
	protected SystemBuilder<UnitOfWorkV3<OsContainerDeployableTask>> getImplementation(final OrchestrationServiceV3<OsContainerDeployableTask> orchestrationService, final VirtualTaskV3<OsContainerDeployableTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		return orchestrationService.systemBuilderOf(new OsCapiClusterUow(virtualTask, osCluster, vimConnectionInformation, capiServerJpa));
	}
}
