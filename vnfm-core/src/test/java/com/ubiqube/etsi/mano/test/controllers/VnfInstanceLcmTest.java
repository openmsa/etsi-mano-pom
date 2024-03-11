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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;
import com.ubiqube.etsi.mano.service.rest.vnfpkg.ManoOnboarded;
import com.ubiqube.etsi.mano.service.rest.vnfpkg.ManoVnfPackage;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.test.MapperService;
import com.ubiqube.etsi.mano.vnfm.controller.vnflcm.VnfInstanceLcmImpl;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;
import com.ubiqube.etsi.mano.vnfm.service.VnfLcmService;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class VnfInstanceLcmTest {
	@Mock
	private EventManager eventManager;

	private final MapperFacade mapper;
	@Mock
	private VnfLcmService vnfLcmService;
	@Mock
	private VnfInstanceService vnfInstanceService;
	@Mock
	private VnfPackageService vnfPackageService;
	@Mock
	private VimManager vimManager;
	@Mock
	private ManoClientFactory manoClientFactory;
	@Mock
	private VnfInstanceServiceVnfm vnfInstanceServiceVnfm;
	@Mock
	private ManoClient onboardVnfPkg;

	public VnfInstanceLcmTest() {
		mapper = MapperService.getInstance().getMapper();
	}

	@Test
	void testGetNull() throws Exception {
		final VnfInstanceLcmImpl vnfInstanceLcm = new VnfInstanceLcmImpl(eventManager, mapper, vnfLcmService,
				vnfInstanceService, vimManager, vnfPackageService,
				vnfInstanceServiceVnfm, manoClientFactory);
		vnfInstanceLcm.get(null, null);
		assertTrue(true);
	}

	@Test
	void testGetEmpty() throws Exception {
		final VnfInstanceLcmImpl vnfInstanceLcm = new VnfInstanceLcmImpl(eventManager, mapper, vnfLcmService,
				vnfInstanceService, vimManager, vnfPackageService,
				vnfInstanceServiceVnfm, manoClientFactory);
		final MultiValueMap<String, String> rq = new LinkedMultiValueMap<>();
		vnfInstanceLcm.get(null, rq);
		assertTrue(true);
	}

	@Test
	void testGetEmptyFilter() throws Exception {
		final VnfInstanceLcmImpl vnfInstanceLcm = new VnfInstanceLcmImpl(eventManager, mapper, vnfLcmService,
				vnfInstanceService, vimManager, vnfPackageService,
				vnfInstanceServiceVnfm, manoClientFactory);
		final MultiValueMap<String, String> rq = new LinkedMultiValueMap<>();
		vnfInstanceLcm.get(null, rq);
		rq.put("filter", List.of());
		assertTrue(true);
	}

	@Test
	void testGetOneElement() throws Exception {
		final VnfInstanceLcmImpl vnfInstanceLcm = new VnfInstanceLcmImpl(eventManager, mapper, vnfLcmService,
				vnfInstanceService, vimManager, vnfPackageService,
				vnfInstanceServiceVnfm, manoClientFactory);
		final MultiValueMap<String, String> rq = new LinkedMultiValueMap<>();
		rq.put("filter", List.of(""));
		vnfInstanceLcm.get(null, rq);
		assertTrue(true);
	}

	@Test
	void testPost() {
		final VnfInstanceLcmImpl vnfInstanceLcm = new VnfInstanceLcmImpl(eventManager, mapper, vnfLcmService,
				vnfInstanceService, vimManager, vnfPackageService,
				vnfInstanceServiceVnfm, manoClientFactory);
		final UUID vnfdId = UUID.randomUUID();
		final VnfPackage pkg = TestFactory.createVnfPkg(vnfdId);
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		when(vnfPackageService.findByVnfdId(vnfdId)).thenReturn(pkg);
		when(vnfInstanceService.save((VnfInstance) any())).thenReturn(vnfInstance);
		vnfInstanceLcm.post(null, vnfdId.toString(), "vnfInstanceName", "descr");
		assertTrue(true);
	}

	@Test
	void testPostOnboard() {
		final VnfInstanceLcmImpl vnfInstanceLcm = new VnfInstanceLcmImpl(eventManager, mapper, vnfLcmService,
				vnfInstanceService, vimManager, vnfPackageService,
				vnfInstanceServiceVnfm, manoClientFactory);
		final UUID vnfdId = UUID.randomUUID();
		when(vnfPackageService.findByVnfdId(vnfdId)).thenThrow(NotFoundException.class);
		//
		final VnfPackage pkg = TestFactory.createVnfPkg(vnfdId);
		pkg.setOnboardingState(OnboardingStateType.ONBOARDED);
		when(manoClientFactory.getClient()).thenReturn(onboardVnfPkg);
		final ManoVnfPackage manoVnfPackage = Mockito.mock(ManoVnfPackage.class);
		when(onboardVnfPkg.vnfPackage()).thenReturn(manoVnfPackage);
		final ManoOnboarded manoOnboard = Mockito.mock(ManoOnboarded.class);
		when(manoVnfPackage.onboarded(any())).thenReturn(manoOnboard);
		when(manoOnboard.find()).thenReturn(pkg);
		when(vnfPackageService.save(pkg)).thenReturn(pkg);
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		when(vnfInstanceService.save((VnfInstance) any())).thenReturn(vnfInstance);
		vnfInstanceLcm.post(null, vnfdId.toString(), "vnfInstanceName", "descr");
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final VnfInstanceLcmImpl lcm = createVnfInstanceLcm();
		final UUID id = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		when(vnfInstanceServiceVnfm.findById(id)).thenReturn(vnfInstance);
		final VnfPackage pkg = TestFactory.createVnfPkg(UUID.randomUUID());
		when(vnfPackageService.findById(any())).thenReturn(pkg);
		lcm.delete(null, id);
		assertTrue(true);
	}

	@Test
	void testInstantiate() {
		final VnfInstanceLcmImpl lcm = createVnfInstanceLcm();
		final UUID id = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		when(vnfInstanceServiceVnfm.findById(id)).thenReturn(vnfInstance);
		final VnfPackage pkg = TestFactory.createVnfPkg(UUID.randomUUID());
		when(vnfPackageService.findById(any())).thenReturn(pkg);
		final VnfBlueprint blueprint = TestFactory.createBlueprint();
		when(vnfLcmService.createIntatiateOpOcc(vnfInstance)).thenReturn(blueprint);
		when(vnfLcmService.save(blueprint)).thenReturn(blueprint);
		final VnfInstantiate instantiateVnfRequest = createVnfInstantiate();
		lcm.instantiate(null, id, instantiateVnfRequest);
		assertTrue(true);
	}

	@Test
	void testTerminate() {
		final VnfInstanceLcmImpl lcm = createVnfInstanceLcm();
		final UUID id = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(id)).thenReturn(vnfInstance);
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		when(vnfLcmService.createTerminateOpOcc(vnfInstance)).thenReturn(bluePrint);
		lcm.terminate(null, id, CancelModeTypeEnum.FORCEFUL, 7);
		assertTrue(true);
	}

	@Test
	void testScleToLevel() {
		final VnfInstanceLcmImpl lcm = createVnfInstanceLcm();
		final UUID id = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(id)).thenReturn(vnfInstance);
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		final VnfScaleToLevelRequest scaleVnfToLevelRequest = new VnfScaleToLevelRequest();
		when(vnfLcmService.createScaleToLevelOpOcc(vnfInstance, scaleVnfToLevelRequest)).thenReturn(bluePrint);
		lcm.scaleToLevel(null, id, scaleVnfToLevelRequest);
		assertTrue(true);
	}

	@Test
	void testScle() {
		final VnfInstanceLcmImpl lcm = createVnfInstanceLcm();
		final UUID id = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(id)).thenReturn(vnfInstance);
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		final VnfScaleRequest scaleVnfToLevelRequest = new VnfScaleRequest();
		when(vnfLcmService.createScaleOpOcc(vnfInstance, scaleVnfToLevelRequest)).thenReturn(bluePrint);
		lcm.scale(null, id, scaleVnfToLevelRequest);
		assertTrue(true);
	}

	@Test
	void testOperate() {
		final VnfInstanceLcmImpl lcm = createVnfInstanceLcm();
		final UUID id = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(id)).thenReturn(vnfInstance);
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		final VnfOperateRequest scaleVnfToLevelRequest = new VnfOperateRequest();
		when(vnfLcmService.createOperateOpOcc(vnfInstance, scaleVnfToLevelRequest)).thenReturn(bluePrint);
		lcm.operate(null, id, scaleVnfToLevelRequest);
		assertTrue(true);
	}

	@Test
	void testChangeExtConn() {
		final VnfInstanceLcmImpl lcm = createVnfInstanceLcm();
		final UUID id = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(id)).thenReturn(vnfInstance);
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		final ChangeExtVnfConnRequest scaleVnfToLevelRequest = new ChangeExtVnfConnRequest();
		when(vnfLcmService.createChangeExtCpOpOcc(vnfInstance, scaleVnfToLevelRequest)).thenReturn(bluePrint);
		lcm.changeExtConn(null, id, scaleVnfToLevelRequest);
		assertTrue(true);
	}

	@Test
	void testHeal() {
		final VnfInstanceLcmImpl lcm = createVnfInstanceLcm();
		final UUID id = UUID.randomUUID();
		final VnfInstance vnfInstance = TestFactory.createVnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceServiceVnfm.findById(id)).thenReturn(vnfInstance);
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		final VnfHealRequest scaleVnfToLevelRequest = new VnfHealRequest();
		when(vnfLcmService.createHealOpOcc(vnfInstance, scaleVnfToLevelRequest)).thenReturn(bluePrint);
		lcm.heal(null, id, scaleVnfToLevelRequest);
		assertTrue(true);
	}

	private static VnfInstantiate createVnfInstantiate() {
		final VnfInstantiate instantiateVnfRequest = new VnfInstantiate();
		instantiateVnfRequest.setFlavourId("");
		return instantiateVnfRequest;
	}

	private VnfInstanceLcmImpl createVnfInstanceLcm() {
		return new VnfInstanceLcmImpl(eventManager, mapper, vnfLcmService,
				vnfInstanceService, vimManager, vnfPackageService,
				vnfInstanceServiceVnfm, manoClientFactory);
	}
}
