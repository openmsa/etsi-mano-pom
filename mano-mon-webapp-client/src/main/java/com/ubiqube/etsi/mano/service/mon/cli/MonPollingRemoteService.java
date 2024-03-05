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
package com.ubiqube.etsi.mano.service.mon.cli;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.dto.PollingJob;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

@HttpExchange(url = "/polling", accept = "application/json", contentType = "application/json")
public interface MonPollingRemoteService {
	@PostExchange
	ResponseEntity<BatchPollingJob> register(final @Valid @Nonnull @RequestBody PollingJob pj);

	@GetExchange
	ResponseEntity<List<BatchPollingJob>> list();

	@DeleteExchange("/{id}")
	ResponseEntity<Void> delete(@PathVariable("id") @Nonnull final UUID id);

}
