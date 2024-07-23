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
package com.ubiqube.etsi.mano.service.mon.jms.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.service.mon.model.MonSubscription;
import com.ubiqube.etsi.mano.service.mon.repository.SubscriptionRepository;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
	private final SubscriptionRepository subscriptionRepository;

	public SubscriptionController(final SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	ResponseEntity<Iterable<MonSubscription>> list() {
		final Iterable<MonSubscription> res = subscriptionRepository.findAll();
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/{id}")
	ResponseEntity<Void> delete(@PathVariable("id") final UUID id) {
		subscriptionRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	ResponseEntity<Optional<MonSubscription>> findById(@PathVariable("id") final UUID id) {
		final Optional<MonSubscription> res = subscriptionRepository.findById(id);
		return ResponseEntity.ok(res);
	}
}
