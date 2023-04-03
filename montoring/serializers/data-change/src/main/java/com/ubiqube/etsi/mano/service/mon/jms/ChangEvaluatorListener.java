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
package com.ubiqube.etsi.mano.service.mon.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.mon.api.BusHelper;
import com.ubiqube.etsi.mano.service.mon.data.JmsMetricHolder;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;

@Service
public class ChangEvaluatorListener {
	private static final Logger LOG = LoggerFactory.getLogger(ChangEvaluatorListener.class);
	private final DataBackend dataBackend;

	private final JmsTemplate jmsTemplate;

	public ChangEvaluatorListener(final DataBackend dataBackend, final JmsTemplate jmsTemplate) {
		this.dataBackend = dataBackend;
		this.jmsTemplate = jmsTemplate;
	}

	@JmsListener(destination = BusHelper.TOPIC_SERIALZE_DATA, containerFactory = "serialzeDataFactory")
	public void changeEvaluator(final JmsMetricHolder results) {
		results.getMetrics().forEach(this::handleOneMetric);
	}

	private void handleOneMetric(final MonitoringDataSlim result) {
		final MonitoringDataSlim res = dataBackend.getLastMetrics(result.getKey(), result.getMasterJobId());
		if (res == null) {
			LOG.warn("Not enought element for {}/{}", result.getKey(), result.getMasterJobId());
			updateMetric(result);
			return;
		}
		final Double value = res.getValue();
		final String text = res.getText();
		if (((value != null) && (value.equals(result.getValue())))
				|| ((text != null) && (text.equals(result.getText())))) {
			return;
		}
		LOG.trace("Metric change {}", res);
		updateMetric(result);
		jmsTemplate.convertAndSend(Constants.QUEUE_CHANGE_NOTIFICATION, new MetricChange(res, result));
	}

	private void updateMetric(final MonitoringDataSlim slim) {
		dataBackend.updateMetric(slim);
	}
}
