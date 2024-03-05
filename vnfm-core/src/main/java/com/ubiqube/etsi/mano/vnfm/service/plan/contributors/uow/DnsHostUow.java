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

import com.ubiqube.etsi.mano.dao.mano.v2.DnsHostTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsHost;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;

import jakarta.annotation.Nullable;

/**
 *
 * @author olivier
 *
 */
public class DnsHostUow extends AbstractVnfmUow<DnsHostTask> {
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final DnsHostTask task;

	public DnsHostUow(final VirtualTaskV3<DnsHostTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, DnsHost.class);
		this.task = task.getTemplateParameters();
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		return vim.dns(vimConnectionInformation).createDnsRecordSet(task.getZoneId(), task.getHostname(), task.getNetworkName());
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		vim.dns(vimConnectionInformation).deleteDnsRecordSet(task.getVimResourceId(), task.getZoneId(), task.getIps());
		return null;
	}

}
