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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.jpa.SubscriptionJpa;
import com.ubiqube.etsi.mano.service.eval.EvalService;
import com.ubiqube.etsi.mano.service.event.Notifications;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;
import com.ubiqube.etsi.mano.service.rest.model.ApiTypesEnum;

import jakarta.persistence.EntityManager;
import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
	@Mock
	private SubscriptionJpa subscriptionJpa;
	@Mock
	private EntityManager em;
	@Mock
	private GrammarParser grammar;
	@Mock
	private Notifications notifications;
	@Mock
	private ServerService serverService;
	@Mock
	private EvalService evalService;
	@Mock
	private MapperFacade mapper;

	@Test
	void testDelete() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.build();
		when(mapper.map(request, Subscription.class)).thenReturn(request);
		subs.save(request, getClass(), SubscriptionType.ALARM);
		assertTrue(true);
	}
}
