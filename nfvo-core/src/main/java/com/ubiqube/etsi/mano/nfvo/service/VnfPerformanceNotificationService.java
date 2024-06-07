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

import com.ubiqube.etsi.mano.dao.mano.pm.PerformanceInformationAvailableNotification;
import com.ubiqube.etsi.mano.dao.mano.pm.ThresholdCrossedNotification;
import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.RemoteSubscriptionJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.PerformanceInformationAvailableNotificationJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.ThresholdCrossedNotificationJpa;

/**
 *
 * @author olivier
 *
 */
@Service
public class VnfPerformanceNotificationService {

	private static final Logger LOG = LoggerFactory.getLogger(VnfPerformanceNotificationService.class);

	private final RemoteSubscriptionJpa remoteSubscriptionJpa;

	private final ThresholdCrossedNotificationJpa vnfPmJpa;

	private final PerformanceInformationAvailableNotificationJpa availableJpa;

	public VnfPerformanceNotificationService(final RemoteSubscriptionJpa remoteSubscriptionJpa, final ThresholdCrossedNotificationJpa vnfIndJpa,
			final PerformanceInformationAvailableNotificationJpa availableJpa) {
		this.remoteSubscriptionJpa = remoteSubscriptionJpa;
		this.vnfPmJpa = vnfIndJpa;
		this.availableJpa = availableJpa;
	}

	public void onCrossedNotification(final ThresholdCrossedNotification event, final String version) {
		final List<RemoteSubscription> subscription = remoteSubscriptionJpa.findByRemoteSubscriptionId(event.getSubscriptionId());
		if (subscription.isEmpty()) {
			LOG.warn("Unable to find notification event {} in database.", event.getSubscriptionId());
			throw new NotFoundException("Unable to find notification event " + event.getSubscriptionId());
		}
		event.setNfvoId(subscription.getFirst().getRemoteServerId());
		final ThresholdCrossedNotification newEvent = vnfPmJpa.save(event);
		LOG.info("Event received: {} => Id: {}", newEvent.getNfvoId(), newEvent.getId());
	}

	public void onAvailableNotification(final PerformanceInformationAvailableNotification event, final String version) {
		final List<RemoteSubscription> subscription = remoteSubscriptionJpa.findByRemoteSubscriptionId(event.getSubscriptionId());
		if (subscription.isEmpty()) {
			LOG.warn("Unable to find notification event {} in database.", event.getSubscriptionId());
			throw new NotFoundException("Unable to find notification event " + event.getSubscriptionId());
		}
		event.setNfvoId(subscription.getFirst().getRemoteServerId());
		final PerformanceInformationAvailableNotification newEvent = availableJpa.save(event);
		LOG.info("Event received: {} => Id: {}", newEvent.getNfvoId(), newEvent.getId());
	}

}
