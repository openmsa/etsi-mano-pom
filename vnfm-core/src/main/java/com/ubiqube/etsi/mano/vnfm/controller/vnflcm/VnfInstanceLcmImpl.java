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

import static com.ubiqube.etsi.mano.Constants.ensureInstantiated;
import static com.ubiqube.etsi.mano.Constants.ensureIsEnabled;
import static com.ubiqube.etsi.mano.Constants.ensureIsOnboarded;
import static com.ubiqube.etsi.mano.Constants.ensureNotInstantiated;
import static com.ubiqube.etsi.mano.Constants.ensureNotLocked;
import static com.ubiqube.etsi.mano.Constants.getSafeUUID;
import static com.ubiqube.etsi.mano.Constants.getSingleField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfInstanceLcm;
import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfComputeAspectDelta;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;
import com.ubiqube.etsi.mano.vnfm.service.VnfLcmService;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import ma.glasnost.orika.MapperFacade;

@Service
public class VnfInstanceLcmImpl implements VnfInstanceLcm {

	private static final Logger LOG = LoggerFactory.getLogger(VnfInstanceLcmImpl.class);

	private final VnfPackageService vnfPackageService;

	private final EventManager eventManager;

	private final MapperFacade mapper;

	private final VnfLcmService vnfLcmService;

	private final VnfInstanceService vnfInstanceService;

	private final VimManager vimManager;

	private final VnfInstanceServiceVnfm vnfInstanceServiceVnfm;

	private final ManoClientFactory manoClientFactory;

	public VnfInstanceLcmImpl(final EventManager eventManager, final MapperFacade mapper, final VnfLcmService vnfLcmService,
			final VnfInstanceService vnfInstanceService, final VimManager vimManager, final VnfPackageService vnfPackageService,
			final VnfInstanceServiceVnfm vnfInstanceServiceVnfm, final ManoClientFactory manoClientFactory) {
		this.eventManager = eventManager;
		this.mapper = mapper;
		this.vnfLcmService = vnfLcmService;
		this.vnfInstanceService = vnfInstanceService;
		this.vimManager = vimManager;
		this.vnfPackageService = vnfPackageService;
		this.vnfInstanceServiceVnfm = vnfInstanceServiceVnfm;
		this.manoClientFactory = manoClientFactory;
	}

	@Override
	public List<VnfInstance> get(final @Nullable Servers servers, final MultiValueMap<String, String> requestParams) {
		final String filter = getSingleField(requestParams, "filter");
		// XXX A little bit short !
		return vnfInstanceService.query(filter);
	}

	@Override
	public VnfInstance post(final @Nullable Servers servers, final String vnfdId, final String vnfInstanceName, @Nullable final String vnfInstanceDescription) {
		VnfPackage vnfPkgInfo;
		try {
			vnfPkgInfo = vnfPackageService.findByVnfdId(vnfdId);
		} catch (final NotFoundException e) {
			LOG.trace("", e);
			vnfPkgInfo = onboardPackage(vnfdId);
		}
		ensureIsOnboarded(vnfPkgInfo);
		ensureIsEnabled(vnfPkgInfo);
		VnfInstance vnfInstance = VnfLcmFactory.createVnfInstance(vnfInstanceName, vnfInstanceDescription, vnfPkgInfo);
		mapper.map(vnfPkgInfo, vnfInstance);
		// VnfIdentifierCreationNotification NFVO + EM
		vnfInstance = vnfInstanceService.save(vnfInstance);
		eventManager.sendNotification(NotificationEvent.VNF_INSTANCE_CREATE, vnfInstance.getId(), Map.of());
		return vnfInstance;
	}

	private VnfPackage onboardPackage(final String vnfdId) {
		final VnfPackage vnfPkg = manoClientFactory.getClient().vnfPackage().onboarded(getSafeUUID(vnfdId)).find();
		vnfPkg.setNfvoId(vnfPkg.getId().toString());
		vnfPkg.setOnboardingState(OnboardingStateType.CREATED);
		vnfPkg.setUsageState(UsageStateEnum.NOT_IN_USE);
		vnfPkg.setId(null);
		vnfPkg.setSoftwareImages(Set.of());
		final VnfPackage nPkg = vnfPackageService.save(vnfPkg);
		eventManager.sendActionVnfm(ActionType.VNF_PKG_ONBOARD_DOWNLOAD_INSTANTIATE, nPkg.getId(), Map.of());
		vnfPkg.setOnboardingState(OnboardingStateType.ONBOARDED);
		return nPkg;
	}

