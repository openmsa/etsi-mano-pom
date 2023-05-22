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

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.repository.AlarmRepository;
import com.ubiqube.etsi.mano.alarm.service.aggregate.AggregateService;
import com.ubiqube.etsi.mano.alarm.service.transform.TransformService;
import com.ubiqube.etsi.mano.service.cond.ConditionService;
import com.ubiqube.etsi.mano.service.cond.Context;
import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.jms.MetricChange;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class ValueChangeListener {

	private static final Logger LOG = LoggerFactory.getLogger(ValueChangeListener.class);

	private final AlarmRepository alarmRepository;

	private final MetricService metricService;

	private final TransformService transformService;

	private final AggregateService aggregateService;

	private final ConditionService conditionService;

	private final ActionService actionService;

	public ValueChangeListener(final AlarmRepository alarmRepository, final MetricService metricService, final TransformService transformService,
			final AggregateService aggregateService, final ActionService actionService, final ConditionService conditionService) {
		this.alarmRepository = alarmRepository;
		this.metricService = metricService;
		this.transformService = transformService;
		this.aggregateService = aggregateService;
		this.conditionService = conditionService;
		this.actionService = actionService;
	}

	@JmsListener(destination = "mano-mon.mano.monitoring.change-notification")
	public void listen(final MetricChange mc) throws JsonProcessingException {
		final ObjectMapper map = new ObjectMapper();
		map.registerModule(new JavaTimeModule());
		final String str = map.writeValueAsString(mc);
		LOG.info("{}", str);
		final List<Alarm> lst = alarmRepository.findByMetricsObjectIdAndMetricsKey(mc.latest().getResourceId(), mc.latest().getKey());
		if (lst.isEmpty()) {
			LOG.warn("Could not find metrics: {}", mc.latest());
			return;
		}
		final Map<MetricKey, MetricChange> metricContext = new HashMap<>();
		lst.forEach(x -> handleAlarm(x, metricContext));
	}

	private void handleAlarm(final Alarm alarm, final Map<MetricKey, MetricChange> metricContext) {
		final AlarmContext ctx = createContext(alarm, metricContext);
		alarm.getTransforms().forEach(x -> transformService.transform(ctx, x));
		alarm.getAggregates().forEach(x -> aggregateService.aggregate(ctx, x));
		final Context eCtx = ctx.getEvaluationContext();
		final Node nodes = conditionService.parse(alarm.getConditions());
		final boolean res = conditionService.evaluate(nodes, eCtx);
		if (res != alarm.isState()) {
			actionService.doAction(alarm);
			upadteAlarmState(alarm, res);
		}
	}

	private void upadteAlarmState(final Alarm alarm, final boolean state) {
		alarm.setState(state);
		alarm.setLastChange(OffsetDateTime.now());
		if (state) {
			alarm.setLastRaised(OffsetDateTime.now());
		}
		alarmRepository.save(alarm);
	}

	private AlarmContext createContext(final Alarm alarm, final Map<MetricKey, MetricChange> metricContext) {
		final AlarmContext ctx = new AlarmContext();
		alarm.getMetrics().forEach(x -> {
			final MetricChange mc2 = metricContext.computeIfAbsent(MetricKey.of(x), y -> {
				final List<MonitoringDataSlim> latests = metricService.findLatest(x);
				return new MetricChange(latests.get(0), latests.get(1));
			});
			ctx.put(MetricKey.of(mc2.latest(), x.getLabel()), mc2);
		});
		return ctx;
	}
}