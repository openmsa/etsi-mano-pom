package com.ubiqube.etsi.mano.controller.vnf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.dao.mano.juju.JujuCloud;
import com.ubiqube.etsi.mano.service.juju.cli.JujuRemoteService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/juju")
public class JujuVnfController {

	private static final Logger LOG = LoggerFactory.getLogger(JujuVnfController.class);
	private final JujuRemoteService remoteService;

	public JujuVnfController(final JujuRemoteService remoteService) {
		this.remoteService = remoteService;
	}

	@PostMapping(value = "/instantiate")
	public ResponseEntity<String> instantiate(@RequestBody @NotNull final JujuCloud jCloud) throws Exception {
		LOG.info("Juju instantiating...");
		if (!remoteService.addCloud(jCloud).getStatusCode().is2xxSuccessful()) throw new Exception("Error Create Cloud");
		if (!remoteService.addCredential(jCloud).getStatusCode().is2xxSuccessful()) throw new Exception("Error Create Credential");
//		if (!remoteService.genMetadata("path", "imageId", "osSeries", "region", "osAuthUrl").getStatusCode().is2xxSuccessful()) throw new Exception("Error Create Metadata"); //No need as will be there
		if (!remoteService.addController(jCloud.getName(), jCloud.getMetadata()).getStatusCode().is2xxSuccessful()) throw new Exception("Error Create Controller");
		if (!remoteService.addModel(jCloud.getMetadata().getModels().get(0).getName()).getStatusCode().is2xxSuccessful()) throw new Exception("Error Create Model");
		if (!remoteService.deployApp(jCloud.getMetadata().getModels().get(0).getCharmName(), jCloud.getMetadata().getModels().get(0).getAppName()).getStatusCode().is2xxSuccessful()) throw new Exception("Error Deploying Charm");

		return ResponseEntity.ok("Success");
	}

	@PostMapping(value = "/terminate")
	public ResponseEntity<String>  terminate(@RequestBody @NotNull final JujuCloud jCloud) throws Exception {
		LOG.info("Juju terminating...");
//		if (!remoteService.removeApplication("charm").getStatusCode().is2xxSuccessful()) throw new Exception("Error Removing Charm"); //No needed as will be removed by Controller
//		if (!remoteService.removeModel("modelName").getStatusCode().is2xxSuccessful()) throw new Exception("Error Removing Model"); //No needed as will be removed by Controller
		if (!remoteService.removeController(jCloud.getMetadata().getName()).getStatusCode().is2xxSuccessful()) throw new Exception("Error Removing Controller");
		if (!remoteService.removeCredential(jCloud.getName(),jCloud.getCredential().getName()).getStatusCode().is2xxSuccessful()) throw new Exception("Error Removing Credential");
		if (!remoteService.removeCloud(jCloud.getName()).getStatusCode().is2xxSuccessful()) throw new Exception("Error Removing Cloud");
		return ResponseEntity.ok("Success");
	}

}
