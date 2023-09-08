/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.controller.vnf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.juju.cli.JujuRemoteService;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;

@RestController
@RequestMapping("/juju")
public class JujuVnfController {

	private static final Logger LOG = LoggerFactory.getLogger(JujuVnfController.class);
	private final JujuRemoteService remoteService;

	public JujuVnfController(final JujuRemoteService remoteService) {
		this.remoteService = remoteService;
	}

	@PostMapping(value = "/instantiate")
	public ResponseEntity<String> instantiate(@RequestBody final JujuCloud jCloud) {
		LOG.info("Juju instantiating...");
		if (!remoteService.addCloud(jCloud).getStatusCode().is2xxSuccessful()) {
			throw new GenericException("Error Create Cloud");
		}
		if (!remoteService.addCredential(jCloud).getStatusCode().is2xxSuccessful()) {
			throw new GenericException("Error Create Credential");
		}
//		if (!remoteService.genMetadata("path", "imageId", "osSeries", "region", "osAuthUrl").getStatusCode().is2xxSuccessful()) throw new Exception("Error Create Metadata"); //No need as will be there
		if (!remoteService.addController(jCloud.getName(), jCloud.getMetadata()).getStatusCode().is2xxSuccessful()) {
			throw new GenericException("Error Create Controller");
		}
		if (!remoteService.addModel(jCloud.getMetadata().getModels().get(0).getName()).getStatusCode().is2xxSuccessful()) {
			throw new GenericException("Error Create Model");
		}
		if (!remoteService.deployApp(jCloud.getMetadata().getModels().get(0).getCharmName(), jCloud.getMetadata().getModels().get(0).getAppName()).getStatusCode().is2xxSuccessful()) {
			throw new GenericException("Error Deploying Charm");
		}

		return ResponseEntity.ok("Success");
	}

	@PostMapping(value = "/terminate")
	public ResponseEntity<String> terminate(@RequestBody final JujuCloud jCloud) {
		LOG.info("Juju terminating...");
//		if (!remoteService.removeApplication("charm").getStatusCode().is2xxSuccessful()) throw new Exception("Error Removing Charm"); //No needed as will be removed by Controller
//		if (!remoteService.removeModel("modelName").getStatusCode().is2xxSuccessful()) throw new Exception("Error Removing Model"); //No needed as will be removed by Controller
		if (!remoteService.removeController(jCloud.getMetadata().getName()).getStatusCode().is2xxSuccessful()) {
			throw new GenericException("Error Removing Controller");
		}
		if (!remoteService.removeCredential(jCloud.getName(), jCloud.getCredential().getName()).getStatusCode().is2xxSuccessful()) {
			throw new GenericException("Error Removing Credential");
		}
		if (!remoteService.removeCloud(jCloud.getName()).getStatusCode().is2xxSuccessful()) {
			throw new GenericException("Error Removing Cloud");
		}
		return ResponseEntity.ok("Success");
	}

}
