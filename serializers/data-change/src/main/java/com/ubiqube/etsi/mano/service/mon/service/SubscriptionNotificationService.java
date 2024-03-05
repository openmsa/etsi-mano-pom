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
package com.ubiqube.etsi.mano.service.mon.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.ubiqube.etsi.mano.mon.MonGenericException;
import com.ubiqube.etsi.mano.service.mon.data.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.jms.MetricChange;
import com.ubiqube.etsi.mano.service.mon.model.MonSubscription;
import com.ubiqube.etsi.mano.service.mon.repository.SubscriptionRepository;

import jakarta.annotation.Nullable;

public class SubscriptionNotificationService {
	private static final Logger LOG = LoggerFactory.getLogger(SubscriptionNotificationService.class);
	private final String id = UUID.randomUUID().toString();
	private final SubscriptionRepository subscriptionRepository;

	public SubscriptionNotificationService(final SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	@JmsListener(destination = "")
	public void onNotification(final MetricChange metricChange) {
		final List<MonSubscription> subscriptions = subscriptionRepository.findByKey(metricChange.latest().getKey());
		subscriptions.forEach(x -> safeSendSubscription(x, metricChange.latest()));
	}

	private void safeSendSubscription(final MonSubscription subscription, final MonitoringDataSlim monitoringDataSlim) {
		try {
			final Builder wc = WebClient.builder()
					.baseUrl(subscription.getCallBackUrl());
			createAuthPart(wc, subscription.getAuthParamOauth2());
			final ResponseEntity<String> client = wc.build()
					.method(HttpMethod.POST)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.bodyValue(monitoringDataSlim)
					.retrieve()
					.toEntity(String.class)
					.block();
			final boolean res = Optional.ofNullable(client).map(ResponseEntity::getStatusCode).map(HttpStatusCode::is2xxSuccessful).orElseThrow();
			if (!res) {
				LOG.error("");
			}
		} catch (final RuntimeException e) {
			throw new MonGenericException(e);
		}
	}

	private void createAuthPart(final Builder wcb, final @Nullable AuthParamOauth2 authParam) {
		if (authParam == null) {
			return;
		}
		final AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
				getRegistration(authParam.getTokenEndpoint(), authParam.getClientId(), authParam.getClientSecret(), "openid"),
				new InMemoryReactiveOAuth2AuthorizedClientService(getRegistration(authParam.getTokenEndpoint(), authParam.getClientId(), authParam.getClientSecret(), "openid")));
		final ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
		oauth2.setDefaultClientRegistrationId(id);
		wcb.filter(oauth2);
	}

	private ReactiveClientRegistrationRepository getRegistration(final String tokenUri, final String clientId, final String clientSecret, final String scope) {
		final ClientRegistration registration = ClientRegistration
				.withRegistrationId(id)
				.tokenUri(tokenUri)
				.clientId(clientId)
				.clientSecret(clientSecret)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.scope(scope)
				.build();
		return new InMemoryReactiveClientRegistrationRepository(registration);
	}

}
