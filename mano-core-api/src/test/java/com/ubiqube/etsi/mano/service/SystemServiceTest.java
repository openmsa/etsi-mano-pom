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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.jpa.SysConnectionJpa;
import com.ubiqube.etsi.mano.jpa.SystemsJpa;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.service.mapping.SystemConnectionsMapping;

@ExtendWith(MockitoExtension.class)
class SystemServiceTest {
	private final SystemConnectionsMapping systemConnectionsMapping = Mappers.getMapper(SystemConnectionsMapping.class);
	@Mock
	private SystemsJpa systemJpa;
	@Mock
	private Patcher patcher;
	@Mock
	private SysConnectionJpa connectionJpa;
	@Mock
	ResourceTypeConverter resourceTypeConverter;

	@Test
	void testName() throws Exception {
		final UUID vimId = UUID.randomUUID();
		final SystemService ss = new SystemService(systemJpa, patcher, connectionJpa, systemConnectionsMapping, resourceTypeConverter);
		final SystemConnections sc = new SystemConnections();
		sc.setVimId(vimId.toString());
		sc.setVimType("OPENSTACK_V3");
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		vimConn.setVimId(vimId.toString());
		vimConn.setVimType("OPENSTACK_V3");
		final InterfaceInfo ii = new InterfaceInfo();
		ii.setSdnEndpoint("http://");
		vimConn.setInterfaceInfo(ii);
		ss.registerVim(vimConn);
		assertTrue(true);
	}
}
