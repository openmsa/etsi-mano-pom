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

import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.ServiceTemplateVt;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.tf.ContrailException;
import com.ubiqube.etsi.mano.tf.entities.ServiceTemplateTask;

class ServiceTemplateUowTest {

	@Test
	void testExecute() {
		final SystemConnections connection = TestUowFactory.createSystemConnections();
		final ServiceTemplateTask task = new ServiceTemplateTask();
		task.setInstanceId(UUID.randomUUID());
		final VirtualTaskV3<ServiceTemplateTask> vt = new ServiceTemplateVt(task);
		final ServiceTemplateUow st = new ServiceTemplateUow(vt, connection);
		assertThrows(ContrailException.class, () -> st.execute(null));
	}

	@Test
	void testRollback() {
		final SystemConnections connection = TestUowFactory.createSystemConnections();
		final ServiceTemplateTask task = new ServiceTemplateTask();
		final VirtualTaskV3<ServiceTemplateTask> vt = new ServiceTemplateVt(task);
		final ServiceTemplateUow st = new ServiceTemplateUow(vt, connection);
		assertThrows(ContrailException.class, () -> st.rollback(null));
	}

}
