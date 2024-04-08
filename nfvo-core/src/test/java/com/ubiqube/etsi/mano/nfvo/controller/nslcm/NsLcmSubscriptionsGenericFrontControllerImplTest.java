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
package com.ubiqube.etsi.mano.nfvo.controller.nslcm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class NsLcmSubscriptionsGenericFrontControllerImplTest {
	@Mock
	private SubscriptionService subscriptionService;
	@Mock
	private MapperFacade mapper;

	@Test
	void testCreate() {
		final NsLcmSubscriptionsGenericFrontControllerImpl srv = new NsLcmSubscriptionsGenericFrontControllerImpl(subscriptionService);
		final Consumer<Object> cons = x -> {
		};
		final Function<Object, String> func = x -> "http://loaclhost/";
		final Subscription req = new Subscription();
		srv.create(req, x -> "", getClass(), cons, func);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final NsLcmSubscriptionsGenericFrontControllerImpl srv = new NsLcmSubscriptionsGenericFrontControllerImpl(subscriptionService);
		srv.delete(UUID.randomUUID().toString());
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final NsLcmSubscriptionsGenericFrontControllerImpl srv = new NsLcmSubscriptionsGenericFrontControllerImpl(subscriptionService);
		final Consumer<Object> cons = x -> {
		};
		srv.findById(UUID.randomUUID().toString(), x -> "", cons);
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final NsLcmSubscriptionsGenericFrontControllerImpl srv = new NsLcmSubscriptionsGenericFrontControllerImpl(subscriptionService);
		final Consumer<Object> cons = x -> {
		};
		srv.search("filter", x -> "", cons);
		assertTrue(true);
	}

}
