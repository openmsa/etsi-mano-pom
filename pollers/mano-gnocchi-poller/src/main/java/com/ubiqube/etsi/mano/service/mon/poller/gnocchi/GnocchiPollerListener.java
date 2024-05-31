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
package com.ubiqube.etsi.mano.service.mon.poller.gnocchi;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;

import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.telemetry.gnocchi.Measure;
import org.openstack4j.model.telemetry.gnocchi.MeasureFilter;
import org.openstack4j.model.telemetry.gnocchi.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.mon.api.BusHelper;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.openstack.OsUtils;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.data.JmsMetricHolder;
import com.ubiqube.etsi.mano.service.mon.data.Metric;
import com.ubiqube.etsi.mano.service.mon.data.MonConnInformation;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;

/**
 *
 * @author olivier
 *
 */
@Service
public class GnocchiPollerListener {
	private static final Logger LOG = LoggerFactory.getLogger(GnocchiPollerListener.class);

	private final JmsTemplate jmsTemplate;

	private final ConfigurableApplicationContext configurableApplicationContext;

	public GnocchiPollerListener(final @Qualifier("topicJmsTemplate") JmsTemplate jmsTemplate, final ConfigurableApplicationContext configurableApplicationContext) {
		this.jmsTemplate = jmsTemplate;
		this.configurableApplicationContext = configurableApplicationContext;
	}

	@JmsListener(destination = Constants.QUEUE_GNOCCHI_DATA_POLLING, concurrency = "5")
	public void onEvent(final BatchPollingJob<InterfaceInfo, KeystoneAuthV3> batchPollingJob) {
		final UUID id = Objects.requireNonNull(batchPollingJob.getId());
		final MonConnInformation<InterfaceInfo, KeystoneAuthV3> conn = batchPollingJob.getConnection();
		final OSClientV3 os = OsUtils.authenticate(conn.getInterfaceInfo(), conn.getAccessInfo());
		final List<MonitoringDataSlim> metrics = getMetrics(id, batchPollingJob.getResourceId(), batchPollingJob.getMetrics(), os);
		jmsTemplate.convertAndSend(resolvQueueName(BusHelper.TOPIC_SERIALZE_DATA), JmsMetricHolder.of(metrics));
	}

	private List<MonitoringDataSlim> getMetrics(final UUID jobId, final String resourceId, final List<Metric> collectedMetrics, final OSClientV3 os) {
		final List<String> colls = collectedMetrics.stream().map(Metric::getMetricName).toList();
		final Resource instanceResources = os.telemetry().resources().instance(resourceId);
		if (null == instanceResources) {
			LOG.warn("Could not fetch resource {}", resourceId);
			return (List<MonitoringDataSlim>) prepareErrors(jobId, collectedMetrics, resourceId);
		}
		final List<Entry<String, String>> colMeter = instanceResources.getMetrics().entrySet().stream().filter(x -> colls.contains(x.getKey())).toList();
		return colMeter.stream().map(x -> map(x, resourceId, jobId, os)).toList();
	}

	private static MonitoringDataSlim map(final Entry<String, String> x, final String resourceId, final UUID jobId, final OSClientV3 os) {
		final MeasureFilter mf = new MeasureFilter();
		mf.start(OffsetDateTime.now().minus(10, ChronoUnit.MINUTES));
		final String jobStr = Objects.requireNonNull(jobId.toString());
		final List<? extends Measure> res;
		try {
			res = os.telemetry().gnocchi().mesures().read(x.getValue());
		} catch (final RuntimeException e) {
			LOG.warn("An error occured.", e);
			return new TelemetryMetricsResult(jobStr, resourceId, x.getKey(), Double.valueOf(0), null, OffsetDateTime.now(), false);
		}
		if (res.isEmpty()) {
			LOG.warn("Metric {} is empty.", x.getValue());
			return new TelemetryMetricsResult(jobStr, resourceId, x.getKey(), Double.valueOf(0), null, OffsetDateTime.now(), false);
		}
		final int idx = res.size() - 1;
		final Double value = res.get(idx).getValue();
		final OffsetDateTime ts = res.get(idx).getTimeStamp();
		return new TelemetryMetricsResult(jobStr, resourceId, x.getKey(), value, null, ts, true);
	}

	private String resolvQueueName(final String queueName) {
		final ConfigurableListableBeanFactory configurableListableBeanFactory = configurableApplicationContext.getBeanFactory();
		final String ret = configurableListableBeanFactory.resolveEmbeddedValue(queueName);
		Objects.requireNonNull(ret);
		return ret;
	}

	private static List<? extends MonitoringDataSlim> prepareErrors(final UUID jobId, final List<Metric> collectedMetrics, final String resourceId) {
		return collectedMetrics.stream()
				.map(x -> new TelemetryMetricsResult(jobId.toString(), resourceId, x.getMetricName(), 0d, null, OffsetDateTime.now(), false))
				.toList();
	}

}
