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
package com.ubiqube.etsi.mano.em.client.vnfconfig;

import java.time.OffsetDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;

import com.ubiqube.etsi.mano.em.v431.model.vnfconfig.VnfConfigModifications;
import com.ubiqube.etsi.mano.em.v431.model.vnfconfig.VnfConfiguration;

@HttpExchange(url = "/vnfind/v1", accept = "application/json", contentType = "application/json")
public interface ConfigurationRemoteService {

	@GetExchange("/configuration")
	ResponseEntity<VnfConfiguration> configurationGet();

	@PatchExchange("/configuration")
	ResponseEntity<VnfConfigModifications> configurationPatch(
			@RequestBody final VnfConfigModifications body,
			@RequestHeader(value = "If-Unmodified-Since", required = false) final OffsetDateTime ifUnmodifiedSince,
			@RequestHeader(value = "If-Match", required = false) final String ifMatch);

}
