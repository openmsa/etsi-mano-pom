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
package com.ubiqube.etsi.mano.controllers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxStyleUtils;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsBlueprintJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsPlanService;
import com.ubiqube.etsi.mano.orchestrator.nodes.ConnectivityEdge;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.scale.ScalingEngine;
import com.ubiqube.etsi.mano.service.VnfPlanService;
import com.ubiqube.etsi.mano.service.graph.Edge2d;
import com.ubiqube.etsi.mano.service.graph.Vertex2d;

/**
 *
 * @author olivier
 *
 */
@RestController
@RequestMapping("/admin")
public class Poc {

	private static final Logger LOG = LoggerFactory.getLogger(Poc.class);

	private final NsLiveInstanceJpa nsLiveInstanceJpa;
	private final NsBlueprintJpa nsBlueprintJpa;
	private final VnfPlanService vnfPlanService;
	private final NsPlanService nsPlanService;

	public Poc(final NsLiveInstanceJpa nsLiveInstanceJpa, final NsBlueprintJpa nsBlueprintJpa, final VnfPlanService vnfPlanService, final NsPlanService nsPlanService) {
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
		this.nsBlueprintJpa = nsBlueprintJpa;
		this.vnfPlanService = vnfPlanService;
		this.nsPlanService = nsPlanService;
	}

//	@GetMapping("/plan/ns/2d/{id}")
	public ResponseEntity<BufferedImage> getNs2dPlan(@PathVariable("id") final UUID id) {
		final ListenableGraph<Vertex2d, Edge2d> g = nsPlanService.getPlanFor(id);
		return ResponseEntity
				.ok().contentType(MediaType.IMAGE_PNG)
				.body(drawGraph2(g));
	}

	@GetMapping("/plan/ns/3d/{id}")
	public ResponseEntity<BufferedImage> getNs3dPlan(@PathVariable("id") final UUID id) {
		final ListenableGraph<Vertex2d, Edge2d> o = nsPlanService.getPlanFor(id);
		final ScalingEngine se = new ScalingEngine();
		final ListenableGraph<Vertex2d, Edge2d> g = se.scale(o, VnfCreateNode.class, "vnf_left");
		return ResponseEntity
				.ok().contentType(MediaType.IMAGE_PNG)
				.body(drawGraph2(g));
	}

//	@GetMapping("/plan/vnf/2d/{id}")
	public ResponseEntity<BufferedImage> getVnf2dPlan(@PathVariable("id") final UUID id) {
		final ListenableGraph<Vertex2d, Edge2d> g = vnfPlanService.getPlanFor(id);
		return ResponseEntity
				.ok().contentType(MediaType.IMAGE_PNG)
				.body(drawGraph2(g));
	}

