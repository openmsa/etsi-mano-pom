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

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.auth.model.OAuth2GrantType;
import com.ubiqube.etsi.mano.service.boot.K8sPkService;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.service.vim.k8s.K8sClient;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.property.JujuWrapperProperty;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.HelmV3DeployUow;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class JujuSystem extends AbstractVimSystemV3<HelmTask> {
	private final K8sClient client;
	private final K8sServerInfoJpa serverInfoJpa;
	private final K8sPkService k8sPkService;
	private final VnfPackageRepository repo;

	private final JujuWrapperProperty props;

	protected JujuSystem(final VimManager vimManager, final K8sClient client, final K8sServerInfoJpa serverInfoJpa, final K8sPkService k8sPkService,
			final VnfPackageRepository repo, final JujuWrapperProperty props) {
		super(vimManager);
		this.client = client;
		this.serverInfoJpa = serverInfoJpa;
		this.k8sPkService = k8sPkService;
		this.repo = repo;
		this.props = props;
	}

	@Override
	protected SystemBuilder getImplementation(final OrchestrationServiceV3<HelmTask> orchestrationService, final VirtualTaskV3<HelmTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		final Servers srv = Servers.builder()
				.authentification(AuthentificationInformations.builder()
						.authParamOauth2(AuthParamOauth2.builder()
								.clientId(props.getOauth2().getClientId())
								.clientSecret(props.getOauth2().getClientSecret())
								.grantType(OAuth2GrantType.CLIENT_CREDENTIAL)
								.tokenEndpoint(props.getOauth2().getTokenEndpoint())
								.build())
						.build())
				.url(props.getUrl())
				.build();
		final UnitOfWorkV3<HelmTask> uow = new HelmV3DeployUow(virtualTask, client, vimConnectionInformation, repo, srv);
		return orchestrationService.systemBuilderOf(uow);
	}

	@Override
	public String getVimType() {
		return "JUJU";
	}

	@Override
	public Class<? extends Node> getType() {
		return HelmNode.class;
	}
}
