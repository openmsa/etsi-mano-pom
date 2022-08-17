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

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.MciopTask;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.boot.K8sPkService;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.service.vim.k8s.K8sClient;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.uow.HelmDeployUowV3;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.uow.MciopUserUowV3;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class MciopSystem extends AbstractVimSystemV3<MciopTask> {
	private final K8sClient client;
	private final K8sServerInfoJpa serverInfoJpa;
	private final Vim vim;
	private final K8sPkService k8sPkService;
	private final VnfPackageRepository repo;

	protected MciopSystem(final VimManager vimManager, final Vim vim, final K8sClient client, final K8sServerInfoJpa serverInfoJpa, final K8sPkService k8sPkService,
			final VnfPackageRepository repo) {
		super(vimManager);
		this.vim = vim;
		this.client = client;
		this.serverInfoJpa = serverInfoJpa;
		this.k8sPkService = k8sPkService;
		this.repo = repo;
	}

	@Override
	protected SystemBuilder getImplementation(final OrchestrationServiceV3<MciopTask> orchestrationService, final VirtualTaskV3<MciopTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		final String crt = k8sPkService.createCsr("CN=kubernetes-admin,O=system:masters");
		final String pk = k8sPkService.getPrivateKey();
		final UnitOfWorkV3<MciopTask> createUser = new MciopUserUowV3(virtualTask, vim, vimConnectionInformation, serverInfoJpa, crt);
		final UnitOfWorkV3<MciopTask> uow = new HelmDeployUowV3(virtualTask, client, serverInfoJpa, repo, pk);
		return orchestrationService.systemBuilderOf(createUser, uow);
	}

	@Override
	public String getVimType() {
		return "OPENSTACK_V3";
	}

	@Override
	public Class<? extends Node> getType() {
		return HelmNode.class;
	}
}
