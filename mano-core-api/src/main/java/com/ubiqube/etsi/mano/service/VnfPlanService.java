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
package com.ubiqube.etsi.mano.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.jgrapht.ListenableGraph;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.orchestrator.Edge2d;
import com.ubiqube.etsi.mano.orchestrator.Relation;
import com.ubiqube.etsi.mano.orchestrator.Vertex2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.AffinityRuleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsHost;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsZone;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.MciopUser;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Monitoring;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SecurityGroupNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SecurityRuleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SubNetwork;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfExtCp;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfIndicator;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfIndicatorStart;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.service.graph.Graph2dBuilder;

/**
 *
 * @author olivier
 *
 */
@Service
public class VnfPlanService {
	private static final String MASTER_ZONE = "master-zone";
	private final VnfPackageService vnfService;

	public VnfPlanService(final VnfPackageService vnfService) {
		this.vnfService = vnfService;
	}

	public ListenableGraph<Vertex2d, Edge2d> getPlanFor(final UUID id) {
		final Graph2dBuilder g = new Graph2dBuilder();
		final VnfPackage vnfPkg = vnfService.findById(id);
		vnfPkg.getAffinityRules().forEach(x -> g.single(AffinityRuleNode.class, x.getToscaName()));
		vnfPkg.getSecurityGroups().forEach(x -> {
			g.single(SecurityGroupNode.class, x.getToscaName());
			g.from(SecurityGroupNode.class, x.getToscaName()).addNext(SecurityRuleNode.class, x.getToscaName(), Relation.ONE_TO_ONE);
		});
		g.single(DnsZone.class, MASTER_ZONE);
		vnfPkg.getVnfVl().forEach(x -> {
			g.single(Network.class, x.getToscaName());
			g.from(Network.class, x.getToscaName()).dependency(DnsZone.class, MASTER_ZONE, Relation.ONE_TO_MANY);
			// x.getPlacementGroup()
			x.getVlProfileEntity().getVirtualLinkProtocolData().stream()
					.forEach(y -> g.from(Network.class, x.getToscaName()).withSubTask(SubNetwork.class, y.getL2ProtocolData().getName(), Relation.ONE_TO_ONE));
		});
		vnfPkg.getVnfExtCp().forEach(x -> {
			g.single(VnfExtCp.class, x.getToscaName());
			final VnfVl vl = findVl(vnfPkg.getVnfVl(), x.getInternalVirtualLink());
			vl.getVlProfileEntity().getVirtualLinkProtocolData().forEach(y -> g.from(SubNetwork.class, vl.getToscaName() + "-" + y.getL2ProtocolData().getName()).addNext(VnfExtCp.class, x.getToscaName(), Relation.ONE_TO_ONE));
		});
		vnfPkg.getVnfStorage().forEach(x -> g.multi(Storage.class, x.getToscaName()));
		vnfPkg.getVnfCompute().forEach(x -> {
			g.single(Compute.class, x.getToscaName());
			x.getStorages().stream().forEach(y -> g.from(Compute.class, x.getToscaName()).dependency(Storage.class, x.getToscaName() + "-" + y, Relation.MULTI));
			x.getAffinityRule().forEach(y -> g.addChild(AffinityRuleNode.class, y).of(Compute.class, x.getToscaName()));
			x.getMonitoringParameters().forEach(y -> g.from(Compute.class, x.getToscaName()).withSubTask(Monitoring.class, y.getName(), Relation.ONE_TO_ONE));
			x.getSecurityGroup().forEach(y -> g.from(Compute.class, x.getToscaName()).dependency(SecurityGroupNode.class, y, Relation.MANY_TO_ONE));
			x.getPorts().forEach(y -> {
				if (null == y.getVirtualLink()) {
					final ListKeyPair port = findPort(vnfPkg, y);
					g.single(Network.class, port.getValue());
					g.from(Compute.class, x.getToscaName()).dependency(VnfPortNode.class, port.getValue(), Relation.ONE_TO_ONE);
					g.from(VnfPortNode.class, port.getValue()).dependency(Network.class, port.getValue(), Relation.ONE_TO_MANY);
					return;
				}
				g.from(Compute.class, x.getToscaName()).dependency(VnfPortNode.class, y.getToscaName(), Relation.ONE_TO_ONE);
				final Optional<VnfVl> vlOpt = vnfPkg.getVnfVl().stream().filter(z -> z.getToscaName().equals(y.getVirtualLink())).findFirst();
				if (vlOpt.isPresent()) {
					final VnfVl vl = vlOpt.get();
					vl.getVlProfileEntity().getVirtualLinkProtocolData().stream()
							.forEach(z -> g.from(VnfPortNode.class, y.getToscaName()).dependency(SubNetwork.class, vl.getToscaName() + "-" + z.getL2ProtocolData().getName(), Relation.ONE_TO_MANY));
				}
			});
			x.getPlacementGroup().forEach(y -> g.from(Compute.class, x.getToscaName()).dependency(SecurityGroupNode.class, y.getToscaName(), Relation.MANY_TO_ONE));
			g.single(DnsHost.class, x.getToscaName());
			g.from(DnsHost.class, x.getToscaName()).addNext(Compute.class, x.getToscaName(), Relation.ONE_TO_ONE);
			g.from(DnsHost.class, x.getToscaName()).dependency(DnsZone.class, MASTER_ZONE, Relation.ONE_TO_MANY);
		});
		vnfPkg.getOsContainerDeployableUnits().forEach(x -> {
			g.single(OsContainerDeployableNode.class, x.getName());
			g.from(OsContainerDeployableNode.class, x.getName()).addNext(OsK8sInformationsNode.class, x.getName(), Relation.ONE_TO_ONE);
			x.getVirtualStorageReq().forEach(y -> g.from(Storage.class, y).addNext(OsContainerDeployableNode.class, x.getName(), Relation.MULTI));
			g.from(OsK8sInformationsNode.class, x.getName()).addNext(MciopUser.class, x.getName(), Relation.ONE_TO_ONE);
			g.from(DnsZone.class, MASTER_ZONE).addNext(OsContainerDeployableNode.class, x.getName(), Relation.ONE_TO_MANY);
		});
		vnfPkg.getOsContainer().forEach(x -> {
			final String oscdn = find(vnfPkg.getOsContainerDeployableUnits(), x.getName());
			g.from(OsK8sInformationsNode.class, oscdn).addNext(OsContainerNode.class, x.getName(), Relation.ONE_TO_MANY);
		});
		vnfPkg.getMciops().forEach(x -> {
			g.single(HelmNode.class, x.getToscaName());
			final String vdu = x.getAssociatedVdu().iterator().next();
			g.from(MciopUser.class, vdu).addNext(HelmNode.class, x.getToscaName(), Relation.ONE_TO_MANY);
		});
		vnfPkg.getVnfIndicator().forEach(x -> {
			g.single(VnfIndicatorStart.class, x.getName());
			g.single(VnfIndicator.class, x.getName());
			vnfPkg.getVnfCompute().forEach(z -> g.from(VnfIndicator.class, x.getName()).dependency(Compute.class, z.getToscaName(), Relation.ONE_TO_MANY));
			g.from(VnfIndicatorStart.class, x.getName()).addNext(VnfIndicator.class, x.getName(), Relation.ONE_TO_MANY);
			// Link to monitoring instead ?
		});
		return g.build();
	}

	private static VnfVl findVl(final Set<VnfVl> vnfVl, final String internalVirtualLink) {
		return vnfVl.stream().filter(x -> x.getToscaName().equals(internalVirtualLink)).findFirst()
				.orElseThrow(() -> new NotFoundException("Unable to find VL: " + internalVirtualLink));
	}

	private static String find(final Set<OsContainerDeployableUnit> osContainerDeployableUnits, final String name) {
		return osContainerDeployableUnits.stream()
				.filter(x -> x.getContainerReq().contains(name))
				.map(OsContainerDeployableUnit::getName)
				.findFirst()
				.orElseThrow();
	}

	private static ListKeyPair findPort(final VnfPackage vnfPkg, final VnfLinkPort vnfLinkPort) {
		return vnfPkg.getVirtualLinks().stream()
				.filter(x -> x.getValue().equals(vnfLinkPort.getToscaName()))
				.findFirst()
				.orElseThrow();
	}
}
