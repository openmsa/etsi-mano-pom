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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.NsdPackageJpa;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.NsdRepository;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NsPackageOnboardingImpl {

	private static final Logger LOG = LoggerFactory.getLogger(NsPackageOnboardingImpl.class);

	private final EventManager eventManager;

	private final NsPackageManager packageManager;

	private final NsdRepository nsdRepository;

	private final NsdPackageJpa nsdPackageJpa;

	private final NsOnboardingMapperService nsOnboardingMapperService;

	public NsPackageOnboardingImpl(final EventManager eventManager, final NsPackageManager packageManager, final NsdRepository nsdRepository, final NsOnboardingMapperService nsOnboardingMapperService,
			final NsdPackageJpa nsdPackageJpa) {
		this.eventManager = eventManager;
		this.packageManager = packageManager;
		this.nsdRepository = nsdRepository;
		this.nsOnboardingMapperService = nsOnboardingMapperService;
		this.nsdPackageJpa = nsdPackageJpa;
	}

	public void nsOnboarding(final UUID objectId) {
		final NsdPackage nsPackage = nsdPackageJpa.findById(objectId).orElseThrow(() -> new NotFoundException("NS Package " + objectId + " Not found."));
		try {
			nsOnboardingInternal(nsPackage);
			nsPackage.setNsdOnboardingState(OnboardingStateType.ONBOARDED);
			nsPackage.setNsdOperationalState(PackageOperationalState.ENABLED);
			nsPackage.setOnboardingFailureDetails(new FailureDetails());
			nsdPackageJpa.save(nsPackage);
		} catch (final RuntimeException e) {
			LOG.error("NSD error", e);
			// XXX: ERROR on 2.6.1+
			final NsdPackage v2 = nsdPackageJpa.findById(nsPackage.getId()).orElseThrow();
			v2.setNsdOnboardingState(OnboardingStateType.ERROR);
			v2.setNsdOperationalState(PackageOperationalState.DISABLED);
			v2.setOnboardingFailureDetails(new FailureDetails(500, e.getMessage()));
			nsdPackageJpa.save(v2);
		}
		eventManager.sendNotification(NotificationEvent.NS_PKG_ONBOARDING, nsPackage.getId(), Map.of());
	}

	public void nsOnboardingInternal(final NsdPackage nsPackage) {
		final ManoResource data = nsdRepository.getBinary(nsPackage.getId(), "nsd");
		final PackageDescriptor<NsPackageProvider> packageProvider = packageManager.getProviderFor(data);
		if (null != packageProvider) {
			try (InputStream is = data.getInputStream()) {
				mapNsPackage(packageProvider.getNewReaderInstance(is, nsPackage.getId()), nsPackage);
			} catch (final IOException e) {
				throw new GenericException(e);
			}
		}
	}

	public void mapNsPackage(final NsPackageProvider packageProvider, final NsdPackage nsPackage) {
		nsPackage.setAutoHealEnabled(packageProvider.isAutoHealEnabled());
		nsOnboardingMapperService.mapper(packageProvider, nsPackage);
	}

}
