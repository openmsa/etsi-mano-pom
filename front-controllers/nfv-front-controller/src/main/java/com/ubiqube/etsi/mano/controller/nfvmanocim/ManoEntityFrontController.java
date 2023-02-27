/**
 *     Copyright (C) 2019-2023 Ubiqube.
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

public interface ManoEntityFrontController {

	ResponseEntity<Void> changeStatus(Object body);

	<U> ResponseEntity<U> find(Class<U> clazz);

	<U> ResponseEntity<List<U>> interfaceSearch(String filter, Class<U> clazz);

	ResponseEntity<Void> interfaceChangeState(String manoServiceInterfaceId, Object body);

	<U> ResponseEntity<U> interfaceFindById(String manoServiceInterfaceId, Class<U> clazz);

	<U> ResponseEntity<U> interfacePatch(String manoServiceInterfaceId, Object body, Class<U> clazz);

	<U> ResponseEntity<U> patch(Object body, Class<U> clazz);

}
