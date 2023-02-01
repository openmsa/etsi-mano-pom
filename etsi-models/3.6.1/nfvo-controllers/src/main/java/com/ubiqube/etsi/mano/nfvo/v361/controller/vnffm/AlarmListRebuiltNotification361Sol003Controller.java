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
package com.ubiqube.etsi.mano.nfvo.v361.controller.vnffm;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnffm.VnffmNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v361.model.vnffm.AlarmListRebuiltNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class AlarmListRebuiltNotification361Sol003Controller implements AlarmListRebuiltNotification361Sol003Api {
	private final VnffmNotificationFrontController fc;

	public AlarmListRebuiltNotification361Sol003Controller(final VnffmNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> alarmListRebuiltCheck() {
		return fc.alarmRebuiltCheck();
	}

	@Override
	public ResponseEntity<Void> alarmListRebuiltNotificationPost(@Valid final AlarmListRebuiltNotification body) {
		return fc.alarmRebuiltNotification(body, "3.6.1");
	}

}
