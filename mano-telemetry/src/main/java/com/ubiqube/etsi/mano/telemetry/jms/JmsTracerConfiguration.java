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

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistry;

import brave.Tracing;
import brave.jakarta.jms.JmsTracing;
import brave.messaging.MessagingRequest;
import brave.messaging.MessagingTracing;
import brave.messaging.MessagingTracingCustomizer;
import brave.sampler.SamplerFunction;
import jakarta.annotation.Nullable;

@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class JmsTracerConfiguration {
	@Bean
	@ConditionalOnMissingBean
	// NOTE: stable bean name as might be used outside sleuth
	MessagingTracing messagingTracing(final Tracing tracing,
			@Nullable final SamplerFunction<MessagingRequest> producerSampler,
			@Nullable final SamplerFunction<MessagingRequest> consumerSampler,
			@Nullable final List<MessagingTracingCustomizer> messagingTracingCustomizers) {

		final MessagingTracing.Builder builder = MessagingTracing.newBuilder(tracing);
		if (producerSampler != null) {
			builder.producerSampler(producerSampler);
		}
		if (consumerSampler != null) {
			builder.consumerSampler(consumerSampler);
		}
		if (messagingTracingCustomizers != null) {
			for (final MessagingTracingCustomizer customizer : messagingTracingCustomizers) {
				customizer.customize(builder);
			}
		}
		return builder.build();
	}

	@Bean
	@ConditionalOnMissingBean
	JmsTracing jmsTracing(final MessagingTracing messagingTracing) {
		return JmsTracing.newBuilder(messagingTracing)
				.remoteServiceName(
						"remote-jms")
				.build();
	}

	@Bean
	// for tests
	@ConditionalOnMissingBean
	TracingConnectionFactoryBeanPostProcessor tracingConnectionFactoryBeanPostProcessor(
			final BeanFactory beanFactory) {
		return new TracingConnectionFactoryBeanPostProcessor(beanFactory);
	}

	@Bean
	JmsListenerConfigurer configureTracing(final BeanFactory beanFactory,
			final JmsListenerEndpointRegistry defaultRegistry) {
		return registrar -> {
			final TracingJmsBeanPostProcessor processor = beanFactory
					.getBean(TracingJmsBeanPostProcessor.class);
			final JmsListenerEndpointRegistry registry = registrar.getEndpointRegistry();
			registrar.setEndpointRegistry((JmsListenerEndpointRegistry) processor
					.wrap(registry == null ? defaultRegistry : registry));
		};
	}

	// Setup the tracing endpoint registry.
	@Bean
	TracingJmsBeanPostProcessor tracingJmsBeanPostProcessor(final BeanFactory beanFactory) {
		return new TracingJmsBeanPostProcessor(beanFactory);
	}
}

class TracingJmsBeanPostProcessor implements BeanPostProcessor {

	private final BeanFactory beanFactory;

	TracingJmsBeanPostProcessor(final BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName)
			throws BeansException {
		return wrap(bean);
	}

	Object wrap(final Object bean) {
		if (typeMatches(bean)) {
			return new TracingJmsListenerEndpointRegistry(
					(JmsListenerEndpointRegistry) bean, this.beanFactory);
		}
		return bean;
	}

	private boolean typeMatches(final Object bean) {
		return (bean instanceof JmsListenerEndpointRegistry)
				&& !(bean instanceof TracingJmsListenerEndpointRegistry);
	}

}