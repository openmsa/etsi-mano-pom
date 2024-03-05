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
package com.ubiqube.etsi.mano.vnfm.service.event;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageOnboardingImpl;
import com.ubiqube.etsi.mano.utils.TemporaryFileSentry;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfPackageOnboardingNotificationJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfmVersionManager;

@Service
public class NotificationActions {

	private static final Logger LOG = LoggerFactory.getLogger(NotificationActions.class);

	private final VnfPackageJpa vnfPackageJpa;
	private final VnfPackageOnboardingNotificationJpa vnfPackageOnboardingNotificationJpa;
	private final VnfmVersionManager vnfmVersionManager;
	private final VnfPackageRepository vnfPackageRepository;
	private final VnfPackageOnboardingImpl vnfPackageOnboarding;
	private final ServerService serverService;

	public NotificationActions(final VnfPackageJpa vnfPackageJpa, final VnfPackageOnboardingNotificationJpa vnfPackageOnboardingNotificationJpa,
			final VnfmVersionManager vnfmVersionManager, final VnfPackageRepository vnfPackageRepository, final VnfPackageOnboardingImpl vnfPackageOnboarding,
			final ServerService serverService) {
		super();
		this.vnfPackageJpa = vnfPackageJpa;
		this.vnfPackageOnboardingNotificationJpa = vnfPackageOnboardingNotificationJpa;
		this.vnfmVersionManager = vnfmVersionManager;
		this.vnfPackageRepository = vnfPackageRepository;
		this.vnfPackageOnboarding = vnfPackageOnboarding;
		this.serverService = serverService;
	}

	public void onPkgOnbarding(@NotNull final UUID objectId) {
		final VnfPackageOnboardingNotification event = vnfPackageOnboardingNotificationJpa.findById(objectId).orElseThrow();
		final String pkgId = event.getVnfPkgId();
		final VnfPackage vnfPkg = vnfmVersionManager.findVnfPkgById(pkgId);
		VnfPackage localPackage = createPackage(vnfPkg);
		final Servers server = serverService.findById(event.getNfvoId());
		localPackage.setServer(server);
		localPackage = vnfPackageJpa.save(localPackage);
		try {
			downloadToTmpFile(localPackage);
			vnfPackageOnboarding.vnfPackagesVnfPkgIdPackageContentPut(localPackage.getId().toString());
		} catch (final RuntimeException e) {
			LOG.error("", e);
			vnfPackageJpa.delete(localPackage);
		}
	}

	private void downloadToTmpFile(final VnfPackage localPackage) {
		try (TemporaryFileSentry tfs = new TemporaryFileSentry()) {
			final Path file = tfs.get();
			vnfmVersionManager.getPackageContent(localPackage.getNfvoId(), file);
			try (FileInputStream fis = new FileInputStream(file.toFile())) {
				vnfPackageRepository.storeBinary(localPackage.getId(), Constants.REPOSITORY_FILENAME_PACKAGE, fis);
			}
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	/**
	 * Copy Original informations into a brand new VnfPackage entity.
	 *
	 * @param vnfPkg The original {@link VnfPackage}
	 * @return a new {@link VnfPackage}
	 */
	private static VnfPackage createPackage(final VnfPackage vnfPkg) {
		final VnfPackage localPackage = new VnfPackage();
		localPackage.setNfvoId(vnfPkg.getId().toString());
		localPackage.setUserDefinedData(vnfPkg.getUserDefinedData());
		localPackage.setOnboardingState(OnboardingStateType.CREATED);
		localPackage.setOperationalState(PackageOperationalState.DISABLED);
		return localPackage;
	}

	public void onPkgOnbardingInstantiate(final UUID objectId) {
		final VnfPackage pkg = vnfPackageJpa.findById(objectId).orElseThrow(() -> new NotFoundException("Unable to find local vnfPackage: " + objectId));
		downloadToTmpFile(pkg);
		vnfPackageOnboarding.vnfPackagesVnfPkgIdPackageContentPut(pkg.getId().toString());
	}
}
