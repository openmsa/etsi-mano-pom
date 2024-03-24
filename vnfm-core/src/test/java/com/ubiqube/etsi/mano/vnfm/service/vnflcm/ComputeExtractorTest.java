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
package com.ubiqube.etsi.mano.vnfm.service.vnflcm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VimResource;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfcResourceInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class ComputeExtractorTest {
	@Mock
	private MapperFacade mapper;

	ComputeExtractor createService() {
		return new ComputeExtractor(mapper);
	}

	VnfLiveInstance createDummyVli() {
		final VnfLiveInstance vli = new VnfLiveInstance();
		final VnfTask t = new HelmTask();
		vli.setTask(t);
		return vli;
	}

	@Test
	void test() {
		final ComputeExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		srv.extract(null, params, List.of());
		final Set<VnfcResourceInfoEntity> mon = params.getVnfcResourceInfo();
		assertNotNull(mon);
		assertTrue(mon.isEmpty());
	}

	@Test
	void testNotPointingOut() {
		final ComputeExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		final VnfLiveInstance vli0 = TestFactory.createLiveCompute();
		final VnfLiveInstance vli1 = createDummyVli();
		final VnfInstance instance = TestFactory.createVnfInstance();
		final VnfcResourceInfoEntity vnfRie = new VnfcResourceInfoEntity();
		when(mapper.map(any(), eq(VnfcResourceInfoEntity.class))).thenReturn(vnfRie);
		final VimResource vr = new VimResource();
		when(mapper.map(any(), eq(VimResource.class))).thenReturn(vr);
		final VnfPortTask p = TestFactory.createVnfPortTask();
		final VnfLiveInstance vli2 = new VnfLiveInstance();
		vli2.setId(UUID.randomUUID());
		vli2.setTask(p);
		srv.extract(instance, params, List.of(vli0, vli1, vli2));
		final Set<VnfcResourceInfoEntity> mon = params.getVnfcResourceInfo();
		assertNotNull(mon);
		assertEquals(1, mon.size());
	}

	@Test
	void testSubnetwork() {
		final ComputeExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		final VnfLiveInstance vli0 = TestFactory.createLiveCompute();
		final VnfLiveInstance vli1 = createDummyVli();
		final VnfInstance instance = TestFactory.createVnfInstance();
		final VnfcResourceInfoEntity vnfRie = new VnfcResourceInfoEntity();
		when(mapper.map(any(), eq(VnfcResourceInfoEntity.class))).thenReturn(vnfRie);
		final VimResource vr = new VimResource();
		when(mapper.map(any(), eq(VimResource.class))).thenReturn(vr);
		final VnfPortTask p = TestFactory.createVnfPortTask();
		final VnfLiveInstance vli2 = new VnfLiveInstance();
		vli2.setId(UUID.randomUUID());
		vli2.setTask(p);
		final VnfLiveInstance vli3 = new VnfLiveInstance();
		vli3.setTask(TestFactory.createSubNetwork());
		vli3.setResourceId("subnet01");
		srv.extract(instance, params, List.of(vli0, vli1, vli2, vli3));
		final Set<VnfcResourceInfoEntity> mon = params.getVnfcResourceInfo();
		assertNotNull(mon);
		assertEquals(1, mon.size());
	}

}
