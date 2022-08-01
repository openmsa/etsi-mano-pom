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
package com.ubiqube.etsi.mano.service;

import java.util.UUID;

import org.jgrapht.ListenableGraph;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.AffinityRuleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsHost;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsZone;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Monitoring;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SecurityGroupNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SubNetwork;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.orchestrator.uow.Relation;
import com.ubiqube.etsi.mano.service.graph.Edge2d;
import com.ubiqube.etsi.mano.service.graph.Graph2dBuilder;
import com.ubiqube.etsi.mano.service.graph.Graph2dBuilder.ChildBuilder;
import com.ubiqube.etsi.mano.service.graph.Vertex2d;

/**
 *
 * @author olivier
 *
 */
@Service
public class VnfPlanService {
	private final VnfPackageService vnfService;

	public VnfPlanService(final VnfPackageService vnfService) {
		this.vnfService = vnfService;
	}

	public ListenableGraph<Vertex2d, Edge2d> getPlanFor(final UUID id) {
		final Graph2dBuilder g = new Graph2dBuilder();
		final VnfPackage vnfPkg = vnfService.findById(id);
		vnfPkg.getAffinityRules().forEach(x -> g.single(AffinityRuleNode.class, x.getToscaName()));
		vnfPkg.getVnfVl().forEach(x -> {
			g.single(Network.class, x.getToscaName());
			g.from(Network.class, x.getToscaName()).addNext(DnsZone.class, x.getToscaName(), Relation.ONE_TO_ONE);
			// x.getPlacementGroup()
			x.getVlProfileEntity().getVirtualLinkProtocolData().stream()
					.forEach(y -> g.from(Network.class, x.getToscaName()).addNext(SubNetwork.class, y.getL2ProtocolData().getName(), Relation.ONE_TO_ONE));
		});
		vnfPkg.getVnfStorage().forEach(x -> g.multi(Storage.class, x.getToscaName()));
		vnfPkg.getVnfCompute().forEach(x -> {
			g.single(Compute.class, x.getToscaName());
			x.getStorages().stream().forEach(y -> g.from(Compute.class, x.getToscaName()).dependency(Storage.class, y, Relation.MULTI));
			x.getAffinityRule().forEach(y -> g.addChild(AffinityRuleNode.class, y, Relation.MANY_TO_ONE).of(Compute.class, x.getToscaName()));
			x.getMonitoringParameters().forEach(y -> g.from(Compute.class, x.getToscaName()).addNext(Monitoring.class, y.getName(), Relation.ONE_TO_ONE));
			x.getSecurityGroup().forEach(y -> g.from(Compute.class, x.getToscaName()).dependency(SecurityGroupNode.class, y, Relation.ONE_TO_ONE));
			x.getPorts().forEach(y -> {
				if (null == y.getVirtualLink()) {
					return;
				}
				final VnfVl vl = vnfPkg.getVnfVl().stream().filter(z -> z.getToscaName().equals(y.getVirtualLink())).findFirst().orElseThrow(() -> new GenericException("Could not find Vl named: " + y.getVirtualLink()));
				g.from(Compute.class, x.getToscaName()).dependency(VnfPortNode.class, y.getToscaName(), Relation.ONE_TO_ONE);
				vl.getVlProfileEntity().getVirtualLinkProtocolData().stream()
						.forEach(z -> {
							g.from(VnfPortNode.class, y.getToscaName()).dependency(SubNetwork.class, z.getL2ProtocolData().getName(), Relation.MANY_TO_ONE);
							g.single(DnsHost.class, x.getToscaName());
							g.from(DnsHost.class, x.getToscaName()).addNext(VnfPortNode.class, y.getToscaName(), Relation.ONE_TO_ONE);
							g.from(DnsHost.class, x.getToscaName()).dependency(DnsZone.class, vl.getToscaName(), Relation.ONE_TO_MANY);
						});
			});
			x.getPlacementGroup().forEach(y -> g.from(Compute.class, x.getToscaName()).dependency(SecurityGroupNode.class, y.getToscaName(), Relation.MANY_TO_ONE));
		});
		vnfPkg.getOsContainer().forEach(x -> g.single(OsContainerNode.class, x.getName()));
		vnfPkg.getOsContainerDeployableUnits().forEach(x -> {
			final ChildBuilder v = g.addChild(OsContainerDeployableNode.class, x.getName(), Relation.MANY_TO_ONE);
			x.getContainerRef().forEach(y -> v.of(OsContainerNode.class, y));
		});
		vnfPkg.getMciops().forEach(x -> {
			g.single(HelmNode.class, x.getToscaName());
			x.getAssociatedVdu().forEach(y -> g.addChild(HelmNode.class, x.getToscaName(), Relation.MANY_TO_ONE).of(OsContainerDeployableNode.class, y));
		});
		return g.build();
	}
}
