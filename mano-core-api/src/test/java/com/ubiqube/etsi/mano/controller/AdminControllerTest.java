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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.GrantService;
import com.ubiqube.etsi.mano.service.event.EventManager;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
	@Mock
	private EventManager eventManager;
	@Mock
	private GrantService grantJpa;

	@Test
	void testDeleteAllGrant() {
		final AdminController srv = new AdminController(eventManager, grantJpa);
		srv.deleteAllGrant();
		assertTrue(true);
	}

	@Test
	void testDeleteAllGrant2() {
		final GrantResponse g1 = new GrantResponse();
		final Iterable<GrantResponse> ite = List.of(g1);
		when(grantJpa.findAll()).thenReturn(ite);
		final AdminController srv = new AdminController(eventManager, grantJpa);
		srv.deleteAllGrant();
		assertTrue(true);
	}

	@Test
	void testDeleteAllGrant3() {
		final GrantResponse g1 = new GrantResponse();
		final Iterable<GrantResponse> ite = List.of(g1);
		when(grantJpa.findAll()).thenReturn(ite);
		doThrow(new GenericException("")).when(grantJpa).delete(any());
		final AdminController srv = new AdminController(eventManager, grantJpa);
		srv.deleteAllGrant();
		assertTrue(true);
	}

	@Test
	void testSendEvent() {
		final AdminController srv = new AdminController(eventManager, grantJpa);
		srv.sendEvent(null, null);
		assertTrue(true);
	}

	@Test
	void testSink() {
		final AdminController srv = new AdminController(eventManager, grantJpa);
		srv.sink();
		assertTrue(true);
	}

	@Test
	void testSinkPost() {
		final AdminController srv = new AdminController(eventManager, grantJpa);
		srv.sinkPost(null);
		assertTrue(true);
	}

	@Test
	void testWhoAmi() {
		final SecurityContext sc = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(sc);
		final Authentication auth = Mockito.mock(Authentication.class);
		when(auth.getPrincipal()).thenReturn("p");
		when(auth.getAuthorities()).thenReturn(List.of());
		when(auth.getDetails()).thenReturn("p");
		when(sc.getAuthentication()).thenReturn(auth);
		final AdminController srv = new AdminController(eventManager, grantJpa);
		srv.whoami();
		assertTrue(true);
	}

}
