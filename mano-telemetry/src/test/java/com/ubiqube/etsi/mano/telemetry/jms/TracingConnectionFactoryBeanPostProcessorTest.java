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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.telemetry.jms;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.jms.connection.CachingConnectionFactory;

import brave.jakarta.jms.JmsTracing;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.TopicConnectionFactory;
import jakarta.jms.XAConnectionFactory;
import jakarta.jms.XAQueueConnectionFactory;

@ExtendWith(MockitoExtension.class)
class TracingConnectionFactoryBeanPostProcessorTest {
	@Mock
	private BeanFactory beanFactory;
	@Mock
	private JmsTracing jmsTracing;
	@Mock
	private ConnectionFactory connectionFactory;
	@Mock
	private XAConnectionFactory xaConnection;

	@Test
	void test() {
		final TracingConnectionFactoryBeanPostProcessor srv = new TracingConnectionFactoryBeanPostProcessor(beanFactory);
		srv.postProcessAfterInitialization(new Object(), "name");
		assertTrue(true);
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void test2(final Object param) throws JMSException {
		final TracingConnectionFactoryBeanPostProcessor srv = new TracingConnectionFactoryBeanPostProcessor(beanFactory);
		final Object res = srv.postProcessAfterInitialization(param, "name");
		if (res instanceof final LazyConnectionAndXaConnectionFactory cf) {
			when(beanFactory.getBean(JmsTracing.class)).thenReturn(jmsTracing);
			when(jmsTracing.connectionFactory(any())).thenReturn(connectionFactory);
			when(jmsTracing.xaConnectionFactory(any())).thenReturn(xaConnection);
			cf.createConnection();
			cf.createConnection(null, null);
			cf.createContext();
			cf.createContext(0);
			cf.createContext(null, null);
			cf.createContext(null, null, 0);
			cf.createXAConnection();
			cf.createXAConnection(null, null);
			cf.createXAContext();
			cf.createXAContext(null, null);
		}
		if (res instanceof final LazyTopicConnectionFactory cf) {
			when(beanFactory.getBean(JmsTracing.class)).thenReturn(jmsTracing);
			when(jmsTracing.connectionFactory(any())).thenReturn(connectionFactory);
			cf.createConnection();
			cf.createConnection(null, null);
			cf.createContext();
			cf.createContext(0);
			cf.createContext(null, null);
			cf.createContext(null, null, 0);
			cf.createTopicConnection();
			cf.createTopicConnection(null, null);
		}
		assertTrue(true);
	}

	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(Mockito.mock(CachingConnectionFactory.class)),
				Arguments.of(Mockito.mock(XAQueueConnectionFactory.class)),
				Arguments.of(Mockito.mock(XAConnectionFactory.class)),
				Arguments.of(Mockito.mock(TopicConnectionFactory.class)),
				Arguments.of(Mockito.mock(ConnectionFactory.class)));
	}

}
