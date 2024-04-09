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
package com.ubiqube.etsi.mano.vnfm.controller.vnf;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfPackageChangeNotification;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification;
import com.ubiqube.etsi.mano.vnfm.fc.vnf.VnfNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.service.VnfNotificationService;

import jakarta.transaction.Transactional;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfNotificationFrontControllerImpl implements VnfNotificationFrontController {
	private final VnfNotificationService vnfNotificationService;

	public VnfNotificationFrontControllerImpl(final VnfNotificationService vnfNotificationService) {
		this.vnfNotificationService = vnfNotificationService;

	}

	@Override
	public ResponseEntity<Void> check() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> onNotification(final VnfPackageOnboardingNotification event, final String version) {
		vnfNotificationService.onNotification(event, version);
		return ResponseEntity.noContent().build();
	}

	@Transactional
	@Override
	public ResponseEntity<Void> onChange(final VnfPackageChangeNotification body, final String version) {
		vnfNotificationService.onChange(body);
		return ResponseEntity.noContent().build();
	}

}
