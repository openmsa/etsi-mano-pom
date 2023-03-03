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
package com.ubiqube.etsi.mano.em.client.vnfind;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.ubiqube.etsi.mano.em.v431.model.vnfind.VnfIndicator;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

@HttpExchange(url = "/vnfind/v1", accept = "application/json", contentType = "application/json")
public interface VnfIndRemoteService {
	@GetExchange("/indicators")
	List<VnfIndicator> indicatorsGet(@Valid @RequestParam(value = "filter", required = false) @Nullable final String filter, @Valid @RequestParam(value = "nextpage_opaque_marker", required = false) @Nullable final String nextpageOpaqueMarker);

	@GetExchange("indicators/{vnfInstanceId}")
	ResponseEntity<List<VnfIndicator>> indicatorsVnfInstanceIdGet(@PathVariable("vnfInstanceId") final String vnfInstanceId, @Valid @RequestParam(value = "filter", required = false) @Nullable final String filter, @Valid @RequestParam(value = "nextpage_opaque_marker", required = false) @Nullable final String nextpageOpaqueMarker);

	@GetExchange("/indicators/{vnfInstanceId}/{indicatorId}")
	ResponseEntity<VnfIndicator> indicatorsVnfInstanceIdIndicatorIdGet(@PathVariable("vnfInstanceId") final String vnfInstanceId, @PathVariable("indicatorId") final String indicatorId);

}
