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

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.dao.mano.dto.VimConnectionRegistrationDto;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.service.Patcher;
import com.ubiqube.etsi.mano.service.VimService;
import com.ubiqube.etsi.mano.service.mapping.VimConnectionInformationMapping;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@RestController
@RequestMapping("/admin")
@Validated
public class VimController {
	private final VimService vimService;
	private final VimManager vimManager;
	private final Patcher patcher;
	private final VimConnectionInformationMapping vimConnectionInformationMapping;

	public VimController(final VimService vimService, final VimConnectionInformationMapping vimConnectionInformationMapping, final VimManager vimManager, final Patcher patcher) {
		this.vimService = vimService;
		this.vimConnectionInformationMapping = vimConnectionInformationMapping;
		this.vimManager = vimManager;
		this.patcher = patcher;
	}

	@PostMapping(value = "/vim/register")
	public ResponseEntity<VimConnectionInformation> registerVim(@RequestBody final VimConnectionRegistrationDto body) {
		final VimConnectionInformation nvim = vimConnectionInformationMapping.map(body);
		final VimConnectionInformation vci = vimManager.register(nvim);
		return ResponseEntity.ok(vci);
	}

	@DeleteMapping(value = "/vim/{id}")
	public ResponseEntity<Void> deleteVim(@PathVariable("id") final String id) {
		vimManager.deleteVim(UUID.fromString(id));
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/vim/{id}/refresh")
	public ResponseEntity<VimConnectionInformation> updateVim(@PathVariable("id") final UUID id) {
		final VimConnectionInformation vci = vimManager.refresh(id);
		return ResponseEntity.ok(vci);
	}

	@PatchMapping(value = "/vim/{id}")
	public ResponseEntity<VimConnectionInformation> patchVim(@PathVariable("id") final UUID id, @RequestBody final String body,
			@RequestHeader(name = HttpHeaders.IF_MATCH, required = false) @Nullable final String ifMatch) {
		final VimConnectionInformation vim = vimManager.findVimById(id);
		if ((ifMatch != null) && !(vim.getVersion() + "").equals(ifMatch)) {
			throw new PreConditionException(ifMatch + " does not match " + vim.getVersion());
		}
		patcher.patch(body, vim);
		final VimConnectionInformation newVim = vimManager.save(vim);
		return ResponseEntity.ok(newVim);
	}

	@GetMapping(value = "/vim/{id}/connect")
	public ResponseEntity<Map<String, String>> connectVim(@PathVariable("id") final UUID id) {
		final Vim vim = vimManager.getVimById(id);
		final VimConnectionInformation vimconn = vimService.findById(id).orElseThrow(() -> new NotFoundException("Could not find vim id " + id));
		final Map<String, String> networks = vim.network(vimconn).getPublicNetworks();
		return ResponseEntity.ok(networks);
	}

	@GetMapping(value = "/vim")
	public ResponseEntity<Iterable<VimConnectionInformation>> listVim() {
		final Iterable<VimConnectionInformation> vci = vimService.findAll();
		return ResponseEntity.ok(vci);
	}

}
