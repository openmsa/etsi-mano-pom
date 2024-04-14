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

import static com.ubiqube.etsi.mano.Constants.ensureInstantiated;
import static com.ubiqube.etsi.mano.Constants.ensureIsEnabled;
import static com.ubiqube.etsi.mano.Constants.ensureIsOnboarded;
import static com.ubiqube.etsi.mano.Constants.ensureNotInstantiated;
import static com.ubiqube.etsi.mano.Constants.ensureNotLocked;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstanceDto;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstantiate;
import com.ubiqube.etsi.mano.dao.mano.nfvo.NsVnfInstance;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgInstance;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsHeal;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScale;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsVnfScalingStepMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VnfScalingLevelMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VnfScalingStepMapping;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.ExternalPortRecord;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vim.vnffg.Classifier;
import com.ubiqube.etsi.mano.nfvo.factory.LcmFactory;
import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.nfvo.service.NsdPackageService;
import com.ubiqube.etsi.mano.nfvo.service.mapping.NsBlueprintMapping;
import com.ubiqube.etsi.mano.nfvo.service.mapping.nsinstance.NsInstanceMapping;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;

@Service
public class NsInstanceControllerServiceImpl implements NsInstanceControllerService {

	private final NsdPackageService nsdPackageService;

	private final NsInstanceService nsInstanceService;

	private final EventManager eventManager;

	private final NsBlueprintMapping mapper;

	private final NsBlueprintService nsBlueprintService;

	private final SearchableService searchableService;

	private final NsInstanceMapping nsInstanceMapping;

	public NsInstanceControllerServiceImpl(final NsdPackageService nsdPackageService, final NsInstanceService nsInstanceService, final EventManager eventManager,
			final NsBlueprintMapping mapper, final NsBlueprintService nsBlueprintService, final SearchableService searchableService, final NsInstanceMapping nsInstanceMapping) {
		this.nsdPackageService = nsdPackageService;
		this.nsInstanceService = nsInstanceService;
		this.eventManager = eventManager;
		this.mapper = mapper;
		this.nsBlueprintService = nsBlueprintService;
		this.searchableService = searchableService;
		this.nsInstanceMapping = nsInstanceMapping;
	}

	@Override
	public NsdInstance createNsd(final String nsdId, final String nsName, final String nsDescription) {
		final NsdPackage nsd = nsdPackageService.findByNsdId(nsdId);
		ensureIsOnboarded(nsd);
		ensureIsEnabled(nsd);
		nsd.setNsdUsageState(UsageStateEnum.IN_USE);
		nsdPackageService.save(nsd);

		final NsdInstance nsInstance = new NsdInstance();
		nsInstance.setNsInstanceName(nsName);
		nsInstance.setNsInstanceDescription(nsDescription);
		nsInstance.setNsdInfo(nsd);
		nsInstance.setInstantiationState(InstantiationState.NOT_INSTANTIATED);
		final NsdInstance nsInstanceTmp = nsInstanceService.save(nsInstance);

		final List<NsVnfInstance> vnfInstances = new ArrayList<>();
		// It's strange, we are creating NSD instance but not VNF.
		nsd.getNestedNsdInfoIds().forEach(x -> {
			// create nested instance.
			final NsdInstance nsIn = createNsd(x.getChild().getNsdId(), "Sub NSD of " + nsdId, nsDescription);
			nsInstanceTmp.addNestedNsInstance(nsIn);
		});
		final Set<ScaleInfo> nsScaleInfo = nsd.getVnfPkgIds().stream()
				.map(NsdPackageVnfPackage::getStepMapping)
				.flatMap(Set::stream)
				.map(VnfScalingStepMapping::getAspectId)
				.distinct()
				.map(x -> new ScaleInfo(x, 0))
				.collect(Collectors.toSet());

		Optional.ofNullable(nsInstanceTmp.getInstantiatedVnfInfo()).orElse(new BlueprintParameters()).setNsStepStatus(nsScaleInfo);
		nsInstanceTmp.setVnfInstance(vnfInstances);
		copyVnfInstances(nsd, nsInstanceTmp);
		return nsInstanceService.save(nsInstanceTmp);
	}

	private void copyVnfInstances(final NsdPackage nsd, final NsdInstance nsInstanceTmp) {
		final Set<NsdVnfPackageCopy> r = nsd.getVnfPkgIds().stream().map(this::copy).collect(Collectors.toSet());
		nsInstanceTmp.setVnfPkgIds(r);
		final Set<VnffgDescriptor> r1 = nsd.getVnffgs().stream().map(this::copyVnffg).collect(Collectors.toSet());
		nsInstanceTmp.setVnffgs(r1);
	}

