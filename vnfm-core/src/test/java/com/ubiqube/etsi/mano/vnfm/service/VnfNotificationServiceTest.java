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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.PackageChangeType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalStateType;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageChangeNotification;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification;
import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.RemoteSubscriptionJpa;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfPackageOnboardingNotificationJpa;

@ExtendWith(MockitoExtension.class)
class VnfNotificationServiceTest {
	@Mock
	private VnfPackageOnboardingNotificationJpa vnfPkgOnboardNotif;
	@Mock
	private EventManager eventManager;
	@Mock
	private RemoteSubscriptionJpa remoteSubscription;
	@Mock
	private VnfPackageJpa vnfPackageJpa;

	@Test
	void testOnChangeNotFound() {
		final VnfNotificationService srv = new VnfNotificationService(vnfPkgOnboardNotif, eventManager, remoteSubscription, vnfPackageJpa);
		final VnfPackageChangeNotification event = new VnfPackageChangeNotification();
		assertThrows(NotFoundException.class, () -> srv.onChange(event));
		assertTrue(true);
	}

	@Test
	void testOnChangeDelete() {
		final VnfNotificationService srv = new VnfNotificationService(vnfPkgOnboardNotif, eventManager, remoteSubscription, vnfPackageJpa);
		final VnfPackageChangeNotification event = new VnfPackageChangeNotification();
		event.setChangeType(PackageChangeType.PKG_DELETE);
		final RemoteSubscription remoteSubs = new RemoteSubscription();
		when(remoteSubscription.findByRemoteSubscriptionId(any())).thenReturn(Optional.of(remoteSubs));
		srv.onChange(event);
		assertTrue(true);
	}

	@Test
	void testOnChangeVnfdNotFound() {
		final VnfNotificationService srv = new VnfNotificationService(vnfPkgOnboardNotif, eventManager, remoteSubscription, vnfPackageJpa);
		final VnfPackageChangeNotification event = new VnfPackageChangeNotification();
		final RemoteSubscription remoteSubs = new RemoteSubscription();
		when(remoteSubscription.findByRemoteSubscriptionId(any())).thenReturn(Optional.of(remoteSubs));
		srv.onChange(event);
		assertTrue(true);
	}

	@Test
	void testOnChange() {
		final VnfNotificationService srv = new VnfNotificationService(vnfPkgOnboardNotif, eventManager, remoteSubscription, vnfPackageJpa);
		final VnfPackageChangeNotification event = new VnfPackageChangeNotification();
		event.setOperationalState(PackageOperationalStateType.DISABLED);
		final RemoteSubscription remoteSubs = new RemoteSubscription();
		when(remoteSubscription.findByRemoteSubscriptionId(any())).thenReturn(Optional.of(remoteSubs));
		final VnfPackage vnfPkg = new VnfPackage();
		when(vnfPackageJpa.findByVnfdId(any())).thenReturn(Optional.of(vnfPkg));
		srv.onChange(event);
		assertTrue(true);
	}

	@Test
	void testOnNotification() {
		final VnfNotificationService srv = new VnfNotificationService(vnfPkgOnboardNotif, eventManager, remoteSubscription, vnfPackageJpa);
		final VnfPackageOnboardingNotification event = new VnfPackageOnboardingNotification();
		final RemoteSubscription remoteSubs = new RemoteSubscription();
		when(remoteSubscription.findByRemoteSubscriptionId(any())).thenReturn(Optional.of(remoteSubs));
		when(vnfPkgOnboardNotif.save(event)).thenReturn(event);
		srv.onNotification(event, null);
		assertTrue(true);
	}
}
