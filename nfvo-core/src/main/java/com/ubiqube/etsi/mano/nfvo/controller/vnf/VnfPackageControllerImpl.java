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
package com.ubiqube.etsi.mano.nfvo.controller.vnf;

import static com.ubiqube.etsi.mano.Constants.ensureDisabled;
import static com.ubiqube.etsi.mano.Constants.ensureNotInUse;
import static com.ubiqube.etsi.mano.Constants.ensureNotOnboarded;
import static com.ubiqube.etsi.mano.Constants.ensureNotProcessing;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.UploadUriParameters;
import com.ubiqube.etsi.mano.exception.ConflictException;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.nfvo.factory.VnfPackageFactory;
import com.ubiqube.etsi.mano.nfvo.service.NsdPackageVnfPackageService;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.Patcher;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

import jakarta.annotation.Nullable;

@Service
public class VnfPackageControllerImpl implements VnfPackageController {
	private final VnfPackageService vnfPackageService;
	private final Patcher patcher;
	private final EventManager eventManager;
	private final VnfPackageRepository vnfPackageRepository;
	private final NsdPackageVnfPackageService nsdPackageVnfPackageService;

	public VnfPackageControllerImpl(final Patcher patcher, final EventManager eventManager, final VnfPackageService vnfPackageService,
			final VnfPackageRepository vnfPackageRepository, final NsdPackageVnfPackageService nsdPackageVnfPackageService) {
		this.patcher = patcher;
		this.eventManager = eventManager;
		this.vnfPackageService = vnfPackageService;
		this.vnfPackageRepository = vnfPackageRepository;
		this.nsdPackageVnfPackageService = nsdPackageVnfPackageService;
	}

	@Override
	public VnfPackage vnfPackagesPost(final Map<String, String> userData) {
		final VnfPackage vnfPackage = VnfPackageFactory.createVnfPkgInfo(userData);
		return vnfPackageService.save(vnfPackage);
	}

	@Override
	public void vnfPackagesVnfPkgIdDelete(final UUID id) {
		final VnfPackage vnfPackage = vnfPackageService.findById(id);
		ensureDisabled(vnfPackage);
		ensureNotInUse(vnfPackage);
		final Set<NsdPackageVnfPackage> res = nsdPackageVnfPackageService.findByVnfdId(vnfPackage.getVnfdId());
		if (!res.isEmpty()) {
			final String error = res.stream()
					.map(NsdPackageVnfPackage::getNsdPackage)
					.map(NsdPackage::getId)
					.map(UUID::toString)
					.collect(Collectors.joining(","));
			throw new ConflictException("Package " + id + ", is in use in the following NSD: " + error);
		}
		vnfPackageRepository.delete(id);
		if (null != vnfPackage.getVnfdId()) {
			eventManager.sendNotification(NotificationEvent.VNF_PKG_ONDELETION, id, buildMap(vnfPackage));
		}
	}

	private static Map<String, String> buildMap(final VnfPackage vnfPackage) {
		final Map<String, String> map = new LinkedHashMap<>();
		map.put("vnfdId", vnfPackage.getVnfdId());
		map.put("vnfProductsFromProviders.vnfProvider", vnfPackage.getVnfProvider());
		map.put("vnfProductsFromProviders.vnfProducts.vnfProductName", vnfPackage.getVnfProductName());
		map.put("vnfProductsFromProviders.vnfProducts.versions.vnfSoftwareVersion", vnfPackage.getVnfSoftwareVersion());
		map.put("vnfProductsFromProviders.vnfProducts.versions.vnfdVersions", vnfPackage.getDescriptorVersion());
		map.put("operationalState", "" + vnfPackage.getOperationalState());
		map.put("usageState", "" + vnfPackage.getUsageState());
		map.put("vnfmInfo", "" + vnfPackage.getVnfmInfo());
		return map;

	}

	@Override
	public VnfPackage vnfPackagesVnfPkgIdPatch(final UUID id, final String body, final @Nullable String ifMatch) {
		final VnfPackage vnfPackage = vnfPackageService.findById(id);
		if ((ifMatch != null) && !(vnfPackage.getVersion() + "").equals(ifMatch)) {
			throw new PreConditionException(ifMatch + " does not match " + vnfPackage.getVersion());
		}
		patcher.patch(body, vnfPackage);
		if (null != vnfPackage.getVnfdId()) {
			eventManager.sendNotification(NotificationEvent.VNF_PKG_ONCHANGE, id, Map.of("vnfdId", vnfPackage.getVnfdId(), "state", vnfPackage.getOperationalState().toString()));
		}
		return vnfPackageService.save(vnfPackage);
	}

	@Override
	public void vnfPackagesVnfPkgIdPackageContentPut(final UUID id, final InputStream is, final String accept) {
		final VnfPackage vnfPackage = vnfPackageService.findById(id);
		ensureNotOnboarded(vnfPackage);
		ensureNotProcessing(vnfPackage);
		forceVnfdId(vnfPackage);
		vnfPackage.setOnboardingState(OnboardingStateType.PROCESSING);
		vnfPackageService.save(vnfPackage);
		vnfPackageRepository.storeBinary(id, Constants.REPOSITORY_FILENAME_PACKAGE, is);
		eventManager.sendActionNfvo(ActionType.VNF_PKG_ONBOARD_FROM_BYTES, id, Map.of());
	}

	@Override
	public void vnfPackagesVnfPkgIdPackageContentUploadFromUriPost(final UUID id, final String contentType, final UploadUriParameters params) {
		final VnfPackage vnfPackage = vnfPackageService.findById(id);
		ensureNotOnboarded(vnfPackage);
		ensureNotProcessing(vnfPackage);
		vnfPackage.setUploadUriParameters(params);
		forceVnfdId(vnfPackage);
		vnfPackageService.save(vnfPackage);
		eventManager.sendActionNfvo(ActionType.VNF_PKG_ONBOARD_FROM_URI, id, Map.of());
	}

	private static void forceVnfdId(final VnfPackage vnfPackage) {
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
