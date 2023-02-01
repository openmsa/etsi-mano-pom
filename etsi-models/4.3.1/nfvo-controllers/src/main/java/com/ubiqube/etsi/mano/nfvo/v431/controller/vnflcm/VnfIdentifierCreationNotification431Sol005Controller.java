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
package com.ubiqube.etsi.mano.nfvo.v431.controller.vnflcm;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.em.v431.model.vnflcm.VnfIdentifierCreationNotification;
import com.ubiqube.etsi.mano.em.v431.model.vnflcm.VnfIdentifierDeletionNotification;
import com.ubiqube.etsi.mano.em.v431.model.vnflcm.VnfLcmOperationOccurrenceNotification;

@RestController
public class VnfIdentifierCreationNotification431Sol005Controller implements VnfIdentifierCreationNotification431Sol005Api {

	@Override
	public ResponseEntity<Void> vnfIdentifierCreationCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> vnfIdentifierCreationPost(@Valid final VnfIdentifierCreationNotification body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> vnfLcmOperationOccurrenceCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> vnfLcmOperationOccurrencePost(@Valid final VnfLcmOperationOccurrenceNotification body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> vnfIdentifierDeletionCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> vnfIdentifierDeletionPost(@Valid final VnfIdentifierDeletionNotification body) {
		// TODO Auto-generated method stub
		return null;
	}

}
