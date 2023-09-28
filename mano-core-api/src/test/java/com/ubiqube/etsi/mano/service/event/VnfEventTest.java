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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.event;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.eval.EvalService;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

@ExtendWith(MockitoExtension.class)
class VnfEventTest {
	@Mock
	private SubscriptionService subscriptionRepo;
	@Mock
	private Notifications notifications;
	@Mock
	private ServerService serverService;
	@Mock
	private EventManager eventManager;
	@Mock
	private EvalService evalService;
	@Mock
	private HttpGateway httpGateway;
	@Mock
	private FluxRest fluxRest;

	private final Servers server = Servers.builder()
			.url(URI.create("http://localhost/"))
			.build();

	@Test
	void testSendNotification001() throws Exception {
		final VnfEvent vnfEvent = new VnfEvent(subscriptionRepo, notifications, serverService, eventManager, evalService);
		final Subscription subsc = Subscription.builder()
				.build();
		final EventMessage event = new EventMessage(NotificationEvent.APP_INSTANCE_CREATE, UUID.randomUUID(), Map.of());
		//
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateway, server, fluxRest);
		//
		when(serverService.buildServerAdapter(subsc)).thenReturn(serverAdapter);
		vnfEvent.sendNotification(subsc, event);
		assertTrue(true);
	}

	@Test
	void testSendNotification002() throws Exception {
		final VnfEvent vnfEvent = new VnfEvent(subscriptionRepo, notifications, serverService, eventManager, evalService);
		final Subscription subsc = Subscription.builder()
				.id(UUID.randomUUID())
				.callbackUri(URI.create("http://test-uri/"))
				.build();
		final EventMessage event = new EventMessage(NotificationEvent.APP_INSTANCE_CREATE, UUID.randomUUID(), Map.of());
		//
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateway, server, fluxRest);
		//
		when(serverService.buildServerAdapter(subsc)).thenReturn(serverAdapter);
		final Object ret = new Object();
		when(httpGateway.createEvent(subsc.getId(), event)).thenReturn(ret);
		vnfEvent.sendNotification(subsc, event);
		verify(notifications).doNotification(ret, subsc.getCallbackUri(), serverAdapter);
	}

	@Test
	void testOnEvent001() throws Exception {
		final VnfEvent vnfEvent = new VnfEvent(subscriptionRepo, notifications, serverService, eventManager, evalService);
		final EventMessage event = new EventMessage(NotificationEvent.APP_INSTANCE_CREATE, UUID.randomUUID(), Map.of());
		vnfEvent.onEvent(event);
		assertTrue(true);
	}

	@Test
	void testOnEvent002() throws Exception {
		final VnfEvent vnfEvent = new VnfEvent(subscriptionRepo, notifications, serverService, eventManager, evalService);
		final EventMessage event = new EventMessage(NotificationEvent.APP_INSTANCE_CREATE, UUID.randomUUID(), Map.of());
		final Subscription subsc1 = Subscription.builder()
				.id(UUID.randomUUID())
				.callbackUri(URI.create("http://test-uri/"))
				.build();
		final Subscription subsc2 = Subscription.builder()
				.id(UUID.randomUUID())
				.callbackUri(URI.create("http://test-uri/"))
				.nodeFilter("{}")
				.build();
		//
		when(subscriptionRepo.selectNotifications(event)).thenReturn(List.of(subsc1, subsc2));
		vnfEvent.onEvent(event);
		assertTrue(true);
	}
}
