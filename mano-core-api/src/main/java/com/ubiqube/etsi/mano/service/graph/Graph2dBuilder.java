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

import java.util.Optional;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Edge2d;
import com.ubiqube.etsi.mano.orchestrator.Relation;
import com.ubiqube.etsi.mano.orchestrator.Vertex2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author olivier
 *
 */
public class Graph2dBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(Graph2dBuilder.class);
	@NotNull
	private final ListenableGraph<Vertex2d, Edge2d> g;

	public Graph2dBuilder() {
		g = new DefaultListenableGraph<>(new DirectedAcyclicGraph<>(Edge2d.class));
		g.addGraphListener(new GraphListener2d());
	}

	public void single(final Class<? extends Node> class1, final String name) {
		g.addVertex(addOrGet(class1, name, null));
	}

	public ChildBuilder addChild(final Class<? extends Node> class1, final String name) {
		return new ChildBuilder(class1, name);
	}

	protected Vertex2d addOrGet(final Class<? extends Node> class1, final String name, @Nullable final Vertex2d parent) {
		LOG.trace("Asking: {}, {}, {}", class1.getSimpleName(), name, Optional.ofNullable(parent).map(x -> x.toString().replace("\n", "-")));
		return g.vertexSet().stream().filter(x -> x.match(class1, name, parent)).findFirst().orElseGet(() -> {
			final Vertex2d v = new Vertex2d(class1, name, parent);
			g.addVertex(v);
			LOG.trace("Got: {}, {}, {}", class1.getSimpleName(), name, Optional.ofNullable(parent).map(x -> x.toString().replace("\n", "-")));
			return v;
		});
	}

	public class ChildBuilder {
		@Nonnull
		private final Class<? extends Node> class1;
		@Nonnull
		private final String name;

		public ChildBuilder(final Class<? extends Node> class1, final String name) {
			this.class1 = class1;
			this.name = name;
		}

		public void of(final Class<? extends Node> parentClass, final String parentName) {
			final Vertex2d left = addOrGet(parentClass, parentName, null);
			final Vertex2d v = addOrGet(class1, name, left);
			g.addEdge(left, v).setRelation(Relation.NONE);
		}

		public void dependency(final Class<? extends Node> depClass, final String depName, final Relation depRel) {
			final Vertex2d p = find(class1, name);
			final Vertex2d v;
			if (depRel == Relation.MULTI) {
				v = new Vertex2d(depClass, depName, p);
				g.addVertex(v);
				LOG.debug("Adding : {}", v);
			} else {
				v = addOrGet(depClass, depName, null);
			}
			Optional.ofNullable(g.addEdge(v, p)).ifPresent(x -> x.setRelation(depRel));
		}

		private Vertex2d find(final Class<? extends Node> depClass, final String depName) {
			return g.vertexSet().stream().filter(x -> x.match(depClass, depName, null)).findFirst().orElseThrow(() -> new GenericException("Unable to find vertex " + depClass.getSimpleName() + "/" + depName));
		}

		public void addNext(final Class<? extends Node> depClass, final String depName, final Relation depRel) {
			final Vertex2d p = find(class1, name);
			final Vertex2d v = new Vertex2d(depClass, depName, null);
			g.addVertex(v);
			Optional.ofNullable(g.addEdge(p, v)).ifPresent(x -> x.setRelation(depRel));
		}

		public void withSubTask(final Class<? extends Node> depClass, final String depName, final Relation depRel) {
			final Vertex2d p = find(class1, name);
			final Vertex2d v = new Vertex2d(depClass, name + "-" + depName, null);
			g.addVertex(v);
			Optional.ofNullable(g.addEdge(p, v)).ifPresent(x -> x.setRelation(depRel));
		}
	}

	public void multi(final Class<? extends Node> class1, final String toscaName) {
		final Vertex2d v = addOrGet(class1, toscaName, null);
		g.addVertex(v);
	}

	public ListenableGraph<Vertex2d, Edge2d> build() {
		return g;
	}

	public ChildBuilder from(final Class<? extends Node> class1, final String toscaName) {
		return new ChildBuilder(class1, toscaName);
	}

}
