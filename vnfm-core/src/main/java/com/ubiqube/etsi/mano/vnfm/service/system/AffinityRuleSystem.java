/**
 *     Copyright (C) 2019-2023 Ubiqube.
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

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.AffinityRuleTask;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.AffinityRuleNode;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.VnfAffinityUow;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class AffinityRuleSystem extends AbstractVimSystemV3<AffinityRuleTask> {
	private final Vim vim;

	public AffinityRuleSystem(final Vim vim, final VimManager vimManager) {
		super(vimManager);
		this.vim = vim;
	}

	@Override
	public String getVimType() {
		return "OPENSTACK_V3";
	}

	@Override
	protected SystemBuilder<UnitOfWorkV3<AffinityRuleTask>> getImplementation(final OrchestrationServiceV3<AffinityRuleTask> orchestrationService, final VirtualTaskV3<AffinityRuleTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		return orchestrationService.systemBuilderOf(new VnfAffinityUow(virtualTask, vim, vimConnectionInformation));
	}

	@Override
	public Class<? extends Node> getType() {
		return AffinityRuleNode.class;
	}

}
