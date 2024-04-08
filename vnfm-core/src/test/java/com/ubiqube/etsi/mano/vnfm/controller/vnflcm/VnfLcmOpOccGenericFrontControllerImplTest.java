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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vnfm.fc.vnflcm.VnfLcmClassMaping;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class VnfLcmOpOccGenericFrontControllerImplTest {
	@Mock
	private VnfLcmController vnfLcmController;
	@Mock
	private MapperFacade mapper;
	@Mock
	private VnfLcmClassMaping mapping;

	@Test
	void testCancel() {
		final VnfLcmOpOccGenericFrontControllerImpl srv = new VnfLcmOpOccGenericFrontControllerImpl(vnfLcmController, mapper);
		final UUID id = UUID.randomUUID();
		srv.lcmOpOccCancel(id);
		assertTrue(true);
	}

	@Test
	void testFail() {
		final VnfLcmOpOccGenericFrontControllerImpl srv = new VnfLcmOpOccGenericFrontControllerImpl(vnfLcmController, mapper);
		final UUID id = UUID.randomUUID();
		srv.lcmOpOccFail(id);
		assertTrue(true);
	}

	@ParameterizedTest
	@EnumSource(value = PlanOperationType.class, mode = Mode.EXCLUDE, names = { "UPDATE", "MODIFY_INFORMATION", "CHANGE_EXTERNAL_VNF_CONNECTIVITY", "SELECT_DEPL_MODS" })
	void testFindById(final PlanOperationType param) {
		final VnfLcmOpOccGenericFrontControllerImpl srv = new VnfLcmOpOccGenericFrontControllerImpl(vnfLcmController, mapper);
		final UUID id = UUID.randomUUID();
		final VnfBlueprint bp = TestFactory.createBlueprint();
		bp.setOperation(param);
		final Set<VnfTask> tasks = new LinkedHashSet<>();
		final ComputeTask t1 = new ComputeTask();
		t1.setType(ResourceTypeEnum.COMPUTE);
		tasks.add(t1);
		final StorageTask t2 = new StorageTask();
		t2.setType(ResourceTypeEnum.STORAGE);
		tasks.add(t2);
		final NetworkTask t3 = new NetworkTask();
		t3.setType(ResourceTypeEnum.VL);
		tasks.add(t3);
		bp.setTasks(tasks);
		when(vnfLcmController.vnfLcmOpOccsVnfLcmOpOccIdGet(id)).thenReturn(bp);
		final Consumer<String> link = x -> {
		};
		final BiConsumer<String, Object> operationParameter = (x, y) -> {
		};
		srv.lcmOpOccFindById(mapping, id, null, link, operationParameter);
		assertTrue(true);
	}

	@Test
	void testFindByIdFail() {
		assertThrows(IllegalArgumentException.class, () -> testFindById(PlanOperationType.UPDATE));
	}

	@Test
	void testRetry() {
		final VnfLcmOpOccGenericFrontControllerImpl srv = new VnfLcmOpOccGenericFrontControllerImpl(vnfLcmController, mapper);
		final UUID id = UUID.randomUUID();
		srv.lcmOpOccRetry(id);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final VnfLcmOpOccGenericFrontControllerImpl srv = new VnfLcmOpOccGenericFrontControllerImpl(vnfLcmController, mapper);
		final UUID id = UUID.randomUUID();
		srv.lcmOpOccRollback(id);
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final VnfLcmOpOccGenericFrontControllerImpl srv = new VnfLcmOpOccGenericFrontControllerImpl(vnfLcmController, mapper);
		final UUID id = UUID.randomUUID();
		srv.search(null, null, null);
		assertTrue(true);
	}
}
