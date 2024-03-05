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
package com.ubiqube.etsi.mano.mon.core.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.jms.support.converter.MessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.jms.ConnectionFactory;

class JmsConfigurationTest {

	@SuppressWarnings("static-method")
	@Test
	void test() {
		final JmsConfiguration jc = new JmsConfiguration();
		final MessageConverter mc = Mockito.mock(MessageConverter.class);
		final ConnectionFactory connFactory = Mockito.mock(ConnectionFactory.class);
		jc.expirityQueueJmsTemplate(connFactory, mc);
		final ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
		jc.jacksonJmsMessageConverter(mapper);
		final DefaultJmsListenerContainerFactoryConfigurer configurer = Mockito.mock(DefaultJmsListenerContainerFactoryConfigurer.class);
		jc.serialzeDataFactory(connFactory, configurer);
		jc.queueJmsTemplate(connFactory, mc);
		jc.topicJmsTemplate(connFactory, mc);
		assertTrue(true);
	}

}