	@GetMapping("/plan/vnf/3d/{id}")
	public ResponseEntity<BufferedImage> getVnf3dPlan(@PathVariable("id") final UUID id) {
		final ListenableGraph<Vertex2d, Edge2d> o = vnfPlanService.getPlanFor(id);
		final ScalingEngine se = new ScalingEngine();
		final ListenableGraph<Vertex2d, Edge2d> g = se.scale(o, Compute.class, "leftVdu01");
		return ResponseEntity
				.ok().contentType(MediaType.IMAGE_PNG)
				.body(drawGraph2(g));
	}

//	@GetMapping("/ns/lcm-op-occs/{id}")
	public ResponseEntity<BufferedImage> getDeployementPicture(@PathVariable("id") final UUID id) {
		final NsBlueprint blueprint = nsBlueprintJpa.findById(id).orElseThrow();
		final List<NsLiveInstance> liveInst = nsLiveInstanceJpa.findByNsInstanceId(blueprint.getInstance().getId());
		final List<TaskVertex> vertices = blueprint.getTasks().stream().map(x -> toVertex(x, liveInst)).toList();
		final ListenableGraph<TaskVertex, ConnectivityEdge<TaskVertex>> g = new DefaultListenableGraph<>(new DirectedAcyclicGraph<>(ConnectivityEdge.class));
		final DOTExporter<TaskVertex, ConnectivityEdge<TaskVertex>> exporter = new DOTExporter<>();
		vertices.forEach(g::addVertex);
		exporter.setVertexAttributeProvider(x -> {
			final Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(x.getAlias()));
			if (x.getStatus() == VertexStatusType.FAILED) {
				map.put("fillcolor", DefaultAttribute.createAttribute("brown1"));
			} else {
				map.put("fillcolor", DefaultAttribute.createAttribute("aquamarine1"));
			}
			return map;
		});
		try (final FileOutputStream out = new FileOutputStream("/home/olivier/graph.dot")) {
			exporter.exportGraph(g, out);
		} catch (final IOException e) {
			LOG.trace("Error in graph export", e);
		}
		final Iterator<TaskVertex> vs = g.vertexSet().iterator();
		g.addEdge(vs.next(), vs.next());
		return ResponseEntity
				.ok().contentType(MediaType.IMAGE_PNG)
				.body(drawGraph(g));
	}

	private static TaskVertex toVertex(final NsTask x, final List<NsLiveInstance> liveInst) {
		return new TaskVertex(x.getAlias(), mapStatus(liveInst, x));
	}

	private static VertexStatusType mapStatus(final List<NsLiveInstance> liveInst, final NsTask nsTask) {
		final Optional<NsTask> live = liveInst.stream()
				.map(NsLiveInstance::getNsTask)
				.filter(x -> x.getAlias().equals(nsTask.getAlias()) && (x.getType() == nsTask.getType()))
				.findFirst();
		if (live.isPresent()) {
			return VertexStatusType.SUCCESS;
		}
		return VertexStatusType.FAILED;
	}

	public static <V, E> BufferedImage drawGraph(final Graph<V, E> graph) {
		final JGraphXAdapter<V, E> graphAdapter = new JGraphXAdapter<>(graph);
		final mxIGraphLayout layout = new mxHierarchicalLayout(graphAdapter);
		layout.execute(graphAdapter.getDefaultParent());
		graphAdapter.getVertexToCellMap().entrySet().forEach(x -> {
			final Object[] lcell = Arrays.asList(x.getValue()).toArray();
			mxStyleUtils.setCellStyles(graphAdapter.getModel(), lcell, mxConstants.STYLE_ROUNDED, "true");
			final TaskVertex v = (TaskVertex) x.getValue().getValue();
			if (v.getStatus() == VertexStatusType.SUCCESS) {
				graphAdapter.setCellStyle("fillColor=7FFFD4", lcell);
			} else {
				graphAdapter.setCellStyle("fillColor=FF4040", lcell);
				mxStyleUtils.setCellStyles(graphAdapter.getModel(), lcell, mxConstants.STYLE_FONTCOLOR, "F0F0F0");
			}
		});
		return mxCellRenderer.createBufferedImage(graphAdapter, null, 1, new Color(255, 255, 255, 255), true, null);
	}

	public static <V, E> BufferedImage drawGraph2(final Graph<V, E> graph) {
		final JGraphXAdapter<V, E> graphAdapter = new JGraphXAdapter<>(graph);
		final mxIGraphLayout layout = new mxHierarchicalLayout(graphAdapter);
		layout.execute(graphAdapter.getDefaultParent());
		graphAdapter.getVertexToCellMap().entrySet().forEach(x -> {
			final Object[] lcell = Arrays.asList(x.getValue()).toArray();
			mxStyleUtils.setCellStyles(graphAdapter.getModel(), lcell, mxConstants.STYLE_ROUNDED, "true");
		});
		return mxCellRenderer.createBufferedImage(graphAdapter, null, 1, new Color(255, 255, 255, 255), true, null);
	}

}
