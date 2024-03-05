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

import java.util.Comparator;
import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.AffinityRuleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SecurityGroupNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.ComputeParameters;
import com.ubiqube.etsi.mano.service.vim.Vim;

import jakarta.annotation.Nullable;

public class VnfComputeUow extends AbstractVnfmUow<ComputeTask> {

	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final ComputeTask task;

	public VnfComputeUow(final VirtualTaskV3<ComputeTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, Compute.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final List<String> storages = task.getVnfCompute().getStorages().stream()
				.map(x -> context.getParent(Storage.class, getVirtualTask().getToscaName() + "-" + x))
				.flatMap(List::stream)
				.toList();
		final List<String> net = task.getVnfCompute().getNetworks().stream()
				.map(x -> context.getParent(Network.class, x))
				.flatMap(List::stream)
				.toList();
		final List<String> affinity = task.getVnfCompute().getAffinityRule().stream()
				.map(x -> context.getParent(AffinityRuleNode.class, x))
				.flatMap(List::stream)
				.toList();
		final List<String> security = task.getVnfCompute().getSecurityGroup().stream()
				.map(x -> context.getParent(SecurityGroupNode.class, x))
				.flatMap(List::stream)
				.toList();
		final List<String> ports = task.getVnfCompute().getPorts().stream()
				.sorted(Comparator.comparingInt(VnfLinkPort::getInterfaceOrder))
				.map(x -> context.getParent(VnfPortNode.class, x.getToscaName()))
				.flatMap(List::stream)
				.toList();
		final ComputeParameters computeParams = ComputeParameters.builder()
				.vimConnectionInformation(vimConnectionInformation)
				.instanceName(task.getAlias())
				.flavorId(task.getFlavorId())
				.imageId(task.getImageId())
				.networks(net)
				.storages(storages)
				.cloudInitData(task.getVnfCompute().getCloudInit())
				.securityGroup(security)
				.affinityRules(affinity)
				.portsId(ports)
				.build();
		return vim.createCompute(computeParams);
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final ComputeTask t = getVirtualTask().getTemplateParameters();
		vim.deleteCompute(vimConnectionInformation, t.getVimResourceId());
		return null;
	}

}
