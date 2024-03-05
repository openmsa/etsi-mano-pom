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
package com.ubiqube.etsi.mano.service.mon;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.dao.mano.pm.RemoteMetric;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.grammar.BooleanExpression;
import com.ubiqube.etsi.mano.grammar.GrammarLabel;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarOperandType;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.grammar.GrammarValue;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;

@ExtendWith(MockitoExtension.class)
class MonitoringManagerTest {
	@Mock
	private ExternalMonitoring em;
	@Mock
	private VnfInstanceService vnfInstanceService;
	@Mock
	private GrammarParser grammar;

	@Test
	void testAddResource() {
		final MonitoringManager srv = new MonitoringManager(em, vnfInstanceService, grammar);
		final PmJob pm = TestFactory.createPmJob();
		srv.addResource(pm, null);
		assertTrue(true);
	}

	@Test
	void testCreate() {
		final MonitoringManager srv = new MonitoringManager(em, vnfInstanceService, grammar);
		final PmJob pm = TestFactory.createPmJob();
		pm.getObjectInstanceIds().add(UUID.randomUUID().toString());
		pm.getSubObjectInstanceIds().add("sub");
		final VnfLiveInstance live = new VnfLiveInstance();
		final VnfTask task = new ComputeTask();
		task.setToscaName("sub");
		live.setTask(task);
		when(vnfInstanceService.findByVnfInstanceId(any())).thenReturn(List.of(live));
		when(em.createBatch(any(), any(), any(), any())).thenReturn(UUID.randomUUID());
		srv.create(pm);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final MonitoringManager srv = new MonitoringManager(em, vnfInstanceService, grammar);
		final PmJob pm = TestFactory.createPmJob();
		final RemoteMetric rmt = new RemoteMetric();
		pm.setRemoteMonitoring(List.of(rmt));
		srv.delete(pm);
		assertTrue(true);
	}

	@Test
	void testDeleteResource() {
		final MonitoringManager srv = new MonitoringManager(em, vnfInstanceService, grammar);
		srv.deleteResource(null);
		assertTrue(true);
	}

	@Test
	void testFind() {
		final MonitoringManager srv = new MonitoringManager(em, vnfInstanceService, grammar);
		srv.findByVnfInstanceId(null, null, null);
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final MonitoringManager srv = new MonitoringManager(em, vnfInstanceService, grammar);
		final TelemetryMetricsResult tm = new TelemetryMetricsResult();
		final BooleanExpression node = new BooleanExpression(new GrammarLabel("name"), GrammarOperandType.EQ, new GrammarValue(null));
		final List<GrammarNode> lst = List.of(node);
		when(grammar.parse(any())).thenReturn(new GrammarNodeResult(lst));
		when(em.searchMetric(any())).thenReturn(List.of(tm));
		srv.search(null, null);
		assertTrue(true);
	}

	@Test
	void testSearchBadNode() {
		final MonitoringManager srv = new MonitoringManager(em, vnfInstanceService, grammar);
		final TelemetryMetricsResult tm = new TelemetryMetricsResult();
		final BooleanExpression node = new BooleanExpression(new GrammarLabel("name"), GrammarOperandType.CONT, new GrammarValue(null));
		final List<GrammarNode> lst = List.of(node);
		when(grammar.parse(any())).thenReturn(new GrammarNodeResult(lst));
		assertThrows(GenericException.class, () -> srv.search(null, null));
	}
}
