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
import com.ubiqube.etsi.mano.orchestrator.OrchestrationService;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWork;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.boot.K8sPkService;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystem;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.service.vim.k8s.K8sClient;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.uow.HelmDeployUow;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.uow.McioUserUow;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class MciopSystem extends AbstractVimSystem<MciopTask> {
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
	public String getProviderId() {
		return "HELM";
	}

	@Override
	protected SystemBuilder getImplementation(final OrchestrationService<MciopTask> orchestrationService, final VirtualTask<MciopTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		final String crt = k8sPkService.createCsr("CN=mano-user,O=cluster-admin");
		final String pk = k8sPkService.getPrivateKey();
		final UnitOfWork<MciopTask> createUser = new McioUserUow(virtualTask, vim, vimConnectionInformation, serverInfoJpa, crt);
		final UnitOfWork<MciopTask> uow = new HelmDeployUow(virtualTask, client, serverInfoJpa, repo, pk);
		return orchestrationService.systemBuilderOf(createUser, uow);
	}

}
