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
package com.ubiqube.etsi.mano.vnfm.service.system;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.JujuCloudService;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.OsContainerUow;

@Service
public class OsContainerSystem extends AbstractVimSystemV3<OsContainerTask> {
	private final Vim vim;
	private final JujuCloudService jujuCloudService;
	private final VnfPackageRepository vnfRepo;

	protected OsContainerSystem(final Vim vim, final VimManager vimManager, final JujuCloudService jujuCloudService, final VnfPackageRepository vnfRepo) {
		super(vimManager);
		this.vim = vim;
		this.jujuCloudService = jujuCloudService;
		this.vnfRepo = vnfRepo;
	}

	@Override
	public String getVimType() {
		return "UBINFV.CISM.V_1";
	}

	@Override
	public Class<? extends Node> getType() {
		return OsContainerNode.class;
	}

	@Override
	protected SystemBuilder<UnitOfWorkV3<OsContainerTask>> getImplementation(final OrchestrationServiceV3<OsContainerTask> orchestrationService, final VirtualTaskV3<OsContainerTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		return orchestrationService.systemBuilderOf(new OsContainerUow(virtualTask, vim, vimConnectionInformation, jujuCloudService, vnfRepo));
	}

}