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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.mapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

class DateTimeMappingTest {

	DateTimeMapping createService() {
		return new DateTimeMapping() {
			//
		};
	}

	@Test
	void testNull() {
		final DateTimeMapping srv = createService();
		assertNull(srv.toLocalDateTime(null));
		assertNull(srv.toOffsetDateTime(null));
	}

	@Test
	void testUri() {
		final DateTimeMapping srv = createService();
		assertNotNull(srv.toLocalDateTime(OffsetDateTime.now()));
		assertNotNull(srv.toOffsetDateTime(LocalDateTime.now()));
	}

}
