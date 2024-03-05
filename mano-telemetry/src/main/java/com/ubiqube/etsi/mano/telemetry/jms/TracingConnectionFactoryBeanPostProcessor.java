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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.listener.endpoint.JmsMessageEndpointManager;

import brave.jakarta.jms.JmsTracing;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TopicConnection;
import jakarta.jms.TopicConnectionFactory;
import jakarta.jms.XAConnection;
import jakarta.jms.XAConnectionFactory;
import jakarta.jms.XAJMSContext;

public class TracingConnectionFactoryBeanPostProcessor implements BeanPostProcessor {

	private final BeanFactory beanFactory;

	TracingConnectionFactoryBeanPostProcessor(final BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName)
			throws BeansException {
		// Wrap the caching connection factories instead of its target, because it
		// catches
		// callbacks
		// such as ExceptionListener. If we don't wrap, cached callbacks like this won't
		// be traced.
		if (bean instanceof final CachingConnectionFactory ccf) {
			return new LazyConnectionFactory(this.beanFactory, ccf);
		}
		if (bean instanceof final JmsMessageEndpointManager manager) {
			final MessageListener listener = manager.getMessageListener();
			manager.setMessageListener(
					new LazyMessageListener(this.beanFactory, listener));
			return bean;
		}
		if ((bean instanceof final XAConnectionFactory xacf) && (bean instanceof final ConnectionFactory cf)) {
			return new LazyConnectionAndXaConnectionFactory(this.beanFactory,
					cf, xacf);
		}
		if (bean instanceof final XAConnectionFactory xacf) {
			return new LazyXAConnectionFactory(this.beanFactory, xacf);
		}
		if (bean instanceof final TopicConnectionFactory tcf) {
			return new LazyTopicConnectionFactory(this.beanFactory, tcf);
		}
		if (bean instanceof final ConnectionFactory cf) {
			return new LazyConnectionFactory(this.beanFactory, cf);
		}
		return bean;
	}

}

class LazyXAConnectionFactory implements XAConnectionFactory {

	private final BeanFactory beanFactory;

	private final XAConnectionFactory delegate;

	private JmsTracing jmsTracing;

	private XAConnectionFactory wrappedDelegate;

	LazyXAConnectionFactory(final BeanFactory beanFactory, final XAConnectionFactory delegate) {
		this.beanFactory = beanFactory;
		this.delegate = delegate;
	}

	@Override
	public XAConnection createXAConnection() throws JMSException {
		return wrappedDelegate().createXAConnection();
	}

	@Override
	public XAConnection createXAConnection(final String s, final String s1) throws JMSException {
		return wrappedDelegate().createXAConnection(s, s1);
	}

	@Override
	public XAJMSContext createXAContext() {
		return wrappedDelegate().createXAContext();
	}

	@Override
	public XAJMSContext createXAContext(final String s, final String s1) {
		return wrappedDelegate().createXAContext(s, s1);
	}

	private JmsTracing jmsTracing() {
		if (this.jmsTracing != null) {
			return this.jmsTracing;
		}
		this.jmsTracing = this.beanFactory.getBean(JmsTracing.class);
		return this.jmsTracing;
	}

	private XAConnectionFactory wrappedDelegate() {
		if (this.wrappedDelegate != null) {
			return this.wrappedDelegate;
		}
		this.wrappedDelegate = jmsTracing().xaConnectionFactory(this.delegate);
		return this.wrappedDelegate;
	}

}

class LazyTopicConnectionFactory implements TopicConnectionFactory {

	private final BeanFactory beanFactory;

	private final TopicConnectionFactory delegate;

	private final LazyConnectionFactory factory;

	private JmsTracing jmsTracing;

	LazyTopicConnectionFactory(final BeanFactory beanFactory, final TopicConnectionFactory delegate) {
		this.beanFactory = beanFactory;
		this.delegate = delegate;
		this.factory = new LazyConnectionFactory(beanFactory, delegate);
	}

	@Override
	public TopicConnection createTopicConnection() throws JMSException {
		return jmsTracing().topicConnection(this.delegate.createTopicConnection());
	}

	@Override
	public TopicConnection createTopicConnection(final String s, final String s1)
			throws JMSException {
		return jmsTracing().topicConnection(this.delegate.createTopicConnection(s, s1));
	}

	@Override
	public Connection createConnection() throws JMSException {
		return this.factory.createConnection();
	}

	@Override
	public Connection createConnection(final String s, final String s1) throws JMSException {
		return this.factory.createConnection(s, s1);
	}

	@Override
	public JMSContext createContext() {
		return this.factory.createContext();
	}

	@Override
	public JMSContext createContext(final String s, final String s1) {
		return this.factory.createContext(s, s1);
	}

	@Override
	public JMSContext createContext(final String s, final String s1, final int i) {
		return this.factory.createContext(s, s1, i);
	}

	@Override
	public JMSContext createContext(final int i) {
		return this.factory.createContext(i);
	}

