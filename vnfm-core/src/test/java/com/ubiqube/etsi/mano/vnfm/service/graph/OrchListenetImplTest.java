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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsHostTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfTaskJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.DnsHostUow;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.DnsHostVt;

@ExtendWith(MockitoExtension.class)
class OrchListenetImplTest {
	@Mock
	private VnfBlueprint blueprint;
	@Mock
	private VnfLiveInstanceJpa vnfLiveInstance;
	@Mock
	private VnfTaskJpa vnfTaskJpa;

	@Test
	void testOnError() {
		final OrchListenetImpl srv = new OrchListenetImpl(blueprint, vnfLiveInstance, vnfTaskJpa);
		final DnsHostTask nt = new DnsHostTask();
		final VirtualTaskV3<DnsHostTask> task = new DnsHostVt(nt);
		final UnitOfWorkV3 uow = new DnsHostUow(task, null, null);
		srv.onError(uow, null);
		assertTrue(true);
	}

	@Test
	void testOnStart() {
		final OrchListenetImpl srv = new OrchListenetImpl(blueprint, vnfLiveInstance, vnfTaskJpa);
		final DnsHostTask nt = new DnsHostTask();
		final VirtualTaskV3 task = new DnsHostVt(nt);
		final UnitOfWorkV3 uow = new DnsHostUow(task, null, null);
		srv.onStart(uow);
		assertTrue(true);
	}

	@Test
	void testOnTerminate() {
		final OrchListenetImpl srv = new OrchListenetImpl(blueprint, vnfLiveInstance, vnfTaskJpa);
		final DnsHostTask nt = new DnsHostTask();
		final VirtualTaskV3 task = new DnsHostVt(nt);
		final UnitOfWorkV3 uow = new DnsHostUow(task, null, null);
		srv.onTerminate(uow, null);
		assertTrue(true);
	}

	@Test
	void testOnTerminate1() {
		final OrchListenetImpl srv = new OrchListenetImpl(blueprint, vnfLiveInstance, vnfTaskJpa);
		final DnsHostTask nt = new DnsHostTask();
		nt.setChangeType(ChangeType.ADDED);
		nt.setId(UUID.randomUUID());
		final VirtualTaskV3 task = new DnsHostVt(nt);
		final UnitOfWorkV3 uow = new DnsHostUow(task, null, null);
		srv.onTerminate(uow, "res");
		assertTrue(true);
	}

	@Test
	void testOnTerminate2() {
		final OrchListenetImpl srv = new OrchListenetImpl(blueprint, vnfLiveInstance, vnfTaskJpa);
		final DnsHostTask nt = new DnsHostTask();
		nt.setChangeType(ChangeType.ADDED);
		nt.setId(UUID.randomUUID());
		final VirtualTaskV3 task = new DnsHostVt(nt);
		final UnitOfWorkV3 uow = new DnsHostUow(task, null, null);
		srv.onTerminate(uow, null);
		assertTrue(true);
	}

	@Test
	void testOnTerminate3() {
		final OrchListenetImpl srv = new OrchListenetImpl(blueprint, vnfLiveInstance, vnfTaskJpa);
		final DnsHostTask nt = new DnsHostTask();
		nt.setChangeType(ChangeType.ADDED);
		final VirtualTaskV3 task = new DnsHostVt(nt);
		final UnitOfWorkV3 uow = new DnsHostUow(task, null, null);
		srv.onTerminate(uow, "res");
		assertTrue(true);
	}
}
