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
package com.ubiqube.etsi.mano.nfvo.v331;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.c331.services.NfvoFactory;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.model.EventMessage;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.PackageOperationalStateType;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.VnfPackageChangeNotification;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.VnfPackageOnboardingNotification;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NfvoFactory331 implements NfvoFactory {
	private final VnfPackageRepository vnfPackageRepository;

	public NfvoFactory331(final VnfPackageRepository vnfPackageRepository) {
		super();
		this.vnfPackageRepository = vnfPackageRepository;
	}

	@Override
	public Object createNotificationVnfPackageOnboardingNotification(final UUID subscriptionId, final EventMessage event) {
		final VnfPackage vnfPkg = vnfPackageRepository.get(event.getObjectId());
		final VnfPackageOnboardingNotification obj = VnfSubscriptionFactory331.createNotificationVnfPackageOnboardingNotification(subscriptionId, event.getObjectId(), vnfPkg.getVnfdId(), new Sol003Linkable());
		obj.setLinks(new Sol005Linkable().createVnfPackageOnboardingNotificationLinks(event.getObjectId(), subscriptionId));
		return obj;
	}

	@Override
	public Object createVnfPackageChangeNotification(final UUID subscriptionId, final EventMessage event) {
		boolean deleted = false;
		try {
			vnfPackageRepository.get(event.getObjectId());
		} catch (final RuntimeException e) {
			deleted = true;
		}
		final VnfPackageChangeNotification obj = VnfSubscriptionFactory331.createVnfPackageChangeNotification(deleted, subscriptionId, event.getObjectId(), event.getAdditionalParameters().get("vnfdId"),
				PackageOperationalStateType.fromValue(event.getAdditionalParameters().get("state")), new Sol003Linkable());
		obj.setLinks(new Sol005Linkable().createNotificationLink(event.getObjectId(), subscriptionId));
		return obj;
	}

}