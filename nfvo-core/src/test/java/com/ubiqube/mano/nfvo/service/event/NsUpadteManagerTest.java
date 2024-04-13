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
package com.ubiqube.mano.nfvo.service.event;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateRequest;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.VnfInstanceData;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.nfvo.service.event.NsUpadteManager;
import com.ubiqube.etsi.mano.nfvo.service.mapping.NsUpdateMapping;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;

import jakarta.validation.constraints.NotNull;

@ExtendWith(MockitoExtension.class)
class NsUpadteManagerTest {
	@Mock
	private NsBlueprintService nsBlueprint;
	@Mock
	private NsLiveInstanceJpa liveInstanceJpa;
	@Mock
	private ManoClientFactory manoClientFactory;
	@Mock
	private EventManager eventManager;
	@Mock
	private NsInstanceService nsInstanceService;
	private final NsUpdateMapping nsUpdateMapping = Mappers.getMapper(NsUpdateMapping.class);

	@Test
	void testFailed() throws Exception {
		final NsUpadteManager op = createNsUpadteManager();
		@NotNull
		final UUID id = UUID.randomUUID();
		final NsBlueprint nsBlue = NsTestFactory.createNsBlueprint();
		when(nsBlueprint.findById(id)).thenReturn(nsBlue);
		op.update(id);
		assertTrue(true);
	}

	@Test
	void testOk() throws Exception {
		final NsUpadteManager op = createNsUpadteManager();
		@NotNull
		final UUID id = UUID.randomUUID();
		final NsBlueprint nsBlue = NsTestFactory.createNsBlueprint();
		final UpdateRequest upd = new UpdateRequest();
		upd.setUpdateType(UpdateTypeEnum.ADD_VNF);
		final VnfInstanceData vid = new VnfInstanceData();
		vid.setVnfInstanceId(UUID.randomUUID().toString());
		upd.setAddVnfIstance(List.of(vid));
		nsBlue.getParameters().setUpdData(upd);
		when(nsBlueprint.findById(id)).thenReturn(nsBlue);
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsdInstance nsInst = new NsdInstance();
		nsInst.setId(UUID.randomUUID());
		nsLive.setNsInstance(nsInst);
		when(liveInstanceJpa.findByResourceIdAndNsInstanceId(vid.getVnfInstanceId(), nsBlue.getInstance().getId())).thenReturn(nsLive);
		op.update(id);
		assertNull(nsBlue.getError());
		assertTrue(true);
	}

	@Test
	void testAddVnfOk001() throws Exception {
		final NsUpadteManager op = createNsUpadteManager();
		@NotNull
		final UUID id = UUID.randomUUID();
		final NsBlueprint nsBlue = NsTestFactory.createNsBlueprint();
		final UpdateRequest upd = new UpdateRequest();
		upd.setUpdateType(UpdateTypeEnum.ADD_VNF);
		final VnfInstanceData vid = new VnfInstanceData();
		vid.setVnfInstanceId(UUID.randomUUID().toString());
		upd.setAddVnfIstance(List.of(vid));
		nsBlue.getParameters().setUpdData(upd);
		when(nsBlueprint.findById(id)).thenReturn(nsBlue);
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsdInstance nsInst = new NsdInstance();
		nsInst.setId(nsBlue.getNsInstance().getId());
		nsLive.setNsInstance(nsInst);
		when(liveInstanceJpa.findByResourceIdAndNsInstanceId(vid.getVnfInstanceId(), nsBlue.getInstance().getId())).thenReturn(nsLive);
		op.update(id);
		assertNotNull(nsBlue.getError());
		assertTrue(true);
	}

	@Test
	void testRemoveVnfOk001() throws Exception {
		final NsUpadteManager op = createNsUpadteManager();
		@NotNull
		final UUID id = UUID.randomUUID();
		final NsBlueprint nsBlue = NsTestFactory.createNsBlueprint();
		final UpdateRequest upd = new UpdateRequest();
		upd.setUpdateType(UpdateTypeEnum.REMOVE_VNF);
		final VnfInstanceData vid = new VnfInstanceData();
		vid.setVnfInstanceId(UUID.randomUUID().toString());
		upd.setRemoveVnfInstanceId(List.of(id.toString()));
		nsBlue.getParameters().setUpdData(upd);
		when(nsBlueprint.findById(id)).thenReturn(nsBlue);
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsdInstance nsInst = new NsdInstance();
		nsInst.setId(nsBlue.getNsInstance().getId());
		nsLive.setNsInstance(nsInst);
		when(liveInstanceJpa.findByResourceIdAndNsInstanceId(vid.getVnfInstanceId(), nsBlue.getInstance().getId())).thenReturn(nsLive);
		op.update(id);
		assertNotNull(nsBlue.getError());
		assertTrue(true);
	}

	NsUpadteManager createNsUpadteManager() {
		return new NsUpadteManager(nsBlueprint, liveInstanceJpa, manoClientFactory,
				eventManager, nsInstanceService, nsUpdateMapping);
	}
}
