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

import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfIndicatorTask;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.mon.ExternalAlarm;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud
 *
 */
public class VnfIndicatorUow extends AbstractVnfmUow<VnfIndicatorTask> {

	private final ExternalAlarm externalAlarm;

	private final VnfIndicatorTask task;

	public VnfIndicatorUow(final VirtualTaskV3<VnfIndicatorTask> task, final Class<? extends Node> node, final ExternalAlarm externalAlarm) {
		super(task, node);
		this.task = task.getTemplateParameters();
		this.externalAlarm = externalAlarm;
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final VnfIndicator vnfInd = task.getVnfIndicator();
		vnfInd.getTriggers();
		return externalAlarm.registerAlarm(vnfInd);
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		externalAlarm.remove(task.getRemovedLiveInstance());
		return null;
	}

}
