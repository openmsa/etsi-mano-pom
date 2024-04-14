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
package com.ubiqube.etsi.mano.nfvo.controller.lcmgrant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.GrantInterface;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.service.mapping.GrantInterfaceMapping;
import com.ubiqube.etsi.mano.service.GrantService;
import com.ubiqube.etsi.mano.service.event.EventManager;

@ExtendWith(MockitoExtension.class)
class GrantMngtSol005Test {
	@Mock
	private GrantService grantJpa;
	private final GrantInterfaceMapping mapper = Mappers.getMapper(GrantInterfaceMapping.class);
	@Mock
	private EventManager eventManager;

	@Test
	void testGet() {
		final GrantMngtSol005 srv = createService();
		final GrantResponse grant = new GrantResponse();
		when(grantJpa.findById(any())).thenReturn(Optional.of(grant));
		srv.get(UUID.randomUUID());
		assertTrue(true);
	}

	private GrantMngtSol005 createService() {
		return new GrantMngtSol005(grantJpa, mapper, eventManager);
	}

	@Test
	void testGetFail() {
		final GrantMngtSol005 srv = createService();
		final UUID uuid = UUID.randomUUID();
		assertThrows(NotFoundException.class, () -> srv.get(uuid));
		assertTrue(true);
	}

	@Test
	void test() {
		final GrantMngtSol005 srv = createService();
		final GrantInterface grantRequest = new GrantResponse();
		final GrantResponse grant = new GrantResponse();
		when(grantJpa.save(grant)).thenReturn(grant);
		srv.post(grantRequest);
		assertTrue(true);
	}
}
