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
package com.ubiqube.etsi.mano.nfvo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.alarm.AckState;
import com.ubiqube.etsi.mano.dao.mano.alarm.Alarms;
import com.ubiqube.etsi.mano.exception.ConflictException;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.vnfm.jpa.AlarmsJpa;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class NfvoAlarmService {
	private final AlarmsJpa alarmJpa;
	private final SearchableService searchableService;

	public NfvoAlarmService(final AlarmsJpa alarmJpa, final SearchableService searchableService) {
		this.alarmJpa = alarmJpa;
		this.searchableService = searchableService;
	}

	public Alarms findById(final UUID id) {
		final Optional<Alarms> res = alarmJpa.findById(id);
		return res.orElseThrow();
	}

	public Alarms modify(final UUID safeUUID, final AckState ackState, @Nullable final String ifMatch) {
		final Alarms alarm = alarmJpa.findById(safeUUID).orElseThrow();
		Optional.ofNullable(ackState).ifPresent(alarm::setAckState);
		if ((ifMatch != null) && ifMatch.equals(alarm.getVersion() + "")) {
			throw new ConflictException("Version header doesn't match: " + ifMatch + " want: " + alarm.getVersion());
		}
		return alarmJpa.save(alarm);
	}

	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<Alarms, U> mapper, final String alarmSearchDefaultExcludeFields, final Set<String> alarmSearchMandatoryFields, final Consumer<U> makeLinks) {
		return searchableService.search(Alarms.class, requestParams, mapper, alarmSearchDefaultExcludeFields, alarmSearchMandatoryFields, makeLinks, List.of());
	}

}
