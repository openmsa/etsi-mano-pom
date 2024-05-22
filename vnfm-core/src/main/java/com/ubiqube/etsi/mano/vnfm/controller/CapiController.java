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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.vnfm.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.vnfm.jpa.CapiServerJpa;

@RestController
@RequestMapping("/vnfm-admin/capi")
public class CapiController {
	private final CapiServerJpa capiServerJpa;

	public CapiController(final CapiServerJpa capiServerJpa) {
		this.capiServerJpa = capiServerJpa;
	}

	@GetMapping
	ResponseEntity<Iterable<CapiServer>> listCapiConnections() {
		final Iterable<CapiServer> ite = capiServerJpa.findAll();
		return ResponseEntity.ok(ite);
	}

	@PostMapping
	public ResponseEntity<CapiServer> post(@RequestBody final CapiServer srv) {
		final CapiServer res = capiServerJpa.save(srv);
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") final UUID id) {
		capiServerJpa.deleteById(id);
	}
}
