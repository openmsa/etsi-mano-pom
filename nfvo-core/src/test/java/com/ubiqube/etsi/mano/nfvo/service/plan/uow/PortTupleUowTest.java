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
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.PortTupleVt;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceInstanceNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.tf.ContrailException;
import com.ubiqube.etsi.mano.tf.entities.PortTupleTask;

@ExtendWith(MockitoExtension.class)
class PortTupleUowTest {
	@Mock
	private Context3d ctx;

	@Test
	void testExecute() {
		final SystemConnections connection = TestUowFactory.createSystemConnections();
		final PortTupleTask task = new PortTupleTask();
		task.setInstanceId(UUID.randomUUID());
		task.setServiceInstanceName("name");
		final VirtualTaskV3<PortTupleTask> vt = new PortTupleVt(task);
		when(ctx.getParent(ServiceInstanceNode.class, task.getServiceInstanceName())).thenReturn(List.of("bce127fc-c56f-11ed-a99b-c8f750509d3b"));
		final PortTupleUow pt = new PortTupleUow(vt, connection);
		assertThrows(ContrailException.class, () -> pt.execute(ctx));
	}

	@Test
	void testRollback() {
		final SystemConnections connection = TestUowFactory.createSystemConnections();
		final PortTupleTask task = new PortTupleTask();
		task.setInstanceId(UUID.randomUUID());
		task.setServiceInstanceName("name");
		final VirtualTaskV3<PortTupleTask> vt = new PortTupleVt(task);
		final PortTupleUow pt = new PortTupleUow(vt, connection);
		assertThrows(ContrailException.class, () -> pt.rollback(ctx));
	}

}
