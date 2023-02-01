/**
 *     Copyright (C) 2019-2020 Ubiqube.
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

import java.util.Objects;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.service.mon.MonitoringEventManager;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.gnocchi.Constants;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class JmsMonitoringEventManager implements MonitoringEventManager {
	private final JmsTemplate jmsQueueTemplate;
	private final ConfigurableApplicationContext configurableApplicationContext;

	public JmsMonitoringEventManager(final JmsTemplate jmsQueueTemplate, final ConfigurableApplicationContext configurableApplicationContext) {
		this.jmsQueueTemplate = jmsQueueTemplate;
		this.configurableApplicationContext = configurableApplicationContext;
	}

	@Override
	public void sendGetDataEvent(final BatchPollingJob pmJob) {
		// PmType controller ?
		jmsQueueTemplate.convertAndSend(resolvQueueName(Constants.QUEUE_GNOCCHI_DATA_POLLING), pmJob);
	}

	@NotNull
	private String resolvQueueName(final String queueName) {
		final ConfigurableListableBeanFactory configurableListableBeanFactory = configurableApplicationContext.getBeanFactory();
		final String ret = configurableListableBeanFactory.resolveEmbeddedValue(queueName);
		Objects.requireNonNull(ret);
		return ret;
	}
}
