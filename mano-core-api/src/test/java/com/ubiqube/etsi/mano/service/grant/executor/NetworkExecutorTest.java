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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualCp;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.vim.Network;
import com.ubiqube.etsi.mano.service.vim.NetworkObject;
import com.ubiqube.etsi.mano.service.vim.Vim;

@ExtendWith(MockitoExtension.class)
class NetworkExecutorTest {
	@Mock
	private VnfPackageService vnfPackageService;
	@Mock
	private Vim vim;
	@Mock
	private Network network;

	NetworkExecutor createService() {
		return new NetworkExecutor(vnfPackageService);
	}

	@Test
	void test001() {
		final NetworkExecutor srv = createService();
		final VnfPackage vnfPackage = new VnfPackage();
		when(vnfPackageService.findByVnfdId(any())).thenReturn(vnfPackage);
		final GrantResponse grant = new GrantResponse();
		final VimConnectionInformation vimInfo = new VimConnectionInformation<>();
		when(vim.network(vimInfo)).thenReturn(network);
		when(network.search(any(), any())).thenReturn(List.of());
		srv.extractNetwork(grant, vimInfo, vim);
		assertTrue(true);
	}

	@Test
	void test002() {
		final NetworkExecutor srv = createService();
		final VnfPackage vnfPackage = new VnfPackage();
		final VirtualCp vcp01 = new VirtualCp();
		vnfPackage.setVirtualCp(Set.of(vcp01));
		final ListKeyPair kp0 = new ListKeyPair();
		kp0.setValue("public");
		vnfPackage.setVirtualLinks(Set.of(kp0));
		when(vnfPackageService.findByVnfdId(any())).thenReturn(vnfPackage);
		final GrantResponse grant = new GrantResponse();
		final VimConnectionInformation vimInfo = new VimConnectionInformation<>();
		when(vim.network(vimInfo)).thenReturn(network);
		final NetworkObject no01 = new NetworkObject("id", "public");
		when(network.search(any(), any())).thenReturn(List.of(no01));
		srv.extractNetwork(grant, vimInfo, vim);
		assertTrue(true);
	}

}
