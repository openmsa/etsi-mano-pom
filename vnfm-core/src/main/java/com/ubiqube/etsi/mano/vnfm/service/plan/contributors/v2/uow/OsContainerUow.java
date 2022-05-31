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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.uow;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.dao.mano.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.CnfK8sParams;
import com.ubiqube.etsi.mano.service.vim.Vim;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class OsContainerUow extends AbstractUowV2<OsContainerTask> {
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final VirtualTask<OsContainerTask> task;

	public OsContainerUow(final VirtualTask<OsContainerTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, OsContainerNode.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task;
	}

	@Override
	public String execute(final Context context) {
		final CnfInformations cnfi = vimConnectionInformation.getCnfInfo();
		final OsContainer osc = task.getParameters().getOsContainer();
		osc.getCpuResourceLimit();
		osc.getEphemeralStorageResourceLimit();
		osc.getExtendedResourceRequests();
		osc.getHugePagesResources();
		osc.getMemoryResourceLimit();
		osc.getRequestedCpuResources();
		osc.getRequestedEphemeralStorageResources();
		osc.getRequestedMemoryResources();
		final CnfK8sParams params = CnfK8sParams.builder()
				.clusterTemplate(cnfi.getClusterTemplate())
				.dnsServer(cnfi.getDnsServer())
				.externalNetworkId("")
				.keypair(cnfi.getKeyPair())
				.masterFlavor(cnfi.getMasterFlavorId())
				.name(task.getAlias())
				.networkDriver("flannel")
				.serverType("vm")
				.volumeSize(10)
				.build();
		vim.cnf(vimConnectionInformation).createContainer(params);
		return null;
	}

	@Override
	public String rollback(final Context context) {
		vim.cnf(vimConnectionInformation).deleteContainer(task.getParameters().getVimResourceId());
		return null;
	}

}
