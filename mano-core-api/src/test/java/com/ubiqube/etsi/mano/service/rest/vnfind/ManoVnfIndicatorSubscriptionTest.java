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
package com.ubiqube.etsi.mano.service.rest.vnfind;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.rest.ManoQueryBuilder;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

@ExtendWith(MockitoExtension.class)
class ManoVnfIndicatorSubscriptionTest {
	@Mock
	private QueryParameters client;
	@Mock
	private ManoQueryBuilder<Object, Object> mqb;

	ManoVnfIndicatorSubscription createService() {
		return new ManoVnfIndicatorSubscription(client);
	}

	@Test
	void testDelete() {
		final ManoVnfIndicatorSubscription srv = createService();
		when(client.createQuery()).thenReturn(mqb);
		srv.delete(UUID.randomUUID());
	}

	@Test
	void testFind() {
		final ManoVnfIndicatorSubscription srv = createService();
		when(client.createQuery()).thenReturn(mqb);
		when(mqb.setWireOutClass(any())).thenReturn(mqb);
		when(mqb.setOutClass(any())).thenReturn(mqb);
		srv.find(UUID.randomUUID());
	}

	@Test
	void testSubscription() {
		final ManoVnfIndicatorSubscription srv = createService();
		final Subscription subs = new Subscription();
		when(client.createQuery()).thenReturn(mqb);
		when(mqb.setWireInClass(any())).thenReturn(mqb);
		when(mqb.setWireOutClass(any())).thenReturn(mqb);
		when(mqb.setOutClass(any())).thenReturn(mqb);
		srv.subscribe(subs);
	}
}
