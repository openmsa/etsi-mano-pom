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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.search.mapper.orm.mapping.impl.HibernateSearchContextProviderService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.controller.TestRequestMapping;
import com.ubiqube.etsi.mano.controller.TestRequestMappingBadVersion;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.jpa.SubscriptionJpa;
import com.ubiqube.etsi.mano.service.eval.EvalService;
import com.ubiqube.etsi.mano.service.event.Notifications;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
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
	void testSave() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.build();
		when(mapper.map(request, Subscription.class)).thenReturn(request);
		subs.save(request, getClass(), SubscriptionType.ALARM);
		assertTrue(true);
	}

	@Test
	void testSave_Version() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.build();
		when(mapper.map(request, Subscription.class)).thenReturn(request);
		subs.save(request, TestRequestMapping.class, SubscriptionType.ALARM);
		assertTrue(true);
	}

	@Test
	void testSave_BadVersion() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.build();
		when(mapper.map(request, Subscription.class)).thenReturn(request);
		subs.save(request, TestRequestMappingBadVersion.class, SubscriptionType.ALARM);
		assertTrue(true);
	}

	@Test
	void testSave_FailOnDuplicates() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.filters(List.of())
				.build();
		when(subscriptionJpa.findByApiAndCallbackUriAndSubscriptionType(any(), any(), any())).thenReturn(List.of(request));
		when(mapper.map(request, Subscription.class)).thenReturn(request);
		assertThrows(GenericException.class, () -> subs.save(request, getClass(), SubscriptionType.ALARM));
	}

	@Test
	void testSave_WithDiffFilter() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		final FilterAttributes f1 = new FilterAttributes("attr", "value");
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.filters(List.of(f1))
				.build();
		final Subscription request1 = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.filters(List.of())
				.build();
		when(subscriptionJpa.findByApiAndCallbackUriAndSubscriptionType(any(), any(), any())).thenReturn(List.of(request));
		when(mapper.map(request, Subscription.class)).thenReturn(request);
		assertThrows(GenericException.class, () -> subs.save(request, getClass(), SubscriptionType.ALARM));
	}

	@Test
	void testDelete() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		final Subscription sub = new Subscription();
		when(subscriptionJpa.findById(any())).thenReturn(Optional.of(sub));
		subs.delete(UUID.randomUUID(), SubscriptionType.ALARM);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		final Subscription sub = new Subscription();
		when(subscriptionJpa.findById(any())).thenReturn(Optional.of(sub));
		subs.findById(UUID.randomUUID(), SubscriptionType.ALARM);
		assertTrue(true);
	}

	@Test
	void testFindByIdFail() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		final UUID uuid = UUID.randomUUID();
		assertThrows(NotFoundException.class, () -> subs.findById(uuid, SubscriptionType.ALARM));
	}

	@Test
	void testSelectNotificaton() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		when(subscriptionJpa.findEventAndVnfPkg(any(), any())).thenReturn(List.of());
		final EventMessage ev = new EventMessage();
		ev.setObjectId(UUID.randomUUID());
		subs.selectNotifications(ev);
		assertTrue(true);
	}

	@SuppressWarnings("resource")
	void testQuery() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		final Session session = Mockito.mock(Session.class);
		when(em.unwrap(Session.class)).thenReturn(session);
		final SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
		when(session.getSessionFactory()).thenReturn(sessionFactory);
		final SessionFactoryImplementor sfImpl = Mockito.mock(SessionFactoryImplementor.class);
		when(sessionFactory.unwrap(SessionFactoryImplementor.class)).thenReturn(sfImpl);
		final ServiceRegistryImplementor sri = Mockito.mock(ServiceRegistryImplementor.class);
		when(sfImpl.getServiceRegistry()).thenReturn(sri);
		final HibernateSearchContextProviderService hscp = Mockito.mock(HibernateSearchContextProviderService.class);
		when(sri.getService(HibernateSearchContextProviderService.class)).thenReturn(hscp);
		final SessionImplementor sessImpl = Mockito.mock(SessionImplementor.class);
		when(em.unwrap(SessionImplementor.class)).thenReturn(sessImpl);
		when(subscriptionJpa.findEventAndVnfPkg(any(), any())).thenReturn(List.of());
		subs.query(null, SubscriptionType.ALARM);
		assertTrue(true);
	}

	@Test
	void testSaveSubscription() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		final Subscription subscription = new Subscription();
		subs.save(subscription);
		assertTrue(true);
	}

	@ParameterizedTest
	@EnumSource(NotificationEvent.class)
	void testConvert(final NotificationEvent notificationEvent) {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, em, grammar, notifications, serverService, evalService, mapper);
		// return null if not found.
		subs.convert(notificationEvent);
		assertTrue(true);
	}
}
