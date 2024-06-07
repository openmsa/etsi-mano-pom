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
package com.ubiqube.etsi.mano.nfvo.controller.vnflcm;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfLcmNotificationFrontController;
import com.ubiqube.etsi.mano.dao.mano.vnflcm.VnfLcmNotification;
import com.ubiqube.etsi.mano.nfvo.service.VnfLcmNotificationService;

/**
 *
 * @author olivier
 *
 */
@Service
public class VnfLcmNotificationFrontControllerImpl implements VnfLcmNotificationFrontController {
	private final VnfLcmNotificationService notificationService;

	public VnfLcmNotificationFrontControllerImpl(final VnfLcmNotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	public ResponseEntity<Void> creationCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> creationNotification(final VnfLcmNotification body, final String version) {
        notificationService.onCreationNotification(body, version);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> deletionCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> deletionNotification(final VnfLcmNotification body, final String version) {
        notificationService.onDeletionNotification(body, version);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> vnflcmopoccCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> vnflcmopoccNotification(final VnfLcmNotification body, final String version) {
        notificationService.onVnfLcmOpOccsNotification(body, version);
		return ResponseEntity.noContent().build();
	}

}
