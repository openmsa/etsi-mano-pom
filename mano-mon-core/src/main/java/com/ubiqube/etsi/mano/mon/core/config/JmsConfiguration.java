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

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ubiqube.etsi.mano.mon.core.Constants;
import com.ubiqube.etsi.mano.mon.core.service.ExpirityJmsTemplate;

import jakarta.jms.ConnectionFactory;

/**
 *
 * @author olivier
 *
 */
@SuppressWarnings("static-method")
@Configuration
public class JmsConfiguration {
	@Bean
	MessageConverter jacksonJmsMessageConverter(final ObjectMapper mapper) {
		final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		mapper.registerModule(new JavaTimeModule());
		converter.setObjectMapper(mapper);
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean
	JmsListenerContainerFactory serialzeDataFactory(final ConnectionFactory connectionFactory, final DefaultJmsListenerContainerFactoryConfigurer configurer) {
		final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message
		// converter
		configurer.configure(factory, connectionFactory);
		// You could still override some of Boot's default if necessary.
		factory.setPubSubDomain(true);
		return factory;
	}

	@Bean
	@Primary
	JmsTemplate queueJmsTemplate(final ConnectionFactory connFactory, final MessageConverter messageConverter) {
		final JmsTemplate jmsTemplate = new JmsTemplate(connFactory);
		jmsTemplate.setPubSubDomain(false);
		jmsTemplate.setMessageConverter(messageConverter);
		return jmsTemplate;
	}

	@Bean
	JmsTemplate topicJmsTemplate(final ConnectionFactory connFactory, final MessageConverter messageConverter) {
		final JmsTemplate jmsTemplate = new JmsTemplate(connFactory);
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.setMessageConverter(messageConverter);
		return jmsTemplate;
	}

	@Bean
	ExpirityJmsTemplate expirityQueueJmsTemplate(final ConnectionFactory connFactory, final MessageConverter messageConverter) {
		final ExpirityJmsTemplate jmsTemplate = new ExpirityJmsTemplate(connFactory);
		jmsTemplate.setMessageConverter(messageConverter);
		jmsTemplate.setExplicitQosEnabled(true);
		jmsTemplate.setTimeToLive(Constants.MANO_MON_TICK_MILLIS);
		return jmsTemplate;
	}
}
