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
package com.ubiqube.etsi.mano.nfvo.service.graph;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.jgrapht.ListenableGraph;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.nfvo.service.NsdPackageService;
import com.ubiqube.etsi.mano.orchestrator.Edge2d;
import com.ubiqube.etsi.mano.orchestrator.Relation;
import com.ubiqube.etsi.mano.orchestrator.Vertex2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PortTupleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PtLinkNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceInstanceNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceTemplateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.NsdExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.VnfExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NetworkPolicyNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NsdCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NsdInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.PortPairNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgLoadbalancerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgPostNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.service.graph.Graph2dBuilder;

/**
 *
 * @author olivier
 *
 */
@Service
public class NsPlanService {
	private final NsdPackageService nsdPackageService;

	public NsPlanService(final NsdPackageService nsdPackageService) {
		this.nsdPackageService = nsdPackageService;
	}

	public ListenableGraph<Vertex2d, Edge2d> getPlanFor(final UUID id) {
		final Graph2dBuilder g = new Graph2dBuilder();
		final NsdPackage nsd = nsdPackageService.findById(id);
		nsd.getNsSaps();
		nsd.getNsVirtualLinks().forEach(x -> g.single(Network.class, x.getToscaName()));
		nsd.getPnfdInfoIds();
		nsd.getNestedNsdInfoIds().forEach(x -> {
			g.single(NsdCreateNode.class, x.getToscaName());
			g.from(NsdCreateNode.class, x.getToscaName()).addNext(NsdInstantiateNode.class, x.getToscaName(), Relation.ONE_TO_ONE);
			x.getVirtualLinks().forEach(y -> g.from(Network.class, y).addNext(NsdInstantiateNode.class, x.getToscaName(), Relation.MANY_TO_ONE));
			g.from(NsdInstantiateNode.class, x.getToscaName()).addNext(NsdExtractorNode.class, x.getToscaName(), Relation.ONE_TO_ONE);
		});
		// makeSfcPlan(g, nsd)
		nsd.getVnfPkgIds().forEach(x -> {
			g.single(VnfCreateNode.class, x.getToscaName());
			g.from(VnfCreateNode.class, x.getToscaName()).addNext(VnfInstantiateNode.class, x.getToscaName(), Relation.ONE_TO_ONE);
			x.getVirtualLinks().forEach(y -> {
				final Optional<ForwarderMapping> fm = findInFm(x.getForwardMapping(), y);
				if (fm.isPresent()) {
					g.from(Network.class, fm.get().getVlName()).addNext(VnfInstantiateNode.class, x.getToscaName(), Relation.ONE_TO_MANY);
				} else {
					g.from(Network.class, y.getValue()).addNext(VnfInstantiateNode.class, x.getToscaName(), Relation.MANY_TO_ONE);
				}
			});
			g.from(VnfInstantiateNode.class, x.getToscaName()).addNext(VnfExtractorNode.class, x.getToscaName(), Relation.ONE_TO_ONE);
		});
		doContrail(nsd, g);
		return g.build();
	}

	private static void makeSfcPlan(final Graph2dBuilder g, final NsdPackage nsd) {
		nsd.getVnffgs().forEach(x -> {
			g.single(VnffgPostNode.class, x.getName());
			x.getName();
			x.getNfpd().forEach(y -> y.getInstances().forEach(z -> {
				g.from(VnffgPostNode.class, x.getName()).dependency(VnffgLoadbalancerNode.class, z.getToscaName(), Relation.MANY_TO_ONE);
				z.getPairs().forEach(p -> {
					g.from(VnffgLoadbalancerNode.class, z.getToscaName()).dependency(PortPairNode.class, p.getToscaName(), Relation.MANY_TO_ONE);
					g.from(PortPairNode.class, p.getToscaName()).dependency(VnfExtractorNode.class, p.getVnf(), Relation.ONE_TO_ONE);
				});
			}));
		});
	}

	private static void doContrail(final NsdPackage nsd, final Graph2dBuilder g) {
		if (nsd.getVnffgs().isEmpty()) {
			return;
		}
		nsd.getVnffgs().forEach(x -> {
			g.single(ServiceTemplateNode.class, x.getName());
			x.getNfpd().stream().flatMap(y -> y.getInstances().stream()).forEach(y -> {
				g.single(ServiceInstanceNode.class, y.getToscaName());
				// Add networks for SI
				g.from(ServiceInstanceNode.class, y.getToscaName()).dependency(ServiceTemplateNode.class, x.getName(), Relation.MANY_TO_ONE);
				// g.from(ServiceInstanceNode.class, y.getToscaName()).addNext(PolicyRule.class,
				// y.getToscaName(), Relation.ONE_TO_ONE)
				g.single(NetworkPolicyNode.class, y.getToscaName());
				g.from(NetworkPolicyNode.class, y.getToscaName()).dependency(ServiceInstanceNode.class, y.getToscaName(), Relation.ONE_TO_ONE);
				// Add Networks.
				y.getPairs().forEach(z -> {
					g.from(ServiceInstanceNode.class, y.getToscaName()).addNext(PortTupleNode.class, z.getToscaName(), Relation.MANY_TO_ONE);
					g.from(ServiceInstanceNode.class, y.getToscaName()).dependency(Network.class, z.getIngressVl(), Relation.MANY_TO_ONE);
					g.from(ServiceInstanceNode.class, y.getToscaName()).dependency(Network.class, z.getEgressVl(), Relation.MANY_TO_ONE);
					g.from(PortTupleNode.class, z.getToscaName()).addNext(PtLinkNode.class, z.getToscaName(), Relation.ONE_TO_ONE);
					g.from(VnfExtractorNode.class, z.getVnf()).addNext(PtLinkNode.class, z.getToscaName(), Relation.ONE_TO_ONE);
				});
			});
		});
	}

	private static Optional<ForwarderMapping> findInFm(final Set<ForwarderMapping> forwardMapping, final ListKeyPair lkp) {
		return forwardMapping.stream().filter(x -> x.getForwardingName().equals(lkp.getValue())).findFirst();
	}
}