	private JmsTracing jmsTracing() {
		if (this.jmsTracing != null) {
			return this.jmsTracing;
		}
		this.jmsTracing = this.beanFactory.getBean(JmsTracing.class);
		return this.jmsTracing;
	}

}

class LazyConnectionFactory implements ConnectionFactory {

	private final BeanFactory beanFactory;

	private final ConnectionFactory delegate;

	private JmsTracing jmsTracing;

	private ConnectionFactory wrappedDelegate;

	LazyConnectionFactory(final BeanFactory beanFactory, final ConnectionFactory delegate) {
		this.beanFactory = beanFactory;
		this.delegate = delegate;
	}

	@Override
	public Connection createConnection() throws JMSException {
		return wrappedDelegate().createConnection();
	}

	@Override
	public Connection createConnection(final String s, final String s1) throws JMSException {
		return wrappedDelegate().createConnection(s, s1);
	}

	@Override
	public JMSContext createContext() {
		return wrappedDelegate().createContext();
	}

	@Override
	public JMSContext createContext(final String s, final String s1) {
		return wrappedDelegate().createContext(s, s1);
	}

	@Override
	public JMSContext createContext(final String s, final String s1, final int i) {
		return wrappedDelegate().createContext(s, s1, i);
	}

	@Override
	public JMSContext createContext(final int i) {
		return wrappedDelegate().createContext(i);
	}

	private JmsTracing jmsTracing() {
		if (this.jmsTracing != null) {
			return this.jmsTracing;
		}
		this.jmsTracing = this.beanFactory.getBean(JmsTracing.class);
		return this.jmsTracing;
	}

	private ConnectionFactory wrappedDelegate() {
		if (this.wrappedDelegate != null) {
			return this.wrappedDelegate;
		}
		this.wrappedDelegate = jmsTracing().connectionFactory(this.delegate);
		return this.wrappedDelegate;
	}

}

class LazyConnectionAndXaConnectionFactory
		implements ConnectionFactory, XAConnectionFactory {

	private final ConnectionFactory connectionFactoryDelegate;

	private final XAConnectionFactory xaConnectionFactoryDelegate;

	LazyConnectionAndXaConnectionFactory(final BeanFactory beanFactory,
			final ConnectionFactory connectionFactoryDelegate,
			final XAConnectionFactory xaConnectionFactoryDelegate) {
		this.connectionFactoryDelegate = new LazyConnectionFactory(beanFactory,
				connectionFactoryDelegate);
		this.xaConnectionFactoryDelegate = new LazyXAConnectionFactory(beanFactory,
				xaConnectionFactoryDelegate);
	}

	@Override
	public Connection createConnection() throws JMSException {
		return this.connectionFactoryDelegate.createConnection();
	}

	@Override
	public Connection createConnection(final String userName, final String password)
			throws JMSException {
		return this.connectionFactoryDelegate.createConnection(userName, password);
	}

	@Override
	public JMSContext createContext() {
		return this.connectionFactoryDelegate.createContext();
	}

	@Override
	public JMSContext createContext(final String userName, final String password) {
		return this.connectionFactoryDelegate.createContext(userName, password);
	}

	@Override
	public JMSContext createContext(final String userName, final String password, final int sessionMode) {
		return this.connectionFactoryDelegate.createContext(userName, password,
				sessionMode);
	}

	@Override
	public JMSContext createContext(final int sessionMode) {
		return this.connectionFactoryDelegate.createContext(sessionMode);
	}

	@Override
	public XAConnection createXAConnection() throws JMSException {
		return this.xaConnectionFactoryDelegate.createXAConnection();
	}

	@Override
	public XAConnection createXAConnection(final String userName, final String password)
			throws JMSException {
		return this.xaConnectionFactoryDelegate.createXAConnection(userName, password);
	}

	@Override
	public XAJMSContext createXAContext() {
		return this.xaConnectionFactoryDelegate.createXAContext();
	}

	@Override
	public XAJMSContext createXAContext(final String userName, final String password) {
		return this.xaConnectionFactoryDelegate.createXAContext(userName, password);
	}

}

class LazyMessageListener implements MessageListener {

	private final BeanFactory beanFactory;

	private final MessageListener delegate;

	private JmsTracing jmsTracing;

	LazyMessageListener(final BeanFactory beanFactory, final MessageListener delegate) {
		this.beanFactory = beanFactory;
		this.delegate = delegate;
	}

	@Override
	public void onMessage(final Message message) {
		wrappedDelegate().onMessage(message);
	}

	private JmsTracing jmsTracing() {
		if (this.jmsTracing != null) {
			return this.jmsTracing;
		}
		this.jmsTracing = this.beanFactory.getBean(JmsTracing.class);
		return this.jmsTracing;
	}

	private MessageListener wrappedDelegate() {
		// Adds a consumer span as we have no visibility into JCA's implementation of
		// messaging
		return jmsTracing().messageListener(this.delegate, true);
	}

}
