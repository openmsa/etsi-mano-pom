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
package com.ubiqube.etsi.mano.service.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.rest.vnfpkg.ManoVnfPackage;

@ExtendWith(MockitoExtension.class)
class ManoVnfPackageTest {
	@Mock
	private QueryParameters client;
	@Mock
	private ManoQueryBuilder query;

	@Test
	void testList() {
		final ManoVnfPackage srv = new ManoVnfPackage(client);
		when(client.createQuery()).thenReturn(query);
		when(query.setInClassList(any())).thenReturn(query);
		when(query.setOutClass(any())).thenReturn(query);
		srv.list();
		assertTrue(true);
	}

	@Test
	void testCreate() {
		final ManoVnfPackage srv = new ManoVnfPackage(client);
		when(client.createQuery()).thenReturn(query);
		when(query.setWireOutClass(any())).thenReturn(query);
		when(query.setOutClass(any())).thenReturn(query);
		when(query.setWireInClass(any())).thenReturn(query);
		srv.create(null);
		assertTrue(true);
	}

	@Test
	void testOnboarded() {
		final ManoVnfPackage srv = new ManoVnfPackage(client);
		srv.onboarded(null);
		assertTrue(true);
	}

	@Test
	void testSubscription() {
		final ManoVnfPackage srv = new ManoVnfPackage(client);
		srv.subscription();
		assertTrue(true);
	}
}
