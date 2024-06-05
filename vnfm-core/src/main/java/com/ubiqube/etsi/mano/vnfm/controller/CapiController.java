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
package com.ubiqube.etsi.mano.vnfm.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.service.CapiServerService;
import com.ubiqube.etsi.mano.vim.k8s.K8s;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi.CapiServerMapping;

@RestController
@RequestMapping("/vnfm-admin/capi")
public class CapiController {
	private final CapiServerService capiServer;
	private final OsClusterService osClusterService;
	private final CapiServerMapping mapper;

	public CapiController(final CapiServerService capiServerJpa, final OsClusterService osClusterService, final CapiServerMapping mapper) {
		this.capiServer = capiServerJpa;
		this.osClusterService = osClusterService;
		this.mapper = mapper;
	}

	@GetMapping
	ResponseEntity<Iterable<CapiServer>> listCapiConnections() {
		final Iterable<CapiServer> ite = capiServer.findAll();
		return ResponseEntity.ok(ite);
	}

	@PostMapping
	public ResponseEntity<CapiServer> post(@RequestBody final CapiServer srv) {
		final CapiServer res = capiServer.save(srv);
		return ResponseEntity.ok(res);
	}

	@PostMapping("kube-config/{context}")
	public ResponseEntity<CapiServer> postKubeConfig(@PathVariable("context") final String context, @RequestParam("file") final MultipartFile file) throws IOException {
		final K8s srv = osClusterService.fromKubeConfig(context, file.getBytes());
		final CapiServer res = capiServer.save(mapper.map(srv));
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") final UUID id) {
		capiServer.deleteById(id);
	}
}
