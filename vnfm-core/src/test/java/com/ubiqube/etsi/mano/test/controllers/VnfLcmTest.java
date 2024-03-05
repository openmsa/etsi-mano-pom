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
package com.ubiqube.etsi.mano.test.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.vnfm.controller.vnflcm.VnfLcmControllerImpl;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfLcmService;

@ExtendWith(MockitoExtension.class)
class VnfLcmTest {
	@Mock
	private VnfLcmService vnfLcmOpOccsRepository;
	@Mock
	private VnfInstanceService vnfInstanceService;
	@Mock
	private SearchableService searchableService;

	@Test
	void testName() throws Exception {
		final VnfLcmControllerImpl vnfController = new VnfLcmControllerImpl(vnfLcmOpOccsRepository, vnfInstanceService, searchableService);
		vnfController.vnfLcmOpOccsVnfLcmOpOccIdGet(null);
		assertTrue(true);
	}

	@Test
	void testFailed() throws Exception {
		final VnfLcmControllerImpl vnfController = new VnfLcmControllerImpl(vnfLcmOpOccsRepository, vnfInstanceService, searchableService);
		final UUID id = UUID.randomUUID();
		final VnfBlueprint vnfBlue = TestFactory.createBlueprint();
		vnfBlue.setVnfInstance(TestFactory.createVnfInstance());
		vnfBlue.setOperationStatus(OperationStatusType.FAILED_TEMP);
		when(vnfLcmOpOccsRepository.findById(id)).thenReturn(vnfBlue);
		vnfController.failed(id);
		assertTrue(true);
	}
}
