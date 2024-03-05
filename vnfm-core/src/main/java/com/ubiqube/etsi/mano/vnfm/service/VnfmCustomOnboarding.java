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
package com.ubiqube.etsi.mano.vnfm.service;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.NfvoService;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;
import com.ubiqube.etsi.mano.service.pkg.vnf.CustomOnboarding;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
@ConditionalOnMissingBean(value = NfvoService.class)
public class VnfmCustomOnboarding implements CustomOnboarding {
	private static final Logger LOG = LoggerFactory.getLogger(VnfmCustomOnboarding.class);

	private final VnfPackageRepository repo;
	private final ManoClientFactory factory;

	public VnfmCustomOnboarding(final VnfPackageRepository repo, final ManoClientFactory factory) {
		this.repo = repo;
		this.factory = factory;
	}

	@Override
	public void handleArtifacts(final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader) {
		vnfPackage.getAdditionalArtifacts()
				.forEach(x -> {
					if (x.getArtifactPath() == null) {
						return;
					}
					final ManoClient cli = factory.getClient(vnfPackage.getServer());
					LOG.info("Downloading artifact: {}", x.getArtifactPath());
					final Consumer<InputStream> tgt = y -> repo.storeBinary(vnfPackage.getId(), Paths.get(Constants.REPOSITORY_FOLDER_ARTIFACTS, x.getArtifactPath()).toString(), y);
					cli.vnfPackage()
							.onboarded(getSafeUUID(vnfPackage.getVnfdId()))
							.artifacts(tgt, x.getArtifactPath());
				});
	}

	@Override
	public void handleArtifacts(final NsdPackage pkg, final NsPackageProvider packageReader) {
		// Nothing to do for VNFM.
	}

}
