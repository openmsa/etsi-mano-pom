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
package com.ubiqube.etsi.mano.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;


import org.jgrapht.ListenableGraph;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.model.NotificationEvent;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.service.GrantService;
import com.ubiqube.etsi.mano.service.VnfPlanService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.graph.Edge2d;
import com.ubiqube.etsi.mano.service.graph.GraphGenerator;
import com.ubiqube.etsi.mano.service.graph.Vertex2d;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageManager;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageOnboardingImpl;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;
import com.ubiqube.etsi.mano.utils.TemporaryFileSentry;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

	private final EventManager eventManager;
	private final GrantService grantService;
	private final VnfPackageManager packageManager;
	private final VnfPackageOnboardingImpl vnfPackageOnboardingImpl;
	private final VnfPlanService vnfPlanService;

	public AdminController(final EventManager eventManager, final GrantService grantJpa, final VnfPackageManager packageManager, 
			final VnfPackageOnboardingImpl vnfPackageOnboardingImpl, final VnfPlanService vnfPlanService) {

		super();
		this.eventManager = eventManager;
		this.grantService = grantJpa;
		this.packageManager = packageManager;
		this.vnfPackageOnboardingImpl = vnfPackageOnboardingImpl;
		this.vnfPlanService = vnfPlanService;
	}

	@GetMapping(value = "/event/{event}/{id}")
	public ResponseEntity<Void> sendEvent(@PathVariable("event") final NotificationEvent event, @PathVariable("id") final UUID id) {
		eventManager.sendNotification(event, id, Map.of());
		return ResponseEntity.accepted().build();
	}

	@DeleteMapping(value = "/grant/all")
	public ResponseEntity<Void> deleteAllGrant() {
		grantService.findAll().forEach(x -> {
			try {
				grantService.delete(x);
			} catch (final RuntimeException e) {
				LOG.trace("", e);
				LOG.info("Unable to delete: {}", x.getId());
			}
		});
		return ResponseEntity.accepted().build();
	}

	@PostMapping(value = "/validate/vnf")
	public ResponseEntity<BufferedImage> validateVnf(@RequestParam("file") MultipartFile file) {

		try (TemporaryFileSentry tfs = new TemporaryFileSentry()) {
				final Path p = tfs.get();
				ManoResource data = new ByteArrayResource(file.getBytes(), p.toFile().getName());
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

	@SuppressWarnings("static-method")
	@GetMapping("/whoami")
	public ResponseEntity<Object> whoami() {
		final Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final Object a = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		return ResponseEntity.ok(Map.of("principal", p, "roles", a));
	}
}
