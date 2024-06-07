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
package com.ubiqube.etsi.mano.nfvo.controller.vnfpm;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.vnf.VnfPerformanceNotificationFrontController;
import com.ubiqube.etsi.mano.dao.mano.pm.PerformanceInformationAvailableNotification;
import com.ubiqube.etsi.mano.dao.mano.pm.ThresholdCrossedNotification;
import com.ubiqube.etsi.mano.nfvo.service.VnfPerformanceNotificationService;

/**
 *
 * @author olivier
 *
 */
@Service
public class VnfPerformanceNotificationFrontControllerImpl implements VnfPerformanceNotificationFrontController {
	private final VnfPerformanceNotificationService notificationService;

	public VnfPerformanceNotificationFrontControllerImpl(final VnfPerformanceNotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	public ResponseEntity<Void> crossedPost(final ThresholdCrossedNotification body, final String version) {
        notificationService.onCrossedNotification(body, version);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> crossedCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> availablePost(final PerformanceInformationAvailableNotification body, final String version) {
        notificationService.onAvailableNotification(body, version);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> availableCheck() {
		return ResponseEntity.noContent().build();
	}

}
