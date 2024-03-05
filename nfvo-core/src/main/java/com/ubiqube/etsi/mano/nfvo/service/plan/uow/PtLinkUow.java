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

import java.util.UUID;

import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PortTupleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PtLinkNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.AbstractUnitOfWork;
import com.ubiqube.etsi.mano.tf.ContrailApi;
import com.ubiqube.etsi.mano.tf.entities.PtLinkTask;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class PtLinkUow extends AbstractUnitOfWork<PtLinkTask> {
	private final SystemConnections vimConnectionInformation;
	private final PtLinkTask task;

	public PtLinkUow(final VirtualTaskV3<PtLinkTask> ptLinkVt, final SystemConnections vimConnectionInformation2) {
		super(ptLinkVt, PtLinkNode.class);
		this.vimConnectionInformation = vimConnectionInformation2;
		this.task = ptLinkVt.getTemplateParameters();
	}

	@Override
	public String execute(final Context3d context) {
		final ContrailApi api = new ContrailApi();
		final String leftUuid = context.get(VnfPortNode.class, task.getLeftPortId());
		final String portTupleId = context.get(PortTupleNode.class, task.getPortTupleName());
		api.updatePort(vimConnectionInformation, leftUuid, portTupleId, "left");
		final String rightUuid = context.get(VnfPortNode.class, task.getRightPortId());
		api.updatePort(vimConnectionInformation, rightUuid, portTupleId, "right");
		return UUID.randomUUID().toString();
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final ContrailApi api = new ContrailApi();
		final String portTupleId = context.get(PortTupleNode.class, task.getPortTupleName());
		api.rollbackVmi(vimConnectionInformation, portTupleId);
		return null;
	}

}
