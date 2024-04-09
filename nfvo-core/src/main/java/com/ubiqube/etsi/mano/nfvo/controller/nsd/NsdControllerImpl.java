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
package com.ubiqube.etsi.mano.nfvo.controller.nsd;

import static com.ubiqube.etsi.mano.Constants.ensureDisabled;
import static com.ubiqube.etsi.mano.Constants.ensureIsOnboarded;
import static com.ubiqube.etsi.mano.Constants.ensureNotInUse;
import static com.ubiqube.etsi.mano.Constants.ensureNotOnboarded;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.NsdRepository;
import com.ubiqube.etsi.mano.service.Patcher;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

import jakarta.annotation.Nullable;

@Service
public class NsdControllerImpl implements NsdController {

	private static final Logger LOG = LoggerFactory.getLogger(NsdControllerImpl.class);
	private final NsdRepository nsdRepository;
	private final Patcher patcher;
	private final EventManager eventManager;
	private final SearchableService searchableService;

	public NsdControllerImpl(final NsdRepository nsdRepository, final Patcher patcher, final EventManager eventManager, final SearchableService searchableService) {
		this.nsdRepository = nsdRepository;
		this.patcher = patcher;
		this.eventManager = eventManager;
		this.searchableService = searchableService;
		LOG.info("Starting NSD Management SOL005 Controller.");
	}

	@Override
	public void nsDescriptorsNsdInfoIdDelete(final UUID id) {
		final NsdPackage nsdPackage = nsdRepository.get(id);
		ensureDisabled(nsdPackage);
		ensureNotInUse(nsdPackage);
		nsdRepository.delete(id);
		eventManager.sendNotification(NotificationEvent.NS_PKG_ONDELETION, id, Map.of());
	}

	@Override
	public NsdPackage nsDescriptorsNsdInfoIdGet(final UUID id) {
		return nsdRepository.get(id);
	}

	@Override
	public ManoResource nsDescriptorsNsdInfoIdNsdContentGet(final UUID id) {
		final NsdPackage nsdInfo = nsdRepository.get(id);
		ensureIsOnboarded(nsdInfo);
		return nsdRepository.getBinary(id, "nsd");
	}

	@Override
	public void nsDescriptorsNsdInfoIdNsdContentPut(final UUID id, final InputStream is) {
		final NsdPackage nsdInfo = nsdRepository.get(id);
		ensureNotOnboarded(nsdInfo);
		forceVnfdId(nsdInfo);
		nsdRepository.save(nsdInfo);
		nsdRepository.storeBinary(id, "nsd", is);
		eventManager.sendActionNfvo(ActionType.NSD_PKG_ONBOARD_FROM_BYTES, nsdInfo.getId(), new HashMap<>());
	}

	@Override
	public NsdPackage nsDescriptorsNsdInfoIdPatch(final UUID id, final String body, final @Nullable String ifMatch) {
		final NsdPackage nsdPkgInfo = nsdRepository.get(id);
		ensureIsOnboarded(nsdPkgInfo);
		if ((ifMatch != null) && !(nsdPkgInfo.getVersion() + "").equals(ifMatch)) {
			throw new PreConditionException(ifMatch + " does not match " + nsdPkgInfo.getVersion());
		}
		patcher.patch(body, nsdPkgInfo);
		eventManager.sendNotification(NotificationEvent.NS_PKG_ONCHANGE, id, Map.of());
		return nsdRepository.save(nsdPkgInfo);
	}

	@Override
	public NsdPackage nsDescriptorsPost(final Map<String, String> userDefinedData) {
		final NsdPackage nsdPackage = new NsdPackage();
		nsdPackage.setUserDefinedData(userDefinedData);
		nsdPackage.setNsdOnboardingState(OnboardingStateType.CREATED);
		nsdPackage.setNsdOperationalState(PackageOperationalState.DISABLED);
		nsdPackage.setNsdUsageState(UsageStateEnum.NOT_IN_USE);
		return nsdRepository.save(nsdPackage);
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<NsdPackage, U> mapper, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink) {
		return searchableService.search(NsdPackage.class, requestParams, mapper, excludeDefaults, mandatoryFields, makeLink, List.of());
	}

	private static void forceVnfdId(final NsdPackage vnfPackage) {
		final RequestAttributes attr = RequestContextHolder.getRequestAttributes();
		if (attr instanceof final ServletRequestAttributes sra) {
			final String xdi = sra.getRequest().getHeader("x-descriptor-id");
			if (null != xdi) {
				vnfPackage.setOverwriteDescId(xdi);
				return;
			}
		}
		vnfPackage.setOverwriteDescId(null);
	}
}
