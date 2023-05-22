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
package com.ubiqube.etsi.mano.alarm.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.AlarmDto;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

/**
 *
 * @author Olivier Vignaud
 *
 */
@HttpExchange(url = "/", accept = "application/json", contentType = "application/json")
public interface AlarmClient {

	@GetExchange
	ResponseEntity<List<Alarm>> listAlarm();

	@GetExchange("/{id}")
	ResponseEntity<Alarm> findById(final @Nonnull @PathVariable("id") UUID id);

	@PostExchange
	ResponseEntity<Alarm> createAlarm(final @RequestBody @Valid AlarmDto alarm);

	@DeleteExchange("/{id}")
	ResponseEntity<Void> deleteAlaram(final @Nonnull @PathVariable("id") UUID id);

}
