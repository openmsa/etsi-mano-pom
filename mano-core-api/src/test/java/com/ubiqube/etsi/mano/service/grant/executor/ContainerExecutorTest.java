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
package com.ubiqube.etsi.mano.service.grant.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.OsContainerDesc;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.grant.ccm.CcmManager;
import com.ubiqube.etsi.mano.service.vim.CirConnectionManager;
import com.ubiqube.etsi.mano.service.vim.VimManager;

@ExtendWith(MockitoExtension.class)
class ContainerExecutorTest {
	@Mock
	private CcmManager ccmManager;
	@Mock
	private CirConnectionManager cirConnectionManager;
	@Mock
	private VimManager vimManager;
	@Mock
	private VnfPackageService vnfPackageService;

	ContainerExecutor createService() {
		return new ContainerExecutor(ccmManager, cirConnectionManager, vimManager, vnfPackageService);
	}

	@Test
	void testAddCir() {
		final ContainerExecutor srv = createService();
		final GrantResponse grant = new GrantResponse();
		final ConnectionInformation con001 = new ConnectionInformation();
		when(cirConnectionManager.findAll()).thenReturn(List.of(con001));
		srv.addCirConnection(grant);
		assertNotNull(grant.getCirConnectionInfo());
		assertEquals(1, grant.getCirConnectionInfo().size());
	}

	@Test
	void testRemoveK8s001() {
		final ContainerExecutor srv = createService();
		final GrantResponse grant = new GrantResponse();
		final VnfPackage vnfPackage = new VnfPackage();
		when(vnfPackageService.findByVnfdId(any())).thenReturn(vnfPackage);
		srv.removeK8sCluster(grant);
		assertTrue(true);
	}

	@Test
	void testRemoveK8s002() {
		final ContainerExecutor srv = createService();
		final GrantResponse grant = new GrantResponse();
		final VnfPackage vnfPackage = new VnfPackage();
		final OsContainer osc = new OsContainer();
		vnfPackage.setOsContainer(Set.of(osc));
		when(vnfPackageService.findByVnfdId(any())).thenReturn(vnfPackage);
		srv.removeK8sCluster(grant);
		assertTrue(true);
	}

	@Test
	void testRemoveK8s003() {
		final ContainerExecutor srv = createService();
		final GrantResponse grant = new GrantResponse();
		final VnfPackage vnfPackage = new VnfPackage();
		final OsContainerDeployableUnit osc = new OsContainerDeployableUnit();
		vnfPackage.setOsContainerDeployableUnits(Set.of(osc));
		when(vnfPackageService.findByVnfdId(any())).thenReturn(vnfPackage);
		srv.removeK8sCluster(grant);
		assertTrue(true);
	}

	@Test
	void testAddOrCreateK8sVim001() {
		final ContainerExecutor srv = createService();
		final GrantResponse grant = new GrantResponse();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setMciops(Set.of());
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		when(vnfPackageService.findByVnfdId(any())).thenReturn(vnfPackage);
		srv.addOrCreateK8sVim(vim01, grant);
		assertTrue(true);
	}

	@Test
	void testAddOrCreateK8sVim002() {
		final ContainerExecutor srv = createService();
		final GrantResponse grant = new GrantResponse();
		final VnfPackage vnfPackage = new VnfPackage();
		final OsContainerDesc osc = new OsContainerDesc();
		vnfPackage.setOsContainerDesc(Set.of(osc));
		when(vnfPackageService.findByVnfdId(any())).thenReturn(vnfPackage);
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		when(ccmManager.getVimConnection(any(), any(), any())).thenReturn(vim01);
		final VimConnectionInformation vim02 = new VimConnectionInformation();
		when(vimManager.findOptionalVimByVimId(any())).thenReturn(Optional.ofNullable(vim02));
		srv.addOrCreateK8sVim(vim02, grant);
		assertTrue(true);
	}

	@Test
	void testAddOrCreateK8sVim003() {
		final ContainerExecutor srv = createService();
		final GrantResponse grant = new GrantResponse();
		final VnfPackage vnfPackage = new VnfPackage();
		final McIops osc = new McIops();
		vnfPackage.setMciops(Set.of(osc));
		vnfPackage.setOsContainer(Set.of());
		vnfPackage.setOsContainerDesc(Set.of());
		vnfPackage.setOsContainerDeployableUnits(Set.of());
		when(vnfPackageService.findByVnfdId(any())).thenReturn(vnfPackage);
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		when(ccmManager.getVimConnection(any(), any(), any())).thenReturn(vim01);
		when(vimManager.findOptionalVimByVimId(any())).thenReturn(Optional.empty());
		final VimConnectionInformation vim02 = new VimConnectionInformation();
		when(vimManager.save(any())).thenReturn(vim02);
		srv.addOrCreateK8sVim(vim02, grant);
		assertTrue(true);
	}
}
