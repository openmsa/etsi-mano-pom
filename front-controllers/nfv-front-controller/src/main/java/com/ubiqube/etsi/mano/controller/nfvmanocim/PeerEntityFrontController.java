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
package com.ubiqube.etsi.mano.controller.nfvmanocim;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface PeerEntityFrontController {

	<U> ResponseEntity<List<U>> search(String filter, Class<U> clazz);

	ResponseEntity<Void> delete(String peerEntityId);

	<U> ResponseEntity<U> findById(String peerEntityId, Class<U> clazz);

	<U> ResponseEntity<U> patch(String peerEntityId, Object body, Class<U> clazz);

	<U> ResponseEntity<U> create(Object body, Class<U> clazz);

}
