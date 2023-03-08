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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.uow;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.common.NicType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class VnfPortUowV3 extends AbstractVnfmUowV3<VnfPortTask> {
	@Nonnull
	private final Vim vim;
	@Nonnull
	private final VimConnectionInformation vimConnectionInformation;
	@Nonnull
	private final VnfPortTask task;

	public VnfPortUowV3(final VirtualTaskV3<VnfPortTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, VnfPortNode.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		if (task.getExternal() != null) {
			final ExtManagedVirtualLinkDataEntity ext = task.getExternal();
			return vim.network(vimConnectionInformation).createPort(getTask().getAlias(), ext.getResourceId(), null, null, NicType.fromValue(getTask().getTemplateParameters().getVnfLinkPort().getVnicType()));
		}
		final VnfLinkPort extCp = task.getVnfLinkPort();
		final String extNetwork = context.get(Network.class, extCp.getVirtualLink());
		return vim.network(vimConnectionInformation).createPort(getTask().getAlias(), extNetwork, null, null, NicType.fromValue(getTask().getTemplateParameters().getVnfLinkPort().getVnicType()));
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final VnfPortTask param = getTask().getTemplateParameters();
		vim.network(vimConnectionInformation).deletePort(param.getVimResourceId());
		return null;
	}

}
