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

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.MciopUserTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.MciopUser;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi.MciopCapiUserUow;

@Service
public class MciopCapiUserSystem extends AbstractVimSystemV3<MciopUserTask> {
	private final K8sServerInfoJpa serverInfoJpa;

	protected MciopCapiUserSystem(final VimManager vimManager, final K8sServerInfoJpa serverInfoJpa) {
		super(vimManager);
		this.serverInfoJpa = serverInfoJpa;
	}

	@Override
	public String getVimType() {
		return "CAPI";
	}

	@Override
	public Class<? extends Node> getType() {
		return MciopUser.class;
	}

	@Override
	protected SystemBuilder<UnitOfWorkV3<MciopUserTask>> getImplementation(final OrchestrationServiceV3<MciopUserTask> orchestrationService, final VirtualTaskV3<MciopUserTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		return orchestrationService.systemBuilderOf(new MciopCapiUserUow(virtualTask, serverInfoJpa));
	}

}
