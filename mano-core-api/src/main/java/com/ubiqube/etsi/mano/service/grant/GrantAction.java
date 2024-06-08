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
package com.ubiqube.etsi.mano.service.grant;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.GrantVimAssetsEntity;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VimComputeResourceFlavourEntity;
import com.ubiqube.etsi.mano.dao.mano.VimSoftwareImageEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.ZoneGroupInformation;
import com.ubiqube.etsi.mano.dao.mano.ZoneInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualCp;
import com.ubiqube.etsi.mano.dao.mano.vim.ImageServiceAware;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.GrantsResponseJpa;
import com.ubiqube.etsi.mano.service.NfvoService;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.elect.VimElection;
import com.ubiqube.etsi.mano.service.event.flavor.FlavorManager;
import com.ubiqube.etsi.mano.service.event.images.SoftwareImageService;
import com.ubiqube.etsi.mano.service.grant.ccm.CcmManager;
import com.ubiqube.etsi.mano.service.sys.ServerGroup;
import com.ubiqube.etsi.mano.service.vim.CirConnectionManager;
import com.ubiqube.etsi.mano.service.vim.NetworkObject;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;

import io.micrometer.context.ContextExecutorService;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
@ConditionalOnBean(value = NfvoService.class)
public class GrantAction {

	private static final Logger LOG = LoggerFactory.getLogger(GrantAction.class);

	private final GrantsResponseJpa grantJpa;

	private final VimManager vimManager;

	private final VimElection vimElection;

	private final SoftwareImageService imageService;

	private final FlavorManager flavorManager;

	private final GrantSupport grantSupport;

	private final GrantContainerAction grantContainerAction;

	private final VnfPackageService vnfPackageService;

	private final CcmManager ccmManager;

	private final CirConnectionManager cirManager;

	private final Set<String> requireVimInfo;

	protected GrantAction(final GrantsResponseJpa grantJpa, final VimManager vimManager, final VimElection vimElection,
			final SoftwareImageService imageService, final FlavorManager flavorManager, final GrantSupport grantSupport,
			final GrantContainerAction grantContainerAction, final VnfPackageService vnfPackageService, final CcmManager ccmManager, final CirConnectionManager cirManager) {
		this.vimManager = vimManager;
		this.vimElection = vimElection;
		this.grantJpa = grantJpa;
		this.imageService = imageService;
		this.flavorManager = flavorManager;
		this.grantSupport = grantSupport;
		this.grantContainerAction = grantContainerAction;
		this.vnfPackageService = vnfPackageService;
		this.ccmManager = ccmManager;
		this.cirManager = cirManager;
		requireVimInfo = Set.of("INSTANTIATE", "SCALE", "SCALE_TO_LEVEL", "CHANGE_FLAVOUR", "CHANGE_VNFPKG");
	}

	public final void grantRequest(final UUID objectId) {
		LOG.info("Evaluating grant {}", objectId);
		final GrantResponse grants = getGrant(objectId);
		try {
			grantRequestException(grants);
		} catch (final RuntimeException e) {
			LOG.error("Set grant: {} on error.", objectId, e);
			grants.setError(new FailureDetails(500, e.getMessage()));
		}
		LOG.debug("Saving grant: {}", grants.getId());
		grants.setAvailable(Boolean.TRUE);
		grantJpa.save(grants);
		LOG.info("Grant {} Available.", grants.getId());
	}

	private final void grantRequestException(final GrantResponse grants) {
		final UUID objectId = grants.getId();
		grants.getOperation();
		removeResources(grants);
		final List<VimConnectionInformation> vims = grantSupport.getVims(grants);
		final VimConnectionInformation vimInfo = vimElection.doElection(vims, null, grantSupport.getVnfCompute(objectId), grantSupport.getVnfStorage(objectId));
		if (vimInfo == null) {
			throw new GenericException("No suitable VIM after election.");
		}
		grants.setVimConnections(Collections.singleton(vimInfo));
		if (requireVimInfo.contains(grants.getOperation())) {
			getVimInformations(vimInfo, grants);
			addOrCreateK8sVim(grants);
			addCirConnection(grants);
		}
		if ("TERMINATE".equals(grants.getOperation())) {
			// XXX: There is no need to attempt image upload, and maybe more.
			getVimInformations(vimInfo, grants);
			removeK8sCluster(grants);
		}
	}

