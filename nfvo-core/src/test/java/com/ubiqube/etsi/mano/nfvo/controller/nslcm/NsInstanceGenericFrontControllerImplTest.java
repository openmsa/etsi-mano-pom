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

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.dto.CreateNsInstance;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstantiate;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateRequest;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsHeal;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScale;

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
		final NsInstanceGenericFrontControllerImpl srv = createService();
		srv.search(null, null, null, null);
		assertTrue(true);
	}

	private NsInstanceGenericFrontControllerImpl createService() {
		return new NsInstanceGenericFrontControllerImpl(instanceController, nsLcmController);
	}

	@Test
	void testDelete() {
		final NsInstanceGenericFrontControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		srv.delete(strId);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final NsInstanceGenericFrontControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		srv.findById(strId, x -> "", t -> {
		});
		assertTrue(true);
	}

	@Test
	void testHeal() {
		final NsInstanceGenericFrontControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		final NsHeal req = new NsHeal();
		srv.heal(strId, req, t -> "");
		assertTrue(true);
	}

	@Test
	void testInstantiate() {
		final NsInstanceGenericFrontControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		final NsInstantiate req = new NsInstantiate();
		srv.instantiate(strId, req, t -> "");
		assertTrue(true);
	}

	@Test
	void testScale() {
		final NsInstanceGenericFrontControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		final NsScale req = new NsScale();
		srv.scale(strId, req, t -> "");
		assertTrue(true);
	}

	@Test
	void testTerminate() {
		final NsInstanceGenericFrontControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		final Object req = "";
		srv.terminate(strId, req, t -> "");
		assertTrue(true);
	}

	@Test
	void testUpdate() {
		final NsInstanceGenericFrontControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		final UpdateRequest req = new UpdateRequest();
		srv.update(strId, req, t -> "");
		assertTrue(true);
	}

	@Test
	void testCreate() {
		final NsInstanceGenericFrontControllerImpl srv = createService();
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		final CreateNsInstance value = new CreateNsInstance();
		value.setNsdId(strId);
		srv.create(value, x -> "", t -> {
		}, t -> "");
		assertTrue(true);
	}

}
