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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.GrantVimAssetsEntity;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.ZoneGroupInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.GrantsResponseJpa;
import com.ubiqube.etsi.mano.service.NfvoService;
import com.ubiqube.etsi.mano.service.event.elect.VimElection;
import com.ubiqube.etsi.mano.service.grant.executor.ExecutorFacade;
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

	private final GrantSupport grantSupport;

	private final GrantContainerAction grantContainerAction;

	private final ExecutorFacade executorExecutorFacade;
	private final Set<String> requireVimInfo;

	protected GrantAction(final GrantsResponseJpa grantJpa, final VimManager vimManager, final VimElection vimElection, final GrantSupport grantSupport,
			final GrantContainerAction grantContainerAction, final ExecutorFacade executorExecutorFacade) {
		this.vimManager = vimManager;
		this.vimElection = vimElection;
		this.grantJpa = grantJpa;
		this.grantSupport = grantSupport;
		this.grantContainerAction = grantContainerAction;
		this.executorExecutorFacade = executorExecutorFacade;
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
			executorExecutorFacade.addOrCreateK8sVim(vimInfo, grants);
			executorExecutorFacade.addCirConnection(grants);
		}
		if ("TERMINATE".equals(grants.getOperation())) {
			// XXX: There is no need to attempt image upload, and maybe more.
			getVimInformations(vimInfo, grants);
			executorExecutorFacade.removeK8sCluster(grants);
		}
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
		final Future<String> futureZone = executorService.submit(executorExecutorFacade.getZone(vimInfo, grants));
		// Zone Group
		final Vim vim = vimManager.getVimById(vimInfo.getId());
		final Future<ZoneGroupInformation> futureSg = executorService.submit(executorExecutorFacade.getServerGroup(grants));

		final GrantVimAssetsEntity grantVimAssetsEntity = new GrantVimAssetsEntity();
		grants.setVimAssets(grantVimAssetsEntity);
		// XXX Push only needed ones. ( in case of terminate no need to push assets.)
		final Runnable getSoftwareImages = () -> grantVimAssetsEntity.setSoftwareImages(executorExecutorFacade.getSoftwareImageSafe(vimInfo, vim, grants));
		executorService.submit(getSoftwareImages);

		final Runnable getComputeResourceFlavours = () -> {
			try {
				grantVimAssetsEntity.getComputeResourceFlavours().addAll(executorExecutorFacade.getFlavors(vimInfo, grants.getId()));
			} catch (final RuntimeException e) {
				LOG.error("", e);
			}
			LOG.debug(" {}", grantVimAssetsEntity.getComputeResourceFlavours());
		};
		executorService.submit(getComputeResourceFlavours);

		final Runnable networkRunnable = () -> executorExecutorFacade.extractNetwork(grants, vimInfo, vim);
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

}
