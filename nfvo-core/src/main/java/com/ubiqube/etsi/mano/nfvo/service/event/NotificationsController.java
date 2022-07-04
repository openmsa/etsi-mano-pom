/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.nfvo.service.event;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.NotificationController;
import com.ubiqube.etsi.mano.service.event.SubscriptionEvent;
import com.ubiqube.etsi.mano.service.event.VnfEvent;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NotificationsController implements NotificationController {
	private final VnfEvent vnfEvent;

	public NotificationsController(final VnfEvent vnfEvent) {
		this.vnfEvent = vnfEvent;
	}

	@Override
	public void onEvent(final EventMessage ev) {
		vnfEvent.onEvent(ev);
	}

	@Override
	public void onNotificationSender(final SubscriptionEvent se) {
		vnfEvent.sendNotification(se.getSubscription(), se.getEvent());
	}
}
