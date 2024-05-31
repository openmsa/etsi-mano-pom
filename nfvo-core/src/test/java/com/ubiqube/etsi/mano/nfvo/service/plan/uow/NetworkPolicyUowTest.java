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

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NetworkPolicyVt;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceInstanceNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.tf.ContrailException;
import com.ubiqube.etsi.mano.tf.entities.NetworkPolicyTask;

@ExtendWith(MockitoExtension.class)
class NetworkPolicyUowTest {
	private final SystemConnections<InterfaceInfo, KeystoneAuthV3> systemConnection = TestUowFactory.createSystemConnections();
	@Mock
	private Context3d ctx;

	@Test
	void testExecute() {
		final NetworkPolicyTask task = new NetworkPolicyTask();
		task.setInstanceId(UUID.randomUUID());
		task.setToscaName("tosca");
		final VirtualTaskV3<NetworkPolicyTask> vt = new NetworkPolicyVt(task);
		when(ctx.get(ServiceInstanceNode.class, "tosca")).thenReturn("");
		final NetworkPolicyUow np = new NetworkPolicyUow(vt, systemConnection);
		assertThrows(ContrailException.class, () -> np.execute(ctx));
	}

	@Test
	void testRollback() {
		final NetworkPolicyTask task = new NetworkPolicyTask();
		final VirtualTaskV3<NetworkPolicyTask> vt = new NetworkPolicyVt(task);
		final NetworkPolicyUow np = new NetworkPolicyUow(vt, systemConnection);
		assertThrows(ContrailException.class, () -> np.rollback(null));
	}

}
