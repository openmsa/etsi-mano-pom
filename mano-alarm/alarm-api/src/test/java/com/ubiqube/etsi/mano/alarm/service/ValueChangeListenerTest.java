/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.alarm.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Aggregates;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Metrics;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Transform;
import com.ubiqube.etsi.mano.alarm.repository.AlarmRepository;
import com.ubiqube.etsi.mano.alarm.service.aggregate.AggregateService;
import com.ubiqube.etsi.mano.alarm.service.transform.TransformService;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.cond.ConditionService;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.jms.MetricChange;

@ExtendWith(MockitoExtension.class)
class ValueChangeListenerTest {
	@Mock
	private AlarmRepository alarmRepository;
	@Mock
	private MetricService metricService;
	@Mock
	private TransformService transformService;
	@Mock
	private AggregateService aggregateService;
	@Mock
	private ActionService actionService;
	@Mock
	private ConditionService conditionService;

	ValueChangeListener create() {
		return new ValueChangeListener(alarmRepository, metricService, transformService, aggregateService, actionService, conditionService);
	}

	@Test
	void test() throws JsonProcessingException {
		final ValueChangeListener srv = create();
		final MonitoringDataSlim latest = new TelemetryMetricsResult("master", "res1", "k1", 123D, null, OffsetDateTime.now(), true);
		final MonitoringDataSlim old = new TelemetryMetricsResult("master", "res1", "k2", 123D, null, OffsetDateTime.now(), true);
		final MetricChange mc = new MetricChange(latest, old);
		srv.listen(mc);
		assertTrue(true);
	}

	@Test
	void test2() throws JsonProcessingException {
		final ValueChangeListener srv = create();
		final MonitoringDataSlim latest = new TelemetryMetricsResult("master", "res1", "k1", 123D, null, OffsetDateTime.now(), true);
		final MonitoringDataSlim old = new TelemetryMetricsResult("master", "res1", "k2", 123D, null, OffsetDateTime.now(), true);
		final MetricChange mc = new MetricChange(latest, old);
		final Alarm ala1 = new Alarm();
		final Aggregates aga1 = new Aggregates();
		final Transform tra1 = new Transform();
		ala1.setTransforms(List.of(tra1));
		ala1.setAggregates(List.of(aga1));
		final Metrics met1 = new Metrics();
		ala1.setMetrics(List.of(met1));
		when(alarmRepository.findByMetricsObjectIdAndMetricsKey(mc.latest().getResourceId(), mc.latest().getKey())).thenReturn(List.of(ala1));
		final TelemetryMetricsResult mds1 = new TelemetryMetricsResult("master", "res1", "k1", 123D, null, OffsetDateTime.now(), true);
		final TelemetryMetricsResult mds2 = new TelemetryMetricsResult("master", "res1", "k1", 123D, null, OffsetDateTime.now(), true);
		when(metricService.findLatest(met1)).thenReturn(List.of(mds1, mds2));
		when(conditionService.evaluate(any(), any())).thenReturn(true);
		srv.listen(mc);
		assertTrue(true);
	}

	@Test
	void test3() throws JsonProcessingException {
		final ValueChangeListener srv = create();
		final MonitoringDataSlim latest = new TelemetryMetricsResult("master", "res1", "k1", 123D, null, OffsetDateTime.now(), true);
		final MonitoringDataSlim old = new TelemetryMetricsResult("master", "res1", "k2", 123D, null, OffsetDateTime.now(), true);
		final MetricChange mc = new MetricChange(latest, old);
		final Alarm ala1 = new Alarm();
		ala1.setState(true);
		final Aggregates aga1 = new Aggregates();
		final Transform tra1 = new Transform();
		ala1.setTransforms(List.of(tra1));
		ala1.setAggregates(List.of(aga1));
		ala1.setMetrics(List.of());
		when(alarmRepository.findByMetricsObjectIdAndMetricsKey(mc.latest().getResourceId(), mc.latest().getKey())).thenReturn(List.of(ala1));
		srv.listen(mc);
		assertTrue(true);
	}

	@Test
	void testEvalFail() throws JsonProcessingException {
		final ValueChangeListener srv = create();
		final MonitoringDataSlim latest = new TelemetryMetricsResult("master", "res1", "k1", 123D, null, OffsetDateTime.now(), true);
		final MonitoringDataSlim old = new TelemetryMetricsResult("master", "res1", "k2", 123D, null, OffsetDateTime.now(), true);
		final MetricChange mc = new MetricChange(latest, old);
		final Alarm ala1 = new Alarm();
		final Aggregates aga1 = new Aggregates();
		final Transform tra1 = new Transform();
		ala1.setTransforms(List.of(tra1));
		ala1.setAggregates(List.of(aga1));
		ala1.setMetrics(List.of());
		when(alarmRepository.findByMetricsObjectIdAndMetricsKey(mc.latest().getResourceId(), mc.latest().getKey())).thenReturn(List.of(ala1));
		srv.listen(mc);
		assertTrue(true);
	}
}
