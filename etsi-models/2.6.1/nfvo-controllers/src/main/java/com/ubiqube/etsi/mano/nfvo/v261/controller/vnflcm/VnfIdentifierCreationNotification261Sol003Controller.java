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
package com.ubiqube.etsi.mano.nfvo.v261.controller.vnflcm;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfLcmNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v261.model.vnflcm.VnfIdentifierCreationNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class VnfIdentifierCreationNotification261Sol003Controller implements VnfIdentifierCreationNotification261Sol003Api {
	private final VnfLcmNotificationFrontController fc;

	public VnfIdentifierCreationNotification261Sol003Controller(final VnfLcmNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> vnfIdentifierCreationNotificationCheck() {
		return fc.creationCheck();
	}

	@Override
	public ResponseEntity<Void> vnfIdentifierCreationNotificationPost(@Valid final VnfIdentifierCreationNotification body) {
		return fc.creationNotification(body, "3.6.1");
	}

}
