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
package com.ubiqube.etsi.mano.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.exception.GenericException;

class VimTypeTest {

	@Test
	void test() {
		VimType vt = new VimType("REG", "NAME");
		assertEquals("REG.NAME", vt.toString());
		vt = new VimType("REG", "NAME", 1);
		assertEquals("REG.NAME.V_1", vt.toString());
		vt = new VimType("REG", "NAME", 1, 2);
		assertEquals("REG.NAME.V_1_2", vt.toString());
		assertThrows(GenericException.class, () -> VimType.of(""));
		assertEquals("REG.NAME", VimType.of("REG.NAME").toString());
		assertEquals("REG.NAME.V_1", VimType.of("REG.NAME.V_1").toString());
		assertEquals("REG.NAME.V_1_2", VimType.of("REG.NAME.V_1_2").toString());
	}

}
