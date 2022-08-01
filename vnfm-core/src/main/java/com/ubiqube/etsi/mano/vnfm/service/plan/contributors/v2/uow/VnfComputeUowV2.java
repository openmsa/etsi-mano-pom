/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.uow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.AffinityRuleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsHost;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SecurityGroupNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.orchestrator.uow.Relation;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.ComputeParameters;
import com.ubiqube.etsi.mano.service.vim.Vim;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class VnfComputeUowV2 extends AbstractUowV2<ComputeTask> {

	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final ComputeTask task;

	public VnfComputeUowV2(final VirtualTask<ComputeTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, Compute.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getParameters();
	}

	@Override
	public String execute(final Context context) {
		final List<String> storages = task.getVnfCompute().getStorages().stream()
				.map(x -> context.getParent(Storage.class, x + "-" + getTask().getAlias()))
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
				.map(x -> context.getParent(VnfPortNode.class, x.getToscaName() + "-" + getTask().getAlias()))
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
	public String rollback(final Context context) {
		final ComputeTask t = getTask().getParameters();
		vim.deleteCompute(vimConnectionInformation, t.getVimResourceId());
		return null;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		final List<NamedDependency> ret = new ArrayList<>();
		task.getVnfCompute().getStorages().stream()
				.map(x -> new NamedDependency(Storage.class, x + "-" + getTask().getAlias()))
				.forEach(ret::add);
		task.getVnfCompute().getNetworks().stream()
				.map(x -> new NamedDependency(Network.class, x))
				.forEach(ret::add);
		task.getVnfCompute().getAffinityRule().stream()
				.map(x -> new NamedDependency(AffinityRuleNode.class, x))
				.forEach(ret::add);
		task.getVnfCompute().getSecurityGroup().stream()
				.map(x -> new NamedDependency(SecurityGroupNode.class, x))
				.forEach(ret::add);
		task.getVnfCompute().getPorts().stream()
				.sorted(Comparator.comparingInt(VnfLinkPort::getInterfaceOrder))
				.map(x -> new NamedDependency(VnfPortNode.class, x.getToscaName() + "-" + getTask().getAlias()))
				.forEach(ret::add);
		ret.add(new NamedDependency(DnsHost.class, task.getToscaName()));
		return ret;
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(getNode(), task.getAlias()));
	}

	@Override
	public List<NamedDependency2d> get2dDependencies() {
		final List<NamedDependency2d> ret = new ArrayList<>();
		task.getVnfCompute().getStorages().stream()
				.map(x -> new NamedDependency2d(Storage.class, x, Relation.ONE_TO_ONE))
				.forEach(ret::add);
		task.getVnfCompute().getNetworks().stream()
				.map(x -> new NamedDependency2d(Network.class, x, Relation.MANY_TO_ONE))
				.forEach(ret::add);
		task.getVnfCompute().getAffinityRule().stream()
				.map(x -> new NamedDependency2d(AffinityRuleNode.class, x, Relation.MANY_TO_ONE))
				.forEach(ret::add);
		task.getVnfCompute().getSecurityGroup().stream()
				.map(x -> new NamedDependency2d(SecurityGroupNode.class, x, Relation.MANY_TO_ONE))
				.forEach(ret::add);
		task.getVnfCompute().getPorts().stream()
				.sorted(Comparator.comparingInt(VnfLinkPort::getInterfaceOrder))
				.map(x -> new NamedDependency2d(VnfPortNode.class, x.getToscaName(), Relation.ONE_TO_ONE))
				.forEach(ret::add);
		ret.add(new NamedDependency2d(DnsHost.class, task.getToscaName(), Relation.ONE_TO_ONE));
		return ret;
	}
}
