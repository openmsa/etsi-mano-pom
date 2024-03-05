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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class VnfInstanceGenericFrontControllerImplTest {
	@Mock
	private VnfInstanceLcmImpl vnfInstanceLcm;
	@Mock
	private VnfInstanceService vnfInstanceService;
	@Mock
	private MapperFacade mapper;
	@Mock
	private VnfInstanceServiceVnfm VnfInstanceVnfm;

	@Test
	void test() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(VnfInstanceVnfm.findById(any())).thenReturn(inst);
		srv.changeExtConn(null, null, func);
		assertTrue(true);
	}

	@Test
	void testChangeFalvor() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(VnfInstanceVnfm.findById(any())).thenReturn(inst);
		assertThrows(GenericException.class, () -> srv.changeFlavour(null, null, func));
	}

	@Test
	void testChangeVnfPkg() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(VnfInstanceVnfm.findById(any())).thenReturn(inst);
		assertThrows(GenericException.class, () -> srv.changeVnfPkg(null, null, func));
	}

	@Test
	void testCreateSnaphot() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(VnfInstanceVnfm.findById(any())).thenReturn(inst);
		assertThrows(GenericException.class, () -> srv.createSnapshot(null, null, func));
	}

	@Test
	void testSnaphot() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(VnfInstanceVnfm.findById(any())).thenReturn(inst);
		assertThrows(GenericException.class, () -> srv.snapshot(null, null));
	}

	@Test
	void testInstantiate() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		srv.instantiate(null, null, func);
		assertTrue(true);
	}

	@Test
	void testHeal() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(VnfInstanceVnfm.findById(any())).thenReturn(inst);
		srv.heal(null, null, null, func);
		assertTrue(true);
	}

	@Test
	void testOperate() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		srv.operate(null, null, func);
		assertTrue(true);
	}

	@Test
	void testModify() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		final Function<VnfInstance, String> getSelfLink = x -> "";
		srv.modify(null, null, null, getSelfLink);
		assertTrue(true);
	}

	@Test
	void testScale() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		srv.scale(null, null, func);
		assertTrue(true);
	}

	@Test
	void testScaleToLevevl() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		srv.scaleToLevel(null, null, func);
		assertTrue(true);
	}

	@Test
	void testTerminate() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Function<VnfBlueprint, String> func = x -> "";
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		srv.terminate(null, null, null, func);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Consumer<String> lnk = x -> {
		};
		srv.findById(null, null, lnk, null);
		assertTrue(true);
	}

	@Test
	void testDeleteById() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		srv.deleteById(null);
		assertTrue(true);
	}

	@Test
	void testCreate() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		final Consumer<String> lnk = x -> {
		};
		srv.create(null, null, null, null, lnk, "http://localhost/");
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final VnfInstanceGenericFrontControllerImpl srv = new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstanceService, mapper, VnfInstanceVnfm);
		srv.search(null, null, null, null);
		assertTrue(true);
	}
}
