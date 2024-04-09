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

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.dao.mano.cnf.CnfServer;
import com.ubiqube.etsi.mano.dao.mano.dto.CnfServerDto;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.service.CnfServerService;
import com.ubiqube.etsi.mano.service.mapping.CnfServerDtoMapping;
import com.ubiqube.etsi.mano.service.vim.VimManager;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@RestController
@RequestMapping("/admin/cnf")
public class CnfServerController {
	private final CnfServerService cnfServerService;
	private final VimManager vimManager;
	private final CnfServerDtoMapping cnfServerDtoMapping;

	public CnfServerController(final CnfServerService cnfServerService, final VimManager vimManager, final CnfServerDtoMapping cnfServerDtoMapping) {
		this.cnfServerService = cnfServerService;
		this.vimManager = vimManager;
		this.cnfServerDtoMapping = cnfServerDtoMapping;
	}

	@GetMapping
	public ResponseEntity<Iterable<CnfServer>> listCnfServer() {
		final Iterable<CnfServer> resp = cnfServerService.findAll();
		return ResponseEntity.ok(resp);
	}

	@PostMapping
	public ResponseEntity<CnfServer> createCnfServer(@Valid @NotNull @RequestBody final CnfServerDto in) {
		final CnfServer cnf = cnfServerDtoMapping.map(in);
		final CnfServer resp = cnfServerService.save(cnf);
		return ResponseEntity.ok(resp);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CnfServer> findCnfServer(final UUID id) {
		final CnfServer resp = cnfServerService.findById(id).orElseThrow(() -> new NotFoundException("Could not find cnf server: " + id));
		return ResponseEntity.ok(resp);
	}

	@GetMapping("/{id}/merge")
	public ResponseEntity<VimConnectionInformation> mergeCnfServer(final UUID id) {
		final CnfServer resp = cnfServerService.findById(id).orElseThrow(() -> new NotFoundException("Could not find cnf server: " + id));
		final VimConnectionInformation vim = vimManager.findVimById(id);
		vim.setCnfInfo(resp.getInfo());
		final VimConnectionInformation newVim = vimManager.save(vim);
		return ResponseEntity.ok(newVim);
	}
}
