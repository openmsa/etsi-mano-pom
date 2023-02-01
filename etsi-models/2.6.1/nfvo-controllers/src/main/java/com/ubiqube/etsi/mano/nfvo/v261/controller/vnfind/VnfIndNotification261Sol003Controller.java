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
package com.ubiqube.etsi.mano.nfvo.v261.controller.vnfind;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.common.v261.model.vnf.VnfIndicatorValueChangeNotification;
import com.ubiqube.etsi.mano.controller.vnfind.VnfIndicatorNotificationFrontController;
/**
 *
 * @author olivier
 *
 */
@RestController
public class VnfIndNotification261Sol003Controller implements VnfIndNotification261Sol003Api {
	private final VnfIndicatorNotificationFrontController fc;

	public VnfIndNotification261Sol003Controller(final VnfIndicatorNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> valueChangeCheck() {
		return fc.valueChangeCheck();
	}

	@Override
	public ResponseEntity<Void> valueChangePost(@Valid final VnfIndicatorValueChangeNotification body) {
		return fc.valueChangeNotification(body, "3.6.1");
	}

	@Override
	public ResponseEntity<Void> supportedCheck() {
		return fc.supportedChangeCheck();
	}

	@Override
	public ResponseEntity<Void> supportedChangePost(@Valid final VnfIndicatorValueChangeNotification body) {
		return fc.supportedChangeNotification(body, "3.6.1");
	}

}
