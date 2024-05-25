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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.vim.k8s.K8sClient;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class HelmV3DeployUow extends AbstractVnfmUow<HelmTask> {
	private final K8sClient client;
	private final HelmTask task;
	private final K8sServerInfoJpa serverInfoJpa;
	private final VnfPackageRepository vnfRepo;
	private final Servers srv;

	public HelmV3DeployUow(final VirtualTaskV3<HelmTask> task, final K8sClient client, final K8sServerInfoJpa serverInfoJpa, final VnfPackageRepository vnfRepo,
			final Servers srv) {
		super(task, HelmNode.class);
		this.client = client;
		this.serverInfoJpa = serverInfoJpa;
		this.vnfRepo = vnfRepo;
		this.srv = srv;
		this.task = task.getTemplateParameters();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final String server = context.get(OsContainerDeployableNode.class, task.getParentVdu());
		final K8sServers s = serverInfoJpa.findById(UUID.fromString(server)).orElseThrow(() -> new GenericException("Unable to find erver " + server));
		final File tmpFile = copyFile(task.getMciop().getArtifacts().entrySet().iterator().next().getValue().getImagePath(), task.getVnfPackageId());
		return client.deploy(srv, s, tmpFile, UUID.randomUUID().toString());
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		// No rollback.
		return null;
	}

	private File copyFile(final String url, final UUID id) {
		final ManoResource f = vnfRepo.getBinary(id, new File(Constants.REPOSITORY_FOLDER_ARTIFACTS, url).toString());
		final Path tmp = createTempFile();
		try (final FileOutputStream fos = new FileOutputStream(tmp.toFile());
				InputStream is = f.getInputStream()) {
			is.transferTo(fos);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		return tmp.toFile();
	}

	private static Path createTempFile() {
		try {
			return Files.createTempFile("", "tgz");
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

}
