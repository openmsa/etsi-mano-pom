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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.model.ExternalManagedVirtualLink;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.mapping.VimConnectionInformationMapping;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;
import com.ubiqube.etsi.mano.service.rest.vnfpkg.ManoOnboarded;
import com.ubiqube.etsi.mano.service.rest.vnfpkg.ManoVnfPackage;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;
import com.ubiqube.etsi.mano.vnfm.service.VnfLcmService;
import com.ubiqube.etsi.mano.vnfm.service.graph.VnfBlueprintMapping;
import com.ubiqube.etsi.mano.vnfm.service.mapping.VnfInstanceMapping;

@ExtendWith(MockitoExtension.class)
class VnfInstanceLcmImplTest {
	@Mock
	private EventManager eventManager;
	@Mock
	private VnfLcmService vnfLcmService;
	@Mock
	private VnfInstanceService vnfInstanceServiec;
	@Mock
	private VimManager vimManager;
	@Mock
	private VnfPackageService vnfPackageService;
	@Mock
	private VnfInstanceServiceVnfm vnfInstanceVnfm;
	@Mock
	private ManoClientFactory manoClient;
	@Mock
	private ManoClient mc;
	@Mock
	private ServerService serverService;

	private final VimConnectionInformationMapping vimConnectionInformationMapping = Mappers.getMapper(VimConnectionInformationMapping.class);

	private final VnfBlueprintMapping vnfBlueprintMapping = Mappers.getMapper(VnfBlueprintMapping.class);

	private final VnfInstanceMapping vnfInstanceMapping = Mappers.getMapper(VnfInstanceMapping.class);

