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
package com.ubiqube.etsi.mano.mon.core.service;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.mon.api.BusHelper;
import com.ubiqube.etsi.mano.mon.core.Constants;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.data.MonConnInformation;

@Service
public class BusHandlerService {
	private static final Logger LOG = LoggerFactory.getLogger(BusHandlerService.class);

	private final ExpirityJmsTemplate jmsTemplate;

	public BusHandlerService(final ExpirityJmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void emit(final BatchPollingJob batchPollingJob) {
		final String queueName = buildQueueName(batchPollingJob);
		LOG.trace("Sending to pollster: {}", queueName);
		Hibernate.initialize(batchPollingJob.getConnection());
		jmsTemplate.convertAndSend(queueName, batchPollingJob, Constants.MANO_MON_TICK_MILLIS - 1);
	}

	private static String buildQueueName(final BatchPollingJob batchPollingJob) {
		final MonConnInformation conn = batchPollingJob.getConnection();
		final String connQueue = conn.getConnType().toLowerCase().replace("_", "-");
		return BusHelper.buildQueueName(connQueue);
	}
}
