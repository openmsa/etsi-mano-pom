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

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.mon.dao.AllHostMetrics;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.gnocchi.Constants;
import com.ubiqube.etsi.mano.service.mon.vim.GnocchiSubTelemetry;
import com.ubiqube.etsi.mano.service.vim.VimManager;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NotificationController {
	private final VimManager vimManager;
	private final JmsTemplate jmsTopicTemplate;
	private final ConfigurableApplicationContext configurableApplicationContext;

	public NotificationController(final VimManager vimManager, @Qualifier("jmsTopicTemplate") final JmsTemplate jmsTopicTemplate, final ConfigurableApplicationContext configurableApplicationContext) {
		this.vimManager = vimManager;
		this.jmsTopicTemplate = jmsTopicTemplate;
		this.configurableApplicationContext = configurableApplicationContext;
	}

	@JmsListener(destination = Constants.QUEUE_GNOCCHI_DATA_POLLING, concurrency = "10")
	public void onGnocchiDataPolling(final BatchPollingJob job) {
		// Get Gnocchi instances and sub metrics.
		final List<TelemetryMetricsResult> allHostMetrics = job.getHosts().stream()
				.flatMap(x -> GnocchiSubTelemetry.getMetricsForVnfc(vimManager.findVimById(job.getVimId()), x, job.getMetrics(), job.getId()).stream())
				.toList();
		final AllHostMetrics metrics = new AllHostMetrics(allHostMetrics, job.getJobType(), job.getMetrics().get(0).getMetricName(), job.getParentObjectId(), job.getId());
		// Now we have a batch of metrics. Send to data poller.
		jmsTopicTemplate.convertAndSend(resolvQueueName(Constants.TOPIC_GNOCCHI_DATA), metrics);
	}

	@NotNull
	private String resolvQueueName(final String queueName) {
		final ConfigurableListableBeanFactory configurableListableBeanFactory = configurableApplicationContext.getBeanFactory();
		final String ret = configurableListableBeanFactory.resolveEmbeddedValue(queueName);
		Objects.requireNonNull(ret);
		return ret;
	}

}
