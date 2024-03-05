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
package com.ubiqube.etsi.mano.controller.nfv.cim;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.nfvmanocim.PeerEntityFrontController;

@Service
public class PeerEntityController implements PeerEntityFrontController {

	@Override
	public <U> ResponseEntity<List<U>> search(final String filter, final Class<U> clazz) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResponseEntity<Void> delete(final String peerEntityId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String peerEntityId, final Class<U> clazz) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> patch(final String peerEntityId, final Object body, final Class<U> clazz) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> create(final Object body, final Class<U> clazz) {
		throw new UnsupportedOperationException();
	}

}
