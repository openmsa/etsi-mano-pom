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
package com.ubiqube.etsi.mano.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.model.VnfHealRequest.VnfHealRequestBuilder;
import com.ubiqube.etsi.mano.model.VnfScaleRequest.VnfScaleRequestBuilder;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest.VnfScaleToLevelRequestBuilder;

@SuppressWarnings("static-method")
class VnfScaleRequestTest {

	@Test
	void test() {
		final VnfScaleRequestBuilder builder = VnfScaleRequest.builder();
		builder.additionalParams(null)
				.aspectId(null)
				.instantiationLevelId(null)
				.numberOfSteps(null)
				.scaleInfo(null)
				.type(null);
		builder.toString();
		builder.hashCode();
		assertNotNull(builder.build());
	}

	@Test
	void testVnfHealRequest() {
		final VnfHealRequest req = VnfHealRequest.of("");
		req.toString();
		final VnfHealRequestBuilder b = VnfHealRequest.builder()
				.additionalParams(null)
				.cause(null);
		b.toString();
		assertNotNull(b.build());
	}

	@Test
	void testVnfScaleToLevelRequest() {
		final VnfScaleToLevelRequestBuilder b = VnfScaleToLevelRequest.builder()
				.additionalParams(null)
				.instantiationLevelId(null)
				.scaleInfo(null);
		b.toString();
		assertNotNull(b.build());
	}

	@Test
	void testProblemDetails() {
		final ProblemDetails p = new ProblemDetails(500, "");
		p.detail(null).instance(null).status(null).title(null).type(null);
		p.getDetail();
		p.getInstance();
		p.getTitle();
		p.getType();
		assertTrue(true);
	}

	@SuppressWarnings("static-method")
	@Test
	void testVnfInstantiate() {
		final VnfInstantiate v = new VnfInstantiate();
		v.setExtManagedVirtualLinks(null);
		v.setExtVirtualLinks(null);
		v.setFlavourId(null);
		v.setInstantiationLevelId(null);
		v.setLocalizationLanguage(null);
		v.setVimConnectionInfo(null);
		v.getExtManagedVirtualLinks();
		v.getExtVirtualLinks();
		v.getFlavourId();
		v.getInstantiationLevelId();
		v.getLocalizationLanguage();
		v.getVimConnectionInfo();
		assertTrue(true);
	}
}
