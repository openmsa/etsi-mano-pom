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
package com.ubiqube.etsi.mano.nfvo.controller;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ubiqube.etsi.mano.dao.mano.TemporaryDownload;
import com.ubiqube.etsi.mano.dao.mano.TemporaryDownload.ObjectType;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.nfvo.service.TemporaryDownloadService;
import com.ubiqube.etsi.mano.repository.ManoResource;

@Controller
//@ApiIgnore
public class HomeController {
	private final TemporaryDownloadService temporaryDownloadService;

	public HomeController(final TemporaryDownloadService temporaryDownloadService) {
		this.temporaryDownloadService = temporaryDownloadService;
	}

	@SuppressWarnings("static-method")
	@GetMapping(value = "/test/{id}")
	public ResponseEntity<VnfPackage> test(@PathVariable("id") final VnfPackage vnfPackage) {
		return ResponseEntity.ok(vnfPackage);
	}

	@GetMapping(value = "/download/{id}")
	public ResponseEntity<InputStream> downloadAnonymous(@PathVariable("id") final String id) {
		final ManoResource res = temporaryDownloadService.getDocument(id);
		return ResponseEntity.ok(res.getInputStream());
	}

	@PostMapping(value = "/expose/{objectType}/{id}")
	public ResponseEntity<TemporaryDownload> exposeAnonymous(@PathVariable("objectType") final ObjectType objectType, @PathVariable("id") final UUID id) {
		final TemporaryDownload td = temporaryDownloadService.exposeDocument(objectType, id);
		return ResponseEntity.ok(td);
	}
}
