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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.dto.VimConnectionRegistrationDto;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;

class VimConnectionInformationMappingTest {

	VimConnectionInformationMapping createService() {
		return Mappers.getMapper(VimConnectionInformationMapping.class);
	}

	@Test
	void testVimConnectionInfoDtoNull() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> res = srv.map((VimConnectionRegistrationDto) null);
		assertNull(res);
	}

	@Test
	void testVimConnectionInfoDto() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionRegistrationDto o = new VimConnectionRegistrationDto();
		o.setVimType("BAD");
		assertThrows(IllegalArgumentException.class, () -> srv.map(o));
	}

	@Test
	void testVimConnectionInfoDtoK8s() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionRegistrationDto o = new VimConnectionRegistrationDto();
		o.setVimType("UBINFV.CISM.V_1");
		final VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> res = srv.map(o);
		assertNotNull(res);
	}

	@Test
	void testVimConnectionInfoDtoOs1() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionRegistrationDto o = new VimConnectionRegistrationDto();
		o.setVimType("ETSINFV.OPENSTACK_KEYSTONE.V_3");
		final VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> res = srv.map(o);
		assertNotNull(res);
	}

	@Test
	void testVimConnectionInfo001() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> res = srv.map((VimConnectionInformation) null);
		assertNull(res);
	}

	@Test
	void testVimConnectionInfo002() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionInformation<InterfaceInfo, AccessInfo> o = new VimConnectionInformation<InterfaceInfo, AccessInfo>();
		o.setVimType("ETSINFV.OPENSTACK_KEYSTONE.V_3");
		final VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> res = srv.map(o);
		assertNotNull(res);
	}

	@Test
	void testVimConnectionInfo003() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionInformation<InterfaceInfo, AccessInfo> o = new VimConnectionInformation<InterfaceInfo, AccessInfo>();
		o.setVimType("UBINFV.CISM.V_1");
		final VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> res = srv.map(o);
		assertNotNull(res);
	}

	@Test
	void testMapUuid() {
		final VimConnectionInformationMapping srv = createService();
		final UUID res = srv.mapUuid(null);
		assertNull(res);
	}

	@Test
	void testMapUuidNotNull() {
		final VimConnectionInformationMapping srv = createService();
		final UUID res = srv.mapUuid("19ac766f-1c72-4e75-b58f-04f77c7b0e79");
		assertNotNull(res);
	}
}
