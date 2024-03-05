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

import java.util.ArrayList;
import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.pm.PmType;
import com.ubiqube.etsi.mano.dao.mano.v2.MonitoringTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Monitoring;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.vnfm.service.VnfMonitoringService;

import jakarta.annotation.Nullable;

public class VnfMonitoringUow extends AbstractVnfmUow<MonitoringTask> {
	private final VnfMonitoringService vnfMonitoringService;
	private final VimConnectionInformation vimConnectionInformation;
	private final MonitoringTask task;

	public VnfMonitoringUow(final VirtualTaskV3<MonitoringTask> task, final VnfMonitoringService vnfMonitoringService, final VimConnectionInformation vimConnectionInformation) {
		super(task, Monitoring.class);
		this.vnfMonitoringService = vnfMonitoringService;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		String toscaName = "";
		List<String> l = new ArrayList<>();
		if (task.getMonitoringParams().getObjectType().equals(PmType.VNFC)) {
			toscaName = task.getVnfCompute().getToscaName();
			l = context.getParent(Compute.class, toscaName);
		} else if (task.getMonitoringParams().getObjectType().equals(PmType.VNF)) {
			l = context.getParent(Compute.class, "VNF_INDICATOR");
		}
		return vnfMonitoringService.registerMonitoring(l.get(0), /* task.getVnfInstance(), */task.getMonitoringParams(), vimConnectionInformation);
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final MonitoringTask params = getVirtualTask().getTemplateParameters();
		vnfMonitoringService.unregister(params.getVimResourceId());
		return null;
	}

}
