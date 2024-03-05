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
package com.ubiqube.etsi.mano.service.graph;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.ubiqube.etsi.mano.orchestrator.nodes.ConnectivityEdge;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.service.graph.vnfm.EdgeListener;

/**
 *
 * @author olivier
 *
 */
public class GraphTools {

	private GraphTools() {
		// Nothing.
	}

	public static <U> ListenableGraph<U, ConnectivityEdge<U>> createGraph() {
		final Class<ConnectivityEdge<U>> t = (Class<ConnectivityEdge<U>>) (Object) ConnectivityEdge.class;
		// Vertex everyThing
		final ListenableGraph<U, ConnectivityEdge<U>> g = new DefaultListenableGraph<>(new DirectedAcyclicGraph<>(t));
		g.addGraphListener(new EdgeListener<>());
		return g;
	}

	public static <U> ListenableGraph<U, ConnectivityEdge<U>> revert(final ListenableGraph<U, ConnectivityEdge<U>> g) {
		final ListenableGraph<U, ConnectivityEdge<U>> gNew = GraphTools.createGraph();
		g.vertexSet().forEach(gNew::addVertex);
		g.edgeSet().forEach(x -> gNew.addEdge(x.getTarget(), x.getSource()));
		return gNew;
	}

	public static String toDotName(final UnitOfWorkV3<?> task) {
		final String base = task.getType().getSimpleName() + "_" + task.getVirtualTask().getName() + "_" + String.format("%04d", task.getVirtualTask().getRank());
		return base.replace("/", "_").replace("-", "_").replace("\n", "_").replace(",", "_").replace("(", "_").replace(")", "_").replace(" ", "_");
	}

}
