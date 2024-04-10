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
package com.ubiqube.etsi.mano.nfvo.controller.nsfm;

import static com.ubiqube.etsi.mano.Constants.ALARM_SEARCH_DEFAULT_EXCLUDE_FIELDS;
import static com.ubiqube.etsi.mano.Constants.ALARM_SEARCH_MANDATORY_FIELDS;
import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.nsfm.NsAlarmFrontController;
import com.ubiqube.etsi.mano.dao.mano.alarm.AckState;
import com.ubiqube.etsi.mano.dao.mano.alarm.Alarms;
import com.ubiqube.etsi.mano.nfvo.service.NfvoAlarmService;

import jakarta.annotation.Nullable;

@Service
public class NsAlarmFrontControllerImpl implements NsAlarmFrontController {
	private final NfvoAlarmService alarmNfvoController;

	public NsAlarmFrontControllerImpl(final NfvoAlarmService alarmNfvoController) {
		this.alarmNfvoController = alarmNfvoController;
	}

	@Override
	public <U> ResponseEntity<U> findById(final UUID id, final Function<Alarms, U> func, final Consumer<U> makeLinks) {
		final Alarms alarm = alarmNfvoController.findById(id);
		final U ret = func.apply(alarm);
		makeLinks.accept(ret);
		return ResponseEntity.ok(ret);
	}

	@Override
	public <U> ResponseEntity<U> patch(final String alarmId, final AckState ackState, final @Nullable String ifMatch, final Function<Alarms, U> func) {
		final Alarms alarm = alarmNfvoController.modify(getSafeUUID(alarmId), ackState, ifMatch);
		return ResponseEntity.ok(func.apply(alarm));
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final String nextpageOpaqueMarker, final Function<Alarms, U> mapper, final Consumer<U> makeLinks, final Class<?> frontClass) {
		return alarmNfvoController.search(requestParams, mapper, ALARM_SEARCH_DEFAULT_EXCLUDE_FIELDS, ALARM_SEARCH_MANDATORY_FIELDS, makeLinks, frontClass);
	}

}
