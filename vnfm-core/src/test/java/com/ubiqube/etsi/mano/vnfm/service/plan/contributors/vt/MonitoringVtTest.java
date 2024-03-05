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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.v2.MonitoringTask;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;

class MonitoringVtTest {

	@Test
	void test() {
		final MonitoringTask nt = new MonitoringTask();
		nt.setStatus(PlanStatusType.FAILED);
		final MonitoringVt vt = new MonitoringVt(nt);
		vt.setAlias("alias");
		assertNotNull(vt.getAlias());
		vt.setName("name");
		assertNotNull(vt.getName());
		vt.setRank(22);
		assertEquals(22, vt.getRank());
		vt.getStatus();
		vt.setSystemBuilder(null);
		vt.getSystemBuilder();
		vt.getTemplateParameters();
		vt.getToscaName();
		vt.getType();
		vt.setVimConnectionId("vimConn");
		assertNotNull(vt.getVimConnectionId());
		vt.setVimResourceId("rs");
		assertNotNull(vt.getVimResourceId());
		vt.setDelete(false);
		vt.setRemovedLiveInstanceId(null);
		vt.isDeleteTask();
		vt.toString();
	}

	@Test
	void testCoverage() throws Exception {
		final MonitoringTask nt = new MonitoringTask();
		nt.setStatus(PlanStatusType.NOT_STARTED);
		final MonitoringVt vt = new MonitoringVt(nt);
		vt.setDelete(true);
		assertTrue(vt.isDeleteTask());
		vt.getStatus();
		nt.setStatus(PlanStatusType.REMOVED);
		assertThrows(IllegalArgumentException.class, () -> vt.getStatus());
		nt.setStatus(PlanStatusType.STARTED);
		vt.getStatus();
		nt.setStatus(PlanStatusType.SUCCESS);
		vt.getStatus();

	}
}
