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
package com.ubiqube.etsi.mano.service.orch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jgrapht.ListenableGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.orchestrator.PostPlanRunner;
import com.ubiqube.etsi.mano.orchestrator.nodes.ConnectivityEdge;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.service.graph.GraphTools;

/**
 *
 * @author olivier
 *
 */
@Service
public class ExportGraphRunner<U> implements PostPlanRunner<U> {

	private static final Logger LOG = LoggerFactory.getLogger(ExportGraphRunner.class);

	@Override
	public void runCreatePost(final ListenableGraph<UnitOfWorkV3<U>, ConnectivityEdge<UnitOfWorkV3<U>>> createImplementation) {
		exportGraph(createImplementation, "orch-added.dot");
	}

	public static <U> void exportGraph(final ListenableGraph<UnitOfWorkV3<U>, ConnectivityEdge<UnitOfWorkV3<U>>> g, final String fileName) {
		final DOTExporter<UnitOfWorkV3<U>, ConnectivityEdge<UnitOfWorkV3<U>>> exporter = new DOTExporter<>(GraphTools::toDotName);
		exporter.setVertexAttributeProvider(x -> {
			final Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(x.getVirtualTask().getAlias() + "\n(" + x.getVirtualTask().getClass().getSimpleName() + ")"));
			map.put("fillcolor", DefaultAttribute.createAttribute("aliceblue"));
			return map;
		});
		try (final FileOutputStream out = new FileOutputStream(fileName)) {
			exporter.exportGraph(g, out);
		} catch (final IOException e) {
			LOG.trace("Error in graph export", e);
		}
	}

	@Override
	public void runDeletePost(final ListenableGraph<UnitOfWorkV3<U>, ConnectivityEdge<UnitOfWorkV3<U>>> deleteImplementation) {
		// Nothing.
	}
}
