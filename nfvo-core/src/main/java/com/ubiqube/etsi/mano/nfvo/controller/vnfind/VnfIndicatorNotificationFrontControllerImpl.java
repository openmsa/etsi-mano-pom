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
package com.ubiqube.etsi.mano.nfvo.controller.vnfind;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.vnfind.VnfIndicatorNotificationFrontController;
import com.ubiqube.etsi.mano.dao.mano.ind.VnfIndiValueChangeNotification;
import com.ubiqube.etsi.mano.nfvo.service.VnfIndicatorNotificationService;

/**
 *
 * @author olivier
 *
 */
@Service
public class VnfIndicatorNotificationFrontControllerImpl implements VnfIndicatorNotificationFrontController {
	private final VnfIndicatorNotificationService vnfNotificationService;

	public VnfIndicatorNotificationFrontControllerImpl(final VnfIndicatorNotificationService vnfNotificationService) {
		this.vnfNotificationService = vnfNotificationService;
	}

	@Override
	public ResponseEntity<Void> valueChangeCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> valueChangeNotification(final VnfIndiValueChangeNotification body, final String version) {
        vnfNotificationService.onNotification(body, version);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> supportedChangeCheck() {
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> supportedChangeNotification(final VnfIndiValueChangeNotification body, final String version) {
		vnfNotificationService.onNotification(body, version);
		return ResponseEntity.noContent().build();
	}

}
