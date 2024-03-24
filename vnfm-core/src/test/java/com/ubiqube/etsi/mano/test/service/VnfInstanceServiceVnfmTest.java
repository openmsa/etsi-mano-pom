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
package com.ubiqube.etsi.mano.test.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;
import com.ubiqube.etsi.mano.test.MapperService;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;
import com.ubiqube.etsi.mano.vnfm.service.graph.DefaultVduNamingStrategy;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class VnfInstanceServiceVnfmTest {

	@Mock
	private VnfInstanceJpa vnfInstanceJpa;
	@Mock
	private VnfLiveInstanceJpa vnfLiveInstanceJpa;
	private final MapperFacade mapper;
	private final DefaultVduNamingStrategy namingStrategy;
	@Mock
	private VnfPackageJpa vnfPackageJpa;

	public VnfInstanceServiceVnfmTest() {
		mapper = MapperService.getInstance().getMapper();
		namingStrategy = new DefaultVduNamingStrategy();
	}

	@Test
	void testFindById() {
		final VnfInstanceServiceVnfm op = createServicce();
		final UUID id = UUID.randomUUID();
		final VnfInstance instance = TestFactory.createVnfInstance();
		final Optional<VnfInstance> vnfInst = Optional.of(instance);
		when(vnfInstanceJpa.findById(id)).thenReturn(vnfInst);
		final VnfInstance res = op.findById(id);
		assertNotNull(res);
	}

	@Test
	void testFindByIdWithCompute01() {
		final VnfInstanceServiceVnfm op = createServicce();
		final UUID id = UUID.randomUUID();
		final VnfInstance instance = TestFactory.createVnfInstance();
		//
		final ListKeyPair lp = new ListKeyPair("comp01", 0);
		instance.getVnfPkg().setVirtualLinks(Set.of(lp));
		//
		final VnfLiveInstance liveCompute = new VnfLiveInstance();
		liveCompute.setId(id);
		final ComputeTask task = new ComputeTask();
		final VnfCompute vnfCompute = new VnfCompute();
		final VnfLinkPort port = new VnfLinkPort();
		port.setToscaName("port01");
		vnfCompute.setPorts(Set.of(port));
		task.setVnfCompute(vnfCompute);
		liveCompute.setTask(task);
		//
		final VnfLiveInstance liveVl = new VnfLiveInstance();
		liveVl.setId(UUID.randomUUID());
		liveVl.setTask(TestFactory.createVnfPortTask());
		//
		final VnfLiveInstance liveSubNet = new VnfLiveInstance();
		liveSubNet.setId(UUID.randomUUID());
		liveSubNet.setResourceId("subnet01");
		liveSubNet.setTask(TestFactory.createSubNetwork());
		//
		final Optional<VnfInstance> vnfInst = Optional.of(instance);
		when(vnfInstanceJpa.findById(id)).thenReturn(vnfInst);
		final Optional<VnfPackage> optPkg = Optional.of(instance.getVnfPkg());
		// when(vnfPackageJpa.findById(instance.getVnfPkg().getId())).thenReturn(optPkg);
		final VnfInstance res = op.findById(id);
		assertNotNull(res);
	}

	private VnfInstanceServiceVnfm createServicce() {
		return new VnfInstanceServiceVnfm(vnfInstanceJpa, vnfLiveInstanceJpa);
	}
}
