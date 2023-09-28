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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.boot.K8sPkService;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.service.vim.k8s.K8sClient;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.property.HelmWrapperProperty;
import com.ubiqube.etsi.mano.vnfm.property.OAuth2;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.HelmVt;

@ExtendWith(MockitoExtension.class)
class HelmSystemTest {
	@Mock
	private VimManager vimManager;
	@Mock
	private K8sClient client;
	@Mock
	private K8sServerInfoJpa serverJpa;
	@Mock
	private K8sPkService k8sService;
	@Mock
	private VnfPackageRepository repo;
	@Mock
	private OrchestrationServiceV3<HelmTask> orchService;

	@Test
	void test() {
		final HelmWrapperProperty props = new HelmWrapperProperty();
		props.setUrl(URI.create("http://localhost/"));
		final OAuth2 oauth2 = new OAuth2();
		props.setOauth2(oauth2);
		final HelmSystem srv = new HelmSystem(vimManager, client, serverJpa, k8sService, repo, props);
		final HelmTask nt = new HelmTask();
		final VirtualTaskV3<HelmTask> vt = new HelmVt(nt);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		srv.getImplementation(orchService, vt, vimConn);
		assertNotNull(srv.getType());
		assertNotNull(srv.getVimType());
	}

}
