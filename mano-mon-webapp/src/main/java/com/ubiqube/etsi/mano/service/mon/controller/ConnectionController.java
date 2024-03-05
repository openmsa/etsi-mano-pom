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
package com.ubiqube.etsi.mano.service.mon.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.mon.api.MonApi;
import com.ubiqube.etsi.mano.service.mon.data.MonConnInformation;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/conn")
public class ConnectionController {
	private final MonApi monApi;

	public ConnectionController(final MonApi monApi) {
		this.monApi = monApi;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") @NotNull final UUID id) {
		monApi.deleteConnection(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<MonConnInformation>> list() {
		return ResponseEntity.ok(monApi.listConnections());
	}
}
