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

import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.VlProtocolData;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsZone;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;

import jakarta.annotation.Nullable;

public class VirtualLinkUow extends AbstractVnfmUow<NetworkTask> {
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final VlProtocolData vlProtocolData;
	private final NetworkTask task;

	public VirtualLinkUow(final VirtualTaskV3<NetworkTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, Network.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
		vlProtocolData = task.getTemplateParameters().getVnfVl().getVlProfileEntity().getVirtualLinkProtocolData().iterator().next();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final String domainName = context.getOptional(DnsZone.class, getVirtualTask().getTemplateParameters().getToscaName());
		return vim.network(vimConnectionInformation).createNetwork(vlProtocolData, task.getAlias(), domainName, task.getQosPolicy());
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final NetworkTask params = getVirtualTask().getTemplateParameters();
		vim.network(vimConnectionInformation).deleteVirtualLink(params.getVimResourceId());
		return null;
	}

}
