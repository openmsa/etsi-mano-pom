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
import com.ubiqube.etsi.mano.service.SubscriptionService;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class VnfSubscriptionSol005FrontControllerImplTest {
	@Mock
	private VnfSubscriptionManagement subscriptionMngt;
	@Mock
	private MapperFacade mapper;
	@Mock
	private SubscriptionService susbsService;

	@Test
	void testCreate() throws Exception {
		final VnfSubscriptionSol005FrontControllerImpl srv = new VnfSubscriptionSol005FrontControllerImpl(subscriptionMngt, mapper, susbsService);
		final Consumer func = x -> {
		};
		srv.create(null, null, null, func);
		assertTrue(true);
	}

	@Test
	void testDelete() throws Exception {
		final VnfSubscriptionSol005FrontControllerImpl srv = new VnfSubscriptionSol005FrontControllerImpl(subscriptionMngt, mapper, susbsService);
		srv.delete(UUID.randomUUID().toString());
		assertTrue(true);
	}

	@Test
	void testFindById() throws Exception {
		final VnfSubscriptionSol005FrontControllerImpl srv = new VnfSubscriptionSol005FrontControllerImpl(subscriptionMngt, mapper, susbsService);
		final Consumer func = x -> {
		};
		srv.findById(UUID.randomUUID().toString(), "".getClass(), func);
		assertTrue(true);
	}

	@Test
	void testSearch() throws Exception {
		final VnfSubscriptionSol005FrontControllerImpl srv = new VnfSubscriptionSol005FrontControllerImpl(subscriptionMngt, mapper, susbsService);
		final Consumer func = x -> {
		};
		srv.search(null, "".getClass(), func);
		assertTrue(true);
	}

	@Test
	void testVnfPackageChangeNotificationPost() throws Exception {
		final VnfSubscriptionSol005FrontControllerImpl srv = new VnfSubscriptionSol005FrontControllerImpl(subscriptionMngt, mapper, susbsService);
		srv.vnfPackageChangeNotificationPost(null);
		assertTrue(true);
	}

	@Test
	void testVnfPackageOnboardingNotificationPost() throws Exception {
		final VnfSubscriptionSol005FrontControllerImpl srv = new VnfSubscriptionSol005FrontControllerImpl(subscriptionMngt, mapper, susbsService);
		srv.vnfPackageOnboardingNotificationPost(null);
		assertTrue(true);
	}
}
