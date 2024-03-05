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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfInstanceLcm;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdTask;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsCreateVt;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;

@ExtendWith(MockitoExtension.class)
class NsUowTest {
	@Mock
	private VnfInstanceLcm nsLcmOpOccsService;

	@Test
	void testExecute() {
		final NsdTask task = new NsdTask();
		task.setVimConnectionInformations(Set.of());
		final VirtualTaskV3<NsdTask> vtTask = new NsCreateVt(task);
		final VnfBlueprint blueprint = new VnfBlueprint();
		blueprint.setId(UUID.randomUUID());
		blueprint.setOperationStatus(OperationStatusType.COMPLETED);
		when(nsLcmOpOccsService.instantiate(any(), any(), any())).thenReturn(blueprint);
		when(nsLcmOpOccsService.vnfLcmOpOccsGet(any(), any())).thenReturn(blueprint);
		final NsUow ns = new NsUow(vtTask, nsLcmOpOccsService);
		ns.execute(null);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final NsdTask task = new NsdTask();
		task.setVimConnectionInformations(Set.of());
		final VirtualTaskV3<NsdTask> vtTask = new NsCreateVt(task);
		final NsUow ns = new NsUow(vtTask, nsLcmOpOccsService);
		final VnfBlueprint blueprint = new VnfBlueprint();
		blueprint.setError(new FailureDetails());
		when(nsLcmOpOccsService.terminate(task.getServer(), task.getNsInstanceId(), null, 0)).thenReturn(blueprint);
		when(nsLcmOpOccsService.vnfLcmOpOccsGet(null, null)).thenReturn(blueprint);
		assertThrows(GenericException.class, () -> ns.rollback(null));
	}

	@Test
	void testRollback001() {
		final NsdTask task = new NsdTask();
		task.setVimConnectionInformations(Set.of());
		final VirtualTaskV3<NsdTask> vtTask = new NsCreateVt(task);
		final NsUow ns = new NsUow(vtTask, nsLcmOpOccsService);
		final VnfBlueprint blueprint = new VnfBlueprint();
		blueprint.setError(new FailureDetails());
		blueprint.setOperationStatus(OperationStatusType.COMPLETED);
		blueprint.setId(UUID.randomUUID());
		when(nsLcmOpOccsService.terminate(task.getServer(), task.getNsInstanceId(), null, 0)).thenReturn(blueprint);
		when(nsLcmOpOccsService.vnfLcmOpOccsGet(null, blueprint.getId())).thenReturn(blueprint);
		ns.rollback(null);
		assertTrue(true);
	}
}
