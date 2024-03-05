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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageOnboardingImpl;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfPackageOnboardingNotificationJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfmVersionManager;

@ExtendWith(MockitoExtension.class)
class NotificationActionsTest {
	@Mock
	private VnfPackageJpa vnfPackageJpa;
	@Mock
	private VnfPackageOnboardingNotificationJpa vnfOnboardingNotification;
	@Mock
	private VnfmVersionManager vnfmVersionManager;
	@Mock
	private VnfPackageRepository vnfPackageRepository;
	@Mock
	private VnfPackageOnboardingImpl vnfOnboarding;
	@Mock
	private ServerService serverService;

	@Test
	void testPackageOnboardingSuccess() {
		final NotificationActions srv = new NotificationActions(vnfPackageJpa, vnfOnboardingNotification, vnfmVersionManager, vnfPackageRepository, vnfOnboarding, serverService);
		final UUID id = UUID.randomUUID();
		final VnfPackageOnboardingNotification event = new VnfPackageOnboardingNotification();
		when(vnfOnboardingNotification.findById(id)).thenReturn(Optional.ofNullable(event));
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setId(id);
		when(vnfmVersionManager.findVnfPkgById(any())).thenReturn(vnfPkg);
		when(vnfPackageJpa.save(any())).thenReturn(vnfPkg);
		srv.onPkgOnbarding(id);
		assertTrue(true);
	}

	@Test
	void testOnPackageOnboardingFail() {
		final NotificationActions srv = new NotificationActions(vnfPackageJpa, vnfOnboardingNotification, vnfmVersionManager, vnfPackageRepository, vnfOnboarding, serverService);
		final UUID id = UUID.randomUUID();
		final VnfPackageOnboardingNotification event = new VnfPackageOnboardingNotification();
		when(vnfOnboardingNotification.findById(id)).thenReturn(Optional.ofNullable(event));
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setId(id);
		when(vnfmVersionManager.findVnfPkgById(any())).thenReturn(vnfPkg);
		srv.onPkgOnbarding(id);
		assertTrue(true);
	}

	@Test
	void testOnPkgOnbardingInstantiateSuccess() throws Exception {
		final NotificationActions srv = new NotificationActions(vnfPackageJpa, vnfOnboardingNotification, vnfmVersionManager, vnfPackageRepository, vnfOnboarding, serverService);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		vnfPkg.setId(id);
		when(vnfPackageJpa.findById(id)).thenReturn(Optional.of(vnfPkg));
		srv.onPkgOnbardingInstantiate(id);
		assertTrue(true);
	}

	@Test
	void testOnPkgOnbardingInstantiateNotFound() throws Exception {
		final NotificationActions srv = new NotificationActions(vnfPackageJpa, vnfOnboardingNotification, vnfmVersionManager, vnfPackageRepository, vnfOnboarding, serverService);
		final UUID id = UUID.randomUUID();
		assertThrows(NotFoundException.class, () -> srv.onPkgOnbardingInstantiate(id));
	}
}
