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
package com.ubiqube.etsi.mano.service.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URI;

import org.junit.jupiter.api.Test;

class StringToUriMappingTest {

	StringToUriMapping createService() {
		return new StringToUriMapping() {
			//
		};
	}

	@Test
	void testNull() {
		final StringToUriMapping srv = createService();
		assertNull(srv.map((String) null));
		assertNull(srv.map((URI) null));
	}

	@Test
	void testUri() {
		final StringToUriMapping srv = createService();
		final String strUri = "http://localhost/";
		final URI uri = URI.create(strUri);
		assertEquals(uri, srv.map("http://localhost/"));
		assertEquals(strUri, srv.map(uri));
	}
}
