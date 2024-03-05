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

import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceInstanceNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NetworkPolicyNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.AbstractUnitOfWork;
import com.ubiqube.etsi.mano.tf.ContrailApi;
import com.ubiqube.etsi.mano.tf.entities.NetworkPolicyTask;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class NetworkPolicyUow extends AbstractUnitOfWork<NetworkPolicyTask> {

	private final SystemConnections vimConnectionInformation;

	public NetworkPolicyUow(final VirtualTaskV3<NetworkPolicyTask> task, final SystemConnections vimConnectionInformation) {
		super(task, NetworkPolicyNode.class);
		this.vimConnectionInformation = vimConnectionInformation;
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final ContrailApi api = new ContrailApi();
		final NetworkPolicyTask p = getVirtualTask().getTemplateParameters();
		final String serviceInstance = context.get(ServiceInstanceNode.class, p.getToscaName());
		final String left = context.get(Network.class, p.getLeftId());
		final String right = context.get(Network.class, p.getRightId());
		final String name = UowNameHelper.buildName(p.getToscaName(), p.getInstanceId());
		return api.createNetworkPolicy(vimConnectionInformation, name, p.getClassifier(), serviceInstance, left, right);
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final ContrailApi api = new ContrailApi();
		api.deleteNetworkPolicy(vimConnectionInformation, getVirtualTask().getTemplateParameters().getVimResourceId());
		return null;
	}

}
