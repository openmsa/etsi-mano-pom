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
package com.ubiqube.etsi.mano.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import org.jgrapht.ListenableGraph;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Edge2d;
import com.ubiqube.etsi.mano.orchestrator.Vertex2d;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.service.VnfPlanService;
import com.ubiqube.etsi.mano.service.graph.GraphGenerator;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageManager;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageOnboardingImpl;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;
import com.ubiqube.etsi.mano.utils.TemporaryFileSentry;

/**
 *
 * @author ncuser
 *
 */
@RestController
@RequestMapping("/v3")
public class V3Controller {
	private final VnfPackageManager packageManager;
	private final VnfPackageOnboardingImpl vnfPackageOnboardingImpl;
	private final VnfPlanService vnfPlanService;

	public V3Controller(final VnfPackageManager packageManager, final VnfPackageOnboardingImpl vnfPackageOnboardingImpl, final VnfPlanService vnfPlanService) {
		super();
		this.packageManager = packageManager;
		this.vnfPackageOnboardingImpl = vnfPackageOnboardingImpl;
		this.vnfPlanService = vnfPlanService;
	}

	@PostMapping(value = "/validate/vnf", consumes = { "multipart/form-data" })
	public ResponseEntity<BufferedImage> validateVnf(@RequestParam("file") final MultipartFile file) {

		try (TemporaryFileSentry tfs = new TemporaryFileSentry()) {
			final Path p = tfs.get();
			final ManoResource data = new ByteArrayResource(file.getBytes(), p.toFile().getName());
			final PackageDescriptor<VnfPackageReader> packageProvider = packageManager.getProviderFor(data);
			final VnfPackage vnfPackage = new VnfPackage();
			vnfPackage.setId(UUID.randomUUID());
			vnfPackageOnboardingImpl.mapVnfPackage(vnfPackage, data, packageProvider);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		return ResponseEntity.accepted().build();
	}

	@GetMapping("/plan/vnf/2d/{id}")
	public ResponseEntity<BufferedImage> getVnf2dPlan(@PathVariable("id") final UUID id) {
		final ListenableGraph<Vertex2d, Edge2d> g = vnfPlanService.getPlanFor(id);
		return ResponseEntity
				.ok().contentType(MediaType.IMAGE_PNG)
				.body(GraphGenerator.drawGraph2(g));
	}

}
