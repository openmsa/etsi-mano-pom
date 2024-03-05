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

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;

import jakarta.annotation.Nonnull;

@HttpExchange(url = "/metrics", accept = "application/json", contentType = "application/json")
public interface MetricsRemoteService {

	@GetExchange
	@Nonnull
	ResponseEntity<List<TelemetryMetricsResult>> searchApi(@RequestParam final MultiValueMap<String, String> params);

	@GetExchange("/{instance}/{subObject}")
	ResponseEntity<TelemetryMetricsResult> search(final @PathVariable("instance") String instance, final @PathVariable("subObject") String object);
}
