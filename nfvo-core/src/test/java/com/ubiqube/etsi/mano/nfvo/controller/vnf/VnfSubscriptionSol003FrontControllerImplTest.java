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

import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.controller.vnf.VnfSubscriptionManagement;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageChangeNotification;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification;
import com.ubiqube.etsi.mano.service.SubscriptionService;

@ExtendWith(MockitoExtension.class)
class VnfSubscriptionSol003FrontControllerImplTest {
	@Mock
	private VnfSubscriptionManagement vnfSubscriptionMngt;
	@Mock
	private SubscriptionService subsService;

	@Test
	void testCreate() throws Exception {
		final VnfSubscriptionSol003FrontControllerImpl srv = new VnfSubscriptionSol003FrontControllerImpl(vnfSubscriptionMngt, subsService);
		final Consumer func = x -> {
			//
		};
		srv.create(null, x -> null, "".getClass(), func);
		assertTrue(true);
	}

	@Test
	void testdelete() throws Exception {
		final VnfSubscriptionSol003FrontControllerImpl srv = new VnfSubscriptionSol003FrontControllerImpl(vnfSubscriptionMngt, subsService);
		final Consumer func = x -> {
			//
		};
		final String id = UUID.randomUUID().toString();
		srv.delete(id);
		assertTrue(true);
	}

	@Test
	void testFindById() throws Exception {
		final VnfSubscriptionSol003FrontControllerImpl srv = new VnfSubscriptionSol003FrontControllerImpl(vnfSubscriptionMngt, subsService);
		final Consumer func = x -> {
			//
		};
		final String id = UUID.randomUUID().toString();
		srv.findById(id, x -> null, func);
		assertTrue(true);
	}

	@Test
	void testSearch() throws Exception {
		final VnfSubscriptionSol003FrontControllerImpl srv = new VnfSubscriptionSol003FrontControllerImpl(vnfSubscriptionMngt, subsService);
		final Consumer func = x -> {
			//
		};
//		when(mapper.mapAsList(anyList(), any())).thenReturn(List.of());
		srv.search(null, x -> "", func);
		assertTrue(true);
	}

	@Test
	void testVnfPackageChangeNotificationPost() throws Exception {
		final VnfSubscriptionSol003FrontControllerImpl srv = new VnfSubscriptionSol003FrontControllerImpl(vnfSubscriptionMngt, subsService);
		final Consumer func = x -> {
			//
		};
		final VnfPackageChangeNotification notificationsMessage = new VnfPackageChangeNotification();
		srv.vnfPackageChangeNotificationPost(notificationsMessage);
		assertTrue(true);
	}

	@Test
	void testVnfPackageOnboardingNotificationPost() throws Exception {
		final VnfSubscriptionSol003FrontControllerImpl srv = new VnfSubscriptionSol003FrontControllerImpl(vnfSubscriptionMngt, subsService);
		final Consumer func = x -> {
			//
		};
		final VnfPackageOnboardingNotification notification = new VnfPackageOnboardingNotification();
		srv.vnfPackageOnboardingNotificationPost(notification);
		assertTrue(true);
	}
}
