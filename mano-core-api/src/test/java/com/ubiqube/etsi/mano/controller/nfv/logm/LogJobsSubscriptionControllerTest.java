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
package com.ubiqube.etsi.mano.controller.nfv.logm;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Olivier Vignaud
 *
 */
class LogJobsSubscriptionControllerTest {

	@Test
	void testSearch() {
		final LogJobsSubscriptionController srv = new LogJobsSubscriptionController();
		assertThrows(UnsupportedOperationException.class, () -> srv.search(null, null));
	}

	@Test
	void testCreate() {
		final LogJobsSubscriptionController srv = new LogJobsSubscriptionController();
		assertThrows(UnsupportedOperationException.class, () -> srv.create(srv, null));
	}

	@Test
	void testDelete() {
		final LogJobsSubscriptionController srv = new LogJobsSubscriptionController();
		assertThrows(UnsupportedOperationException.class, () -> srv.delete(null));
	}

	@Test
	void testFindById() {
		final LogJobsSubscriptionController srv = new LogJobsSubscriptionController();
		assertThrows(UnsupportedOperationException.class, () -> srv.findById(null, null));
	}
}
