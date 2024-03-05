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

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;

class VnfLcmFactoryTest {

	@Test
	void testCreateVnfBlueprint() {
		VnfLcmFactory.createVnfBlueprint(PlanOperationType.CHANGE_EXT_CONN, UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testCreateVnfInstance() {
		final VnfPackage vnfPkgInfo = new VnfPackage();
		VnfLcmFactory.createVnfInstance("name", "descr", vnfPkgInfo);
		assertTrue(true);
	}
}
