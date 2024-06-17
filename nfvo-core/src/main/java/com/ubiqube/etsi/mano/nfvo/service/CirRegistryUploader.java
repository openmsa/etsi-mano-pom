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
package com.ubiqube.etsi.mano.nfvo.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.docker.DockerService;
import com.ubiqube.etsi.mano.docker.RegistryInformations;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.mapping.CirConnectionControllerMapping;
import com.ubiqube.etsi.mano.service.pkg.vnf.RegistryUploader;
import com.ubiqube.etsi.mano.service.vim.CirConnectionManager;

@Service
public class CirRegistryUploader implements RegistryUploader {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(CirRegistryUploader.class);

	private final VnfPackageRepository packageRepository;
	private final DockerService dockerService;
	private final CirConnectionManager vimManager;
	private final CirConnectionControllerMapping mapper;

	public CirRegistryUploader(final VnfPackageRepository packageRepository, final DockerService dockerService, final CirConnectionManager vimManager, final CirConnectionControllerMapping mapper) {
		this.vimManager = vimManager;
		this.packageRepository = packageRepository;
		this.dockerService = dockerService;
		this.mapper = mapper;
	}

	@Override
	public void uploadToRegistry(final VnfPackage vnfPackage) {
		if ((null == vnfPackage.getOsContainer()) || vnfPackage.getOsContainer().isEmpty()) {
			return;
		}
		final ConnectionInformation cirConn = getCirConnection();
		final RegistryInformations regInfo = mapper.map(cirConn);
		vnfPackage.getOsContainer().stream().flatMap(x -> x.getArtifacts().values().parallelStream())
				.forEach(x -> {
					LOG.info("Uploading to CIR: {} from {}", x.getName(), x.getImagePath());
					File f = new File(Constants.REPOSITORY_FOLDER_ARTIFACTS, x.getImagePath());
					ManoResource res = packageRepository.getBinary(vnfPackage.getId(), f.toString());
					try (InputStream raw = res.getInputStream();
							InputStream is = unpackStream(raw, x.getImagePath())) {
						dockerService.sendToRegistry(is, f.toString(), regInfo, x.getName(), Optional.ofNullable(x.getVersion()).orElse("latest"));
					} catch (IOException e) {
						throw new GenericException("While opening " + x.getImagePath(), e);
					}
				});
	}

	private static InputStream unpackStream(final InputStream raw, final String imagePath) throws IOException {
		if (isBz2(imagePath)) {
			return new BZip2CompressorInputStream(raw);
		}
		if (isGz(imagePath)) {
			return new GzipCompressorInputStream(raw);
		}
		return raw;
	}

	private static boolean isGz(final String imagePath) {
		return imagePath.endsWith(".gz") || imagePath.endsWith(".tgz");
	}

	private static boolean isBz2(final String imagePath) {
		return imagePath.endsWith(".bz2");
	}

	private ConnectionInformation getCirConnection() {
		final Iterator<ConnectionInformation> cirConnIte = vimManager.findAll().iterator();
		if (!cirConnIte.hasNext()) {
			throw new GenericException("No CIR connection information found.");
		}
		final List<ConnectionInformation> lst = StreamSupport.stream(Spliterators.spliteratorUnknownSize(cirConnIte, Spliterator.ORDERED), false)
				.filter(x -> x.getConnType().equals(ConnectionType.OCI))
				.toList();
		if (lst.isEmpty()) {
			throw new GenericException("No suitable CIR connection information found.");
		}
		return lst.getFirst();
	}

}
