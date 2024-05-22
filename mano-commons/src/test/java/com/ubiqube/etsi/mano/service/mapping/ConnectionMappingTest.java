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

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;

import uk.co.jemos.podam.api.PodamFactoryImpl;

class ConnectionMappingTest {
	ConnectionMapping mapper = Mappers.getMapper(ConnectionMapping.class);
	private final PodamFactoryImpl podam;

	public ConnectionMappingTest() {
		this.podam = new PodamFactoryImpl();
	}

	@Test
	void testInterfaceInfo() {
		final InterfaceInfo ai = podam.manufacturePojo(InterfaceInfo.class);
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testInterfaceInfoNull() {
		final Map<String, String> res = mapper.map((InterfaceInfo) null);
		assertNotNull(res);
	}

	@Test
	void testAccessInfo() {
		final AccessInfo ai = podam.manufacturePojo(AccessInfo.class);
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testAccessInfoNull() {
		final Map<String, String> res = mapper.map((AccessInfo) null);
		assertNotNull(res);
	}
}
