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
package com.ubiqube.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.mapper.OffsetDateTimeToDateConverter;
import com.ubiqube.etsi.mano.mapper.UuidConverter;

@SuppressWarnings("static-method")
class ConverterTest {

	@Test
	void testUuidConverter() {
		final UuidConverter conv = new UuidConverter();
		final UUID uuid = UUID.randomUUID();
		final String res = conv.convertFrom(uuid, null, null);
		assertEquals(uuid.toString(), res);
		final UUID rUuid = conv.convertTo(res, null, null);
		assertEquals(uuid, rUuid);
	}

	@Test
	void testOffsetDateTime() {
		final OffsetDateTimeToDateConverter conv = new OffsetDateTimeToDateConverter();
		final Date date = new Date();
		final OffsetDateTime offsetDateTime = date.toInstant().atOffset(ZoneOffset.UTC);
		final OffsetDateTime res = conv.convertFrom(date, null, null);
		assertEquals(offsetDateTime, res);
		final Date rDate = conv.convertTo(offsetDateTime, null, null);
		assertEquals(date, rDate);

	}
}
