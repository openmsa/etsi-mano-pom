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

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpoint;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.config.MethodJmsListenerEndpoint;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.adapter.MessagingMessageListenerAdapter;

import brave.Span;
import brave.jakarta.jms.JmsTracing;
import brave.propagation.CurrentTraceContext;
import jakarta.annotation.Nullable;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.Session;

public class TracingJmsListenerEndpointRegistry
		extends JmsListenerEndpointRegistry {

	private final BeanFactory beanFactory;

	private final JmsListenerEndpointRegistry delegate;

	private JmsTracing jmsTracing;

	private CurrentTraceContext currentTraceContext;

// Not all state can be copied without using reflection
	final Field messageHandlerMethodFactoryField;

	final Field embeddedValueResolverField;

	public TracingJmsListenerEndpointRegistry(final JmsListenerEndpointRegistry registry,
			final BeanFactory beanFactory) {
		this.delegate = registry;
		this.beanFactory = beanFactory;
		this.messageHandlerMethodFactoryField = tryField("messageHandlerMethodFactory");
		this.embeddedValueResolverField = tryField("embeddedValueResolver");
	}

	private JmsTracing jmsTracing() {
		if (this.jmsTracing == null) {
			this.jmsTracing = this.beanFactory.getBean(JmsTracing.class);
		}
		return this.jmsTracing;
	}

	private CurrentTraceContext currentTraceContext() {
		if (this.currentTraceContext == null) {
			this.currentTraceContext = this.beanFactory
					.getBean(CurrentTraceContext.class);
		}
		return this.currentTraceContext;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.delegate.setApplicationContext(applicationContext);
	}

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		this.delegate.onApplicationEvent(event);
	}

	@Override
	public MessageListenerContainer getListenerContainer(final String id) {
		return this.delegate.getListenerContainer(id);
	}

	@Override
	public Set<String> getListenerContainerIds() {
		return this.delegate.getListenerContainerIds();
	}

	@Override
	public Collection<MessageListenerContainer> getListenerContainers() {
		return this.delegate.getListenerContainers();
	}

	@Override
	public void registerListenerContainer(final JmsListenerEndpoint endpoint,
			final JmsListenerContainerFactory<?> factory) {
		this.delegate.registerListenerContainer(wrapEndpoint(endpoint), factory);
	}

	@Override
	public int getPhase() {
		return this.delegate.getPhase();
	}

	@Override
	public void start() {
		this.delegate.start();
	}

	@Override
	public void stop() {
		this.delegate.stop();
	}

	@Override
	public void stop(final Runnable callback) {
		this.delegate.stop(callback);
	}

	@Override
	public boolean isRunning() {
		return this.delegate.isRunning();
	}

	@Override
	public void destroy() {
		this.delegate.destroy();
	}

	@Override
	public boolean isAutoStartup() {
		return this.delegate.isAutoStartup();
	}

	@Nullable
	static Field tryField(final String name) {
		try {
			final Field field = MethodJmsListenerEndpoint.class.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (final NoSuchFieldException e) {
			return null;
		}
	}

	@Nullable
	static <T> T get(final Object object, final Field field) throws IllegalAccessException {
		return (T) field.get(object);
	}

	@Override
	public void registerListenerContainer(final JmsListenerEndpoint endpoint,
			final JmsListenerContainerFactory<?> factory, final boolean startImmediately) {
		this.delegate.registerListenerContainer(wrapEndpoint(endpoint), factory,
				startImmediately);
	}

	private JmsListenerEndpoint wrapEndpoint(final JmsListenerEndpoint endpoint) {
		if (endpoint instanceof final MethodJmsListenerEndpoint mjle) {
			return trace(mjle);
		}
		if (endpoint instanceof final SimpleJmsListenerEndpoint sjle) {
			return trace(sjle);
		}
		return endpoint;
	}

	/**
	 * This wraps the {@link SimpleJmsListenerEndpoint#getMessageListener()}
	 * delegate in a new span.
	 *
	 * @param source jms endpoint
	 * @return wrapped endpoint
	 */
	SimpleJmsListenerEndpoint trace(final SimpleJmsListenerEndpoint source) {
		final MessageListener del = source.getMessageListener();
		if (del == null) {
			return source;
		}
		source.setMessageListener(jmsTracing().messageListener(del, false));
		return source;
	}

	/**
	 * It would be better to trace by wrapping, but
	 * {@link MethodJmsListenerEndpoint#createMessageListenerInstance()}, is
	 * protected so we can't call it from outside code. In other words, a forwarding
	 * pattern can't be used. Instead, we copy state from the input.
	 * <p>
	 * NOTE: As {@linkplain MethodJmsListenerEndpoint} is neither final, nor
	 * effectively final. For this reason we can't ensure copying will get all
	 * state. For example, a subtype could hold state we aren't aware of, or change
	 * behavior. We can consider checking that input is not a subtype, and most
	 * conservatively leaving unknown subtypes untraced.
	 *
	 * @param source jms endpoint
	 * @return wrapped endpoint
	 */
	MethodJmsListenerEndpoint trace(final MethodJmsListenerEndpoint source) {
		// Skip out rather than incompletely copying the source
		if ((this.messageHandlerMethodFactoryField == null)
				|| (this.embeddedValueResolverField == null)) {
			return source;
		}
		// We want the stock implementation, except we want to wrap the message listener
		// in a new span
		final MethodJmsListenerEndpoint dest = new MethodJmsListenerEndpoint() {
			@Override
			protected MessagingMessageListenerAdapter createMessageListenerInstance() {
				return new TracingMessagingMessageListenerAdapter(jmsTracing(),
						currentTraceContext());
			}
		};
		// set state from AbstractJmsListenerEndpoint
		dest.setId(source.getId());
		dest.setDestination(source.getDestination());
		dest.setSubscription(source.getSubscription());
		dest.setSelector(source.getSelector());
		dest.setConcurrency(source.getConcurrency());
		// set state from MethodJmsListenerEndpoint
		dest.setBean(source.getBean());
		dest.setMethod(source.getMethod());
		dest.setMostSpecificMethod(source.getMostSpecificMethod());
		try {
			dest.setMessageHandlerMethodFactory(
					get(source, this.messageHandlerMethodFactoryField));
			dest.setEmbeddedValueResolver(get(source, this.embeddedValueResolverField));
		} catch (final IllegalAccessException e) {
			return source; // skip out rather than incompletely copying the source
		}
		return dest;
	}

}

final class TracingMethodJmsListenerEndpoint extends MethodJmsListenerEndpoint {
	//
}

/**
 * This wraps the message listener in a child span.
 */
final class TracingMessagingMessageListenerAdapter
		extends MessagingMessageListenerAdapter {

	final JmsTracing jmsTracing;

	final CurrentTraceContext current;

	TracingMessagingMessageListenerAdapter(final JmsTracing jmsTracing,
			final CurrentTraceContext current) {
		this.jmsTracing = jmsTracing;
		this.current = current;
	}

	@Override
	public void onMessage(final Message message, final @Nullable Session session) throws JMSException {
		final Span span = this.jmsTracing.nextSpan(message).name("on-message").start();
		try (CurrentTraceContext.Scope ws = this.current.newScope(span.context())) {
			super.onMessage(message, session);
		} catch (JMSException | RuntimeException | Error e) {
			span.error(e);
			throw e;
		} finally {
			span.finish();
		}
	}

}
