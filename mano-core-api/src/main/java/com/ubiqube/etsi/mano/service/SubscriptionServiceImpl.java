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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ubiqube.etsi.mano.controller.subscription.ApiAndType;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.exception.SeeOtherException;
import com.ubiqube.etsi.mano.grammar.BooleanExpression;
import com.ubiqube.etsi.mano.grammar.GrammarLabel;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarOperandType;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.grammar.GrammarValue;
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
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;
import com.ubiqube.etsi.mano.service.search.ManoSearch;
import com.ubiqube.etsi.mano.utils.Version;

import jakarta.annotation.Nullable;
import ma.glasnost.orika.MapperFacade;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	private static final Logger LOG = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

	private final SubscriptionJpa subscriptionJpa;

	private final GrammarParser grammarParser;

	private final Notifications notifications;

	private final ServerService serverService;

	private final EvalService evalService;

	private final MapperFacade mapper;

	private final ManoSearch manoSearch;

	public SubscriptionServiceImpl(final SubscriptionJpa repository, final GrammarParser grammarParser, final Notifications notifications,
			final @Lazy ServerService serverService, final EvalService evalService, final MapperFacade mapper, final ManoSearch manoSearch) {
		this.subscriptionJpa = repository;
		this.grammarParser = grammarParser;
		this.notifications = notifications;
		this.serverService = serverService;
		this.evalService = evalService;
		this.mapper = mapper;
		this.manoSearch = manoSearch;
	}

	@Override
	public List<Subscription> query(final String filter, final ApiVersionType type) {
		final GrammarNodeResult nodes = grammarParser.parse(filter);
		final GrammarNode gn = new BooleanExpression(new GrammarLabel("subscriptionType"), GrammarOperandType.EQ, new GrammarValue(type.toString().toUpperCase()));
		final ArrayList<GrammarNode> lst = new ArrayList<>(nodes.getNodes());
		lst.add(gn);
		return manoSearch.getCriteria(lst, Subscription.class);
	}

	@Override
	public Subscription save(final Subscription subscription) {
		ensureUniqueness(subscription);
		checkAvailability(subscription);
		return subscriptionJpa.save(subscription);
	}

	@Override
	public Subscription save(final Object subscriptionRequest, final Class<?> version, final ApiVersionType type) {
		final Subscription subscription = mapper.map(subscriptionRequest, Subscription.class);
		final ApiAndType nt = ServerService.apiVersionTosubscriptionType(type);
		subscription.setSubscriptionType(nt.type());
		subscription.setApi(nt.api());
		checkAuthData(subscription);
		ensureUniqueness(subscription);
		subscription.setNodeFilter(evalService.convertRequestToString(subscriptionRequest));
		subscription.setVersion(extractVersion(version, nt.type()));
		checkAvailability(subscription);
		return subscriptionJpa.save(subscription);
	}

	private static void checkAuthData(final Subscription subscription) {
		final AuthentificationInformations authInfo = subscription.getAuthentication();
		if (null == authInfo) {
			return;
		}
		authInfo.getAuthType().forEach(x -> check(x, authInfo));
	}

	private static void check(final AuthType authType, final AuthentificationInformations authInfo) {
		switch (authType) {
		case BASIC -> checkBasic(authInfo.getAuthParamBasic());
		case OAUTH2_CLIENT_CREDENTIALS -> checkOauth2(authInfo.getAuthParamOauth2());
		case TLS_CERT -> checkTls(authInfo.getAuthTlsCert());
		default -> throw new IllegalArgumentException("Unexpected value: " + authType);
		}
	}

	private static void checkTls(final String authTlsCert) {
		Objects.requireNonNull(authTlsCert, "TLS certificate should not be empty.");
	}

	private static void checkOauth2(final @Nullable AuthParamOauth2 authParamOauth2) {
		if (authParamOauth2 == null) {
			throw new GenericException("No OAuth2 parameters.");
		}
		if (OAuth2GrantType.CLIENT_CREDENTIAL.equals(authParamOauth2.getGrantType())) {
			Objects.requireNonNull(authParamOauth2.getClientId(), "Client ID must not be null");
			Objects.requireNonNull(authParamOauth2.getClientSecret(), "Client Secret must not be null");
		} else if (OAuth2GrantType.PASSWORD.equals(authParamOauth2.getGrantType())) {
			Objects.requireNonNull(authParamOauth2.getO2Username(), "Username must not be null.");
			Objects.requireNonNull(authParamOauth2.getO2Password(), "Passord must not be null.");
		}
	}

	private static void checkBasic(final AuthParamBasic authParamBasic) {
		Objects.requireNonNull(authParamBasic.getUserName(), "Username must not be null.");
	}

	private void checkAvailability(final Subscription subscription) {
		final ServerAdapter server = serverService.buildServerAdapter(subscription);
		notifications.check(server, subscription.getCallbackUri());
	}

	private @Nullable String extractVersion(final Class<?> version, final SubscriptionType type) {
		final String v = extractVersion(version);
		return serverService.convertManoVersionToFe(type, v).map(Version::toString).orElse(null);
	}

	private void ensureUniqueness(final Subscription subscription) {
		final List<Subscription> lst = findByApiAndCallbackUriSubscriptionType(subscription.getApi(), subscription.getCallbackUri(), subscription.getSubscriptionType());
		final Optional<Subscription> res = getMatchingList(subscription, lst);
		if (res.isPresent()) {
			final URI uri = makeLink(res.get());
			throw new SeeOtherException(uri, "Subscription already exist.");
		}
	}

	private URI makeLink(final Subscription subscription) {
		final Optional<HttpGateway> optGateway = serverService.getHttpGatewayFromManoVersion(subscription.getVersion());
		if (optGateway.isEmpty()) {
			throw new GenericException("Could not find gateway for " + subscription.getSubscriptionType() + "/" + subscription.getVersion());
		}
		final HttpGateway gateway = optGateway.get();
		final ApiAndType at = ApiAndType.of(subscription.getApi(), subscription.getSubscriptionType());
		final String str = gateway.getSubscriptionUriFor(at, subscription.getId().toString());
		return URI.create(str);
	}

	private static Optional<Subscription> getMatchingList(final Subscription subscription, final List<Subscription> lst) {
		final List<FilterAttributes> filters = Optional.ofNullable(subscription.getFilters()).orElseGet(List::of);
		if (filters.isEmpty()) {
			return lst.stream().filter(x -> x.getFilters().isEmpty()).findFirst();
		}
		return lst.stream()
				.filter(x -> isFilterMatching(x, filters))
				.findFirst();
	}

	private static boolean isFilterMatching(final Subscription subs, final List<FilterAttributes> filters) {
		final List<FilterAttributes> left = subs.getFilters();
		final List<FilterAttributes> inter = new ArrayList<>(left);
		inter.retainAll(filters);
		return filters.size() == inter.size();
	}

	private static @Nullable String extractVersion(final Class<?> version) {
		final RequestMapping ann = AnnotationUtils.findAnnotation(version, RequestMapping.class);
		if (null == ann) {
			return null;
		}
		final String[] headers = ann.headers();
		for (final String header : headers) {
			if (header.startsWith("Version=")) {
				return header.substring("Version=".length());
			}
		}
		return null;
	}

	@Override
	public void delete(final UUID subscriptionId, final ApiVersionType type) {
		findById(subscriptionId, type);
		subscriptionJpa.deleteById(subscriptionId);
	}

	@Override
	public Subscription findById(final UUID subscriptionId, final ApiVersionType type) {
		return subscriptionJpa.findById(subscriptionId).orElseThrow(() -> new NotFoundException("Could not find subscription id: " + subscriptionId));
	}

	@Override
	public List<Subscription> selectNotifications(final EventMessage ev) {
		final List<Subscription> lst = subscriptionJpa.findEventAndVnfPkg(ev.getNotificationEvent(), ev.getObjectId().toString());
		return lst.stream()
				.filter(x -> x.getFilters().stream().anyMatch(y -> y.getAttribute().startsWith("notificationTypes[") && y.getValue().equals(convert(ev.getNotificationEvent()))))
				.toList();
	}

	@Nullable
	public static String convert(final NotificationEvent notificationEvent) {
		return switch (notificationEvent) {
		case VNF_PKG_ONBOARDING -> "VnfPackageOnboardingNotification";
		case VNF_PKG_ONCHANGE, VNF_PKG_ONDELETION -> "VnfPackageChangeNotification";
		case NS_PKG_ONBOARDING -> "NsdOnBoardingNotification";
		case NS_PKG_ONBOARDING_FAILURE -> "NsdOnboardingFailureNotification";
		case NS_PKG_ONCHANGE -> "NsdChangeNotification";
		case NS_PKG_ONDELETION -> "NsdDeletionNotification";
		case NS_INSTANCE_CREATE -> "NsIdentifierCreationNotification";
		case NS_INSTANCE_DELETE -> "NsIdentifierDeletionNotification";
		case NS_INSTANTIATE -> "NsLcmOperationOccurrenceNotification";
		case NS_TERMINATE -> "NsLcmOperationOccurrenceNotification";
		case VNF_INSTANCE_DELETE -> "VnfIdentifierDeletionNotification";
		case VNF_INSTANCE_CREATE -> "VnfIdentifierCreationNotification";
		case VNF_INSTANCE_CHANGED -> "VnfLcmOperationOccurrenceNotification";
		case VNF_TERMINATE -> "VnfLcmOperationOccurrenceNotification";
		case VNF_INDICATOR_VALUE_CHANGED -> "VnfIndicatorValueChangeNotification";
		case VRQAN -> "VrQuotaAvailNotification";
		default -> {
			LOG.warn("Unexpected value: {}", notificationEvent);
			yield null;
		}
		};
	}

	@Override
	public List<Subscription> findByApiAndCallbackUriSubscriptionType(final @Nullable ApiTypesEnum api, final URI callbackUri, final SubscriptionType subscriptionType) {
		return subscriptionJpa.findByApiAndCallbackUriAndSubscriptionType(api, callbackUri, subscriptionType);
	}

}
