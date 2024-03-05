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
package com.ubiqube.etsi.mano.test.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.OperationalStateType;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.model.CommScaleInfo;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;
import com.ubiqube.etsi.mano.service.search.ManoSearch;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfBlueprintJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfLcmService;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
class VnfLcmServiceTest {
	@Mock
	private VnfBlueprintJpa vnfBlueprintJpa;
	@Mock
	private EntityManager em;
	@Mock
	private VnfInstanceService vnfInstancesService;
	@Mock
	private GrammarParser grammarParser;
	@Mock
	private ManoSearch manoSearch;

	@Test
	void testCreateInstantiate() {
		final VnfLcmService op = createVnfLcmService();
		final VnfInstance instance = TestFactory.createVnfInstance();
		//
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		when(vnfBlueprintJpa.save(any())).thenReturn(bluePrint);
		final VnfBlueprint res = op.createIntatiateOpOcc(instance);
		assertNotNull(res);
	}

	@Test
	void testCreateTerminate() {
		final VnfLcmService op = createVnfLcmService();
		final VnfInstance instance = TestFactory.createVnfInstance();
		//
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		when(vnfBlueprintJpa.save(any())).thenReturn(bluePrint);
		final VnfBlueprint res = op.createTerminateOpOcc(instance);
		assertNotNull(res);
	}

	@Test
	void testCreateHealOpOcc() {
		final VnfLcmService op = createVnfLcmService();
		final VnfInstance instance = TestFactory.createVnfInstance();
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		final VnfHealRequest heal = new VnfHealRequest();
		when(vnfBlueprintJpa.save(any())).thenReturn(bluePrint);
		final VnfLiveInstance comp01 = TestFactory.createLiveCompute();
		final List<VnfLiveInstance> lives = List.of(comp01);
		when(vnfInstancesService.getLiveComputeInstanceOf(instance)).thenReturn(lives);
		final VnfBlueprint res = op.createHealOpOcc(instance, heal);
		assertNotNull(res);
	}

	@Test
	void createOperateLcmOpOcc() {
		final VnfLcmService op = createVnfLcmService();
		final VnfInstance instance = TestFactory.createVnfInstance();
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		final VnfOperateRequest rq = new VnfOperateRequest();
		rq.setChangeStateTo(OperationalStateType.STARTED);
		rq.setGracefulStopTimeout(111);
		final List<VnfLiveInstance> lives = List.of(TestFactory.createLiveCompute());
		when(vnfInstancesService.getLiveComputeInstanceOf(instance)).thenReturn(lives);
		when(vnfBlueprintJpa.save(any())).thenReturn(bluePrint);
		final VnfBlueprint res = op.createOperateOpOcc(instance, rq);
		assertNotNull(res);
	}

	@Test
	void createScaleLcmOpOcc() {
		final VnfLcmService op = createVnfLcmService();
		final VnfInstance instance = TestFactory.createVnfInstance();
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		final VnfScaleRequest rq = new VnfScaleRequest();
		rq.setAspectId("");
		rq.setNumberOfSteps(11);
		final List<VnfLiveInstance> lives = List.of(TestFactory.createLiveCompute());
		when(vnfBlueprintJpa.save(any())).thenReturn(bluePrint);
		final VnfBlueprint res = op.createScaleOpOcc(instance, rq);
		assertNotNull(res);
	}

	@Test
	void createScaleToLevelLcmOpOcc() {
		final VnfLcmService op = createVnfLcmService();
		final VnfInstance instance = TestFactory.createVnfInstance();
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		final VnfScaleToLevelRequest rq = new VnfScaleToLevelRequest();
		when(vnfBlueprintJpa.save(any())).thenReturn(bluePrint);
		final VnfBlueprint res = op.createScaleToLevelOpOcc(instance, rq);
		assertNotNull(res);
	}

	@Test
	void createScaleToLevelLcmOpOcc002() {
		final VnfLcmService op = createVnfLcmService();
		final VnfInstance instance = TestFactory.createVnfInstance();
		final VnfBlueprint bluePrint = TestFactory.createBlueprint();
		final VnfScaleToLevelRequest rq = new VnfScaleToLevelRequest();
		final CommScaleInfo s0 = new CommScaleInfo("aspect", 1);
		final List<CommScaleInfo> scaleInfos = List.of(s0);
		rq.setScaleInfo(scaleInfos);
		when(vnfBlueprintJpa.save(any())).thenReturn(bluePrint);
		final VnfBlueprint res = op.createScaleToLevelOpOcc(instance, rq);
		assertNotNull(res);
	}

	@Test
	void testFindById() {
		final VnfLcmService op = createVnfLcmService();
		final VnfBlueprint bp = TestFactory.createBlueprint();
		when(vnfBlueprintJpa.findById(any())).thenReturn(Optional.of(bp));
		op.findById(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testFindByVnfInstanceId() {
		final VnfLcmService op = createVnfLcmService();
		op.findByVnfInstanceId(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testSave() {
		final VnfLcmService op = createVnfLcmService();
		op.save(null);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final VnfLcmService op = createVnfLcmService();
		op.deleteByVnfInstance(null);
		assertTrue(true);
	}

	@Test
	void testCreateChangeExtCpOpOcc() {
		final VnfLcmService op = createVnfLcmService();
		final VnfInstance vnfinst = TestFactory.createVnfInstance();
		final VnfBlueprint bp = TestFactory.createBlueprint();
		when(vnfBlueprintJpa.save(any())).thenReturn(bp);
		op.createChangeExtCpOpOcc(vnfinst, null);
		assertTrue(true);
	}

	private VnfLcmService createVnfLcmService() {
		return new VnfLcmService(vnfBlueprintJpa, vnfInstancesService, grammarParser, manoSearch);
	}
}
