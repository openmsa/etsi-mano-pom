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
package com.ubiqube.etsi.mano.nfvo.controller.vnffm;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.vnffm.VnffmNotificationFrontController;
import com.ubiqube.etsi.mano.dao.mano.alarm.AlarmNotification;
import com.ubiqube.etsi.mano.nfvo.service.VnffmNotificationService;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author olivier
 *
 */
@Service
public class VnffmNotificationFrontControllerImpl implements VnffmNotificationFrontController {
	private final MapperFacade mapper;
	private final VnffmNotificationService vnffmNotificationService;

	public VnffmNotificationFrontControllerImpl(final MapperFacade mapper, final VnffmNotificationService ns, final VnffmNotificationService vnffmNotificationService) {
		this.mapper = mapper;
		this.vnffmNotificationService = vnffmNotificationService;
	}

	@Override
	public ResponseEntity<Void> alarmCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> alarmNotification(final Object body, final String version) {
		final AlarmNotification event = mapper.map(body, AlarmNotification.class);
		vnffmNotificationService.onNotification(event, version);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> alarmRebuiltCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> alarmRebuiltNotification(final Object body, final String version) {
		final AlarmNotification event = mapper.map(body, AlarmNotification.class);
		vnffmNotificationService.onNotification(event, version);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> alarmClearedCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> alarmClearedNotification(final Object body, final String version) {
		final AlarmNotification event = mapper.map(body, AlarmNotification.class);
		vnffmNotificationService.onNotification(event, version);
		return ResponseEntity.noContent().build();
	}

}
