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
package com.ubiqube.etsi.mano.service.mon.poller.snmp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.smi.Counter32;
import org.snmp4j.smi.Counter64;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UnsignedInteger32;
import org.snmp4j.smi.Variable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import com.ubiqube.etsi.mano.mon.api.BusHelper;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.data.JmsMetricHolder;
import com.ubiqube.etsi.mano.service.mon.data.Metric;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;

import jakarta.annotation.Nonnull;

public abstract class AbstractSnmpPoller {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractSnmpPoller.class);

	private final JmsTemplate jmsTemplate;

	private final ConfigurableApplicationContext configurableApplicationContext;

	protected AbstractSnmpPoller(@Qualifier("topicJmsTemplate") final JmsTemplate jmsTemplate, final ConfigurableApplicationContext configurableApplicationContext) {
		this.jmsTemplate = jmsTemplate;
		this.configurableApplicationContext = configurableApplicationContext;
	}

	protected void getMetrics(final BatchPollingJob pj) {
		final PDU resp = getResponse(pj);
		final List<MonitoringDataSlim> metrics = prepareResponse(pj, resp);
		jmsTemplate.convertAndSend(resolvQueueName(BusHelper.TOPIC_SERIALZE_DATA), JmsMetricHolder.of(metrics));
	}

	private static List<MonitoringDataSlim> prepareResponse(final BatchPollingJob pj, final PDU resp) {
		if (null == resp) {
			LOG.warn("Time out: {}", resp);
			return (List<MonitoringDataSlim>) prepareError(pj);
		}
		return (List<MonitoringDataSlim>) prepareReponse(pj, resp);
	}

	abstract PDU getResponse(BatchPollingJob pj);

	private static List<? extends MonitoringDataSlim> prepareReponse(final BatchPollingJob pj, final PDU resp) {
		return pj.getMetrics().stream()
				.map(x -> map(pj, x, resp))
				.toList();
	}

	protected static List<? extends MonitoringDataSlim> prepareError(final BatchPollingJob pj) {
		final List<Metric> metrics = pj.getMetrics();
		return metrics.stream()
				.map(x -> new TelemetryMetricsResult(pj.getResourceId(), pj.getResourceId(), x.getMetricName(), Double.valueOf(0), null, OffsetDateTime.now(), false))
				.toList();
	}

	private static TelemetryMetricsResult map(final BatchPollingJob pj, final Metric x, final PDU resp) {
		final Variable variable = resp.getVariable(new OID(x.getMetricName()));
		double value = 0;
		boolean success = false;
		if (variable != null) {
			value = extractValue(variable);
			success = true;
		}
		final TelemetryMetricsResult tmr = new TelemetryMetricsResult(pj.getId().toString(), x.getMetricName(), x.getMetricName(), value, null, OffsetDateTime.now(), success);
		if (variable instanceof final OctetString os) {
			tmr.setText(os.toString());
		}
		return tmr;
	}

	private static double extractValue(final Variable variable) {
		if (variable instanceof final Counter64 c64) {
			return c64.getValue();
		}
		if (variable instanceof final Counter32 c32) {
			return c32.getValue();
		}
		if (variable instanceof final Integer32 i32) {
			return i32.getValue();
		}
		if (variable instanceof final UnsignedInteger32 ui32) {
			return ui32.getValue();
		}
		if (variable instanceof OctetString) {
			return 0;
		}
		LOG.warn("Could not find variable type: {}", variable.getClass().getSimpleName());
		return 0;
	}

	@Nonnull
	private String resolvQueueName(final String queueName) {
		final ConfigurableListableBeanFactory configurableListableBeanFactory = configurableApplicationContext.getBeanFactory();
		final String ret = configurableListableBeanFactory.resolveEmbeddedValue(queueName);
		Objects.requireNonNull(ret);
		return ret;
	}
}
