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
package com.ubiqube.etsi.mano.controller;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.alarm.AckState;
import com.ubiqube.etsi.mano.dao.mano.alarm.Alarms;
import com.ubiqube.etsi.mano.dao.mano.alarm.PerceivedSeverityType;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public interface AlarmVnfmController {

	Alarms findById(UUID id);

	void escalate(UUID id, @Valid PerceivedSeverityType proposedPerceivedSeverity);

	Alarms modify(UUID id, AckState acknowledged, @Nullable String ifMatch);

	<U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<Alarms, U> mapper, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink, Class<?> frontClass);

}
