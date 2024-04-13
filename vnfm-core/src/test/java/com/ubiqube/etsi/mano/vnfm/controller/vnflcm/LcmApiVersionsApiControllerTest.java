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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.controller.EtsiImplementation;
import com.ubiqube.etsi.mano.vnfm.service.mapping.ApiVersionInformationApiVersionsMapping;

class LcmApiVersionsApiControllerTest {
	ApiVersionInformationApiVersionsMapping apiVersionInformationApiVersionsMapping = Mappers.getMapper(ApiVersionInformationApiVersionsMapping.class);

	@Test
	void testApiVersionsGet() {
		final List<EtsiImplementation> impls = List.of();
		final LcmApiVersionsApiController srv = createService(impls);
		srv.apiVersionsGet(null);
		assertTrue(true);
	}

	private LcmApiVersionsApiController createService(final List<EtsiImplementation> impls) {
		return new LcmApiVersionsApiController(impls, apiVersionInformationApiVersionsMapping);
	}

	@Test
	void testApiVersionsV1Get() {
		final List<EtsiImplementation> impls = List.of();
		final LcmApiVersionsApiController srv = createService(impls);
		srv.apiVersionsV1Get(null);
		assertTrue(true);
	}
}
