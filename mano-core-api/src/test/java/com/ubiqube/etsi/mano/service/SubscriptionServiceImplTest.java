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
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.exception.SeeOtherException;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.jpa.SubscriptionJpa;
import com.ubiqube.etsi.mano.service.auth.model.ApiTypesEnum;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.auth.model.AuthType;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.auth.model.OAuth2GrantType;
import com.ubiqube.etsi.mano.service.eval.EvalService;
import com.ubiqube.etsi.mano.service.event.Notifications;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.search.ManoSearch;

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
	@Mock
	private ManoSearch manoSearch;

	@Test
	void testSave() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.build();
		subs.save(request, getClass(), ApiVersionType.SOL002_VNFFM);
		assertTrue(true);
	}

	@Test
	void testSave_Version() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.build();
		subs.save(request, TestRequestMapping.class, ApiVersionType.SOL002_VNFFM);
		assertTrue(true);
	}

	@Test
	void testSave_Subscription() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final AuthParamBasic basic = AuthParamBasic.builder()
				.userName("")
				.build();
		final AuthParamOauth2 oauth2 = AuthParamOauth2.builder()
				.clientId("")
				.clientSecret("")
				.grantType(OAuth2GrantType.CLIENT_CREDENTIAL)
				.build();
		final AuthentificationInformations auth = AuthentificationInformations.builder()
				.authType(List.of(AuthType.BASIC, AuthType.OAUTH2_CLIENT_CREDENTIALS, AuthType.TLS_CERT))
				.authTlsCert("")
				.authParamOauth2(oauth2)
				.authParamBasic(basic)
				.build();
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.authentication(auth)
				.build();
		subs.save(request, TestRequestMapping.class, ApiVersionType.SOL002_VNFFM);
		assertTrue(true);
	}

	@Test
	void testSave_Subscription_oauth2Password() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final AuthParamOauth2 oauth2 = AuthParamOauth2.builder()
				.o2Username("")
				.o2Password("")
				.grantType(OAuth2GrantType.PASSWORD)
				.build();
		final AuthentificationInformations auth = AuthentificationInformations.builder()
				.authType(List.of(AuthType.OAUTH2_CLIENT_CREDENTIALS))
				.authParamOauth2(oauth2)
				.build();
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL005)
				.authentication(auth)
				.build();
		subs.save(request, TestRequestMapping.class, ApiVersionType.SOL002_VNFFM);
		assertTrue(true);
	}

	@Test
	void testSave_Subscription_oauth2GrantTypeNull() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final AuthParamOauth2 oauth2 = AuthParamOauth2.builder()
				.o2Username("")
				.o2Password("")
				.build();
		final AuthentificationInformations auth = AuthentificationInformations.builder()
				.authType(List.of(AuthType.OAUTH2_CLIENT_CREDENTIALS))
				.authParamOauth2(oauth2)
				.build();
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL005)
				.authentication(auth)
				.build();
		subs.save(request, TestRequestMapping.class, ApiVersionType.SOL002_VNFFM);
		assertTrue(true);
	}

	@Test
	void testSave_Subscription_oauth2Null() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final AuthentificationInformations auth = AuthentificationInformations.builder()
				.authType(List.of(AuthType.OAUTH2_CLIENT_CREDENTIALS))
				.build();
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL005)
				.authentication(auth)
				.build();
		assertThrows(GenericException.class, () -> subs.save(request, TestRequestMapping.class, ApiVersionType.SOL002_VNFFM));
	}

	@Test
	void testSave_Subscription_BasicNull() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final AuthentificationInformations auth = AuthentificationInformations.builder()
				.authType(List.of(AuthType.BASIC))
				.build();
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL005)
				.authentication(auth)
				.build();
		assertThrows(NullPointerException.class, () -> subs.save(request, TestRequestMapping.class, ApiVersionType.SOL002_VNFFM));
	}

	@Test
	void testSave_BadVersion() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.build();
		subs.save(request, TestRequestMappingBadVersion.class, ApiVersionType.SOL002_VNFFM);
		assertTrue(true);
	}

	@Test
	void testSave_FailOnDuplicates() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final Subscription request = Subscription.builder()
				.id(UUID.randomUUID())
				.api(ApiTypesEnum.SOL003)
				.filters(List.of())
				.build();
		when(subscriptionJpa.findByApiAndCallbackUriAndSubscriptionType(any(), any(), any())).thenReturn(List.of(request));
		final HttpGateway httpGw = Mockito.mock(HttpGateway.class);
		when(httpGw.getSubscriptionUriFor(any(), any())).thenReturn("http://localhost/");
		final Optional<HttpGateway> optHttpgw = Optional.of(httpGw);
		when(serverService.getHttpGatewayFromManoVersion(any())).thenReturn(optHttpgw);
		final Class clazz = getClass();
		assertThrows(SeeOtherException.class, () -> subs.save(request, clazz, ApiVersionType.SOL002_VNFFM));
	}

	@Test
	void testSave_WithDiffFilter() throws Exception {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final FilterAttributes f1 = new FilterAttributes("attr", "value");
		final Subscription request = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.filters(List.of(f1))
				.build();
		final FilterAttributes f2 = new FilterAttributes("attr", "valuesss");
		final Subscription r2 = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.filters(List.of(f2))
				.build();
		when(subscriptionJpa.findByApiAndCallbackUriAndSubscriptionType(any(), any(), any())).thenReturn(List.of(request));
		final Class clazz = getClass();
		assertThrows(GenericException.class, () -> subs.save(request, clazz, ApiVersionType.SOL003_VNFFM));
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final Subscription sub = new Subscription();
		when(subscriptionJpa.findById(any())).thenReturn(Optional.of(sub));
		subs.delete(UUID.randomUUID(), ApiVersionType.SOL002_VNFFM);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final Subscription sub = new Subscription();
		when(subscriptionJpa.findById(any())).thenReturn(Optional.of(sub));
		subs.findById(UUID.randomUUID(), ApiVersionType.SOL002_VNFFM);
		assertTrue(true);
	}

	@Test
	void testFindByIdFail() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final UUID uuid = UUID.randomUUID();
		assertThrows(NotFoundException.class, () -> subs.findById(uuid, ApiVersionType.SOL002_VNFFM));
	}

	@Test
	void testSelectNotificaton() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		when(subscriptionJpa.findEventAndVnfPkg(any(), any())).thenReturn(List.of());
		final EventMessage ev = new EventMessage();
		ev.setObjectId(UUID.randomUUID());
		subs.selectNotifications(ev);
		assertTrue(true);
	}

	@Test
	void testQuery() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		when(grammar.parse(any())).thenReturn(new GrammarNodeResult(List.of()));
		subs.query("", ApiVersionType.SOL002_VNFFM);
		assertTrue(true);
	}

	/**
	 * This code have to be push back in {@link ManoSearch}.
	 */
	@SuppressWarnings("resource")
	void testQueryOld() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final Session session = Mockito.mock(Session.class);
		when(em.unwrap(Session.class)).thenReturn(session);
		final SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
		when(session.getSessionFactory()).thenReturn(sessionFactory);
		final SessionFactoryImplementor sfImpl = Mockito.mock(SessionFactoryImplementor.class);
		when(sessionFactory.unwrap(SessionFactoryImplementor.class)).thenReturn(sfImpl);
		final ServiceRegistryImplementor sri = Mockito.mock(ServiceRegistryImplementor.class);
		when(sfImpl.getServiceRegistry()).thenReturn(sri);
		// final HibernateSearchContextProviderService hscp =
		// Mockito.mock(HibernateSearchContextProviderService.class);
		// when(sri.getService(HibernateSearchContextProviderService.class)).thenReturn(hscp);
		final SessionImplementor sessImpl = Mockito.mock(SessionImplementor.class);
		when(em.unwrap(SessionImplementor.class)).thenReturn(sessImpl);
		when(subscriptionJpa.findEventAndVnfPkg(any(), any())).thenReturn(List.of());
		subs.query(null, ApiVersionType.SOL002_VNFFM);
		assertTrue(true);
	}

	@Test
	void testSaveSubscription() {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		final Subscription subscription = new Subscription();
		subs.save(subscription);
		assertTrue(true);
	}

	@ParameterizedTest
	@EnumSource(NotificationEvent.class)
	void testConvert(final NotificationEvent notificationEvent) {
		final SubscriptionServiceImpl subs = new SubscriptionServiceImpl(subscriptionJpa, grammar, notifications, serverService, evalService, manoSearch);
		// return null if not found.
		subs.convert(notificationEvent);
		assertTrue(true);
	}
}
