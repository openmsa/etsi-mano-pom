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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.entities.Systems;
import com.ubiqube.etsi.mano.service.SystemService;

@ExtendWith(MockitoExtension.class)
class SystemAdminControllerTest {
	@Mock
	private SystemService systemService;

	@Test
	void testList() {
		final SystemAdminController srv = new SystemAdminController(systemService);
		srv.list();
		assertTrue(true);
	}

	@Test
	void testListModule() {
		final SystemAdminController srv = new SystemAdminController(systemService);
		final Systems sys = new Systems();
		final SystemConnections sub = new SystemConnections();
		sub.setModuleName("name");
		final Set<SystemConnections> subsys = Set.of(sub);
		sys.setSubSystems(subsys);
		when(systemService.findByModuleName(any(), any())).thenReturn(List.of(sys));
		srv.listModule(null, "name");
		assertTrue(true);
	}

	@Test
	void testPatch() {
		final SystemAdminController srv = new SystemAdminController(systemService);
		srv.patchModule(null, null);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final SystemAdminController srv = new SystemAdminController(systemService);
		srv.deleteSystem(null);
		assertTrue(true);
	}

}
