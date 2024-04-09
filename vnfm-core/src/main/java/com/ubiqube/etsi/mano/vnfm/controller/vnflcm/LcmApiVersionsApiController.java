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
package com.ubiqube.etsi.mano.vnfm.controller.vnflcm;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.ubiqube.etsi.mano.controller.EtsiImplementation;
import com.ubiqube.etsi.mano.model.ApiVersionInformation;
import com.ubiqube.etsi.mano.vnfm.service.mapping.ApiVersionInformationApiVersionsMapping;

@Controller
public class LcmApiVersionsApiController implements LcmApiVersionsApi {

	private final ApiVersionInformationApiVersionsMapping apiVersionInformationApiVersionsMapping;
	private final List<EtsiImplementation> implementations;

	public LcmApiVersionsApiController(final List<EtsiImplementation> implementations, final ApiVersionInformationApiVersionsMapping apiVersionInformationApiVersionsMapping) {
		this.apiVersionInformationApiVersionsMapping = apiVersionInformationApiVersionsMapping;
		this.implementations = implementations;
	}

	@Override
	public ResponseEntity<ApiVersionInformation> apiVersionsGet(final String version) {
		final ApiVersionInformation apiVersionInformation = new ApiVersionInformation();
		apiVersionInformation.setApiVersions(implementations.stream().map(apiVersionInformationApiVersionsMapping::map).toList());
		return ResponseEntity.ok(apiVersionInformation);
	}

	@Override
	public ResponseEntity<ApiVersionInformation> apiVersionsV1Get(final String version) {
		final ApiVersionInformation apiVersionInformation = new ApiVersionInformation();
		apiVersionInformation.setApiVersions(implementations.stream().map(apiVersionInformationApiVersionsMapping::map).toList());
		return ResponseEntity.ok(apiVersionInformation);
	}

}
