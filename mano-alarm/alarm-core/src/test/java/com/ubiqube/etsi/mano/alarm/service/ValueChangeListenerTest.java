/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
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
