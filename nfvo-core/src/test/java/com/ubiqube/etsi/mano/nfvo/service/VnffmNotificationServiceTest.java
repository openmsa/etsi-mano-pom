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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.alarm.AlarmNotification;
import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.RemoteSubscriptionJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.AlarmNotificationJpa;

@ExtendWith(MockitoExtension.class)
class VnffmNotificationServiceTest {
	@Mock
	private RemoteSubscriptionJpa remoteSubscriptionJpa;
	@Mock
	private AlarmNotificationJpa vnfFmJpa;

	@Test
	void testName() throws Exception {
		final VnffmNotificationService srv = new VnffmNotificationService(remoteSubscriptionJpa, vnfFmJpa);
		final AlarmNotification event = new AlarmNotification();
		final RemoteSubscription rmt = new RemoteSubscription();
		when(remoteSubscriptionJpa.findByRemoteSubscriptionId(any())).thenReturn(Optional.of(rmt));
		final AlarmNotification newEvent = new AlarmNotification();
		when(vnfFmJpa.save(any())).thenReturn(newEvent);
		srv.onNotification(event, null);
		assertTrue(true);
	}

	@Test
	void testName_Fail() throws Exception {
		final VnffmNotificationService srv = new VnffmNotificationService(remoteSubscriptionJpa, vnfFmJpa);
		final AlarmNotification event = new AlarmNotification();
		final RemoteSubscription rmt = new RemoteSubscription();
		assertThrows(NotFoundException.class, () -> srv.onNotification(event, null));
	}
}
