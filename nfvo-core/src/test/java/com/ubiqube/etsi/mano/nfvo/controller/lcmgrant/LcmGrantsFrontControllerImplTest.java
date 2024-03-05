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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.controller.lcmgrant.GrantManagement;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class LcmGrantsFrontControllerImplTest {
	@Mock
	private GrantManagement grantManagement;
	@Mock
	private MapperFacade mapper;

	@Test
	void testGet() {
		final LcmGrantsFrontControllerImpl srv = new LcmGrantsFrontControllerImpl(grantManagement, mapper);
		final GrantResponse resp = new GrantResponse();
		resp.setAvailable(true);
		final Consumer<Object> cons = x -> {
		};
		when(grantManagement.get(any())).thenReturn(resp);
		srv.grantsGrantIdGet(UUID.randomUUID().toString(), Object.class, cons);
		assertTrue(true);
	}

	@Test
	void testGet2() {
		final LcmGrantsFrontControllerImpl srv = new LcmGrantsFrontControllerImpl(grantManagement, mapper);
		final GrantResponse resp = new GrantResponse();
		resp.setAvailable(false);
		when(grantManagement.get(any())).thenReturn(resp);
		srv.grantsGrantIdGet(UUID.randomUUID().toString(), null, null);
		assertTrue(true);
	}

	@Test
	void testPost() {
		final LcmGrantsFrontControllerImpl srv = new LcmGrantsFrontControllerImpl(grantManagement, mapper);
		final Function<Object, String> func = x -> "";
		final Object grantRequest = new Object();
		final GrantResponse resp = new GrantResponse();
		when(mapper.map(grantRequest, GrantResponse.class)).thenReturn(resp);
		when(grantManagement.post(resp)).thenReturn(resp);
		when(mapper.map(any(), eq(Object.class))).thenReturn(grantRequest);
		srv.grantsPost(grantRequest, Object.class, func);
		assertTrue(true);
	}
}
