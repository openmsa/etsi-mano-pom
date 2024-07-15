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

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualCp;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.vim.NetowrkSearchField;
import com.ubiqube.etsi.mano.service.vim.NetworkObject;
import com.ubiqube.etsi.mano.service.vim.Vim;

@Service
public class NetworkExecutor {
	private final VnfPackageService vnfPackageService;

	public NetworkExecutor(final VnfPackageService vnfPackageService) {
		this.vnfPackageService = vnfPackageService;
	}

	public void extractNetwork(final GrantResponse grants, final VimConnectionInformation vimInfo, final Vim vim) {
		// Add public networks.
		final VnfPackage vnfPkg = vnfPackageService.findByVnfdId(grants.getVnfdId());
		// XXX The none match ExtCp cause to discard all extCp pointing to an existing
		// network.
		final List<String> lst = vnfPkg.getVirtualLinks().stream()
				.filter(x -> noneMatchVirtualCp(vnfPkg, x) /* && noneMatchExtCp(vnfPkg, x) */)
				.map(ListKeyPair::getValue)
				.toList();
		final List<NetworkObject> netFound = vim.network(vimInfo).search(NetowrkSearchField.NAME, lst);
		netFound.forEach(x -> {
			final ExtManagedVirtualLinkDataEntity extVl = new ExtManagedVirtualLinkDataEntity();
			extVl.setResourceId(x.name());
			extVl.setResourceProviderId(vim.getType());
			extVl.setVimConnectionId(vimInfo.getVimId());
			extVl.setVnfVirtualLinkDescId(x.id());
			extVl.setGrants(grants);
			grants.addExtManagedVl(extVl);
		});
	}

	private static boolean noneMatchExtCp(final VnfPackage vnfPkg, final ListKeyPair x) {
		return vnfPkg.getVnfExtCp().stream().noneMatch(y -> Optional.ofNullable(y.getExternalVirtualLink())
				.filter(z -> z.equals(x.getValue()))
				.isPresent());
	}

	private static boolean noneMatchVirtualCp(final VnfPackage vnfPkg, final ListKeyPair x) {
		final Set<VirtualCp> vcpSet = vnfPkg.getVirtualCp();
		for (final VirtualCp cp : vcpSet) {
			// Temporarily hardcoded to value as 'a' to solve nullpointer
			cp.setVirtualLinkRef("a");
		}
		return vcpSet.stream()
				.noneMatch(y -> y.getVirtualLinkRef().equals(x.getValue()));
	}

}
