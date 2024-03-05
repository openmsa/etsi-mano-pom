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
package com.ubiqube.etsi.mano.nfvo.service.plan.uow;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.ServiceInstanceVt;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.tf.ContrailException;
import com.ubiqube.etsi.mano.tf.entities.ServiceInstanceTask;

@ExtendWith(MockitoExtension.class)
class ServiceInstanceUowTest {
	@Mock
	private Context3d context;

	@Test
	void testExecute() {
		final SystemConnections connection = TestUowFactory.createSystemConnections();
		final ServiceInstanceTask task = new ServiceInstanceTask();
		task.setInstanceId(UUID.randomUUID());
		final CpPair pair = new CpPair();
		task.setCpPorts(pair);
		final VirtualTaskV3<ServiceInstanceTask> vt = new ServiceInstanceVt(task);
		final ServiceInstanceUow si = new ServiceInstanceUow(vt, connection);
		assertThrows(ContrailException.class, () -> si.execute(context));
	}

	@Test
	void testRollback() {
		final SystemConnections connection = TestUowFactory.createSystemConnections();
		final ServiceInstanceTask task = new ServiceInstanceTask();
		final VirtualTaskV3<ServiceInstanceTask> vt = new ServiceInstanceVt(task);
		final ServiceInstanceUow si = new ServiceInstanceUow(vt, connection);
		assertThrows(ContrailException.class, () -> si.rollback(context));
	}

}
