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
package com.ubiqube.etsi.mano.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class SubscriptionFrontControllerImplTest {
	@Mock
	private SubscriptionService subscriptionService;
	@Mock
	private MapperFacade mapper;
	@Mock
	private ServerService serverService;

	@Test
	void testSearch() {
		final SubscriptionFrontControllerImpl srv = createService();
		final List<Subscription> lst = List.of();
		when(subscriptionService.query(any(), any())).thenReturn(lst);
		final List<Object> lo = List.of();
		when(mapper.mapAsList(eq(lst), any())).thenReturn(lo);
		final Consumer<Object> cons = x -> {
		};
		srv.search(null, Object.class, cons, null);
		assertTrue(true);
	}

	@Test
	void testCreate() {
		final SubscriptionFrontControllerImpl srv = createService();
		final Consumer<Object> cons = x -> {
		};
		final Function<Object, String> func = x -> "http://localhost/";
		final Subscription req = new Subscription();
		srv.create(req, x -> "", getClass(), cons, func, null);
		assertTrue(true);
	}

	private SubscriptionFrontControllerImpl createService() {
		return new SubscriptionFrontControllerImpl(subscriptionService, mapper);
	}

	@Test
	void testDelete() {
		final SubscriptionFrontControllerImpl srv = createService();
		srv.deleteById(UUID.randomUUID().toString(), null);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final SubscriptionFrontControllerImpl srv = createService();
		final Consumer<Object> cons = x -> {
		};
		srv.findById(UUID.randomUUID().toString(), null, cons, null);
		assertTrue(true);
	}

}
