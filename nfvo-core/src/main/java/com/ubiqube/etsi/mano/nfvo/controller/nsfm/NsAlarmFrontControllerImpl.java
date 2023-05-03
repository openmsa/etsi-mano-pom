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
package com.ubiqube.etsi.mano.nfvo.controller.nsfm;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.nsfm.NsAlarmFrontController;

@Service
public class NsAlarmFrontControllerImpl implements NsAlarmFrontController {

	@Override
	public <U> ResponseEntity<U> findById(final UUID safeUUID, final Class<U> clazz, final Consumer<U> makeLinks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> ResponseEntity<U> patch(final String alarmId, final Object body, final Class<U> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> ResponseEntity<List<U>> search(final MultiValueMap<String, String> requestParams, final String nextpageOpaqueMarker, final Class<U> clazz, final Consumer<U> makeLinks) {
		// TODO Auto-generated method stub
		return null;
	}

}
