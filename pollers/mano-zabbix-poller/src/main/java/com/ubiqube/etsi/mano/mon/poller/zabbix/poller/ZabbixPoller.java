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
package com.ubiqube.etsi.mano.mon.poller.zabbix.poller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.mon.api.BusHelper;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.data.JmsMetricHolder;
import com.ubiqube.etsi.mano.service.mon.data.Metric;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;

@Service
public class ZabbixPoller {

	private static final Logger LOG = LoggerFactory.getLogger(ZabbixPoller.class);

	private final JmsTemplate jmsTemplate;

	private final ConfigurableApplicationContext configurableApplicationContext;

	private final ZabbixMetricQuery zmq;

	public ZabbixPoller(final @Qualifier("topicJmsTemplate") JmsTemplate jmsTemplate, final ConfigurableApplicationContext configurableApplicationContext) {
		this.jmsTemplate = jmsTemplate;
		this.configurableApplicationContext = configurableApplicationContext;
		zmq = new ZabbixMetricQuery();
	}

	@JmsListener(destination = Constants.QUEUE_ZABBIX_DATA_POLLING, concurrency = "5")
	public void onEvent(final BatchPollingJob<InterfaceInfo, AccessInfo> batchPollingJob) {
		final List<MonitoringDataSlim> metrics = (List<MonitoringDataSlim>) getMetrics(batchPollingJob);
		jmsTemplate.convertAndSend(resolvQueueName(BusHelper.TOPIC_SERIALZE_DATA), JmsMetricHolder.of(metrics));
	}

	private List<? extends MonitoringDataSlim> getMetrics(final BatchPollingJob<InterfaceInfo, AccessInfo> batchPollingJob) {
		final String resourceId = batchPollingJob.getResourceId();
		final List<Metric> metrics = batchPollingJob.getMetrics();
		final int port = getPort(resourceId);
		final String host = getHost(resourceId);
		return metrics.stream().map(x -> map(batchPollingJob, host, port, x)).toList();
	}

	private TelemetryMetricsResult map(final BatchPollingJob<InterfaceInfo, AccessInfo> pj, final String host, final int port, final Metric x) {
		try {
			final TelemetryMetricsResult tmr = new TelemetryMetricsResult(pj.getId().toString(), pj.getResourceId(), x.getMetricName(), null, null, OffsetDateTime.now(), false);
			final List<String> res = zmq.result(host, port, x.getMetricName());
			if ((x.getType() != null) && "numeric".equalsIgnoreCase(x.getType())) {
				tmr.setValue(Double.valueOf(res.get(0)));
			} else {
				tmr.setText(res.get(0));
			}
			return tmr;
		} catch (final RuntimeException e) {
			LOG.warn("Error while fetching {}", host, e);
			return new TelemetryMetricsResult(pj.getId().toString(), pj.getResourceId(), x.getMetricName(), null, e.getMessage(), OffsetDateTime.now(), false);
		}
	}

	private String resolvQueueName(final String queueName) {
		final ConfigurableListableBeanFactory configurableListableBeanFactory = configurableApplicationContext.getBeanFactory();
		final String ret = configurableListableBeanFactory.resolveEmbeddedValue(queueName);
		Objects.requireNonNull(ret);
		return ret;
	}

	private static String getHost(final String resourceId) {
		final int pos = resourceId.lastIndexOf(':');
		if (-1 == pos) {
			return resourceId;
		}

		return resourceId.substring(0, pos - 1);
	}

	private static int getPort(final String resourceId) {
		final int pos = resourceId.lastIndexOf(':');
		if (-1 == pos) {
			return 10050;
		}
		return Integer.parseInt(resourceId.substring(pos));
	}

}
