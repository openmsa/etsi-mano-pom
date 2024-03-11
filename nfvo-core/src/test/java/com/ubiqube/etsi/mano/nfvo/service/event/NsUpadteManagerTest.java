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
package com.ubiqube.etsi.mano.nfvo.service.event;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.AddVnffgData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeExtVnfConnectivityData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeVnfFlavourData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.InstantiateVnfData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ModifyVnfInfoData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.MoveVnfInstanceData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.OperateVnfData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateRequest;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateVnffgData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.VnfInstanceData;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;
import com.ubiqube.etsi.mano.service.rest.ManoVnfInstance;
import com.ubiqube.etsi.mano.service.rest.ManoVnfInstanceId;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class NsUpadteManagerTest {
	@Mock
	private NsLiveInstanceJpa liveInstanceJpa;
	@Mock
	private MapperFacade mapper;
	@Mock
	private ManoClientFactory manoClientFactory;
	@Mock
	private EventManager eventManager;
	@Mock
	private NsInstanceService nsInstanceService;
	@Mock
	private NsBlueprintService nsBlueprint;
	@Mock
	private ManoClient manoClient;
	@Mock
	private ManoVnfInstanceId manoVnf;

	@Test
	void testUpdate() {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNotNull(blueprint.getError());
	}

	@Test
	void testUpdate_AddVnf_allReadyPresent() {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		instance.setId(UUID.randomUUID());
		upd.setUpdateType(UpdateTypeEnum.ADD_VNF);
		final VnfInstanceData data = new VnfInstanceData();
		upd.setAddVnfIstance(List.of(data));
		final NsLiveInstance nsLiveInst = new NsLiveInstance();
		nsLiveInst.setNsInstance(instance);
		when(liveInstanceJpa.findByResourceIdAndNsInstanceId(any(), any())).thenReturn(nsLiveInst);
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNotNull(blueprint.getError());
	}

	@Test
	void testUpdate_AddVnf() {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		instance.setId(UUID.randomUUID());
		upd.setUpdateType(UpdateTypeEnum.ADD_VNF);
		final VnfInstanceData data = new VnfInstanceData();
		upd.setAddVnfIstance(List.of(data));
		final NsLiveInstance nsLiveInst = new NsLiveInstance();
		final NsdInstance instance2 = new NsdInstance();
		instance2.setId(UUID.randomUUID());
		nsLiveInst.setNsInstance(instance2);
		when(liveInstanceJpa.findByResourceIdAndNsInstanceId(any(), any())).thenReturn(nsLiveInst);
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNull(blueprint.getError());
	}

	@Test
	void testUpdate_ChenageExtConn() throws Exception {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		final ChangeExtVnfConnectivityData data = new ChangeExtVnfConnectivityData();
		data.setVnfInstanceId("");
		upd.setUpdateType(UpdateTypeEnum.CHANGE_EXTERNAL_VNF_CONNECTIVITY);
		upd.setChangeExtVnfConnectivityData(List.of(data));
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsVnfTask nsTask = new NsVnfTask();
		nsLive.setNsTask(nsTask);
		nsLive.setResourceId(UUID.randomUUID().toString());
		when(liveInstanceJpa.findByResourceIdAndNsInstanceId("", instance.getId())).thenReturn(nsLive);
		sendNotify();
		//
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNull(blueprint.getError());
	}

	@Test
	void testUpdate_DhangeVnfDf() throws Exception {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		upd.setUpdateType(UpdateTypeEnum.CHANGE_VNF_DF);
		final ChangeVnfFlavourData data = new ChangeVnfFlavourData();
		upd.setChangeVnfFlavourData(List.of(data));
		//
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsVnfTask nsTask = new NsVnfTask();
		nsLive.setNsTask(nsTask);
		nsLive.setResourceId(UUID.randomUUID().toString());
		//
		when(liveInstanceJpa.findByResourceIdAndNsInstanceId(null, instance.getId())).thenReturn(nsLive);
		//
		sendNotify();
		//
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNull(blueprint.getError());
	}

	private void sendNotify() {
		when(manoClientFactory.getClient(any())).thenReturn(manoClient);
		final ManoVnfInstance manoVnfInstance = Mockito.mock(ManoVnfInstance.class);
		when(manoClient.vnfInstance()).thenReturn(manoVnfInstance);
		when(manoVnfInstance.id(any())).thenReturn(manoVnf);
	}

	@Test
	void testUpdate_InstantiateVnf() throws Exception {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		upd.setUpdateType(UpdateTypeEnum.INSTANTIATE_VNF);
		final InstantiateVnfData data = new InstantiateVnfData();
		upd.setInstantiateVnfData(List.of(data));
		//
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsVnfTask nsTask = new NsVnfTask();
		nsLive.setNsTask(nsTask);
		nsLive.setResourceId(UUID.randomUUID().toString());
		//
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNull(blueprint.getError());
	}

	@Test
	void testUpdate_ModifyVnfInfo() throws Exception {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		upd.setUpdateType(UpdateTypeEnum.MODIFY_VNF_INFORMATION);
		final ModifyVnfInfoData data = new ModifyVnfInfoData();
		data.setVnfInstanceId(id.toString());
		upd.setModifyVnfInfoData(List.of(data));
		//
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsVnfTask nsTask = new NsVnfTask();
		nsLive.setId(id);
		nsLive.setNsTask(nsTask);
		nsLive.setResourceId("004032a6-c5a0-11ed-a6fe-c8f750509d3b");
		//
		when(liveInstanceJpa.findByNsdInstanceAndClass(instance, VnfCreateNode.class)).thenReturn(List.of(nsLive));
		sendNotify();
		//
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNull(blueprint.getError());
	}

	@Test
	void testUpdate_OperateVnf() throws Exception {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		upd.setUpdateType(UpdateTypeEnum.OPERATE_VNF);
		final OperateVnfData data = new OperateVnfData();
		data.setVnfInstanceId("");
		data.setVnfInstanceId(id.toString());
		upd.setOperateVnfData(List.of(data));
		//
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsVnfTask nsTask = new NsVnfTask();
		nsLive.setId(id);
		nsLive.setNsTask(nsTask);
		nsLive.setResourceId("004032a6-c5a0-11ed-a6fe-c8f750509d3b");
		//
		when(liveInstanceJpa.findByResourceIdAndNsInstanceId(data.getVnfInstanceId(), instance.getId())).thenReturn(nsLive);
		sendNotify();
		//
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNull(blueprint.getError());
	}

	@Test
	void testUpdate_RemoveVnf() throws Exception {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		upd.setUpdateType(UpdateTypeEnum.REMOVE_VNF);
		upd.setRemoveVnfInstanceId(List.of(""));
		//
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsVnfTask nsTask = new NsVnfTask();
		nsTask.setToscaName("tosca");
		nsLive.setId(id);
		nsLive.setNsTask(nsTask);
		nsLive.setResourceId("004032a6-c5a0-11ed-a6fe-c8f750509d3b");
		//
		final NsdVnfPackageCopy pkg = new NsdVnfPackageCopy();
		pkg.setToscaName("tosca");
		final Set<NsdVnfPackageCopy> pkgs = new LinkedHashSet<>();
		pkgs.add(pkg);
		instance.setVnfPkgIds(pkgs);
		when(liveInstanceJpa.findByResourceIdAndNsInstanceId("", instance.getId())).thenReturn(nsLive);
		when(liveInstanceJpa.findByResourceId(any())).thenReturn(List.of(nsLive));
		//
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNull(blueprint.getError());
	}

	@Test
	void testUpdate_RemoveVnf_NotFound() throws Exception {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		upd.setUpdateType(UpdateTypeEnum.REMOVE_VNF);
		upd.setRemoveVnfInstanceId(List.of(""));
		//
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsVnfTask nsTask = new NsVnfTask();
		nsLive.setId(id);
		nsLive.setNsTask(nsTask);
		nsLive.setResourceId("004032a6-c5a0-11ed-a6fe-c8f750509d3b");
		//
		//
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNotNull(blueprint.getError());
	}

	@Test
	void testUpdate_MoveVnf() throws Exception {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		upd.setUpdateType(UpdateTypeEnum.MOVE_VNF);
		final MoveVnfInstanceData data = new MoveVnfInstanceData();
		upd.setMoveVnfInstanceData(List.of(data));
		//
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsVnfTask nsTask = new NsVnfTask();
		nsLive.setId(id);
		nsLive.setNsTask(nsTask);
		nsLive.setResourceId("004032a6-c5a0-11ed-a6fe-c8f750509d3b");
		//
		//
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNull(blueprint.getError());
	}

	@Test
	void testUpdate_AddVnffg() throws Exception {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		upd.setUpdateType(UpdateTypeEnum.ADD_VNFFG);
		final AddVnffgData data = new AddVnffgData();
		upd.setAddVnffg(List.of(data));
		//
		//
		instance.setVnffgs(new LinkedHashSet<>());
		//
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNull(blueprint.getError());
	}

	@Test
	void testUpdate_RemoveVnffg() throws Exception {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		upd.setUpdateType(UpdateTypeEnum.REMOVE_VNFFG);
		upd.setRemoveVnffgId(List.of(""));
		//
		//
		instance.setVnffgs(new LinkedHashSet<>());
		//
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNull(blueprint.getError());
	}

	@Test
	void testUpdate_UpdateVnffg() throws Exception {
		final NsUpadteManager um = new NsUpadteManager(nsBlueprint, liveInstanceJpa, mapper, manoClientFactory, eventManager, nsInstanceService);
		final UUID id = UUID.randomUUID();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance instance = new NsdInstance();
		blueprint.setNsInstance(instance);
		final BlueprintParameters params = new BlueprintParameters();
		final UpdateRequest upd = new UpdateRequest();
		//
		upd.setUpdateType(UpdateTypeEnum.UPDATE_VNFFG);
		final UpdateVnffgData data = new UpdateVnffgData();
		data.setVnffgInfoId(id.toString());
		data.setNfp(new ArrayList<>());
		upd.setUpdateVnffg(List.of(data));
		//
		//
		instance.setVnffgs(new LinkedHashSet<>());
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		vnffg.setId(id);
		vnffg.setName(id.toString());
		vnffg.setNfpd(new ArrayList<>());
		instance.getVnffgs().add(vnffg);
		when(mapper.mapAsList(anyList(), eq(NfpDescriptor.class))).thenReturn(List.of());
		//
		params.setUpdData(upd);
		blueprint.setParameters(params);
		when(nsBlueprint.findById(id)).thenReturn(blueprint);
		um.update(id);
		assertNull(blueprint.getError());
	}
}
