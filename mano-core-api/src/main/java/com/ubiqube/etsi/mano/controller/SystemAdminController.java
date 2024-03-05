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

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.entities.Systems;
import com.ubiqube.etsi.mano.service.SystemService;

import jakarta.annotation.security.RolesAllowed;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Controller
@RolesAllowed({ "ROLE_ADMIN" })
@RequestMapping("/admin/system")
public class SystemAdminController {
	private final SystemService systemService;

	public SystemAdminController(final SystemService systemService) {
		this.systemService = systemService;
	}

	@GetMapping
	public ResponseEntity<Iterable<Systems>> list() {
		final Iterable<Systems> res = systemService.findAll();
		return ResponseEntity.ok(res);
	}

	@GetMapping("/{id}/module/{moduleName}")
	public ResponseEntity<SystemConnections> listModule(@PathVariable("id") final UUID id, @PathVariable("moduleName") final String moduleName) {
		final List<Systems> res = systemService.findByModuleName(id, moduleName);
		final SystemConnections l = res.stream()
				.flatMap(x -> x.getSubSystems().stream())
				.filter(x -> x.getModuleName().equals(moduleName))
				.findFirst()
				.orElseThrow();
		return ResponseEntity.ok(l);
	}

	@PatchMapping("/module/{id}")
	public ResponseEntity<SystemConnections> patchModule(@PathVariable("id") final UUID id, @RequestBody final String body) {
		return systemService.patchModule(id, body);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSystem(final @PathVariable UUID id) {
		systemService.deleteByVimOrigin(id);
		return ResponseEntity.noContent().build();
	}
}
