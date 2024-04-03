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
package com.ubiqube.etsi.mano.service.event;

import java.util.EnumSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.SubscriptionServiceImpl;
import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.eval.EvalService;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
@Transactional
public class VnfEvent {
	/** Logger instance. */
	private static final Logger LOG = LoggerFactory.getLogger(VnfEvent.class);
	private final ServerService serverService;
	private final SubscriptionService subscriptionService;
	private final Notifications notifications;
	private final EventManager eventManager;
	private final EvalService evalService;
	private EnumSet<NotificationEvent> deleteEnums;

	public VnfEvent(final SubscriptionService subscriptionRepository, final Notifications notifications, final ServerService serverService,
			final EventManager eventManager, final EvalService evalService) {
		this.subscriptionService = subscriptionRepository;
		this.notifications = notifications;
		this.serverService = serverService;
		this.eventManager = eventManager;
		this.evalService = evalService;
		this.deleteEnums = EnumSet.of(NotificationEvent.VNF_INSTANCE_DELETE, NotificationEvent.NS_PKG_ONDELETION, NotificationEvent.NS_INSTANCE_DELETE, NotificationEvent.VNF_PKG_ONDELETION);
	}

	public void sendNotification(final Subscription subscription, final EventMessage event) {
		final ServerAdapter server = serverService.buildServerAdapter(subscription);
		final HttpGateway httpGateway = server.httpGateway();
		final Object object = httpGateway.createEvent(subscription.getId(), event);
		if (object == null) {
			LOG.warn("Skippping event {}, for {}", event, httpGateway.getClass().getSimpleName());
			return;
		}
		final var callbackUri = subscription.getCallbackUri();
		notifications.doNotification(object, callbackUri, server);
	}

	public void onEvent(final EventMessage ev) {
		final List<Subscription> res = subscriptionService.selectNotifications(ev);
		LOG.info("Event received: {}/{} with {} elements.", ev.getNotificationEvent(), ev.getObjectId(), res.size());
		res.stream()
				.filter(x -> isMatching(x, ev))
				.forEach(x -> {
					try {
						final SubscriptionEvent se = SubscriptionEvent.builder().event(ev).subscription(x).build();
						eventManager.notificationSender(se);
					} catch (final RuntimeException e) {
						LOG.error("Could not send notfication {}", x.getCallbackUri(), e);
					}
				});
	}

	private boolean isMatching(final Subscription subscription, final EventMessage ev) {
		final String filters = subscription.getNodeFilter();
		if (null == filters) {
			return true;
		}
		final Node nodes = evalService.convertStringToNode(filters);
		final String eventName = SubscriptionServiceImpl.convert(ev.getNotificationEvent());
		if (deleteEnums.contains(ev.getNotificationEvent())) {
			return evalService.evaluate(nodes, ev.getAdditionalParameters(), eventName);
		}
		return evalService.evaluate(nodes, ev.getObjectId(), subscription.getSubscriptionType(), eventName);
	}

}
