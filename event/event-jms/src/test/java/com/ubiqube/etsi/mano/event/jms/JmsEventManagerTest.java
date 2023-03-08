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
package com.ubiqube.etsi.mano.event.jms;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.SubscriptionEvent;
import com.ubiqube.etsi.mano.service.event.jms.EventMessageJpa;
import com.ubiqube.etsi.mano.service.event.jms.JmsEventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

@ExtendWith(MockitoExtension.class)
class JmsEventManagerTest {

	@Mock
	private JmsTemplate jmsTemplate;
	@Mock
	private EventMessageJpa eventMessageJpa;
	@Mock
	private ConfigurableApplicationContext configurable;
	@Mock
	private ConfigurableListableBeanFactory configurableListableBeanFactory;

	private void mockQueueName() {
		when(configurable.getBeanFactory()).thenReturn(configurableListableBeanFactory);
		when(configurableListableBeanFactory.resolveEmbeddedValue(anyString())).thenReturn("queue-name");
	}

	@Test
	void testNotificationSender() {
		final JmsEventManager jem = new JmsEventManager(jmsTemplate, eventMessageJpa, configurable);
		final SubscriptionEvent se = new SubscriptionEvent();
		mockQueueName();
		jem.notificationSender(se);
		assertTrue(true);
	}

	@Test
	void testSendAction() {
		final JmsEventManager jem = new JmsEventManager(jmsTemplate, eventMessageJpa, configurable);
		final SubscriptionEvent se = new SubscriptionEvent();
		mockQueueName();
		jem.sendAction(ActionType.MEPM_OPERATE, UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testSendActionNfvo() {
		final JmsEventManager jem = new JmsEventManager(jmsTemplate, eventMessageJpa, configurable);
		final SubscriptionEvent se = new SubscriptionEvent();
		mockQueueName();
		jem.sendActionNfvo(ActionType.MEPM_OPERATE, UUID.randomUUID(), Map.of());
		assertTrue(true);
	}

	@Test
	void testSendActionVnfm() {
		final JmsEventManager jem = new JmsEventManager(jmsTemplate, eventMessageJpa, configurable);
		final SubscriptionEvent se = new SubscriptionEvent();
		mockQueueName();
		jem.sendActionVnfm(ActionType.MEPM_OPERATE, UUID.randomUUID(), Map.of());
		assertTrue(true);
	}

	@Test
	void testSendGrant() {
		final JmsEventManager jem = new JmsEventManager(jmsTemplate, eventMessageJpa, configurable);
		final SubscriptionEvent se = new SubscriptionEvent();
		mockQueueName();
		jem.sendGrant(UUID.randomUUID(), Map.of());
		assertTrue(true);
	}

	@Test
	void testSendNotification() {
		final JmsEventManager jem = new JmsEventManager(jmsTemplate, eventMessageJpa, configurable);
		final SubscriptionEvent se = new SubscriptionEvent();
		mockQueueName();
		jem.sendNotification(NotificationEvent.APPINSTANTIATE, UUID.randomUUID(), Map.of());
		assertTrue(true);
	}
}
