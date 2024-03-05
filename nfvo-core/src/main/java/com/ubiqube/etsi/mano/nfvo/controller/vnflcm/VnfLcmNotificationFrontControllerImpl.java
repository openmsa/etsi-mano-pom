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

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author olivier
 *
 */
@Service
public class VnfLcmNotificationFrontControllerImpl implements VnfLcmNotificationFrontController {
	private final MapperFacade mapper;
	private final VnfLcmNotificationService notificationService;

	public VnfLcmNotificationFrontControllerImpl(final MapperFacade mapper, final VnfLcmNotificationService notificationService) {
		this.mapper = mapper;
		this.notificationService = notificationService;
	}

	@Override
	public ResponseEntity<Void> creationCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> creationNotification(final Object body, final String version) {
		final VnfLcmNotification event = mapper.map(body, VnfLcmNotification.class);
		notificationService.onCreationNotification(event, version);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> deletionCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> deletionNotification(final Object body, final String version) {
		final VnfLcmNotification event = mapper.map(body, VnfLcmNotification.class);
		notificationService.onDeletionNotification(event, version);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> vnflcmopoccCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> vnflcmopoccNotification(final Object body, final String version) {
		final VnfLcmNotification event = mapper.map(body, VnfLcmNotification.class);
		notificationService.onVnfLcmOpOccsNotification(event, version);
		return ResponseEntity.noContent().build();
	}

}
