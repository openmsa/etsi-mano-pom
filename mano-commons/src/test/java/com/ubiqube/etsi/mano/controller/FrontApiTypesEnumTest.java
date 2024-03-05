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
package com.ubiqube.etsi.mano.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class FrontApiTypesEnumTest {

	@Test
	void testNullInput() {
		final FrontApiTypesEnum en = FrontApiTypesEnum.fromValue(null);
		assertNull(en);
	}

	@Test
	void testBadInput() {
		final FrontApiTypesEnum en = FrontApiTypesEnum.fromValue("bad");
		assertNull(en);
	}

	@Test
	void testCorrectInput() {
		final FrontApiTypesEnum en = FrontApiTypesEnum.fromValue("SOL003");
		assertNotNull(en);
		en.toString();
		en.value();
	}

}
