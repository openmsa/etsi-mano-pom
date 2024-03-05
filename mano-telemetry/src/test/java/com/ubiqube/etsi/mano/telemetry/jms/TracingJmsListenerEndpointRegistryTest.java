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

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.config.MethodJmsListenerEndpoint;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

import brave.jakarta.jms.JmsTracing;
import jakarta.jms.MessageListener;

@ExtendWith(MockitoExtension.class)
class TracingJmsListenerEndpointRegistryTest {
	@Mock
	private JmsListenerEndpointRegistry registry;
	@Mock
	private BeanFactory beanFactory;
	@Mock
	private JmsTracing jmsTracing;

	@Test
	void test() {
		final TracingJmsListenerEndpointRegistry srv = new TracingJmsListenerEndpointRegistry(registry, beanFactory);
		srv.getListenerContainer(null);
		assertTrue(true);
	}

	@Test
	void testGetListenerContainerIds() {
		final TracingJmsListenerEndpointRegistry srv = new TracingJmsListenerEndpointRegistry(registry, beanFactory);
		srv.getListenerContainerIds();
		assertTrue(true);
	}

	@Test
	void testGetListenerContainers() {
		final TracingJmsListenerEndpointRegistry srv = new TracingJmsListenerEndpointRegistry(registry, beanFactory);
		srv.getListenerContainers();
		assertTrue(true);
	}

	@Test
	void testPhase() {
		final TracingJmsListenerEndpointRegistry srv = new TracingJmsListenerEndpointRegistry(registry, beanFactory);
		srv.getPhase();
		assertTrue(true);
	}

	@Test
	void testStartStop() {
		final TracingJmsListenerEndpointRegistry srv = new TracingJmsListenerEndpointRegistry(registry, beanFactory);
		srv.start();
		srv.stop();
		srv.stop(() -> {
		});
		srv.isRunning();
		srv.destroy();
		srv.isAutoStartup();
		srv.setApplicationContext(null);
		srv.onApplicationEvent(null);
		assertTrue(true);
	}

	@Test
	void testRegisterListenerContainer() {
		final TracingJmsListenerEndpointRegistry srv = new TracingJmsListenerEndpointRegistry(registry, beanFactory);
		srv.registerListenerContainer(null, null);
		assertTrue(true);
	}

	@Test
	void testRegisterListenerContainer2() {
		final TracingJmsListenerEndpointRegistry srv = new TracingJmsListenerEndpointRegistry(registry, beanFactory);
		final MethodJmsListenerEndpoint endp = Mockito.mock(MethodJmsListenerEndpoint.class);
		final JmsListenerContainerFactory<?> factory = Mockito.mock(JmsListenerContainerFactory.class);
		srv.registerListenerContainer(endp, factory, true);
		assertTrue(true);
	}

	@Test
	void testRegisterListenerContainer3() {
		final TracingJmsListenerEndpointRegistry srv = new TracingJmsListenerEndpointRegistry(registry, beanFactory);
		final SimpleJmsListenerEndpoint endp = Mockito.mock(SimpleJmsListenerEndpoint.class);
		final MessageListener messageListener = Mockito.mock(MessageListener.class);
		when(beanFactory.getBean(JmsTracing.class)).thenReturn(jmsTracing);
		when(endp.getMessageListener()).thenReturn(messageListener);
		srv.registerListenerContainer(endp, null, true);
		assertTrue(true);
	}

	@Test
	void testGet() throws IllegalAccessException {
		final TracingJmsListenerEndpointRegistry srv = new TracingJmsListenerEndpointRegistry(registry, beanFactory);
		final Field filed = Mockito.mock(Field.class);
		srv.get("", filed);
		assertTrue(true);
	}

	@Test
	void testTryField() {
		final TracingJmsListenerEndpointRegistry srv = new TracingJmsListenerEndpointRegistry(registry, beanFactory);
		srv.tryField("bad");
		assertTrue(true);
	}

}