	@Transactional
	@Override
	public void delete(final @Nullable Servers servers, @Nonnull final UUID vnfInstanceId) {
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(vnfInstanceId);
		ensureNotInstantiated(vnfInstance);
		ensureNotLocked(vnfInstance);
		vnfLcmService.deleteByVnfInstance(vnfInstance);
		vnfInstanceService.delete(vnfInstanceId);
		if (!vnfInstanceService.isInstantiate(vnfInstance.getVnfPkg().getId())) {
			final VnfPackage vnfPkg = vnfPackageService.findById(vnfInstance.getVnfPkg().getId());
			vnfPkg.setUsageState(UsageStateEnum.NOT_IN_USE);
			vnfPackageService.save(vnfPkg);
		}
		// VnfIdentitifierDeletionNotification NFVO + EM
		eventManager.sendNotification(NotificationEvent.VNF_INSTANCE_DELETE, vnfInstance.getId(), Map.of());
	}

	@Override
	public VnfBlueprint instantiate(final @Nullable Servers servers, @Nonnull final UUID vnfInstanceId, final VnfInstantiate instantiateVnfRequest) {
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(vnfInstanceId);
		ensureNotInstantiated(vnfInstance);
		ensureNotLocked(vnfInstance);
		final UUID vnfPkgId = vnfInstance.getVnfPkg().getId();
		final VnfPackage vnfPkg = vnfPackageService.findById(vnfPkgId);
		ensureIsEnabled(vnfPkg);

		if (null != instantiateVnfRequest.getVimConnectionInfo()) {
			final List<VimConnectionInformation> vimconnections = mapper.mapAsList(instantiateVnfRequest.getVimConnectionInfo(), VimConnectionInformation.class);
			final Set<VimConnectionInformation> vimSet = vimconnections.stream()
					.map(x -> {
						final Optional<VimConnectionInformation> optVim = vimManager.findOptionalVimByVimId(x.getVimId());
						if (optVim.isPresent()) {
							return optVim.get();
						}
						return vimManager.save(x);
					}).collect(Collectors.toSet());
			vnfInstance.setVimConnectionInfo(vimSet);
			vnfInstanceService.save(vnfInstance);
		}

		VnfBlueprint blueprint = vnfLcmService.createIntatiateOpOcc(vnfInstance);
		instantiateVnfRequest.setExtManagedVirtualLinks(new ArrayList<>());
		mapper.map(instantiateVnfRequest, blueprint);
		fixMapping(instantiateVnfRequest, blueprint);
		blueprint.getParameters().setScaleStatus(extractScaleStaus(vnfPkg));
		blueprint = vnfLcmService.save(blueprint);
		eventManager.sendActionVnfm(ActionType.VNF_INSTANTIATE, blueprint.getId(), new HashMap<>());
		LOG.info("VNF Instantiation Event Sucessfully sent. {}", blueprint.getId());
		return blueprint;
	}

	private static void fixMapping(final VnfInstantiate instantiateVnfRequest, final VnfBlueprint blueprint) {
		final Set<ExtManagedVirtualLinkDataEntity> res = instantiateVnfRequest.getExtManagedVirtualLinks().stream().map(x -> {
			final ExtManagedVirtualLinkDataEntity emvde = new ExtManagedVirtualLinkDataEntity();
			emvde.setId(x.getId());
			emvde.setResourceId(x.getResourceId());
			emvde.setVimConnectionId(x.getVimId());
			emvde.setVnfVirtualLinkDescId(x.getVnfVirtualLinkDescId());
			return emvde;
		}).collect(Collectors.toSet());
		blueprint.getParameters().setExtManagedVirtualLinks(res);
	}

	private static Set<ScaleInfo> extractScaleStaus(final VnfPackage vnfPkg) {
		return vnfPkg.getVnfCompute().stream()
				.flatMap(x -> x.getScalingAspectDeltas().stream())
				.map(VnfComputeAspectDelta::getAspectName)
				.collect(Collectors.toSet())
				.stream()
				.map(x -> new ScaleInfo(x, 0))
				.collect(Collectors.toSet());
	}

