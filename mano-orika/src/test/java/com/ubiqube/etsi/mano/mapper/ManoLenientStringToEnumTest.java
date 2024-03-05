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
package com.ubiqube.etsi.mano.mapper;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;

import com.ubiqube.etsi.mano.mapper.object.FilterEnum;

class ManoLenientStringToEnumTest {

	@SuppressWarnings("static-method")
	@Test
	void test() {
		final ManoLenientStringToEnum srv = new ManoLenientStringToEnum();
		final Converter<String, FilterEnum> ret = srv.getConverter(FilterEnum.class);
		ret.convert("");
		ret.convert("STARTED");
		ret.convert("stopped");
		assertThrows(IllegalArgumentException.class, () -> ret.convert("Fail"));
	}

}
