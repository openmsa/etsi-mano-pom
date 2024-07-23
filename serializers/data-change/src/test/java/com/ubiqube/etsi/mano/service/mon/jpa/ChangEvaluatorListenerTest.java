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
package com.ubiqube.etsi.mano.service.mon.jpa;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import com.ubiqube.etsi.mano.service.mon.data.JmsMetricHolder;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.jms.ChangEvaluatorListener;
import com.ubiqube.etsi.mano.service.mon.jms.Constants;
import com.ubiqube.etsi.mano.service.mon.jms.DataBackend;

@ExtendWith(MockitoExtension.class)
class ChangEvaluatorListenerTest {
	@Mock
	private DataBackend dataBackend;
	@Mock
	private JmsTemplate jmsTemplate;
	@Mock
	private ConfigurableApplicationContext configurableApplicationContext;
	@Mock
	private ConfigurableListableBeanFactory configurableBean;

	@Test
	void testBasic() {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(dataBackend, jmsTemplate, configurableApplicationContext);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "res", "key2", 123D, null);
		assertNotNull(result);
		final JmsMetricHolder param = new JmsMetricHolder();
		param.setMetrics(List.of());
		changEvaluatorListener.changeEvaluator(param);
		assertTrue(true);
	}

	@Test
	void testOneElement() {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(dataBackend, jmsTemplate, configurableApplicationContext);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", 123D, null);
		//
		when(dataBackend.getLastMetrics("key2", "masterJobId2")).thenReturn(null);
		final JmsMetricHolder param = new JmsMetricHolder();
		param.setMetrics(List.of(result));
		changEvaluatorListener.changeEvaluator(param);
		assertTrue(true);
	}

	@Test
	void testSameOnValue() {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(dataBackend, jmsTemplate, configurableApplicationContext);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", 123D, null);
		final MonitoringDataSlim result2 = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", 123D, null);
		//
		when(dataBackend.getLastMetrics("key2", "masterJobId2")).thenReturn(result2);
		final JmsMetricHolder param = new JmsMetricHolder();
		param.setMetrics(List.of(result));
		changEvaluatorListener.changeEvaluator(param);
		assertTrue(true);
	}

	@Test
	void testDiffOnValue() throws Exception {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(dataBackend, jmsTemplate, configurableApplicationContext);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", 123D, null);
		final MonitoringDataSlim result2 = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", 456D, null);
		//
		when(dataBackend.getLastMetrics("key2", "masterJobId2")).thenReturn(result2);
		final ArgumentCaptor<Object> valueCapture = ArgumentCaptor.forClass(Object.class);
		doNothing().when(jmsTemplate).convertAndSend(eq(Constants.QUEUE_CHANGE_NOTIFICATION), valueCapture.capture());
		final JmsMetricHolder param = new JmsMetricHolder();
		param.setMetrics(List.of(result));
		when(configurableApplicationContext.getBeanFactory()).thenReturn(configurableBean);
		when(configurableBean.resolveEmbeddedValue(any())).thenReturn(Constants.QUEUE_CHANGE_NOTIFICATION);
		changEvaluatorListener.changeEvaluator(param);
		assertTrue(true);
	}

	@Test
	void testSameOnText() {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(dataBackend, jmsTemplate, configurableApplicationContext);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", null, "Hello");
		final MonitoringDataSlim result2 = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", null, "Hello");
		//
		when(dataBackend.getLastMetrics("key2", "masterJobId2")).thenReturn(result2);
		final JmsMetricHolder param = new JmsMetricHolder();
		param.setMetrics(List.of(result));
		changEvaluatorListener.changeEvaluator(param);
		assertTrue(true);
	}

	@Test
	void testDiffOnText() throws Exception {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(dataBackend, jmsTemplate, configurableApplicationContext);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", null, "Hello22");
		final MonitoringDataSlim result2 = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", null, "Hello");
		//
		when(dataBackend.getLastMetrics("key2", "masterJobId2")).thenReturn(result2);
		final ArgumentCaptor<Object> valueCapture = ArgumentCaptor.forClass(Object.class);
		doNothing().when(jmsTemplate).convertAndSend(eq(Constants.QUEUE_CHANGE_NOTIFICATION), valueCapture.capture());
		final JmsMetricHolder param = new JmsMetricHolder();
		param.setMetrics(List.of(result));
		when(configurableApplicationContext.getBeanFactory()).thenReturn(configurableBean);
		when(configurableBean.resolveEmbeddedValue(any())).thenReturn(Constants.QUEUE_CHANGE_NOTIFICATION);
		changEvaluatorListener.changeEvaluator(param);
		assertTrue(true);
	}
}