	private void addCirConnection(final GrantResponse grants) {
		final Map<String, ConnectionInformation> cirMap = StreamSupport.stream(cirManager.findAll().spliterator(), false)
				.collect(Collectors.toMap(ConnectionInformation::getName, x -> x));
		grants.setCirConnectionInfo(cirMap);
	}

	private void removeK8sCluster(final GrantResponse grants) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(grants.getVnfdId());
		if (!haveCni(vnfPackage)) {
			return;
		}
		ccmManager.getTerminateCluster(grants.getVnfInstanceId());
	}

	private void addOrCreateK8sVim(final GrantResponse grants) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(grants.getVnfdId());
		if (!haveCni(vnfPackage)) {
			return;
		}
		VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> vimc = ccmManager.getVimConnection(grants, vnfPackage);
		vimc = storeIfNeeded(vimc);
		grants.setCismConnections(Set.of(vimc));
	}

	private VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> storeIfNeeded(final VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> vimc) {
		final Optional<VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo>> res = vimManager.findOptionalVimByVimId(vimc.getVimId());
		if (res.isPresent()) {
			return res.get();
		}
		return vimManager.save(vimc);
	}

	private static boolean haveCni(final VnfPackage vnfPackage) {
		if (((null != vnfPackage.getOsContainer()) && !vnfPackage.getOsContainer().isEmpty()) || ((null != vnfPackage.getOsContainerDeployableUnits()) && !vnfPackage.getOsContainerDeployableUnits().isEmpty())) {
			return true;
		}
		if (((null != vnfPackage.getOsContainerDesc()) && !vnfPackage.getOsContainerDesc().isEmpty()) || ((null != vnfPackage.getMciops()) && !vnfPackage.getMciops().isEmpty())) {
			return true;
		}
		return false;
	}

	private void removeResources(final GrantResponse grants) {
		grants.getRemoveResources().forEach(x -> {
			if (x.getReservationId() != null) {
				final VimConnectionInformation vci = vimManager.findVimById(getSafeUUID(x.getVimConnectionId()));
				final Vim vim = vimManager.getVimById(getSafeUUID(x.getVimConnectionId()));
				vim.freeResources(vci, x.getReservationId());
			}
		});
	}

	private GrantResponse getGrant(final UUID objectId) {
		final Optional<GrantResponse> grantsOpt = grantJpa.findById(objectId);
		return grantsOpt.orElseThrow(() -> new NotFoundException("Grant ID " + objectId + " Not found."));
	}

	protected final void getVimInformations(final VimConnectionInformation vimInfo, final GrantResponse grants) {
		final ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		final ExecutorService executorService = ContextExecutorService.wrap(tpe);
		Exception throwable = null;
		try {
			getGrantInformationsImpl(executorService, vimInfo, grants);
		} catch (final RuntimeException | InterruptedException | ExecutionException e) {
			throwable = e;
			LOG.info("", e);
			Thread.currentThread().interrupt();
		}
		LOG.debug("Shutdown Grant executor.");
		executorService.shutdown();
		try {
			executorService.awaitTermination(15, TimeUnit.MINUTES);
		} catch (final InterruptedException e) {
			LOG.info("", e);
			Thread.currentThread().interrupt();
		}
		if (null != throwable) {
			throw new GenericException(throwable);
		}
	}

	private void getGrantInformationsImpl(final ExecutorService executorService, final VimConnectionInformation vimInfo, final GrantResponse grants) throws InterruptedException, ExecutionException {
		// Zones.
		final Future<String> futureZone = executorService.submit(getZone(vimInfo, grants));
		// Zone Group
		final Vim vim = vimManager.getVimById(vimInfo.getId());
		final Future<ZoneGroupInformation> futureSg = executorService.submit(getServerGroup(grants));

		final GrantVimAssetsEntity grantVimAssetsEntity = new GrantVimAssetsEntity();
		grants.setVimAssets(grantVimAssetsEntity);
		// XXX Push only needed ones. ( in case of terminate no need to push assets.)
		final Runnable getSoftwareImages = () -> grantVimAssetsEntity.setSoftwareImages(getSoftwareImageSafe(vimInfo, vim, grants));
		executorService.submit(getSoftwareImages);

		final Runnable getComputeResourceFlavours = () -> {
			try {
				grantVimAssetsEntity.getComputeResourceFlavours().addAll(getFlavors(vimInfo, grants.getId()));
			} catch (final RuntimeException e) {
				LOG.error("", e);
			}
			LOG.debug(" {}", grantVimAssetsEntity.getComputeResourceFlavours());
		};
		executorService.submit(getComputeResourceFlavours);

		final Runnable networkRunnable = () -> extractNetwork(grants, vimInfo, vim);
		executorService.submit(networkRunnable);
		grantSupport.getUnmanagedNetworks(grants, vim, vimInfo);
		final String zoneId = futureZone.get();
		final ZoneGroupInformation zgi = futureSg.get();
		// XXX It depends on Grant policy GRANT_RESERVE_SINGLE.
		grants.getAddResources().forEach(x -> {
			vim.allocateResources(vimInfo, x.getReservationId());
			x.setResourceProviderId(vim.getType());
			x.setVimConnectionId(vimInfo.getVimId());
			x.setZoneId(zoneId);
			x.setResourceGroupId(zgi.getZoneId().iterator().next());
			if (x.getType() == ResourceTypeEnum.OS_CONTAINER) {
				x.setContainerNamespace("mano");
				x.setVimConnectionId(null);
			}
		});
		grantContainerAction.handleGrant(grants);
	}

	private void extractNetwork(final GrantResponse grants, final VimConnectionInformation vimInfo, final Vim vim) {
		// Add public networks.
		final VnfPackage vnfPkg = vnfPackageService.findByVnfdId(grants.getVnfdId());
		// XXX The none match ExtCp cause to discard all extCp pointing to an existing
		// network.
		final List<String> lst = vnfPkg.getVirtualLinks().stream()
				.filter(x -> noneMatchVirtualCp(vnfPkg, x) /* && noneMatchExtCp(vnfPkg, x) */)
				.map(ListKeyPair::getValue)
				.toList();
		final List<NetworkObject> netFound = vim.network(vimInfo).searchByName(lst);
		netFound.forEach(x -> {
			final ExtManagedVirtualLinkDataEntity extVl = new ExtManagedVirtualLinkDataEntity();
			extVl.setResourceId(x.name());
			extVl.setResourceProviderId(vim.getType());
			extVl.setVimConnectionId(vimInfo.getVimId());
			extVl.setVnfVirtualLinkDescId(x.id());
			extVl.setGrants(grants);
			grants.addExtManagedVl(extVl);
		});

	}

	private static boolean noneMatchExtCp(final VnfPackage vnfPkg, final ListKeyPair x) {
		return vnfPkg.getVnfExtCp().stream().noneMatch(y -> Optional.ofNullable(y.getExternalVirtualLink())
				.filter(z -> z.equals(x.getValue()))
				.isPresent());
	}

	private static boolean noneMatchVirtualCp(final VnfPackage vnfPkg, final ListKeyPair x) {
		final Set<VirtualCp> vcpSet = vnfPkg.getVirtualCp();
		for (final VirtualCp cp : vcpSet) {
			// Temporarily hardcoded to value as 'a' to solve nullpointer
			cp.setVirtualLinkRef("a");
		}
		return vcpSet.stream()
				.noneMatch(y -> y.getVirtualLinkRef().equals(x.getValue()));
	}

	private static ZoneInfoEntity mapZone(final String zoneId, final VimConnectionInformation vimInfo) {
		final ZoneInfoEntity zoneInfoEntity = new ZoneInfoEntity();
		zoneInfoEntity.setVimConnectionId(vimInfo.getVimId());
		zoneInfoEntity.setResourceProviderId(vimInfo.getVimType());
		zoneInfoEntity.setZoneId(zoneId);
		return zoneInfoEntity;
	}

	private String chooseZone(final VimConnectionInformation vimInfo) {
		final Vim vim = vimManager.getVimById(vimInfo.getId());
		final List<String> list = vim.getZoneAvailableList(vimInfo);
		return list.getFirst();
	}

	private List<VimComputeResourceFlavourEntity> getFlavors(final VimConnectionInformation vimConnectionInformation, final UUID id) {
		final Set<VnfCompute> comp = grantSupport.getVnfCompute(id);
		return flavorManager.getFlavors(vimConnectionInformation, comp);
	}

	private Set<VimSoftwareImageEntity> getSoftwareImageSafe(final VimConnectionInformation vimInfo, final Vim vim, final GrantResponse grants) {
		try {
			return getSoftwareImage(vimInfo, vim, grants);
		} catch (final RuntimeException e) {
			LOG.error("getImage error", e);
		}
		return new HashSet<>();
	}

	private Set<VimSoftwareImageEntity> getSoftwareImage(final VimConnectionInformation vimInfo, final Vim vim, final GrantResponse grants) {
		final UUID vnfPkgId = grantSupport.convertVnfdToId(grants.getVnfdId());
		final List<SoftwareImage> vimImgs = imageService.getFullDetailImageList(vimInfo);
		final Set<VimSoftwareImageEntity> ret = new LinkedHashSet<>(getImage(grantSupport.getVnfCompute(grants.getId()), vimImgs, vimInfo, vim, vnfPkgId));
		final Set<VnfStorage> storage = grantSupport.getVnfStorage(grants.getId());
		ret.addAll(getImage(storage, vimImgs, vimInfo, vim, vnfPkgId));
		return ret;
	}

	private Set<VimSoftwareImageEntity> getImage(final Set<? extends ImageServiceAware> storage, final List<SoftwareImage> vimImgs, final VimConnectionInformation vimInfo, final Vim vim, final UUID vnfPkgId) {
		return storage.stream()
				.map(ImageServiceAware::getSoftwareImage)
				.filter(Objects::nonNull)
				.map(x -> {
					final SoftwareImage newImg = imageService.getImage(vimImgs, x, vimInfo, vnfPkgId);
					return mapSoftwareImage(newImg, x.getName(), vimInfo, vim);
				})
				.collect(Collectors.toSet());
	}

	private static VimSoftwareImageEntity mapSoftwareImage(final SoftwareImage softwareImage, final String vduId, final VimConnectionInformation vimInfo, final Vim vim) {
		final VimSoftwareImageEntity vsie = new VimSoftwareImageEntity();
		vsie.setVimSoftwareImageId(softwareImage.getVimId());
		vsie.setVnfdSoftwareImageId(vduId);
		vsie.setVimConnectionId(vimInfo.getVimId());
		vsie.setResourceProviderId(vim.getType());
		return vsie;
	}

	private Callable<String> getZone(final VimConnectionInformation vimInfo, final GrantResponse grants) {
		return () -> {
			final String zoneId = chooseZone(vimInfo);
			final ZoneInfoEntity zones = mapZone(zoneId, vimInfo);
			grants.addZones(zones);
			return zoneId;
		};
	}

	/**
	 * XXX Check Vim caps. ZoneGroup are not available on all vims.
	 *
	 * @param grants
	 * @return
	 */
	private static Callable<ZoneGroupInformation> getServerGroup(final GrantResponse grants) {
		return () -> {
			final List<ServerGroup> sg = new ArrayList<>();
			final ServerGroup serverGroup = new ServerGroup("1", "az", "az");
			sg.add(serverGroup);
			final Set<String> sgList = sg.stream().map(ServerGroup::getId).collect(Collectors.toSet());
			final ZoneGroupInformation zgi = new ZoneGroupInformation();
			zgi.setZoneId(sgList);
			grants.setZoneGroups(Collections.singleton(zgi));
			return zgi;
		};
	}

}
