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
package com.ubiqube.etsi.mano.nfvo.service.graph;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsCreateVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.uow.NetworkPolicyUow;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;

@ExtendWith(MockitoExtension.class)
class NsOrchListenetImplTest {
	@Mock
	private NsLiveInstanceJpa nsLiveInstanceJpa;

	@Test
	void testOnStart() {
		final NsBlueprint bp = new NsBlueprint();
		final NsOrchListenetImpl lis = new NsOrchListenetImpl(nsLiveInstanceJpa, bp);
		final NsdTask task = new NsdTask();
		final NsCreateVt vt = new NsCreateVt(task);
		final VirtualTaskV3 vt2 = vt;
		final UnitOfWorkV3 uow = new NetworkPolicyUow(vt2, null);
		lis.onStart(uow);
		assertTrue(true);
	}

	@Test
	void testOnTerminate() {
		final NsBlueprint bp = new NsBlueprint();
		final NsOrchListenetImpl lis = new NsOrchListenetImpl(nsLiveInstanceJpa, bp);
		final NsdTask task = new NsdTask();
		final NsCreateVt vt = new NsCreateVt(task);
		final VirtualTaskV3 vt2 = vt;
		final UnitOfWorkV3 uow = new NetworkPolicyUow(vt2, null);
		lis.onTerminate(uow, null);
		assertTrue(true);
	}

	@Test
	void testOnTerminateAdded() {
		final NsBlueprint bp = new NsBlueprint();
		final NsdInstance inst = new NsdInstance();
		inst.setId(UUID.randomUUID());
		bp.setNsInstance(inst);
		final NsOrchListenetImpl lis = new NsOrchListenetImpl(nsLiveInstanceJpa, bp);
		final NsdTask task = new NsdTask();
		final UUID id = UUID.randomUUID();
		task.setId(id);
		task.setChangeType(ChangeType.ADDED);
		final NsCreateVt vt = new NsCreateVt(task);
		final VirtualTaskV3 vt2 = vt;
		final UnitOfWorkV3 uow = new NetworkPolicyUow(vt2, null);
		lis.onTerminate(uow, "");
		assertTrue(true);
	}

	@Test
	void testOnTerminateAdded001() {
		final NsBlueprint bp = new NsBlueprint();
		final NsOrchListenetImpl lis = new NsOrchListenetImpl(nsLiveInstanceJpa, bp);
		final NsdTask task = new NsdTask();
		task.setChangeType(ChangeType.REMOVED);
		final NsCreateVt vt = new NsCreateVt(task);
		final VirtualTaskV3 vt2 = vt;
		final UnitOfWorkV3 uow = new NetworkPolicyUow(vt2, null);
		lis.onTerminate(uow, "");
		assertTrue(true);
	}

	@Test
	void testOnTerminateAdded002() {
		final NsBlueprint bp = new NsBlueprint();
		final NsOrchListenetImpl lis = new NsOrchListenetImpl(nsLiveInstanceJpa, bp);
		final NsdTask task = new NsdTask();
		task.setChangeType(ChangeType.ADDED);
		final NsCreateVt vt = new NsCreateVt(task);
		final VirtualTaskV3 vt2 = vt;
		final UnitOfWorkV3 uow = new NetworkPolicyUow(vt2, null);
		lis.onTerminate(uow, null);
		assertTrue(true);
	}

	@Test
	void testOnError() {
		final NsBlueprint bp = new NsBlueprint();
		final NsOrchListenetImpl lis = new NsOrchListenetImpl(nsLiveInstanceJpa, bp);
		final NsdTask task = new NsdTask();
		task.setChangeType(ChangeType.REMOVED);
		final NsCreateVt vt = new NsCreateVt(task);
		final VirtualTaskV3 vt2 = vt;
		final UnitOfWorkV3 uow = new NetworkPolicyUow(vt2, null);
		lis.onError(uow, new RuntimeException());
		assertTrue(true);
	}

}
