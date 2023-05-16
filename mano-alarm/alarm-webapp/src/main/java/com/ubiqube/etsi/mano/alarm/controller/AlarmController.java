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
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.alarm.entities.AlarmDto;
import com.ubiqube.etsi.mano.alarm.entities.Subscription;
import com.ubiqube.etsi.mano.alarm.entities.SubscriptionDto;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.service.AlarmService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

/**
 *
 * @author Olivier Vignaud
 *
 */
@RestController
@RequestMapping("/")
@Validated
public class AlarmController {
	private final AlarmService alarmService;

	public AlarmController(final AlarmService alarmService) {
		this.alarmService = alarmService;
	}

	@Operation(summary = "List all Alarms", description = "List all alarms.", tags = {})
	@GetMapping
	public ResponseEntity<List<Alarm>> listAlarm() {
		final List<Alarm> lst = alarmService.find();
		return ResponseEntity.ok(lst);
	}

	@Operation(summary = "Get a unique alarm.", description = "Get a unique alarm.", tags = {})
	@GetMapping("/{id}")
	public ResponseEntity<Alarm> findById(final @Nonnull @PathVariable("id") UUID id) {
		final Optional<Alarm> res = alarmService.findById(id);
		if (res.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(res.get());
	}

	@Operation(summary = "Create an alarm.", description = "Create an alarm.", tags = {})
	@PostMapping
	public ResponseEntity<Alarm> createAlarm(final @RequestBody @Valid AlarmDto alarm) {
		final Alarm subs = map(alarm);
		final Alarm res = alarmService.create(subs);
		return ResponseEntity.ok(res);
	}

	@Operation(summary = "Delete an alarm.", description = "Delete an alarm.", tags = {})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAlaram(final @Nonnull @PathVariable("id") UUID id) {
		alarmService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@Nonnull
	private static Alarm map(final AlarmDto alarm) {
		final Alarm a = new Alarm();
		a.setAggregates(alarm.getAggregates());
		a.setConditions(alarm.getConditions());
		a.setMetrics(alarm.getMetrics());
		a.setTransforms(alarm.getTransforms());
		a.setSubscription(map(alarm.getSubscription()));
		return a;
	}

	private static Subscription map(final SubscriptionDto subscription) {
		final Subscription s = new Subscription();
		s.setAuthentication(subscription.getAuthentication());
		s.setCallbackUri(subscription.getCallbackUri().toString());
		s.setRemoteId(subscription.getRemoteId());
		return s;
	}
}
