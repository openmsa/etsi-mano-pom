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
package com.ubiqube.etsi.mano.nfvo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.alarm.AlarmNotification;
import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.RemoteSubscriptionJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.AlarmNotificationJpa;

/**
 *
 * @author olivier
 *
 */
@Service
public class VnffmNotificationService {

	private static final Logger LOG = LoggerFactory.getLogger(VnffmNotificationService.class);
	private final RemoteSubscriptionJpa remoteSubscriptionJpa;

	private final AlarmNotificationJpa vnfFmJpa;

	public VnffmNotificationService(final RemoteSubscriptionJpa remoteSubscriptionJpa, final AlarmNotificationJpa vnfFmJpa) {
		this.remoteSubscriptionJpa = remoteSubscriptionJpa;
		this.vnfFmJpa = vnfFmJpa;
	}

	public void onNotification(final AlarmNotification event, final String version) {
		final List<RemoteSubscription> subscription = remoteSubscriptionJpa.findByRemoteSubscriptionId(event.getSubscriptionId());
		if (subscription.isEmpty()) {
			LOG.warn("Unable to find notification event {} in database.", event.getSubscriptionId());
			throw new NotFoundException("Unable to find notification event " + event.getSubscriptionId());
		}
		event.setNfvoId(subscription.get(0).getRemoteServerId());
		final AlarmNotification newEvent = vnfFmJpa.save(event);
		LOG.info("Event received: {} => Id: {}", newEvent.getNfvoId(), newEvent.getId());
	}

}
