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
package com.ubiqube.etsi.mano.nfvo.service.graph;

import java.util.Set;
import java.util.UUID;

import org.jgrapht.ListenableGraph;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.service.NsdPackageService;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.VnfContextExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.PortPairNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgLoadbalancerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgPostNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.uow.Relation;
import com.ubiqube.etsi.mano.service.graph.Edge2d;
import com.ubiqube.etsi.mano.service.graph.Graph2dBuilder;
import com.ubiqube.etsi.mano.service.graph.Vertex2d;

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
		nsd.getNsVirtualLinks().forEach(x -> g.single(Network.class, x.getToscaName())

		);
		nsd.getPnfdInfoIds();
		nsd.getVnffgs().forEach(x -> {
			g.single(VnffgPostNode.class, x.getName());
			x.getName();
			x.getNfpd().forEach(y -> {
				y.getInstances().forEach(z -> {
					g.from(VnffgPostNode.class, x.getName()).dependency(VnffgLoadbalancerNode.class, z.getToscaName(), Relation.MANY_TO_ONE);
					z.getPairs().forEach(p -> {
						g.from(VnffgLoadbalancerNode.class, z.getToscaName()).dependency(PortPairNode.class, p.getToscaName(), Relation.MANY_TO_ONE);
						g.from(PortPairNode.class, p.getToscaName()).dependency(VnfContextExtractorNode.class, p.getVnf(), Relation.ONE_TO_ONE);
					});
				});
			});
		});
		nsd.getVnfPkgIds().forEach(x -> {
			g.single(VnfCreateNode.class, x.getToscaName());
			g.from(VnfCreateNode.class, x.getToscaName()).addNext(VnfInstantiateNode.class, x.getToscaName(), Relation.ONE_TO_ONE);
			x.getVirtualLinks().forEach(y -> {
				final ForwarderMapping fm = findInFm(x.getForwardMapping(), y);
				g.from(Network.class, fm.getVlName()).addNext(VnfCreateNode.class, x.getToscaName(), Relation.ONE_TO_MANY);
			});
			g.from(VnfInstantiateNode.class, x.getToscaName()).addNext(VnfContextExtractorNode.class, x.getToscaName(), Relation.ONE_TO_ONE);
		});
		return g.build();
	}

	private static ForwarderMapping findInFm(final Set<ForwarderMapping> forwardMapping, final ListKeyPair lkp) {
		return forwardMapping.stream().filter(x -> x.getForwardingName().equals(lkp.getValue())).findFirst().orElseThrow(() -> new GenericException("Unable to find forwarder :" + lkp.getValue()));
	}
}
