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

import java.util.stream.Collectors;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.common.NicType;
import com.ubiqube.etsi.mano.dao.mano.v2.IpSubnet;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.FixedIp;
import com.ubiqube.etsi.mano.service.vim.Port;
import com.ubiqube.etsi.mano.service.vim.Vim;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class VnfPortUow extends AbstractVnfmUow<VnfPortTask> {
	@Nonnull
	private final Vim vim;
	@Nonnull
	private final VimConnectionInformation vimConnectionInformation;

	public VnfPortUow(final VirtualTaskV3<VnfPortTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, VnfPortNode.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final VnfPortTask task = getTask();
		Port p;
		if (task.getExternal() != null) {
			final ExtManagedVirtualLinkDataEntity ext = task.getExternal();
			p = vim.network(vimConnectionInformation).createPort(getVirtualTask().getAlias(), ext.getResourceId(), null, null, NicType.fromValue(getVirtualTask().getTemplateParameters().getVnfLinkPort().getVnicType()));
		} else {
			final VnfLinkPort extCp = task.getVnfLinkPort();
			final String extNetwork = context.get(Network.class, extCp.getVirtualLink());
			p = vim.network(vimConnectionInformation).createPort(getVirtualTask().getAlias(), extNetwork, null, null, NicType.fromValue(getVirtualTask().getTemplateParameters().getVnfLinkPort().getVnicType()));
		}
		task.setIpSubnet(p.getFixedIps().stream().map(this::map).collect(Collectors.toSet()));
		task.setMacAddress(p.getMacAddress());
		return p.getId().toString();
	}

	private IpSubnet map(final FixedIp ip) {
		final IpSubnet is = new IpSubnet();
		is.setIp(ip.getIp());
		is.setSubnetId(ip.getSubnetId());
		return is;
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final VnfPortTask param = getVirtualTask().getTemplateParameters();
		vim.network(vimConnectionInformation).deletePort(param.getVimResourceId());
		return null;
	}

}