	@Override
	public VnfBlueprint terminate(final @Nullable Servers servers, @Nonnull final UUID vnfInstanceId, final @Nullable CancelModeTypeEnum terminationType, final @Nullable Integer gracefulTerminationTimeout) {
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(vnfInstanceId);
		ensureInstantiated(vnfInstance);
		ensureNotLocked(vnfInstance);
		final VnfBlueprint blueprint = vnfLcmService.createTerminateOpOcc(vnfInstance);
		eventManager.sendActionVnfm(ActionType.VNF_TERMINATE, blueprint.getId(), new HashMap<>());
		LOG.info("Terminate sent for instancce: {}", vnfInstanceId);
		return blueprint;
	}

	@Override
	public VnfBlueprint scaleToLevel(final @Nullable Servers servers, @Nonnull final UUID uuid, final VnfScaleToLevelRequest scaleVnfToLevelRequest) {
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(uuid);
		ensureInstantiated(vnfInstance);
		ensureNotLocked(vnfInstance);
		final VnfBlueprint lcmOpOccs = vnfLcmService.createScaleToLevelOpOcc(vnfInstance, scaleVnfToLevelRequest);
		eventManager.sendActionVnfm(ActionType.VNF_SCALE_TO_LEVEL, lcmOpOccs.getId(), new HashMap<>());
		return lcmOpOccs;
	}

	@Override
	public VnfBlueprint scale(final @Nullable Servers servers, @Nonnull final UUID uuid, final VnfScaleRequest scaleVnfRequest) {
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(uuid);
		ensureInstantiated(vnfInstance);
		ensureNotLocked(vnfInstance);
		final VnfBlueprint lcmOpOccs = vnfLcmService.createScaleOpOcc(vnfInstance, scaleVnfRequest);
		eventManager.sendActionVnfm(ActionType.VNF_SCALE_TO_LEVEL, lcmOpOccs.getId(), new HashMap<>());
		return lcmOpOccs;
	}

	@Override
	public VnfBlueprint operate(final @Nullable Servers servers, @Nonnull final UUID uuid, final VnfOperateRequest operateVnfRequest) {
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(uuid);
		ensureInstantiated(vnfInstance);
		ensureNotLocked(vnfInstance);
		final VnfBlueprint lcmOpOccs = vnfLcmService.createOperateOpOcc(vnfInstance, operateVnfRequest);
		eventManager.sendActionVnfm(ActionType.VNF_OPERATE, lcmOpOccs.getId(), new HashMap<>());
		return lcmOpOccs;
	}

	@Override
	public VnfBlueprint vnfLcmOpOccsGet(final @Nullable Servers servers, final UUID id) {
		return vnfLcmService.findById(id);
	}

	@Override
	public List<VnfBlueprint> findByVnfInstanceId(final @Nullable Servers servers, final UUID id) {
		return vnfLcmService.findByVnfInstanceId(id);
	}

	@Override
	public VnfBlueprint changeExtConn(final @Nullable Servers servers, final UUID uuid, final ChangeExtVnfConnRequest cevcr) {
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(uuid);
		ensureInstantiated(vnfInstance);
		ensureNotLocked(vnfInstance);
		final VnfBlueprint lcmOpOccs = vnfLcmService.createChangeExtCpOpOcc(vnfInstance, cevcr);
		eventManager.sendActionVnfm(ActionType.VNF_CHANGE_CONN, lcmOpOccs.getId(), new HashMap<>());
		return lcmOpOccs;
	}

	@Override
	public VnfInstance findById(final @Nullable Servers servers, final String vnfInstance) {
		return vnfInstanceServiceVnfm.findById(getSafeUUID(vnfInstance));
	}

	@Override
	public VnfBlueprint heal(final @Nullable Servers servers, @Nonnull final UUID vnfInstanceId, final VnfHealRequest healVnfRequest) {
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(vnfInstanceId);
		ensureInstantiated(vnfInstance);
		ensureNotLocked(vnfInstance);
		final VnfBlueprint lcmOpOccs = vnfLcmService.createHealOpOcc(vnfInstance, healVnfRequest);
		eventManager.sendActionVnfm(ActionType.VNF_HEAL, lcmOpOccs.getId(), new HashMap<>());
		return lcmOpOccs;
	}

}
