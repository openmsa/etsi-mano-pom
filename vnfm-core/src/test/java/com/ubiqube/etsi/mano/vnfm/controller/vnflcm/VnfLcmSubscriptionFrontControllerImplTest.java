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
package com.ubiqube.etsi.mano.vnfm.controller.vnflcm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.ubiqube.etsi.mano.controller.SubscriptionFrontController;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

class VnfLcmSubscriptionFrontControllerImplTest {
	private final VnfLcmSubscriptionFrontControllerImpl srv;

	public VnfLcmSubscriptionFrontControllerImplTest() {
		final SubscriptionFrontController subs = Mockito.mock(SubscriptionFrontController.class);
		srv = new VnfLcmSubscriptionFrontControllerImpl(subs);
	}

	@Test

	void testFindById() {
		srv.findById(null, null, null);
		assertTrue(true);
	}

	@Test
	void testSearch() {
		srv.search(null, null, null, null);
		assertTrue(true);
	}

	@Test
	void testCreate() {
		final Subscription req = new Subscription();
		srv.create(req, x -> "", getClass(), null, null);
		assertTrue(true);
	}

	@Test
	void testDeleteById() {
		srv.deleteById(null);
		assertTrue(true);
	}

}
