/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.alarm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Transform;

@SuppressWarnings("static-method")
class JsonTest {

	@Test
	void testName() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final Transform obj = new Transform();
		obj.setParameters(Map.of("val1", "k1", "k2", "v2"));
		final String str = mapper.writeValueAsString(obj);
		System.out.println(str);
		assertTrue(true);
	}
}
