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
package com.ubiqube.etsi.mano.service;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.ubiqube.etsi.mano.exception.VnfmException;
import com.ubiqube.etsi.mano.jpa.JujuCloudJpa;
import com.ubiqube.etsi.mano.jpa.JujuCredentialJpa;
import com.ubiqube.etsi.mano.jpa.JujuMetadataJpa;
import com.ubiqube.etsi.mano.service.auth.model.ServerConnection;
import com.ubiqube.etsi.mano.service.juju.cli.JujuRemoteService;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.vim.VimException;

import reactor.core.publisher.Mono;

@Service
public class JujuCloudService {
	private static final Logger LOG = LoggerFactory.getLogger(JujuCloudService.class);
	private final JujuCloudJpa jujuCloudJpa;
	private final JujuCredentialJpa jujuCredentialJpa;
	private final JujuMetadataJpa jujuMetadataJpa;
	private final JujuRemoteService remoteService;
	private final Environment environment;

	public JujuCloudService(final JujuCloudJpa jujuCloudJpa, final JujuCredentialJpa jujuCredentialJpa,
			final JujuMetadataJpa jujuMetadataJpa, final JujuRemoteService remoteService, final Environment environment) {
		this.jujuCloudJpa = jujuCloudJpa;
		this.jujuCredentialJpa = jujuCredentialJpa;
		this.jujuMetadataJpa = jujuMetadataJpa;
		this.remoteService = remoteService;
		this.environment = environment;
	}

	public JujuCloud saveCloud(final JujuCloud jCloud) {
		jujuCredentialJpa.save(jCloud.getCredential());
		jujuMetadataJpa.save(jCloud.getMetadata());
		jCloud.setStatus("PROCESS");
		return jujuCloudJpa.save(jCloud);
	}

	public List<JujuCloud> findByMetadataName(final String controllername, final String status) {
		return jujuCloudJpa.findByMetadataName(controllername, status);
	}

	public boolean jujuInstantiate(final UUID objectId) {
		final JujuCloud jCloud = jujuCloudJpa.findById(objectId)
				.orElseThrow(() -> new VnfmException("Could not find Juju Cloud: " + objectId));
		try {
			if (jCloud.getName() == null) {
				throw new VnfmException("Error Create Cloud");
			}
			remoteService.addCloud(jCloud);
			if (jCloud.getCredential().getName() == null) {
				throw new VnfmException("Error Create Credential");
			}
			remoteService.addCredential(jCloud);
			if (jCloud.getMetadata().getName() == null) {
				throw new VnfmException("Error Create Controller");
			}
			remoteService.addController(jCloud.getName(), jCloud.getMetadata());
			final ResponseEntity<String> responseobject = remoteService
					.controllerDetail(jCloud.getMetadata().getName());
			if ((responseobject.getBody() == null) || (responseobject.getBody().contains("ERROR"))) {
				jCloud.setStatus("FAIL");
				jujuCloudJpa.save(jCloud);
				throw new VnfmException("Error Create Controller");
			}
			if (jCloud.getMetadata().getModels().getFirst().getName() == null) {
				throw new VnfmException("Error Create Model");
			}
			remoteService.addModel(jCloud.getMetadata().getModels().getFirst().getName());
			if ((jCloud.getMetadata().getModels().getFirst().getCharmName() == null) || (jCloud.getMetadata().getModels().getFirst().getAppName() == null)) {
				throw new VnfmException("Error Deploying Charm");
			}
			remoteService.deployApp(jCloud.getMetadata().getModels().getFirst().getCharmName(),
					jCloud.getMetadata().getModels().getFirst().getAppName());
		} catch (final VnfmException e) {
			jCloud.setStatus("FAIL");
			jujuCloudJpa.save(jCloud);
			return false;
		}
		jCloud.setStatus("PASS");
		jujuCloudJpa.save(jCloud);
		return true;
	}

	public boolean jujuTerminate(final UUID objectId) {
		try {
			final JujuCloud jCloud = jujuCloudJpa.findById(objectId)
					.orElseThrow(() -> new VnfmException("Could not find Juju Cloud: " + objectId));
			remoteService.removeController(jCloud.getMetadata().getName());
			jujuCloudJpa.delete(jCloud);
			return true;
		} catch (final VnfmException e) {
			return false;
		}
	}

	public boolean installHelm(final String helmname, final File helmFile) throws URISyntaxException {
		final Boolean opt = Optional.ofNullable(remoteService.isK8sReady())
				.map(ResponseEntity::getBody)
				.map(Boolean::booleanValue)
				.orElse(Boolean.FALSE);
		while (Boolean.FALSE.equals(opt)) {
			LOG.info("Kubernetes Not Ready.  Waiting...");
			try {
				Thread.sleep(5000);
			} catch (final InterruptedException e) {
				LOG.info("", e);
				Thread.currentThread().interrupt();
			}
		}
		remoteService.addKubeConfig("/home/ubuntu/.kube/config");
		final String jujuUrl = environment.getProperty("mano.juju.url");
		final ServerConnection server = new ServerConnection(null, new URI(jujuUrl));
		final FluxRest fr = new FluxRest(server);
		final WebClient wc = fr.getWebClient();
		final MultipartBodyBuilder builder = new MultipartBodyBuilder();
		builder.part("file", new FileSystemResource(helmFile), MediaType.APPLICATION_OCTET_STREAM);
		final Mono<Object> res = wc.post().uri(server.getUrl() + "/juju/helminstall/" + helmname)
				.contentType(MediaType.MULTIPART_FORM_DATA).body(BodyInserters.fromMultipartData(builder.build()))
				.exchangeToMono(response -> {
					if (HttpStatus.OK.equals(response.statusCode())) {
						return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
					}
					throw new VimException("Error uploading file");
				});
		res.block();
		return true;
	}

	public void uninstallHelm(final String helmName) {
		remoteService.helmUninstall(helmName);
	}
}
