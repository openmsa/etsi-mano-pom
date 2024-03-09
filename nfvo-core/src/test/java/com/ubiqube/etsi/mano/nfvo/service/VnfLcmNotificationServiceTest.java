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

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.vnflcm.VnfLcmNotification;
import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.RemoteSubscriptionJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.VnfLcmNotificationJpa;

@ExtendWith(MockitoExtension.class)
class VnfLcmNotificationServiceTest {
	@Mock
	private RemoteSubscriptionJpa remoteSubscription;
	@Mock
	private VnfLcmNotificationJpa vnfLcmJpa;

	@Test
	void testOnCreationNotification() throws Exception {
		final VnfLcmNotificationService srv = new VnfLcmNotificationService(remoteSubscription, vnfLcmJpa);
		final VnfLcmNotification evt = new VnfLcmNotification();
		final RemoteSubscription rmt = new RemoteSubscription();
		when(remoteSubscription.findByRemoteSubscriptionId(any())).thenReturn(List.of(rmt));
		when(vnfLcmJpa.save(evt)).thenReturn(evt);
		srv.onCreationNotification(evt, null);
		assertTrue(true);
	}

	@Test
	void testOnDeletionNotification() throws Exception {
		final VnfLcmNotificationService srv = new VnfLcmNotificationService(remoteSubscription, vnfLcmJpa);
		final VnfLcmNotification evt = new VnfLcmNotification();
		final RemoteSubscription rmt = new RemoteSubscription();
		when(remoteSubscription.findByRemoteSubscriptionId(any())).thenReturn(List.of(rmt));
		when(vnfLcmJpa.save(evt)).thenReturn(evt);
		srv.onDeletionNotification(evt, null);
		assertTrue(true);
	}

	@Test
	void testOnVnfLcmOpOccsNotification() throws Exception {
		final VnfLcmNotificationService srv = new VnfLcmNotificationService(remoteSubscription, vnfLcmJpa);
		final VnfLcmNotification evt = new VnfLcmNotification();
		final RemoteSubscription rmt = new RemoteSubscription();
		when(remoteSubscription.findByRemoteSubscriptionId(any())).thenReturn(List.of(rmt));
		when(vnfLcmJpa.save(evt)).thenReturn(evt);
		srv.onVnfLcmOpOccsNotification(evt, null);
		assertTrue(true);
	}

	@Test
	void testOnVnfLcmOpOccsNotificationNotFound() throws Exception {
		final VnfLcmNotificationService srv = new VnfLcmNotificationService(remoteSubscription, vnfLcmJpa);
		final VnfLcmNotification evt = new VnfLcmNotification();
		when(remoteSubscription.findByRemoteSubscriptionId(any())).thenReturn(List.of());
		assertThrows(NotFoundException.class, () -> srv.onVnfLcmOpOccsNotification(evt, null));
		assertTrue(true);
	}
}
