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
package com.ubiqube.etsi.mano.vnfm.controller.vnfpm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.AlarmVnfmController;
import com.ubiqube.etsi.mano.dao.mano.alarm.AckState;
import com.ubiqube.etsi.mano.dao.mano.alarm.Alarms;
import com.ubiqube.etsi.mano.dao.mano.alarm.PerceivedSeverityType;
import com.ubiqube.etsi.mano.exception.ConflictException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.vnfm.service.AlarmDatabaseService;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class AlarmVnfmControllerImpl implements AlarmVnfmController {

	private final AlarmDatabaseService alarmsService;
	private final SearchableService searchableService;

	public AlarmVnfmControllerImpl(final SearchableService searchableService, final AlarmDatabaseService alarmsService) {
		this.searchableService = searchableService;
		this.alarmsService = alarmsService;
	}

	@Override
	public Alarms findById(final UUID id) {
		return alarmsService.findById(id).orElseThrow(() -> new NotFoundException("Could not find Alarm ID: " + id));
	}

	@Override
	public void escalate(final UUID id, final PerceivedSeverityType proposedPerceivedSeverity) {
		final Alarms alarm = findById(id);
		alarm.setPerceivedSeverity(proposedPerceivedSeverity);
		alarmsService.save(alarm);
	}

	@Override
	public Alarms modify(final UUID id, final AckState acknowledged, final @Nullable String ifMatch) {
		final Alarms alarm = findById(id);
		if ((ifMatch != null) && !ifMatch.equals(alarm.getVersion() + "")) {
			throw new PreConditionException(ifMatch + " does not match " + alarm.getVersion());
		}
		if (alarm.getAckState() == acknowledged) {
			throw new ConflictException("State is already " + acknowledged);
		}
		alarm.setAckState(acknowledged);
		alarm.setAlarmAcknowledgedTime(LocalDateTime.now());
		return alarmsService.save(alarm);
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink) {
		return searchableService.search(Alarms.class, requestParams, clazz, excludeDefaults, mandatoryFields, makeLink, List.of());
	}

}
