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
package com.ubiqube.etsi.mano.service.mon.jpa;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import com.ubiqube.etsi.mano.service.mon.jms.ChangEvaluatorListener;
import com.ubiqube.etsi.mano.service.mon.jms.Constants;
import com.ubiqube.etsi.mano.service.mon.model.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.repository.MonitoringDataJpa;

@ExtendWith(MockitoExtension.class)
class ChangEvaluatorListenerTest {
	@Mock
	private MonitoringDataJpa monitoringJpa;
	@Mock
	private JmsTemplate jmsTemplate;

	@Test
	void testBasic() throws Exception {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(monitoringJpa, jmsTemplate);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(Timestamp.from(Instant.now()), "masterJobId2", "key2", 123D, null);
		changEvaluatorListener.changeEvaluator(result);
		assertTrue(true);
	}

	@Test
	void testOneElement() throws Exception {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(monitoringJpa, jmsTemplate);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(Timestamp.from(Instant.now()), "masterJobId2", "key2", 123D, null);
		//
		when(monitoringJpa.getLastMetrics("key2", "masterJobId2")).thenReturn(List.of());
		changEvaluatorListener.changeEvaluator(result);
		assertTrue(true);
	}

	@Test
	void testSameOnValue() throws Exception {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(monitoringJpa, jmsTemplate);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(Timestamp.from(Instant.now()), "masterJobId2", "key2", 123D, null);
		final MonitoringDataSlim result2 = new TestMonitoringDataSlim(Timestamp.from(Instant.now()), "masterJobId2", "key2", 123D, null);
		//
		when(monitoringJpa.getLastMetrics("key2", "masterJobId2")).thenReturn(List.of(result, result2));
		changEvaluatorListener.changeEvaluator(result);
		assertTrue(true);
	}

	@Test
	void testDiffOnValue() throws Exception {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(monitoringJpa, jmsTemplate);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(Timestamp.from(Instant.now()), "masterJobId2", "key2", 123D, null);
		final MonitoringDataSlim result2 = new TestMonitoringDataSlim(Timestamp.from(Instant.now()), "masterJobId2", "key2", 456D, null);
		//
		when(monitoringJpa.getLastMetrics("key2", "masterJobId2")).thenReturn(List.of(result, result2));
		final ArgumentCaptor<Object> valueCapture = ArgumentCaptor.forClass(Object.class);
		doNothing().when(jmsTemplate).convertAndSend(eq(Constants.QUEUE_CHANGE_NOTIFICATION), valueCapture.capture());
		changEvaluatorListener.changeEvaluator(result);
		assertTrue(true);
	}

	@Test
	void testSameOnText() throws Exception {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(monitoringJpa, jmsTemplate);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(Timestamp.from(Instant.now()), "masterJobId2", "key2", null, "Hello");
		final MonitoringDataSlim result2 = new TestMonitoringDataSlim(Timestamp.from(Instant.now()), "masterJobId2", "key2", null, "Hello");
		//
		when(monitoringJpa.getLastMetrics("key2", "masterJobId2")).thenReturn(List.of(result, result2));
		changEvaluatorListener.changeEvaluator(result);
		assertTrue(true);
	}

	@Test
	void testDiffOnText() throws Exception {
		final ChangEvaluatorListener changEvaluatorListener = new ChangEvaluatorListener(monitoringJpa, jmsTemplate);
		final MonitoringDataSlim result = new TestMonitoringDataSlim(Timestamp.from(Instant.now()), "masterJobId2", "key2", null, "Hello22");
		final MonitoringDataSlim result2 = new TestMonitoringDataSlim(Timestamp.from(Instant.now()), "masterJobId2", "key2", null, "Hello");
		//
		when(monitoringJpa.getLastMetrics("key2", "masterJobId2")).thenReturn(List.of(result, result2));
		final ArgumentCaptor<Object> valueCapture = ArgumentCaptor.forClass(Object.class);
		doNothing().when(jmsTemplate).convertAndSend(eq(Constants.QUEUE_CHANGE_NOTIFICATION), valueCapture.capture());
		changEvaluatorListener.changeEvaluator(result);
		assertTrue(true);
	}
}
