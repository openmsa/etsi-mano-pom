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

import com.ubiqube.etsi.mano.dao.mano.v2.ExternalCpTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfExtCp;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;

import jakarta.annotation.Nullable;

public class VnfExtCpUow extends AbstractVnfmUow<ExternalCpTask> {
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final ExternalCpTask task;

	public VnfExtCpUow(final VirtualTaskV3<ExternalCpTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, VnfExtCp.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final com.ubiqube.etsi.mano.dao.mano.VnfExtCp extCp = task.getVnfExtCp();
		final String networkId = context.get(Network.class, extCp.getInternalVirtualLink());
		final String extNetwork = context.get(Network.class, extCp.getExternalVirtualLink());
		return vim.network(vimConnectionInformation).createRouter(task.getAlias(), networkId, extNetwork);
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final ExternalCpTask param = getVirtualTask().getTemplateParameters();
		vim.network(vimConnectionInformation).deleteRouter(param.getVimResourceId());
		return null;
	}

}
