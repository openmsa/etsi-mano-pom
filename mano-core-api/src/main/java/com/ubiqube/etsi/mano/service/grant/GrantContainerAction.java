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
package com.ubiqube.etsi.mano.service.grant;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.docker.DockerApiException;
import com.ubiqube.etsi.mano.docker.DockerService;
import com.ubiqube.etsi.mano.docker.RegistryInformations;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.ConnectionInformationJpa;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.rest.FluxRest;

@Service
public class GrantContainerAction {

	private static final Logger LOG = LoggerFactory.getLogger(GrantContainerAction.class);

	private final ConnectionInformationJpa connJpa;

	private final DockerService dockerService;

	private final VnfPackageService vnfPackageService;

	private final VnfPackageRepository vnfRepository;

	public GrantContainerAction(final ConnectionInformationJpa connJpa, final DockerService dockerService, final VnfPackageService vnfPackageService, final VnfPackageRepository vnfRepository) {
		this.connJpa = connJpa;
		this.dockerService = dockerService;
		this.vnfPackageService = vnfPackageService;
		this.vnfRepository = vnfRepository;
	}

	public void handleGrant(final GrantResponse grants) {
		final VnfPackage vnfPkg = vnfPackageService.findByVnfdId(grants.getVnfdId());
		setConnectionConnection(grants, ResourceTypeEnum.OS_CONTAINER, ConnectionType.OCI, grants::setCirConnectionInfo);
		upload(vnfPkg, ConnectionType.OCI);
		setConnectionConnection(grants, ResourceTypeEnum.HELM, ConnectionType.HELM, grants::setMciopRepositoryInfo);
		uploadHelm(vnfPkg);
	}

	private void uploadHelm(final VnfPackage vnfPkg) {
		vnfPkg.getMciops().stream()
				.map(McIops::getArtifacts)
				.filter(Objects::nonNull)
				.filter(x -> !x.isEmpty())
				.map(x -> x.entrySet().iterator().next())
				.map(Entry::getValue)
				.forEach(x -> {
					final ConnectionInformation ci = getConnection(x);
					if (ci != null) {
						final ManoResource mr = vnfRepository.getBinary(vnfPkg.getId(), Constants.REPOSITORY_FOLDER_ARTIFACTS + "/" + x.getImagePath());
						final String name = makeHelmName(x);
						uploadFile(mr, ci, name);
					}
				});
	}

	private static void uploadFile(final ManoResource mr, final ConnectionInformation ci, final String name) {
		final FluxRest fr = new FluxRest(ci.toServers());
		final URI uri = URI.create(ci.getUrl() + "/" + name);
		final String mimeType = makeMimeType(name);
		try {
			fr.upload(uri, mr, mimeType, null);
		} catch (final RuntimeException e) {
			LOG.trace("", e);
			LOG.warn("Unable to upload helm file: {}", name);
		}
	}

	private static String makeMimeType(final String name) {
		if (name.endsWith(".tar.gz") || name.endsWith(".tgz")) {
			return "application/tar+gzip";
		}
		if (name.endsWith(".tar")) {
			return "application/tar";
		}
		return "application/octet-stream";
	}

	private static String makeHelmName(final SoftwareImage x) {
		final StringBuilder sb = new StringBuilder(x.getName());
		if (x.getVersion() != null) {
			sb.append(":").append(x.getVersion());
		}
		return sb.toString();
	}

	private ConnectionInformation getConnection(final SoftwareImage img) {
		if (img.getRepository() != null) {
			return connJpa.findByName(img.getRepository());
		}
		final List<ConnectionInformation> res = connJpa.findByConnType(ConnectionType.HELM);
		if (res.isEmpty()) {
			return null;
		}
		return res.getFirst();

	}

	private void upload(final VnfPackage vnfPkg, final ConnectionType ct) {
		vnfPkg.getOsContainer().stream().forEach(x -> {
			final List<ConnectionInformation> res = connJpa.findByConnType(ct);
			final ConnectionInformation conn = checkAndGet(res, ct);
			final SoftwareImage ref = find(vnfPkg.getOsContainer(), x.getName());
			if (ref != null) {
				final ManoResource bin = vnfRepository.getBinary(vnfPkg.getId(), Constants.REPOSITORY_FOLDER_ARTIFACTS + "/" + ref.getImagePath());
				final String imageName = buildImageName(conn, "mano", ref.getName());
				final String tag = Optional.ofNullable(ref.getVersion()).orElse("latest");
				final RegistryInformations regInfo = convert(conn);
				try (InputStream is = bin.getInputStream()) {
					dockerService.sendToRegistry(is, regInfo, imageName, tag);
				} catch (final DockerApiException e) {
					LOG.warn("Could not upload blob: {}", x.getName());
					LOG.trace("", e);
				} catch (final IOException e) {
					throw new GenericException(e);
				}
			}
		});
	}

	private static ConnectionInformation checkAndGet(final List<ConnectionInformation> res, final ConnectionType ct) {
		if (res.isEmpty()) {
			throw new GenericException("No connection information for " + ct);
		}
		final ConnectionInformation ci = res.getFirst();
		if (res.size() > 1) {
			LOG.warn("More than one connection for {}, using first one {}", ct, ci.getId());
		}
		return ci;
	}

	private static RegistryInformations convert(final ConnectionInformation conn) {
		final URI uri = conn.getUrl();
		return RegistryInformations.builder()
				.server(uri.getHost())
				.username(conn.getAuthentification().getAuthParamBasic().getUserName())
				.password(conn.getAuthentification().getAuthParamBasic().getPassword())
				.build();
	}

	private static String buildImageName(final ConnectionInformation conn, final String component, final String name) {
		final URI uri = conn.getUrl();
		return uri.getPath() + component + "/" + name;
	}

	private static SoftwareImage find(final Set<OsContainer> osContainer, final String vduId) {
		for (final OsContainer oscon : osContainer) {
			if (oscon.getArtifacts().isEmpty()) {
				return null;
			}
		}
		return osContainer.stream().filter(x -> x.getName().equals(vduId))
				.map(x -> x.getArtifacts().entrySet().iterator().next())
				.map(Entry::getValue)
				.findFirst()
				.orElse(null);
	}

	private void setConnectionConnection(final GrantResponse grants, final ResourceTypeEnum rt, final ConnectionType ct, final Consumer<Map<String, ConnectionInformation>> func) {
		if (grants.getAddResources().stream().noneMatch(x -> x.getType() == rt)) {
			return;
		}
		final List<ConnectionInformation> res = connJpa.findByConnType(ct);
		final Map<String, ConnectionInformation> map = res.stream().collect(Collectors.toMap(ConnectionInformation::getName, x -> x));
		func.accept(map);
	}

}
