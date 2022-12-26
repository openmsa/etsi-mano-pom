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
package com.ubiqube.etsi.mano.controller.nfvmanologm;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface LogJobsFrontController {

	<U> ResponseEntity<String> search(MultiValueMap<String, String> requestParams, Class<U> class1, String nextpageOpaqueMarker);

	<U> ResponseEntity<U> compile(String logJobId, Object body, Class<U> clazz);

	ResponseEntity<Void> delete(String logJobId);

	<U> ResponseEntity<U> findById(String logJobId, Class<U> clazz);

	<U> ResponseEntity<U> findLogReport(String logJobId, String logReportId, Class<U> clazz);

	<U> ResponseEntity<U> create(Object body, Class<U> clazz);

}
