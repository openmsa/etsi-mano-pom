package com.ubiqube.etsi.mano.nfvo.service.event;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.common.FailureDetails;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeExtVnfConnectivityData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeVnfFlavourData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.InstantiateVnfData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ModifyVnfInfoData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.MoveVnfInstanceData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.OperateVnfData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateRequest;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.VnfInstanceData;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsWorkflow;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.service.NsBlueprintService;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author olivier
 *
 */
@Service
public class NsUpadteManager {

	private static final Logger LOG = LoggerFactory.getLogger(NsUpadteManager.class);

	private final NsBlueprintService nsBlueprint;
	private final NsLiveInstanceJpa liveInstanceJpa;
	private final MapperFacade mapper;
	private final ManoClientFactory manoClientFactory;
	private final NfvoActions nfvoActions;
	private final NsWorkflow nsWorkflow;
	private final NsBlueprintService nsBlueprintService;
	private final EventManager eventManager;

	public NsUpadteManager(final NsBlueprintService nsBlueprint, final NsLiveInstanceJpa liveInstanceJpa, final MapperFacade mapper, final ManoClientFactory manoClientFactory,
			final NfvoActions nfvoActions, final NsWorkflow nsWorkflow, final NsBlueprintService nsBlueprintService, final EventManager eventManager) {
		this.nsBlueprint = nsBlueprint;
		this.liveInstanceJpa = liveInstanceJpa;
		this.mapper = mapper;
		this.manoClientFactory = manoClientFactory;
		this.nfvoActions = nfvoActions;
		this.nsWorkflow = nsWorkflow;
		this.nsBlueprintService = nsBlueprintService;
		this.eventManager = eventManager;
	}

	public void update(@NotNull final UUID objectId) {
		final NsBlueprint nb = nsBlueprint.findById(objectId);
		try {
			updateNoEx(nb);
		} catch (final RuntimeException e) {
			LOG.error("", e);
			final FailureDetails error = new FailureDetails(500, e.getMessage());
			nb.setError(error);
			nb.setOperationStatus(OperationStatusType.FAILED);
			nsBlueprint.save(nb);
		}
	}

	private void updateNoEx(final NsBlueprint nb) {
		final NsdInstance inst = nb.getInstance();
		final UpdateRequest req = nb.getParameters().getUpdData();
		switch (req.getUpdateType()) {
		case ADD_VNF -> addVnf(inst, nb, req.getAddVnfIstance());
		case CHANGE_EXTERNAL_VNF_CONNECTIVITY -> changeExtVnfConn(inst, req.getChangeExtVnfConnectivityData());
		case CHANGE_VNF_DF -> changeVnfDf(inst, req.getChangeVnfFlavourData());
		case INSTANTIATE_VNF -> instantiateVnf(inst, req.getInstantiateVnfData());
		case MODIFY_VNF_INFORMATION -> modifyVnfInformation(inst, req.getModifyVnfInfoData());
		case OPERATE_VNF -> vnfOperate(inst, req.getOperateVnfData());
		case REMOVE_VNF -> removeVnf(inst, req.getRemoveVnfInstanceId(), nb);
		case MOVE_VNF -> moveVnf(inst, req.getMoveVnfInstanceData());
		default -> throw new GenericException("Unable to find action " + req.getUpdateType());
		}
	}

	private Object moveVnf(final NsdInstance inst, final List<MoveVnfInstanceData> moveVnfInstanceData) {
		moveVnfInstanceData.forEach(x -> {

		});
		return null;
	}

	private void removeVnf(final NsdInstance inst, final List<String> removeVnfInstanceId, final NsBlueprint nb) {
		final ArrayList<UUID> ret = new ArrayList<>();
		nb.getParameters().getUpdData().setRealVnfInstanceToRemove(ret);
		removeVnfInstanceId.stream().forEach(x -> {
			final NsLiveInstance liveInst = liveInstanceJpa.findByResourceIdAndNsInstanceId(x, inst.getId());
			if (null == liveInst) {
				throw new NotFoundException("Unable to find instance " + x);
			}
			final NsdVnfPackageCopy cp = find(inst.getVnfPkgIds(), liveInst.getNsTask().getToscaName());
			inst.getVnfPkgIds().remove(cp);
			// Check if some one use it.
			final List<NsLiveInstance> allInst = liveInstanceJpa.findByResourceId(x);
			if (allInst.isEmpty()) {
				ret.add(liveInst.getId());
			}
		});
		if (ret.isEmpty()) {
			return;
		}
		nsBlueprint.save(nb);
		eventManager.sendAction(ActionType.NS_UPDATE, nb.getId());
	}

	private static NsdVnfPackageCopy find(final Set<NsdVnfPackageCopy> vnfPkgIds, final String toscaName) {
		return vnfPkgIds.stream().filter(x -> toscaName.startsWith(x.getToscaName())).findFirst().orElseThrow(() -> new NotFoundException("Unable to find " + toscaName + " in " + vnfPkgIds));
	}

	private Object vnfOperate(final NsdInstance inst, final List<OperateVnfData> operateVnfData) {
		operateVnfData.forEach(x -> {
			final NsLiveInstance liveInst = liveInstanceJpa.findByResourceIdAndNsInstanceId(x.getVnfInstanceId(), inst.getId());
			final NsVnfTask t = (NsVnfTask) liveInst.getNsTask();
			sendOperate(liveInst.getResourceId(), t.getServer(), x);
		});
		return null;
	}

	private void sendOperate(final String resourceId, final Servers servers, final OperateVnfData operateData) {
		final ManoClient mc = manoClientFactory.getClient(servers);
		final VnfOperateRequest req = mapper.map(operateData, VnfOperateRequest.class);
		mc.vnfInstance(getSafeUUID(resourceId)).operate(req);
	}

