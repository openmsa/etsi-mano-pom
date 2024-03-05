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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service.plan.uow;

import java.util.List;

import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PortTupleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceInstanceNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.AbstractUnitOfWork;
import com.ubiqube.etsi.mano.tf.ContrailApi;
import com.ubiqube.etsi.mano.tf.entities.PortTupleTask;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class PortTupleUow extends AbstractUnitOfWork<PortTupleTask> {
	@Nonnull
	private final SystemConnections vimConnectionInformation;
	@Nonnull
	private final PortTupleTask task;

	public PortTupleUow(final VirtualTaskV3<PortTupleTask> task, final SystemConnections vimConnectionInformation) {
		super(task, PortTupleNode.class);
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final ContrailApi api = new ContrailApi();
		final List<String> serviceInstanceId = context.getParent(ServiceInstanceNode.class, task.getServiceInstanceName());
		final String name = UowNameHelper.buildName(task.getToscaName(), task.getInstanceId());
		return api.createPortTuple(vimConnectionInformation, name, serviceInstanceId.get(0));
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final ContrailApi api = new ContrailApi();
		api.deletePortTuple(vimConnectionInformation, task.getVimResourceId());
		return null;
	}

}
