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
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ExtCpInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;

@ExtendWith(MockitoExtension.class)
class ExtCpExtractorTest {
	@Mock
	private VnfPackageJpa vnfPackageJpa;

	ExtCpExtractor createService() {
		return new ExtCpExtractor(vnfPackageJpa);
	}

	VnfLiveInstance createDummyVli() {
		final VnfLiveInstance vli = new VnfLiveInstance();
		final VnfTask t = new HelmTask();
		vli.setTask(t);
		return vli;
	}

	@Test
	void test() {
		final ExtCpExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		srv.extract(null, params, List.of());
		final Set<ExtCpInfo> mon = params.getExtCpInfo();
		assertNotNull(mon);
		assertTrue(mon.isEmpty());
	}

	@Test
	void testNotPointingOut() {
		final ExtCpExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		final VnfLiveInstance vli0 = new VnfLiveInstance();
		vli0.setTask(TestFactory.createVnfPortTask());
		final VnfLiveInstance vli1 = createDummyVli();
		final VnfInstance instance = TestFactory.createVnfInstance();
		srv.extract(instance, params, List.of(vli0, vli1));
		final Set<ExtCpInfo> mon = params.getExtCpInfo();
		assertNotNull(mon);
		assertEquals(0, mon.size());
	}

	@Test
	void testPointingOut() {
		final ExtCpExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		final VnfLiveInstance vli0 = new VnfLiveInstance();
		vli0.setId(UUID.randomUUID());
		vli0.setTask(TestFactory.createVnfPortTask());
		final VnfLiveInstance vli1 = createDummyVli();
		final VnfInstance instance = TestFactory.createVnfInstance();
		//
		final ListKeyPair lp = new ListKeyPair("port01", 0);
		instance.getVnfPkg().setVirtualLinks(Set.of(lp));
		final VnfPackage pkg = new VnfPackage();
		when(vnfPackageJpa.findById(any())).thenReturn(Optional.of(pkg));
		srv.extract(instance, params, List.of(vli0, vli1));
		final Set<ExtCpInfo> mon = params.getExtCpInfo();
		assertNotNull(mon);
		assertEquals(1, mon.size());
	}

	@Test
	void testPointingOut2() {
		final ExtCpExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		final VnfLiveInstance vli0 = new VnfLiveInstance();
		vli0.setId(UUID.randomUUID());
		final VnfPortTask t = TestFactory.createVnfPortTask();
		t.getVnfLinkPort().setToscaName("port01");
		vli0.setTask(t);
		final VnfLiveInstance vli1 = createDummyVli();
		final VnfInstance instance = TestFactory.createVnfInstance();
		//
		final ListKeyPair lp = new ListKeyPair("port01", 0);
		instance.getVnfPkg().setVirtualLinks(Set.of(lp));
		when(vnfPackageJpa.findById(any())).thenReturn(Optional.of(instance.getVnfPkg()));
		srv.extract(instance, params, List.of(vli0, vli1));
		final Set<ExtCpInfo> mon = params.getExtCpInfo();
		assertNotNull(mon);
		assertEquals(1, mon.size());
	}

	@Test
	void testPointingOutNoVl() {
		final ExtCpExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		final VnfLiveInstance vli0 = new VnfLiveInstance();
		vli0.setId(UUID.randomUUID());
		final VnfPortTask t = TestFactory.createVnfPortTask();
		t.getVnfLinkPort().setVirtualLink("dd");
		vli0.setTask(t);
		final VnfLiveInstance vli1 = createDummyVli();
		final VnfInstance instance = TestFactory.createVnfInstance();
		//
		final ListKeyPair lp = new ListKeyPair("port01", 0);
		instance.getVnfPkg().setVirtualLinks(Set.of(lp));
		srv.extract(instance, params, List.of(vli0, vli1));
		final Set<ExtCpInfo> mon = params.getExtCpInfo();
		assertNotNull(mon);
		assertEquals(1, mon.size());
	}
}
