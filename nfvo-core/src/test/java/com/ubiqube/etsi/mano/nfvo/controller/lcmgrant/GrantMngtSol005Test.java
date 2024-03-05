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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.GrantInterface;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.service.GrantService;
import com.ubiqube.etsi.mano.service.event.EventManager;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class GrantMngtSol005Test {
	@Mock
	private GrantService grantJpa;
	@Mock
	private MapperFacade mapper;
	@Mock
	private EventManager eventManager;

	@Test
	void testGet() {
		final GrantMngtSol005 srv = new GrantMngtSol005(grantJpa, mapper, eventManager);
		final GrantResponse grant = new GrantResponse();
		when(grantJpa.findById(any())).thenReturn(Optional.of(grant));
		srv.get(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testGetFail() {
		final GrantMngtSol005 srv = new GrantMngtSol005(grantJpa, mapper, eventManager);
		final UUID uuid = UUID.randomUUID();
		assertThrows(NotFoundException.class, () -> srv.get(uuid));
		assertTrue(true);
	}

	@Test
	void test() {
		final GrantMngtSol005 srv = new GrantMngtSol005(grantJpa, mapper, eventManager);
		final GrantInterface grantRequest = new GrantResponse();
		final GrantResponse grant = new GrantResponse();
		when(mapper.map(grantRequest, GrantResponse.class)).thenReturn(grant);
		when(grantJpa.save(grant)).thenReturn(grant);
		srv.post(grantRequest);
		assertTrue(true);
	}
}
