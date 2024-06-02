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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;
import com.ubiqube.etsi.mano.service.vim.VimTypeConverter;
import com.ubiqube.etsi.mano.vnfm.controller.vnflcm.VnfInstanceGenericFrontControllerImpl;
import com.ubiqube.etsi.mano.vnfm.controller.vnflcm.VnfInstanceLcmImpl;
import com.ubiqube.etsi.mano.vnfm.fc.vnflcm.VnfInstanceGenericFrontController;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;

import jakarta.annotation.Nonnull;

@ExtendWith(MockitoExtension.class)
class VnfInstanceGenericFrontControllerTest {
	@Mock
	private VnfInstanceLcmImpl vnfInstanceLcm;
	@Mock
	private VnfInstanceService vnfInstancesService;
	@Mock
	private VnfInstanceServiceVnfm vnfInstanceServiceVnfm;
	@Nonnull
	private final Function<VnfBlueprint, String> getSelfLink = x -> "http://test-link";
	@Nonnull
	private final Function<VnfInstance, String> getInstanceSelfLink = x -> "http://test-link";
	@Mock
	private VimTypeConverter vimTypeConverter;
	@Nonnull
	private final Consumer<Object> makeLink = x -> {
		//
	};

	@Test
	void testTerminate() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID vnfInstanceId = UUID.randomUUID();
		final VnfBlueprint blue = TestFactory.createBlueprint();
		when(vnfInstanceLcm.terminate(null, vnfInstanceId, CancelModeTypeEnum.FORCEFUL, 56)).thenReturn(blue);
		final ResponseEntity<Void> res = fc.terminate(vnfInstanceId, CancelModeTypeEnum.FORCEFUL, 56, getSelfLink);
		assertEquals(HttpStatusCode.valueOf(202), res.getStatusCode());
		assertEquals(URI.create("http://test-link"), res.getHeaders().getLocation());
	}

	@Test
	void testScaleToLevel() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID vnfInstanceId = UUID.randomUUID();
		final ResponseEntity<Void> res = fc.scaleToLevel(vnfInstanceId, new VnfScaleToLevelRequest(), getSelfLink);
		assertEquals(HttpStatusCode.valueOf(202), res.getStatusCode());
		assertEquals(URI.create("http://test-link"), res.getHeaders().getLocation());
	}

	@Test
	void testScale() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID vnfInstanceId = UUID.randomUUID();
		final ResponseEntity<Void> res = fc.scale(vnfInstanceId, new VnfScaleRequest(), getSelfLink);
		assertEquals(HttpStatusCode.valueOf(202), res.getStatusCode());
		assertEquals(URI.create("http://test-link"), res.getHeaders().getLocation());
	}

	@Test
	void testSnapshot() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID vnfInstanceId = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(vnfInstanceId)).thenReturn(vnfInstance);
		final Object request = new Object();
		assertThrows(GenericException.class, () -> fc.snapshot(vnfInstanceId, request));

	}

	@Test
	void testModify() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID vnfInstanceId = UUID.randomUUID();
		final ResponseEntity<Void> res = fc.modify(vnfInstanceId, "{}", null, getInstanceSelfLink);
		assertEquals(HttpStatusCode.valueOf(202), res.getStatusCode());
		assertEquals(URI.create("http://test-link"), res.getHeaders().getLocation());
	}

	@Test
	void testOperate() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID vnfInstanceId = UUID.randomUUID();
		final ResponseEntity<Void> res = fc.operate(vnfInstanceId, new VnfOperateRequest(), getSelfLink);
		assertEquals(HttpStatusCode.valueOf(202), res.getStatusCode());
		assertEquals(URI.create("http://test-link"), res.getHeaders().getLocation());
	}

	@Test
	void testInstantiate() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID vnfInstanceId = UUID.randomUUID();
		final ResponseEntity<Void> res = fc.instantiate(vnfInstanceId, new VnfInstantiate(), getSelfLink);
		assertEquals(HttpStatusCode.valueOf(202), res.getStatusCode());
		assertEquals(URI.create("http://test-link"), res.getHeaders().getLocation());
	}

	@Test
	void testHeal() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID vnfInstanceId = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(vnfInstanceId)).thenReturn(vnfInstance);
		final ResponseEntity<Void> res = fc.heal(vnfInstanceId, "cause", Map.of(), getSelfLink);
		assertEquals(HttpStatusCode.valueOf(202), res.getStatusCode());
		assertEquals(URI.create("http://test-link"), res.getHeaders().getLocation());
	}

	@Test
	void testFindById() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID vnfInstanceId = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(vnfInstanceId)).thenReturn(vnfInstance);
		final ResponseEntity<Object> res = fc.findById(vnfInstanceId, x -> "", makeLink, "http://test-link");
		assertEquals(HttpStatusCode.valueOf(200), res.getStatusCode());
	}

	@Test
	void testDeleteById() {
		final VnfInstanceGenericFrontController fc = createFc();
		final ResponseEntity<Void> res = fc.deleteById(UUID.randomUUID());
		assertEquals(HttpStatusCode.valueOf(204), res.getStatusCode());
	}

	@Test
	void testCreateSnapShot() {
		final VnfInstanceGenericFrontController fc = createFc();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		final UUID id = UUID.randomUUID();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(id)).thenReturn(vnfInstance);
		final Object obj = new Object();
		assertThrows(GenericException.class, () -> fc.createSnapshot(id, obj, getSelfLink));
	}

	@Test
	void testChangeVnfpkg() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID id = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(id)).thenReturn(vnfInstance);
		final Object obj = new Object();
		assertThrows(GenericException.class, () -> fc.changeVnfPkg(id, obj, getSelfLink));
	}

	@Test
	void testChangeFlavor() {
		final VnfInstanceGenericFrontController fc = createFc();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		final UUID id = UUID.randomUUID();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(id)).thenReturn(vnfInstance);
		final Object obj = new Object();
		assertThrows(GenericException.class, () -> fc.changeFlavour(id, obj, getSelfLink));
	}

	@Test
	void testChangeExtconn() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID vnfInstanceId = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(vnfInstanceId)).thenReturn(vnfInstance);
		final ResponseEntity<Void> res = fc.changeExtConn(vnfInstanceId, new ChangeExtVnfConnRequest(), getSelfLink);
		assertEquals(HttpStatusCode.valueOf(202), res.getStatusCode());
		assertEquals(URI.create("http://test-link"), res.getHeaders().getLocation());
	}

	@Test
	void testCreate() {
		final VnfInstanceGenericFrontController fc = createFc();
		final UUID vnfInstanceId = UUID.randomUUID();
		final ResponseEntity<Object> res = fc.create(vnfInstanceId.toString(), "name", "descr", x -> "", makeLink, getSelfLink.apply(null));
		assertEquals(HttpStatusCode.valueOf(201), res.getStatusCode());
		assertEquals(URI.create("http://test-link"), res.getHeaders().getLocation());
	}

	@Test
	void testSearch() {
		final VnfInstanceGenericFrontController fc = createFc();
		fc.search(null, null, null, null, null);
		assertTrue(true);
	}

	private VnfInstanceGenericFrontController createFc() {
		return new VnfInstanceGenericFrontControllerImpl(vnfInstanceLcm, vnfInstancesService, vnfInstanceServiceVnfm, vimTypeConverter);
	}
}
