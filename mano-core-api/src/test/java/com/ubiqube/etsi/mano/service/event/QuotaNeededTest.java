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
package com.ubiqube.etsi.mano.service.event;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class QuotaNeededTest {

	@Test
	void test() {
		final QuotaNeeded qn = new QuotaNeeded();
		qn.setDisk(0);
		qn.setRam(0);
		qn.setVcpu(0);
		qn.getDisk();
		qn.getRam();
		qn.getVcpu();
		assertTrue(true);
	}

	@Test
	void test2() {
		final QuotaNeeded qn = new QuotaNeeded(0, 0, 1);
		assertNotNull(qn);
	}
}
