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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.service.mon.model.MonitoringData;
import com.ubiqube.etsi.mano.service.mon.repository.MonitoringDataJpa;

@Service
public class ChangEvaluatorListener {
	private static final Logger LOG = LoggerFactory.getLogger(ChangEvaluatorListener.class);
	private final MonitoringDataJpa monitoringDataJpa;

	private final JmsTemplate jmsTemplate;

	public ChangEvaluatorListener(final MonitoringDataJpa monitoringDataJpa, final JmsTemplate jmsTemplate) {
		this.monitoringDataJpa = monitoringDataJpa;
		this.jmsTemplate = jmsTemplate;
	}

	@JmsListener(destination = Constants.QUEUE_CHANGE_EVALUATOR)
	public void changeEvaluator(final MonitoringData result) {
		result.getKey();
		result.getMasterJobId();
		final List<MonitoringData> res = monitoringDataJpa.getLastMetrics(result.getKey(), result.getMasterJobId());
		if (res.size() != 2) {
			LOG.warn("Not enought element for {}/{}", result.getKey(), result.getMasterJobId());
			return;
		}
		if (((res.get(0).getValue() != null) && (res.get(0).getValue().equals(res.get(1).getValue())))
				|| ((res.get(0).getText() != null) && (res.get(0).getText().equals(res.get(1).getText())))) {
			return;
		}
		LOG.trace("Metric change {}", res);
		jmsTemplate.convertAndSend(Constants.QUEUE_CHANGE_NOTIFICATION, new MetricChange(res.get(0), res.get(1)));
	}
}
