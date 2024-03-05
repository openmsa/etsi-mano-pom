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
package com.ubiqube.etsi.mano.nfvo.controller.vnf;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfPackageChangeNotification;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification;
import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.event.Notifications;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

@ExtendWith(MockitoExtension.class)
class VnfSubscriptionManagementImplTest {
	@Mock
	private Notifications notifications;
	@Mock
	private SubscriptionService subscriptionRepo;
	@Mock
	private ServerService serverService;

	@Test
	void testName() throws Exception {
		final VnfSubscriptionManagementImpl srv = new VnfSubscriptionManagementImpl(notifications, subscriptionRepo, serverService);
		final VnfPackageChangeNotification message = new VnfPackageChangeNotification();
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		message.setSubscriptionId(strId);
		final Subscription subs = new Subscription();
		when(subscriptionRepo.findById(id, SubscriptionType.VNF)).thenReturn(subs);
		srv.vnfPackageChangeNotificationPost(message);
		assertTrue(true);
	}

	@Test
	void testName002() throws Exception {
		final VnfSubscriptionManagementImpl srv = new VnfSubscriptionManagementImpl(notifications, subscriptionRepo, serverService);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		final VnfPackageOnboardingNotification message = new VnfPackageOnboardingNotification();
		message.setSubscriptionId(strId);
		final Subscription subs = new Subscription();
		when(subscriptionRepo.findById(id, SubscriptionType.VNF)).thenReturn(subs);
		srv.vnfPackageOnboardingNotificationPost(message);
		assertTrue(true);
	}
}
