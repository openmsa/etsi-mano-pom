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
package com.ubiqube.etsi.mano.nfvo.controller;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.jpa.NsBlueprintJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsPlanService;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsPackageManager;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsPackageOnboardingImpl;
import com.ubiqube.etsi.mano.orchestrator.nodes.ConnectivityEdge;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.service.graph.Edge2d;
import com.ubiqube.etsi.mano.service.graph.GraphGenerator;
import com.ubiqube.etsi.mano.service.graph.TaskVertex;
import com.ubiqube.etsi.mano.service.graph.Vertex2d;
import com.ubiqube.etsi.mano.service.graph.VertexStatusType;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsPackageManager;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsPackageOnboardingImpl;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;
import com.ubiqube.etsi.mano.utils.TemporaryFileSentry;

@Controller
@RequestMapping("/admin")
public class AdminNfvoController {

	private static final Logger LOG = LoggerFactory.getLogger(AdminNfvoController.class);

	private final NsPackageManager packageManager;
	private final NsPackageOnboardingImpl nsPackageOnboardingImpl;
	private final NsPlanService nsPlanService;
	private final NsLiveInstanceJpa nsLiveInstanceJpa;
	private final NsBlueprintJpa nsBlueprintJpa;

	public AdminNfvoController(NsPackageManager packageManager, final NsPackageOnboardingImpl nsPackageOnboardingImpl, final NsPlanService nsPlanService,
			final NsLiveInstanceJpa nsLiveInstanceJpa, final NsBlueprintJpa nsBlueprintJpa) {
		super();
		this.packageManager = packageManager;
		this.nsPackageOnboardingImpl = nsPackageOnboardingImpl;
		this.nsPlanService = nsPlanService;
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
		this.nsBlueprintJpa = nsBlueprintJpa;
	}

	@PostMapping(value = "/validate/ns")
	public ResponseEntity<Void> validateNs(@RequestParam("file") MultipartFile file) {
		final NsdPackage nsPackage = new NsdPackage();
		nsPackage.setId(UUID.randomUUID());
		try (TemporaryFileSentry tfs = new TemporaryFileSentry()) {
			final Path p = tfs.get();
			ManoResource data = new ByteArrayResource(file.getBytes(), p.toFile().getName());
			final PackageDescriptor<NsPackageProvider> packageProvider = packageManager.getProviderFor(data);
			if (null != packageProvider) {
				try (InputStream is = data.getInputStream()) {
					nsPackageOnboardingImpl.mapNsPackage(packageProvider.getNewReaderInstance(is, nsPackage.getId()), nsPackage);
				}
			}
		} catch (IOException e) {
			throw new GenericException(e);
		}
		return ResponseEntity.accepted().build();
	}


	@GetMapping("/plan/ns/2d/{id}")
	public ResponseEntity<BufferedImage> getNs2dPlan(@PathVariable("id") final UUID id) {
		final ListenableGraph<Vertex2d, Edge2d> g = nsPlanService.getPlanFor(id);
		return ResponseEntity
				.ok().contentType(MediaType.IMAGE_PNG)
				.body(GraphGenerator.drawGraph2(g));
	}
	
	@GetMapping("/ns/lcm-op-occs/{id}")
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
				.body(GraphGenerator.drawGraph(g));
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

}
