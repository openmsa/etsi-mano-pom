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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.magnum;

import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.CnfK8sParams;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.AbstractVnfmUow;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class OsMagnumContainerUow extends AbstractVnfmUow<OsContainerTask> {
	@Nonnull
	private final Vim vim;
	@Nonnull
	private final VimConnectionInformation vimConnectionInformation;
	@Nonnull
	private final OsContainerTask task;

	public OsMagnumContainerUow(final VirtualTaskV3<OsContainerTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, OsContainerNode.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final CnfInformations cnfi = vimConnectionInformation.getCnfInfo();
		final OsContainer osc = task.getOsContainer();
		osc.getCpuResourceLimit();
		osc.getEphemeralStorageResourceLimit();
		osc.getExtendedResourceRequests();
		osc.getHugePagesResources();
		osc.getMemoryResourceLimit();
		osc.getRequestedCpuResources();
		osc.getRequestedEphemeralStorageResources();
		osc.getRequestedMemoryResources();
		final CnfK8sParams params = CnfK8sParams.builder()
				.clusterTemplate("" /* cnfi.getClusterTemplate() */)
				.dnsServer(cnfi.getDnsServer())
				.externalNetworkId("")
				.keypair(cnfi.getKeyPair())
				.masterFlavor("" /* cnfi.getMasterFlavorId() */)
				.name(task.getAlias())
				.networkDriver("flannel")
				.serverType("vm")
				.volumeSize(10)
				.build();
		vim.cnf(vimConnectionInformation).createContainer(params);
		return null;
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		vim.cnf(vimConnectionInformation).deleteContainer(task.getVimResourceId());
		return null;
	}

}
