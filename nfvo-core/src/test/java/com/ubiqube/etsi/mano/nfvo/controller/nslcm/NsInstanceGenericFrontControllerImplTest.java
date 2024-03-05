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
package com.ubiqube.etsi.mano.nfvo.controller.nslcm;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.dto.CreateNsInstance;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class NsInstanceGenericFrontControllerImplTest {
	@Mock
	private MapperFacade mapper;
	@Mock
	private NsInstanceControllerService instanceController;
	@Mock
	private NsInstanceController nsLcmController;

	@Test
	void testSearch() {
		final NsInstanceGenericFrontControllerImpl srv = new NsInstanceGenericFrontControllerImpl(mapper, instanceController, nsLcmController);
		srv.search(null, null, null, null);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final NsInstanceGenericFrontControllerImpl srv = new NsInstanceGenericFrontControllerImpl(mapper, instanceController, nsLcmController);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		srv.delete(strId);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final NsInstanceGenericFrontControllerImpl srv = new NsInstanceGenericFrontControllerImpl(mapper, instanceController, nsLcmController);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		srv.findById(strId, getClass(), t -> {
		});
		assertTrue(true);
	}

	@Test
	void testHeal() {
		final NsInstanceGenericFrontControllerImpl srv = new NsInstanceGenericFrontControllerImpl(mapper, instanceController, nsLcmController);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		srv.heal(strId, "req", t -> "");
		assertTrue(true);
	}

	@Test
	void testInstantiate() {
		final NsInstanceGenericFrontControllerImpl srv = new NsInstanceGenericFrontControllerImpl(mapper, instanceController, nsLcmController);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		srv.instantiate(strId, "req", t -> "");
		assertTrue(true);
	}

	@Test
	void testScale() {
		final NsInstanceGenericFrontControllerImpl srv = new NsInstanceGenericFrontControllerImpl(mapper, instanceController, nsLcmController);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		srv.scale(strId, "req", t -> "");
		assertTrue(true);
	}

	@Test
	void testTerminate() {
		final NsInstanceGenericFrontControllerImpl srv = new NsInstanceGenericFrontControllerImpl(mapper, instanceController, nsLcmController);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		srv.terminate(strId, "req", t -> "");
		assertTrue(true);
	}

	@Test
	void testUpdate() {
		final NsInstanceGenericFrontControllerImpl srv = new NsInstanceGenericFrontControllerImpl(mapper, instanceController, nsLcmController);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		srv.update(strId, "req", t -> "");
		assertTrue(true);
	}

	@Test
	void testCreate() {
		final NsInstanceGenericFrontControllerImpl srv = new NsInstanceGenericFrontControllerImpl(mapper, instanceController, nsLcmController);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		final CreateNsInstance value = new CreateNsInstance();
		value.setNsdId(strId);
		when(mapper.map(any(), eq(CreateNsInstance.class))).thenReturn(value);
		srv.create("req", getClass(), t -> {
		}, t -> "");
		assertTrue(true);
	}

}
