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
import java.util.Base64;
import java.util.UUID;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.ai.KubernetesV1Auth;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.ii.K8sInterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.vim.k8s.K8sClient;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class HelmV3DeployUow extends AbstractVnfmUow<HelmTask> {
	private final K8sClient client;
	private final HelmTask task;
	private final VnfPackageRepository vnfRepo;
	private final Servers srv;
	private final VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> vimConnection;

	public HelmV3DeployUow(final VirtualTaskV3<HelmTask> task, final K8sClient client, final VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> vimConnectionInformation, final VnfPackageRepository vnfRepo,
			final Servers srv) {
		super(task, HelmNode.class);
		this.client = client;
		this.vnfRepo = vnfRepo;
		this.srv = srv;
		this.task = task.getTemplateParameters();
		this.vimConnection = vimConnectionInformation;
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final File tmpFile = copyFile(task.getMciop().getArtifacts().entrySet().iterator().next().getValue().getImagePath(), task.getVnfPackageId());
		final K8sServers s = K8sServers.builder()
				.apiAddress(vimConnection.getInterfaceInfo().getEndpoint())
				.caPem(base64Decode(vimConnection.getInterfaceInfo().getCertificateAuthorityData()))
				.userCrt(base64Decode(vimConnection.getAccessInfo().getClientCertificateData()))
				.userKey(base64Decode(vimConnection.getAccessInfo().getClientKeyData()))
				.build();
		return client.deploy(srv, s, tmpFile, UUID.randomUUID().toString());
	}

	private static String base64Decode(String in) {
		return new String(Base64.getDecoder().decode(in));
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
