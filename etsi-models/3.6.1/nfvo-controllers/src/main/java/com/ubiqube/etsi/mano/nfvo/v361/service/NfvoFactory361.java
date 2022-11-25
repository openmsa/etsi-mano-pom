/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.nfvo.v361.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.nfvo.v361.model.vnf.PackageOperationalStateType;
import com.ubiqube.etsi.mano.nfvo.v361.services.NfvoFactory;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;

/**
 *
 * @author olivier
 *
 */
@Service
public class NfvoFactory361 implements NfvoFactory {
	private final VnfPackageRepository vnfPackageRepository;

	public NfvoFactory361(final VnfPackageRepository vnfPackageRepository) {
		this.vnfPackageRepository = vnfPackageRepository;
	}

	@Override
	public Object createNotificationVnfPackageOnboardingNotification(final UUID subscriptionId, final EventMessage event) {
		final VnfPackage vnfPkg = vnfPackageRepository.get(event.getObjectId());
		return VnfSubscriptionFactory361.createNotificationVnfPackageOnboardingNotification(subscriptionId, event.getObjectId(), vnfPkg.getVnfdId(), new Sol005Linkable());
	}

	@Override
	public Object createVnfPackageChangeNotification(final UUID subscriptionId, final EventMessage event) {
		boolean deleted = false;
		try {
			vnfPackageRepository.get(event.getObjectId());
		} catch (final RuntimeException e) {
			deleted = true;
		}
		return VnfSubscriptionFactory361.createVnfPackageChangeNotification(deleted, subscriptionId, event.getId(), event.getAdditionalParameters().get("vnfdId"), event.getObjectId(),
				PackageOperationalStateType.fromValue(event.getAdditionalParameters().get("state")), new Sol005Linkable());
	}

}