	private Object modifyVnfInformation(final NsdInstance inst, final List<ModifyVnfInfoData> modifyVnfInfoData) {
		// Send a request to vnf to change the name descr ...
		final List<NsLiveInstance> liveInst = liveInstanceJpa.findByNsdInstanceAndClass(inst, VnfCreateNode.class.getSimpleName());
		modifyVnfInfoData.stream().forEach(x -> {
			final NsLiveInstance nsli = find(liveInst, x.getVnfInstanceId());
			final NsVnfTask t = (NsVnfTask) nsli.getNsTask();
			// Send data to this instance.
			sendModify(nsli.getResourceId(), t.getServer(), x);
		});
		return null;
	}

	private void sendModify(final String resourceId, final Servers servers, final ModifyVnfInfoData patchData) {
		final ManoClient mc = manoClientFactory.getClient(servers);
		final Map<String, Object> req = toMap(patchData);
		mc.vnfInstance(getSafeUUID(resourceId)).patch(req);
	}

	private static Map<String, Object> toMap(final ModifyVnfInfoData patchData) {
		final Map<String, Object> ret = new HashMap<>();
		Optional.ofNullable(patchData.getExtensions()).ifPresent(x -> ret.put("extensions", x));
		Optional.ofNullable(patchData.getMetadata()).ifPresent(x -> ret.put("metadata", x));
		Optional.ofNullable(patchData.getVnfConfigurableProperties()).ifPresent(x -> ret.put("vnfConfigurableProperties", x));
		Optional.ofNullable(patchData.getVnfdId()).ifPresent(x -> ret.put("vnfPkgId", x));
		Optional.ofNullable(patchData.getVnfInstanceDescription()).ifPresent(x -> ret.put("vnfInstanceDescription", x));
		Optional.ofNullable(patchData.getVnfInstanceName()).ifPresent(x -> ret.put("vnfInstanceName", x));
		return ret;
	}

	private static NsLiveInstance find(final List<NsLiveInstance> liveInst, final String vnfInstanceId) {
		return liveInst.stream().filter(x -> x.getId().toString().equals(vnfInstanceId)).findFirst().orElseThrow();
	}

	private Object instantiateVnf(final NsdInstance inst, final List<InstantiateVnfData> instantiateVnfData) {
		instantiateVnfData.stream().forEach(x -> {
			final NsBlueprint nb = mapper.map(x, NsBlueprint.class);

		});
		return null;
	}

	private Object changeVnfDf(final NsdInstance inst, final List<ChangeVnfFlavourData> changeVnfFlavourData) {
		changeVnfFlavourData.forEach(x -> {
			final NsLiveInstance liveInstance = liveInstanceJpa.findByResourceIdAndNsInstanceId(x.getVnfInstanceId(), inst.getId());
			final NsVnfTask t = (NsVnfTask) liveInstance.getNsTask();
			sendChangeDf(liveInstance.getResourceId(), t.getServer(), x);
		});
		return null;
	}

	private void sendChangeDf(final String resourceId, final Servers server, final ChangeVnfFlavourData x) {
		final ChangeVnfFlavourData req = mapper.map(x, ChangeVnfFlavourData.class);
		final ManoClient mc = manoClientFactory.getClient(server);
		mc.vnfInstance(getSafeUUID(resourceId)).changeFlavour(req);
	}

	private Object changeExtVnfConn(final NsdInstance inst, final List<ChangeExtVnfConnectivityData> changeExtVnfConnectivityData) {
		changeExtVnfConnectivityData.forEach(x -> {
			final NsLiveInstance liveInstance = liveInstanceJpa.findByResourceIdAndNsInstanceId(x.getVnfInstanceId(), inst.getId());
			final NsVnfTask t = (NsVnfTask) liveInstance.getNsTask();
			sendChangeExt(liveInstance.getResourceId(), t.getServer(), x);
		});
		return null;
	}

	private void sendChangeExt(final String resourceId, final Servers server, final ChangeExtVnfConnectivityData x) {
		final ChangeExtVnfConnRequest req = mapper.map(x, ChangeExtVnfConnRequest.class);
		final ManoClient mc = manoClientFactory.getClient(server);
		mc.vnfInstance(getSafeUUID(resourceId)).changeExtConn(req);
	}

	private void addVnf(final NsdInstance inst, final NsBlueprint blueprint, final List<VnfInstanceData> addVnfIstance) {
		addVnfIstance.forEach(x -> {
			final NsLiveInstance liveInstance = Optional.ofNullable(liveInstanceJpa.findByResourceIdAndNsInstanceId(x.getVnfInstanceId(), inst.getId()))
					.orElseThrow(() -> new NotFoundException("Unable to find vnfInstance: " + x.getVnfInstanceId()));
			if (liveInstance.getNsInstance().getId().equals(inst.getId())) {
				throw new GenericException("Id " + x.getVnfInstanceId() + " is already present in instance " + inst.getId());
			}
			final NsLiveInstance newLi = copy(inst, blueprint, liveInstance);
			liveInstanceJpa.save(newLi);
		});
	}

	private static NsLiveInstance copy(final NsdInstance inst, final NsBlueprint blueprint, final NsLiveInstance in) {
		final NsLiveInstance o = new NsLiveInstance();
		o.setNsBlueprint(blueprint);
		o.setNsInstance(inst);
		o.setNsTask(in.getNsTask());
		o.setResourceId(in.getResourceId());
		o.setResourceProviderId(in.getResourceProviderId());
		o.setVimConnectionId(in.getVimConnectionId());
		o.setVimLevelResourceType(in.getVimLevelResourceType());
		return o;
	}

}
