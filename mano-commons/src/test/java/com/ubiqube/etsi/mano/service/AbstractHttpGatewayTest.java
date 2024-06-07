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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.exception.GenericException;

/**
 *
 * @author olivier
 *
 */
class AbstractHttpGatewayTest {

	@Test
	void testName() {
		Assertions.assertThrows(GenericException.class, HttpGatewayBad::new);
	}

	@Test
	void testName002() {
		final HttpGatewayGood res = new HttpGatewayGood();
		final Object r2 = res.getUrlFor(ApiVersionType.SOL003_GRANT);
		assertNotNull(r2);
		final Optional<String> r3 = res.getHeaderVersion(ApiVersionType.SOL003_GRANT);
		assertEquals("1.4.0", r3.get());
	}

}
