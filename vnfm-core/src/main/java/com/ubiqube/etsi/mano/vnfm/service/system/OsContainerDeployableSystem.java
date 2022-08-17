/**
 *     Copyright (C) 2019-2020 Ubiqube.
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

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.K8sInformationsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.OsK8sClusterVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.uow.OsContainerDeployableUow3;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.uow.OsK8sClusterUowV3;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class OsContainerDeployableSystem extends AbstractVimSystemV3<OsContainerDeployableTask> {

	private final Vim vim;
	private final K8sServerInfoJpa serverInfoJpa;

	protected OsContainerDeployableSystem(final Vim vim, final VimManager vimManager, final K8sServerInfoJpa serverInfoJpa) {
		super(vimManager);
		this.vim = vim;
		this.serverInfoJpa = serverInfoJpa;
	}

	@Override
	protected SystemBuilder getImplementation(final OrchestrationServiceV3<OsContainerDeployableTask> orchestrationService, final VirtualTaskV3<OsContainerDeployableTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		final SystemBuilder builder = orchestrationService.createEmptySystemBuilder();
		final OsContainerDeployableUow3 left = new OsContainerDeployableUow3(virtualTask, vim, vimConnectionInformation);
		final K8sInformationsTask k8sInfo = createTask(virtualTask.getTemplateParameters());
		final OsK8sClusterUowV3 right = new OsK8sClusterUowV3(new OsK8sClusterVt(k8sInfo), vim, vimConnectionInformation, serverInfoJpa);
		builder.add(left, right);
		return builder;
	}

	private static K8sInformationsTask createTask(final OsContainerDeployableTask p) {
		final K8sInformationsTask k8sInfo = new K8sInformationsTask();
		k8sInfo.setAlias("k8s-info-" + p.getAlias());
		k8sInfo.setToscaName(p.getToscaName());
		k8sInfo.setBlueprint(p.getBlueprint());
		k8sInfo.setChangeType(p.getChangeType());
		k8sInfo.setRemovedLiveInstance(p.getRemovedLiveInstance());
		k8sInfo.setType(ResourceTypeEnum.CNF_INFO);
		k8sInfo.setVimConnectionId(p.getVimConnectionId());
		k8sInfo.setVimResourceId(p.getVimResourceId());
		return k8sInfo;
	}

	@Override
	public String getVimType() {
		return "OPENSTACK_V3";
	}

	@Override
	public Class<? extends Node> getType() {
		return OsContainerDeployableNode.class;
	}
}