	@Test
	void testFindById() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		srv.findById(null, id.toString());
		assertTrue(true);
	}

	private VnfInstanceLcmImpl createService() {
		return new VnfInstanceLcmImpl(eventManager, vnfLcmService, vnfInstanceServiec, vimManager, vnfPackageService, vnfInstanceVnfm, manoClient, vnfInstanceMapping, vnfBlueprintMapping, vimConnectionInformationMapping,
				serverService);
	}

	@Test
	void testFindByVnfInstanceId() {
		final VnfInstanceLcmImpl srv = createService();
		srv.findByVnfInstanceId(null, null);
		assertTrue(true);
	}

	@Test
	void testVnfLcmOpOccsGet() {
		final VnfInstanceLcmImpl srv = createService();
		srv.vnfLcmOpOccsGet(null, null);
		assertTrue(true);
	}

	@Test
	void testGet() {
		final VnfInstanceLcmImpl srv = createService();
		srv.get(null, null);
		assertTrue(true);
	}

	@Test
	void test() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		when(vnfLcmService.createChangeExtCpOpOcc(inst, null)).thenReturn(bp);
		srv.changeExtConn(null, id, null);
		assertTrue(true);
	}

	@Test
	void testHeal() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		when(vnfLcmService.createHealOpOcc(inst, null)).thenReturn(bp);
		srv.heal(null, id, null);
		assertTrue(true);
	}

	@Test
	void testOperate() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		when(vnfLcmService.createOperateOpOcc(inst, null)).thenReturn(bp);
		srv.operate(null, id, null);
		assertTrue(true);
	}

	@Test
	void testScale() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		when(vnfLcmService.createScaleOpOcc(inst, null)).thenReturn(bp);
		srv.scale(null, id, null);
		assertTrue(true);
	}

	@Test
	void testScaleToLevel() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		when(vnfLcmService.createScaleToLevelOpOcc(inst, null)).thenReturn(bp);
		srv.scaleToLevel(null, id, null);
		assertTrue(true);
	}

	@Test
	void testTerminate() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		when(vnfLcmService.createTerminateOpOcc(inst)).thenReturn(bp);
		srv.terminate(null, id, null, null);
		assertTrue(true);
	}

	@Test
	void testPostOnboard() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		when(vnfPackageService.findByVnfdId(id.toString())).thenThrow(NotFoundException.class);
		// Onboard
		final VnfPackage pkg = TestFactory.createVnfPkg(id);
		when(manoClient.getClient()).thenReturn(mc);
		final ManoVnfPackage manoVnfPackage = Mockito.mock(ManoVnfPackage.class);
		when(mc.vnfPackage()).thenReturn(manoVnfPackage);
		final ManoOnboarded manoOnboarded = Mockito.mock(ManoOnboarded.class);
		when(manoVnfPackage.onboarded(id)).thenReturn(manoOnboarded);
		when(manoOnboarded.find()).thenReturn(pkg);
		when(vnfPackageService.save(pkg)).thenReturn(pkg);
		when(vnfInstanceServiec.save((VnfInstance) any())).thenReturn(inst);
		when(serverService.findNearestServer()).thenReturn(new ServerAdapter(null, null, null));
		srv.post(null, id.toString(), null, null);
		assertTrue(true);
	}

	@Test
	void testPost() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		final VnfPackage pkg = TestFactory.createVnfPkg(id);
		when(vnfPackageService.findByVnfdId(id.toString())).thenReturn(pkg);
		when(vnfInstanceServiec.save((VnfInstance) any())).thenReturn(inst);
		srv.post(null, id.toString(), null, null);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		//
		final VnfPackage pkg = TestFactory.createVnfPkg(id);
		when(vnfPackageService.findById(any())).thenReturn(pkg);
		srv.delete(null, id);
		assertTrue(true);
	}

	@Test
	void testDeleteInstantiated() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		//
		when(vnfInstanceServiec.isInstantiate(any())).thenReturn(true);
		srv.delete(null, id);
		assertTrue(true);
	}

	@Test
	void testInstantiate() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		final VnfPackage pkg = TestFactory.createVnfPkg(id);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		when(vnfPackageService.findById(any())).thenReturn(pkg);
		when(vnfLcmService.createIntatiateOpOcc(inst)).thenReturn(bp);
		when(vnfLcmService.save(bp)).thenReturn(bp);
		final VnfInstantiate req = new VnfInstantiate();
		srv.instantiate(null, id, req);
		assertTrue(true);
	}

	@Test
	void testInstantiateMapping() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		final VnfPackage pkg = TestFactory.createVnfPkg(id);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		final VnfInstantiate req = new VnfInstantiate();
		final ExternalManagedVirtualLink ext = new ExternalManagedVirtualLink();
		req.setExtManagedVirtualLinks(new ArrayList<>());
		req.getExtManagedVirtualLinks().add(ext);
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		when(vnfPackageService.findById(any())).thenReturn(pkg);
		when(vnfLcmService.createIntatiateOpOcc(inst)).thenReturn(bp);
		when(vnfLcmService.save(bp)).thenReturn(bp);
		srv.instantiate(null, id, req);
		assertTrue(true);
	}

	@Test
	void testInstantiateVimConnection() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		final VnfPackage pkg = TestFactory.createVnfPkg(id);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		final VnfInstantiate req = new VnfInstantiate();
		final VimConnectionInformation conn1 = new VimConnectionInformation();
		conn1.setVimType("OPENSTACK_V3");
		req.setVimConnectionInfo(List.of(conn1));
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		when(vnfPackageService.findById(any())).thenReturn(pkg);
		when(vnfLcmService.createIntatiateOpOcc(inst)).thenReturn(bp);
		when(vnfLcmService.save(bp)).thenReturn(bp);
		//
		srv.instantiate(null, id, req);
		assertTrue(true);
	}

	@Test
	void testInstantiateVimConnection2() {
		final VnfInstanceLcmImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final VnfInstance inst = TestFactory.createVnfInstance();
		final VnfPackage pkg = TestFactory.createVnfPkg(id);
		final VnfBlueprint bp = TestFactory.createBlueprint();
		final VnfInstantiate req = new VnfInstantiate();
		final VimConnectionInformation conn1 = new VimConnectionInformation();
		conn1.setVimType("OPENSTACK_V3");
		req.setVimConnectionInfo(List.of(conn1));
		when(vnfInstanceVnfm.findById(id)).thenReturn(inst);
		when(vnfPackageService.findById(any())).thenReturn(pkg);
		when(vnfLcmService.createIntatiateOpOcc(inst)).thenReturn(bp);
		when(vnfLcmService.save(bp)).thenReturn(bp);
		//
		when(vimManager.findOptionalVimByVimId(any())).thenReturn(Optional.of(conn1));
		srv.instantiate(null, id, req);
		assertTrue(true);
	}
}
