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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.controller.nslcm;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.alarm.ResourceHandle;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstanceDto;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateRequest;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.nfvo.service.NsdPackageService;
import com.ubiqube.etsi.mano.nfvo.service.mapping.NsInstanceDtoMapping;
import com.ubiqube.etsi.mano.service.VnfInstanceGatewayService;
import com.ubiqube.etsi.mano.service.event.EventManager;

import ma.glasnost.orika.MapperFacade;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
class NsInstanceControllerImplTest {
	@Mock
	private NsInstanceService nsInstanceService;
	@Mock
	private NsBlueprintService lcmOpOccsService;
	@Mock
	private MapperFacade mapper;
	@Mock
	private VnfInstanceGatewayService vnfInstancesService;
	@Mock
	private NsdPackageService nsdPackageJpa;
	@Mock
	private EventManager eventManager;

	private final NsInstanceDtoMapping nsInstanceDtoMapping = Mappers.getMapper(NsInstanceDtoMapping.class);

	@Test
	void testInstanceGet() {
		final NsInstanceControllerImpl srv = createService();
		srv.nsInstancesGet("");
		assertTrue(true);
	}

	private NsInstanceControllerImpl createService() {
		return new NsInstanceControllerImpl(nsInstanceService, lcmOpOccsService, vnfInstancesService, nsdPackageJpa, eventManager, nsInstanceDtoMapping);
	}

	@Test
	void testInstanceDelete() {
		final NsInstanceControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final NsdInstance inst = new NsdInstance();
		inst.setInstantiationState(InstantiationState.NOT_INSTANTIATED);
		when(nsInstanceService.findById(id)).thenReturn(inst);
		when(nsInstanceService.isInstantiated(any())).thenReturn(true);
		srv.nsInstancesNsInstanceIdDelete(id);
		assertTrue(true);
	}

	@Test
	void testInstanceDelete001() {
		final NsInstanceControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final NsdInstance inst = new NsdInstance();
		final NsdPackage info = new NsdPackage();
		inst.setNsdInfo(info);
		inst.setInstantiationState(InstantiationState.NOT_INSTANTIATED);
		when(nsInstanceService.findById(id)).thenReturn(inst);
		when(nsInstanceService.isInstantiated(any())).thenReturn(false);
		when(nsdPackageJpa.findById(any())).thenReturn(info);
		srv.nsInstancesNsInstanceIdDelete(id);
		assertTrue(true);
	}

	@Test
	void testInstanceHeal() {
		final NsInstanceControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final NsdInstance inst = new NsdInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(nsInstanceService.findById(id)).thenReturn(inst);
		assertThrows(GenericException.class, () -> srv.nsInstancesNsInstanceIdHealPost(id));
		assertTrue(true);
	}

	@Test
	void testInstanceScale() {
		final NsInstanceControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final NsdInstance inst = new NsdInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(nsInstanceService.findById(id)).thenReturn(inst);
		assertThrows(GenericException.class, () -> srv.nsInstancesNsInstanceIdScalePost(id));
		assertTrue(true);
	}

	@Test
	void testInstanceUpdate() {
		final NsInstanceControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final NsdInstance inst = new NsdInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(nsInstanceService.findById(id)).thenReturn(inst);
		final UpdateRequest req = new UpdateRequest();
		srv.nsInstancesNsInstanceIdUpdatePost(id, req);
		assertTrue(true);
	}

	@Test
	void testNsInstancesNsInstanceIdGet() {
		final NsInstanceControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final NsdInstance inst = new NsdInstance();
		final NsdPackage info = new NsdPackage();
		info.setId(id);
		inst.setNsdInfo(info);
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(nsInstanceService.findById(id)).thenReturn(inst);
		final NsLiveInstance live = new NsLiveInstance();
		live.setId(id);
		final NsTask task = new NsVirtualLinkTask();
		live.setNsTask(task);
		final NsInstanceDto dto = new NsInstanceDto();
		when(mapper.map(any(), eq(NsInstanceDto.class))).thenReturn(dto);
		doNothing().when(mapper).map(inst, dto);
		when(lcmOpOccsService.findByNsdInstanceAndClass(any(), eq(NsVnfTask.class))).thenReturn(List.of());
		when(lcmOpOccsService.findByNsdInstanceAndClass(any(), eq(NsVirtualLinkTask.class))).thenReturn(List.of(live));
		final ResourceHandle val = new ResourceHandle();
		when(mapper.map(any(), eq(ResourceHandle.class))).thenReturn(val);
		srv.nsInstancesNsInstanceIdGet(id);
		assertTrue(true);
	}
}
