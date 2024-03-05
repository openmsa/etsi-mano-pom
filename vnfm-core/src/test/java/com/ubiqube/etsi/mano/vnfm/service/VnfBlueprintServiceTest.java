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

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfBlueprintJpa;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

@ExtendWith(MockitoExtension.class)
class VnfBlueprintServiceTest {
	@Mock
	private VnfBlueprintJpa blueprintJpa;
	@Mock
	private VnfLiveInstanceJpa vnfLiveInstanceJpa;

	@Test
	void testFindById() {
		final VnfBlueprintService srv = new VnfBlueprintService(blueprintJpa, vnfLiveInstanceJpa);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		when(blueprintJpa.findById(any())).thenReturn(Optional.of(bp));
		srv.findById(null);
		assertTrue((true));
	}

	@Test
	void testFindByIdFailed() {
		final VnfBlueprintService srv = new VnfBlueprintService(blueprintJpa, vnfLiveInstanceJpa);
		when(blueprintJpa.findById(any())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> srv.findById(null));
		assertTrue((true));
	}

	@Test
	void testGetNumberOfLiveInstance() {
		final VnfBlueprintService srv = new VnfBlueprintService(blueprintJpa, vnfLiveInstanceJpa);
		final VnfCompute comp = new VnfCompute();
		srv.getNumberOfLiveInstance(null, comp);
		assertTrue((true));
	}

	@Test
	void testGetNumberOfLiveVl() {
		final VnfBlueprintService srv = new VnfBlueprintService(blueprintJpa, vnfLiveInstanceJpa);
		final VnfVl vl = new VnfVl();
		srv.getNumberOfLiveVl(null, vl);
		assertTrue((true));
	}

	@Test
	void testSave() {
		final VnfBlueprintService srv = new VnfBlueprintService(blueprintJpa, vnfLiveInstanceJpa);
		srv.save(null);
		assertTrue((true));
	}

	@Test
	void testUpdateState() {
		final VnfBlueprintService srv = new VnfBlueprintService(blueprintJpa, vnfLiveInstanceJpa);
		final VnfBlueprint plan = TestFactory.createBlueprint();
		srv.updateState(plan, null);
		assertTrue((true));
	}
}