	private NsdVnfPackageCopy copy(final NsdPackageVnfPackage o) {
		final NsdVnfPackageCopy n = new NsdVnfPackageCopy();
		n.setForwardMapping(copyForwardMapping(o.getForwardMapping()));
		n.setLevelMapping(copyLevelMapping(o.getLevelMapping()));
		n.setNets(copy(o.getNets()));
		n.setNsdPackageId(o.getNsdPackage().getId());
		n.setStepMapping(copyStepMapping(o.getStepMapping()));
		n.setToscaId(o.getToscaId());
		n.setToscaName(o.getToscaName());
		n.setVirtualLinks(copyVirtualLinks(o.getVirtualLinks()));
		n.setVnfdId(o.getVnfPackage().getVnfdId());
		return n;
	}

	private static Set<ExternalPortRecord> copy(final Set<ExternalPortRecord> nets) {
		return nets.stream().map(NsInstanceControllerServiceImpl::copy).collect(Collectors.toSet());
	}

	private static ExternalPortRecord copy(final ExternalPortRecord x) {
		final ExternalPortRecord epr = new ExternalPortRecord();
		epr.setToscaName(x.getToscaName());
		epr.setVirtualLink(x.getVirtualLink());
		epr.setVirtualLinkPort(x.getVirtualLinkPort());
		return epr;
	}

	private Set<ListKeyPair> copyVirtualLinks(final Set<ListKeyPair> virtualLinks) {
		final List<ListKeyPair> tmp = virtualLinks.stream().map(x -> nsInstanceMapping.map(x)).toList();
		tmp.forEach(x -> x.setId(null));
		return tmp.stream().collect(Collectors.toSet());
	}

	private Set<VnfScalingStepMapping> copyStepMapping(final Set<VnfScalingStepMapping> stepMapping) {
		final List<VnfScalingStepMapping> tmp = stepMapping.stream().map(x -> nsInstanceMapping.map(x)).toList();
		tmp.forEach(x -> {
			x.setId(null);
			x.getLevels().forEach(y -> y.setId(null));
		});
		return tmp.stream().collect(Collectors.toSet());
	}

	private Set<VnfScalingLevelMapping> copyLevelMapping(final Set<VnfScalingLevelMapping> levelMapping) {
		final List<VnfScalingLevelMapping> tmp = levelMapping.stream().map(x -> nsInstanceMapping.map(x)).toList();
		tmp.forEach(x -> x.setId(null));
		return tmp.stream().collect(Collectors.toSet());
	}

	private Set<ForwarderMapping> copyForwardMapping(final Set<ForwarderMapping> forwardMapping) {
		final List<@NonNull ForwarderMapping> tmp = forwardMapping.stream().map(x -> nsInstanceMapping.map(x)).toList();
		tmp.forEach(x -> x.setId(null));
		return tmp.stream().collect(Collectors.toSet());
	}

	private VnffgDescriptor copyVnffg(final VnffgDescriptor o) {
		final VnffgDescriptor n = new VnffgDescriptor();
		n.setId(null);
		n.setName(o.getName());
		n.setDescription(o.getDescription());
		final Classifier c = nsInstanceMapping.map(o.getClassifier());
		c.setId(null);
		n.setClassifier(c);
		n.setVnfProfileId(o.getVnfProfileId());
		n.setPnfProfileId(o.getPnfProfileId());
		n.setNestedNsProfileId(o.getNestedNsProfileId());
		n.setVirtualLinkProfileId(o.getVirtualLinkProfileId());

		n.setNfpd(copyNfpd(o.getNfpd()));
		return n;
	}

	private List<NfpDescriptor> copyNfpd(final List<NfpDescriptor> nfpd) {
		final List<NfpDescriptor> tmp = nfpd.stream().map(x -> nsInstanceMapping.map(x)).toList();
		tmp.forEach(x -> {
			x.setId(null);
			x.setInstances(copyVnffgInstance(x.getInstances()));
		});
		return tmp.stream().toList();
	}

	private List<VnffgInstance> copyVnffgInstance(final List<VnffgInstance> instance) {
		final List<VnffgInstance> tmp = instance.stream().map(x -> nsInstanceMapping.map(x)).toList();
		tmp.forEach(x -> {
			x.setId(null);
			x.setPairs(copyCpPair(x.getPairs()));
		});
		return tmp.stream().toList();
	}

	private List<CpPair> copyCpPair(final List<CpPair> pair) {
		final List<CpPair> tmp = pair.stream().map(x -> nsInstanceMapping.map(x)).toList();
		tmp.forEach(x -> x.setId(null));
		return tmp.stream().toList();
	}

