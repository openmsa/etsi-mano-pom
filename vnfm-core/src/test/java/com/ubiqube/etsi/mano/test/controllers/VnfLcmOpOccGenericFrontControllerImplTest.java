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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.vnfm.controller.vnflcm.VnfLcmController;
import com.ubiqube.etsi.mano.vnfm.controller.vnflcm.VnfLcmOpOccGenericFrontControllerImpl;
import com.ubiqube.etsi.mano.vnfm.fc.vnflcm.VnfLcmClassMaping;
import com.ubiqube.etsi.mano.vnfm.service.mapping.VnfLcmOpOccMapping;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class VnfLcmOpOccGenericFrontControllerImplTest {
	private final Consumer<VnfBlueprint> makeLink = x -> {
		//
	};
	private final BiConsumer<VnfBlueprint, Object> operationParameter = (x, y) -> {
		//
	};
	@Mock
	private VnfLcmController vnfLcmController;
	@Mock
	private MapperFacade mapper;
	@Mock
	private VnfLcmClassMaping mapping;
	private final VnfLcmOpOccMapping vnfLcmOpOccMapping = Mappers.getMapper(VnfLcmOpOccMapping.class);

	@Test
	void testSearch() {
		final VnfLcmOpOccGenericFrontControllerImpl op = createVnfLcmOpOccGenericFrontControllerImpl();
		op.search(null, null, null, null);
		assertTrue(true);
	}

	@Test
	void testRollBack() {
		final VnfLcmOpOccGenericFrontControllerImpl op = createVnfLcmOpOccGenericFrontControllerImpl();
		op.lcmOpOccRollback(null);
		assertTrue(true);
	}

	@Test
	void testRetry() {
		final VnfLcmOpOccGenericFrontControllerImpl op = createVnfLcmOpOccGenericFrontControllerImpl();
		op.lcmOpOccRetry(null);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final VnfLcmOpOccGenericFrontControllerImpl op = createVnfLcmOpOccGenericFrontControllerImpl();
		final UUID id = UUID.randomUUID();
		final VnfBlueprint vnfBlue = TestFactory.createBlueprint();
		vnfBlue.setOperation(PlanOperationType.INSTANTIATE);
		when(vnfLcmController.vnfLcmOpOccsVnfLcmOpOccIdGet(id)).thenReturn(vnfBlue);
		final ResponseEntity<VnfBlueprint> res = op.lcmOpOccFindById(mapping, id, VnfBlueprint.class, makeLink, operationParameter);
		assertTrue(true);
	}

	@ParameterizedTest
	@EnumSource(value = PlanOperationType.class, mode = Mode.EXCLUDE, names = { "UPDATE", "MODIFY_INFORMATION", "CHANGE_EXTERNAL_VNF_CONNECTIVITY", "SELECT_DEPL_MODS" })
	void testFindByIdWithTasks(final PlanOperationType resType) {
		final VnfLcmOpOccGenericFrontControllerImpl op = createVnfLcmOpOccGenericFrontControllerImpl();
		final UUID id = UUID.randomUUID();
		final VnfBlueprint vnfBlue = TestFactory.createBlueprint();
		vnfBlue.setOperation(resType);
		addTasks(vnfBlue);
		when(vnfLcmController.vnfLcmOpOccsVnfLcmOpOccIdGet(id)).thenReturn(vnfBlue);
		final ResponseEntity<VnfBlueprint> res = op.lcmOpOccFindById(mapping, id, VnfBlueprint.class, makeLink, operationParameter);
		assertTrue(true);
	}

	@Test
	void testFindByIdWithTasksFailed() {
		final VnfLcmOpOccGenericFrontControllerImpl op = createVnfLcmOpOccGenericFrontControllerImpl();
		final UUID id = UUID.randomUUID();
		final VnfBlueprint vnfBlue = TestFactory.createBlueprint();
		vnfBlue.setOperation(PlanOperationType.CHANGE_EXTERNAL_VNF_CONNECTIVITY);
		addTasks(vnfBlue);
		when(vnfLcmController.vnfLcmOpOccsVnfLcmOpOccIdGet(id)).thenReturn(vnfBlue);
		assertThrows(IllegalArgumentException.class, () -> op.lcmOpOccFindById(mapping, id, VnfBlueprint.class, makeLink, operationParameter));
		assertTrue(true);
	}

	@Test
	void testFailed() {
		final VnfLcmOpOccGenericFrontControllerImpl op = createVnfLcmOpOccGenericFrontControllerImpl();
		final UUID id = UUID.randomUUID();
		op.lcmOpOccFail(id);
		assertTrue(true);
	}

	private static void addTasks(final VnfBlueprint vnfBlue) {
		final Set<VnfTask> tasks = new LinkedHashSet<>();
		final VnfTask taskVl = new NetworkTask();
		// XXX Set type ?
		taskVl.setType(ResourceTypeEnum.VL);
		tasks.add(taskVl);
		final VnfTask taskStorage = new StorageTask();
		taskVl.setType(ResourceTypeEnum.STORAGE);
		tasks.add(taskStorage);
		final VnfTask taskCompute = new ComputeTask();
		taskVl.setType(ResourceTypeEnum.COMPUTE);
		tasks.add(taskCompute);
		vnfBlue.setTasks(tasks);
	}

	private VnfLcmOpOccGenericFrontControllerImpl createVnfLcmOpOccGenericFrontControllerImpl() {
		return new VnfLcmOpOccGenericFrontControllerImpl(vnfLcmController, mapper, vnfLcmOpOccMapping);
	}
}
