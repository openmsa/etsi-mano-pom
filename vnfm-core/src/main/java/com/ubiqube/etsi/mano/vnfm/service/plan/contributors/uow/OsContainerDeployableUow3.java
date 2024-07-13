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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import java.util.Optional;

import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class OsContainerDeployableUow3 extends AbstractVnfmUow<OsContainerDeployableTask> {
	@Nonnull
	private final Vim vim;
	@Nonnull
	private final VimConnectionInformation vci;
	@Nonnull
	private final OsContainerDeployableTask task;

	public OsContainerDeployableUow3(final VirtualTaskV3<OsContainerDeployableTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, OsContainerDeployableNode.class);
		this.vim = vim;
		this.vci = vimConnectionInformation;
		this.task = task.getTemplateParameters();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final String network = Optional.ofNullable(task.getNetwork()).map(x -> context.get(Network.class, x)).orElse(null);
		final OsContainerDeployableUnit du = task.getOsContainerDeployableUnit();
		final CnfInformations ci = vci.getCnfInfo();
		if (ci == null) {
			return null;
		}
		return vim.cnf(vci).createK8sCluster("clusterTemplateId", ci.getKeyPair(), du.getVduProfile().getMinNumberOfInstances(), task.getToscaName(), du.getVduProfile().getMinNumberOfInstances(), network);
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		vim.cnf(vci).deleteK8s(task.getVimResourceId());
		return null;
	}

}
