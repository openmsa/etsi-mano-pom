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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.PtLinkVt;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.tf.ContrailException;
import com.ubiqube.etsi.mano.tf.entities.PtLinkTask;

@ExtendWith(MockitoExtension.class)
class PtLinkUowTest {
	@Mock
	private Context3d context;

	@Test
	void testExecute() {
		final SystemConnections connection = TestUowFactory.createSystemConnections();
		final PtLinkTask task = new PtLinkTask();
		final VirtualTaskV3<PtLinkTask> vt = new PtLinkVt(task);
		final PtLinkUow pt = new PtLinkUow(vt, connection);
		assertThrows(ContrailException.class, () -> pt.execute(context));
	}

	@Test
	void testRollback() {
		final SystemConnections connection = TestUowFactory.createSystemConnections();
		final PtLinkTask task = new PtLinkTask();
		final VirtualTaskV3<PtLinkTask> vt = new PtLinkVt(task);
		final PtLinkUow pt = new PtLinkUow(vt, connection);
		assertThrows(ContrailException.class, () -> pt.rollback(context));
	}

}
