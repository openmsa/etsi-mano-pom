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
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.JmsListenerEndpointRegistry;

import brave.Tracing;
import brave.messaging.MessagingTracing;
import brave.messaging.MessagingTracingCustomizer;
import brave.propagation.Propagation;

@ExtendWith(MockitoExtension.class)
class JmsTracerConfigurationTest {
	@Mock
	private BeanFactory beanFactory;
	@Mock
	private JmsListenerEndpointRegistry defaultRegistry;
	@Mock
	private TracingJmsBeanPostProcessor tracingBean;
	@Mock
	private JmsListenerEndpointRegistrar registar;
	@Mock
	private MessagingTracing jmsTracing;
	@Mock
	private Tracing tracing;
	@Mock
	private Propagation<String> propagation;

	@Test
	void test() {
		final JmsTracerConfiguration srv = new JmsTracerConfiguration();
		when(beanFactory.getBean(TracingJmsBeanPostProcessor.class)).thenReturn(tracingBean);
		final JmsListenerConfigurer res = srv.configureTracing(beanFactory, defaultRegistry);
		res.configureJmsListeners(registar);
		assertTrue(true);
	}

	@Test
	void testTracingJmsBeanPostProcessor() {
		final JmsTracerConfiguration srv = new JmsTracerConfiguration();
		final TracingJmsBeanPostProcessor res = srv.tracingJmsBeanPostProcessor(beanFactory);
		res.postProcessAfterInitialization(res, null);
		assertTrue(true);
	}

	@Test
	void testTracingJmsBeanPostProcessor2() {
		final JmsTracerConfiguration srv = new JmsTracerConfiguration();
		final TracingJmsBeanPostProcessor res = srv.tracingJmsBeanPostProcessor(beanFactory);
		final TracingJmsListenerEndpointRegistry reg = Mockito.mock(TracingJmsListenerEndpointRegistry.class);
		res.postProcessAfterInitialization(reg, null);
		assertTrue(true);
	}

	@Test
	void testTracingJmsBeanPostProcessor3() {
		final JmsTracerConfiguration srv = new JmsTracerConfiguration();
		final TracingJmsBeanPostProcessor res = srv.tracingJmsBeanPostProcessor(beanFactory);
		final JmsListenerEndpointRegistry reg = Mockito.mock(JmsListenerEndpointRegistry.class);
		res.postProcessAfterInitialization(reg, null);
		assertTrue(true);
	}

	@Test
	void testTracingConnectionFactoryBeanPostProcessor() {
		final JmsTracerConfiguration srv = new JmsTracerConfiguration();
		srv.tracingConnectionFactoryBeanPostProcessor(beanFactory);
		assertTrue(true);
	}

	@Test
	void testJmsTracing() {
		final JmsTracerConfiguration srv = new JmsTracerConfiguration();
		when(jmsTracing.tracing()).thenReturn(tracing);
		when(jmsTracing.propagation()).thenReturn(propagation);
		srv.jmsTracing(jmsTracing);
		assertTrue(true);
	}

	@Test
	void testMessagingTracing() {
		final JmsTracerConfiguration srv = new JmsTracerConfiguration();
		srv.messagingTracing(tracing, null, null, List.of());
		assertTrue(true);
	}

	@Test
	void testMessagingTracingNull() {
		final JmsTracerConfiguration srv = new JmsTracerConfiguration();
		srv.messagingTracing(tracing, null, null, null);
		assertTrue(true);
	}

	@Test
	void testMessagingTracing2() {
		final JmsTracerConfiguration srv = new JmsTracerConfiguration();
		final MessagingTracingCustomizer customizer = Mockito.mock(MessagingTracingCustomizer.class);
		srv.messagingTracing(tracing, null, null, List.of(customizer));
		assertTrue(true);
	}
}
