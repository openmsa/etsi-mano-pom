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
package com.ubiqube.etsi.mano.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.VnfmException;
import com.ubiqube.etsi.mano.jpa.JujuCloudJpa;
import com.ubiqube.etsi.mano.jpa.JujuCredentialJpa;
import com.ubiqube.etsi.mano.jpa.JujuMetadataJpa;
import com.ubiqube.etsi.mano.service.juju.cli.JujuRemoteService;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;

@Service
public class JujuCloudService {

	private final JujuCloudJpa jujuCloudJpa;
	private final JujuCredentialJpa jujuCredentialJpa;
	private final JujuMetadataJpa jujuMetadataJpa;
	final JujuRemoteService remoteService;

	public JujuCloudService(final JujuCloudJpa jujuCloudJpa, final JujuCredentialJpa jujuCredentialJpa,
			final JujuMetadataJpa jujuMetadataJpa, final JujuRemoteService remoteService) {
		this.jujuCloudJpa = jujuCloudJpa;
		this.jujuCredentialJpa = jujuCredentialJpa;
		this.jujuMetadataJpa = jujuMetadataJpa;
		this.remoteService = remoteService;
	}

	public JujuCloud saveCloud(final JujuCloud jCloud) {
		jujuCredentialJpa.save(jCloud.getCredential());
		jujuMetadataJpa.save(jCloud.getMetadata());
		jCloud.setStatus("PROCESS");
		return jujuCloudJpa.save(jCloud);
	}

	public List<JujuCloud> findByMetadataName(String controllername, String status) {
		return jujuCloudJpa.findByMetadataName(controllername, status);
	}
	
	public boolean jujuInstantiate(final UUID objectId) {
		System.out.println("objectidinstantite= " + objectId);
		final JujuCloud jCloud = jujuCloudJpa.findById(objectId)
				.orElseThrow(() -> new VnfmException("Could not find Juju Cloud: " + objectId));
		try {
			if (jCloud.getName() != null) {
				remoteService.addCloud(jCloud);
				if (jCloud.getCredential().getName() != null) {
					remoteService.addCredential(jCloud);
					if (jCloud.getMetadata().getName() != null) {
						remoteService.addController(jCloud.getName(), jCloud.getMetadata());
						ResponseEntity<String> responseobject = remoteService
								.controllerDetail(jCloud.getMetadata().getName());
						if (responseobject.getBody() != null && !(responseobject.getBody().contains("ERROR"))) {
							if (jCloud.getMetadata().getModels().get(0).getName() != null) {
								remoteService.addModel(jCloud.getMetadata().getModels().get(0).getName());
								if (jCloud.getMetadata().getModels().get(0).getCharmName() != null
										&& jCloud.getMetadata().getModels().get(0).getAppName() != null) {
									remoteService.deployApp(jCloud.getMetadata().getModels().get(0).getCharmName(),
											jCloud.getMetadata().getModels().get(0).getAppName());
								} else {
									throw new VnfmException("Error Deploying Charm");
								}
							} else {
								throw new VnfmException("Error Create Model");
							}
						} else {
							jCloud.setStatus("FAIL");
							jujuCloudJpa.save(jCloud);
							throw new VnfmException("Error Create Controller");
						}
					} else {
						throw new VnfmException("Error Create Controller");
					}
				} else {
					throw new VnfmException("Error Create Credential");
				}
			} else {
				throw new VnfmException("Error Create Cloud");
			}
		} catch (VnfmException e) {
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
		} catch (VnfmException e) {
			return false;
		}
	}

}