	@Override
	public NsBlueprint instantiate(final UUID nsUuid, final NsInstantiate req) {
		final NsdInstance nsInstanceDb = findPackageForAction(nsUuid);
		nsInstanceDb.getNsdInfo().getVnfPkgIds().forEach(x -> {
			final VnfPackage pkg = x.getVnfPackage();
			ensureIsEnabled(pkg);
			ensureIsOnboarded(pkg);
		});
		final NsBlueprint nsLcm = LcmFactory.createNsLcmOpOcc(nsInstanceDb, PlanOperationType.INSTANTIATE);
		mapper.map(req, nsLcm);
		if (req.getNsInstantiationLevelId() == null) {
			nsLcm.setNsInstantiationLevelId(nsInstanceDb.getNsdInfo().getInstantiationLevel());
		}
		nsLcm.getParameters().setNsStepStatus(requestToScaleInfo(nsInstanceDb.getNsdInfo()));
		nsBlueprintService.save(nsLcm);
		mapper.map(req, nsInstanceDb);
		nsInstanceService.save(nsInstanceDb);
		eventManager.sendActionNfvo(ActionType.NS_INSTANTIATE, nsLcm.getId(), new HashMap<>());
		return nsLcm;
	}

	private NsdInstance findPackageForAction(final UUID nsUuid) {
		final NsdInstance nsInstanceDb = nsInstanceService.findById(nsUuid);
		ensureNotInstantiated(nsInstanceDb);
		ensureNotLocked(nsInstanceDb);
		return nsInstanceDb;
	}

	private static Set<ScaleInfo> requestToScaleInfo(final NsdPackage nsd) {
		final Set<String> aspects = new HashSet<>();
		nsd.getVnfPkgIds().stream().forEach(x -> Optional.ofNullable(x.getStepMapping()).map(Set::iterator).filter(Iterator::hasNext).map(Iterator::next).map(VnfScalingStepMapping::getAspectId).ifPresent(aspects::add));
		nsd.getNestedNsdInfoIds().stream().forEach(x -> Optional.ofNullable(x.getStepMapping()).map(Set::iterator).filter(Iterator::hasNext).map(Iterator::next).map(NsVnfScalingStepMapping::getAspectId).ifPresent(aspects::add));
		return aspects.stream().map(x -> new ScaleInfo(x, 0)).collect(Collectors.toSet());
	}

	@Override
	public NsBlueprint terminate(final UUID nsInstanceUuid, final OffsetDateTime terminationTime) {
		final NsBlueprint nsLcm = lcmForRunningOperation(nsInstanceUuid, PlanOperationType.TERMINATE);
		nsLcm.setStartTime(terminationTime);
		nsBlueprintService.save(nsLcm);
		// XXX we can use quartz cron job for terminationTime.
		eventManager.sendActionNfvo(ActionType.NS_TERMINATE, nsLcm.getId(), new HashMap<>());
		return nsLcm;
	}

	@Override
	public NsBlueprint heal(final UUID nsInstanceUuid, final NsHeal nsHeal) {
		final NsBlueprint nsLcm = lcmForRunningOperation(nsInstanceUuid, PlanOperationType.HEAL);
		nsLcm.getParameters().setNsHeal(nsHeal);
		nsBlueprintService.save(nsLcm);
		eventManager.sendActionNfvo(ActionType.NS_HEAL, nsLcm.getId(), new HashMap<>());
		return nsLcm;
	}

	@Override
	public NsBlueprint scale(final UUID nsInstanceUuid, final NsScale nsInst) {
		final NsBlueprint nsLcm = lcmForRunningOperation(nsInstanceUuid, PlanOperationType.SCALE);
		nsLcm.getParameters().setNsScale(nsInst);
		nsBlueprintService.save(nsLcm);
		eventManager.sendActionNfvo(ActionType.NS_SCALE, nsLcm.getId(), new HashMap<>());
		return nsLcm;
	}

	private NsBlueprint lcmForRunningOperation(final UUID nsInstanceUuid, final PlanOperationType pt) {
		final NsdInstance nsInstanceDb = nsInstanceService.findById(nsInstanceUuid);
		ensureInstantiated(nsInstanceDb);
		ensureNotLocked(nsInstanceDb);
		return LcmFactory.createNsLcmOpOcc(nsInstanceDb, pt);
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<NsInstanceDto, U> mapper, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink, final Class<?> frontClass) {
		return searchableService.search(NsdInstance.class, requestParams, mapper, excludeDefaults, mandatoryFields, makeLink, List.of(), frontClass);
	}
}
