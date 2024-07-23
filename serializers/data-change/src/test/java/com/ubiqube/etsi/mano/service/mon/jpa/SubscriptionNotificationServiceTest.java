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
package com.ubiqube.etsi.mano.service.mon.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.mon.data.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.data.OAuth2GrantType;
import com.ubiqube.etsi.mano.service.mon.jms.MetricChange;
import com.ubiqube.etsi.mano.service.mon.model.MonSubscription;
import com.ubiqube.etsi.mano.service.mon.repository.SubscriptionRepository;
import com.ubiqube.etsi.mano.service.mon.service.SubscriptionNotificationService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@ExtendWith(MockitoExtension.class)
class SubscriptionNotificationServiceTest {
	private static final UUID id = UUID.fromString("5f57786c-b457-11ed-ac52-c8f750509d3b");

	private static final String JSON_TOKEN = "{\"access_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJYcFc2QzY3Rjk3RUk3N2NOSTZyZWdDZjFSTDhrLXFfVDdMUG9BcGNkZWVVIn0.eyJleHAiOjE2NzcyNTYzNTYsImlhdCI6MTY3NzI1MzM1NiwianRpIjoiODMzZjExM2ItMGI3OC00NTFhLTk2YzItMjZmZDJkZjY2Y2U5IiwiaXNzIjoiaHR0cDovL21hbm8tYXV0aDo4MDgwL2F1dGgvcmVhbG1zL21hbm8tcmVhbG0iLCJzdWIiOiIzZDAzOWI3MS01MWU1LTQ5ZDEtODk1NS0xZDljODk5Y2E3MjQiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJtYW5vLXZuZm0iLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIlZORk0iXX0sInJlc291cmNlX2FjY2VzcyI6eyJtYW5vLXZuZm0iOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX19LCJzY29wZSI6InByb2ZpbGUgbWFubzpvdmkgZW1haWwiLCJjbGllbnRIb3N0IjoiMTAuMC4wLjIiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImNsaWVudElkIjoibWFuby12bmZtIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LW1hbm8tdm5mbSIsImNsaWVudEFkZHJlc3MiOiIxMC4wLjAuMiJ9.jSqyvO_lzB9g1F4rPjdjqdehY-HvG0OJ2QI6xxX2MtSREmGKkwSZknhJLb3utpjg0gRjPoPeA0yq0Q3naKEvUBKCkcrisKnphFMclrwr2XCVX87kvyR2boe2OWecTEJMiYN5IihgjPmnil4lHBfRLluRr0a_bxAc5cf_HCH2Q7ppIh4PE9Rq6pk9WaZj2lbmNY0lDVk1fT3yHuMqbBzJuKtNYnpLZeQJoMfUa8SrkKhGn_865q2k-M2lHwR4GfMNI4hFDcnMYwgCSd1bey2_02RwDLpbLGiYJRjAztpBFOP3JvGRlr5cnFqRi5AkURhVlX_NWDHLCm7p0QxhN8KmlQ\",\"expires_in\":3000,\"refresh_expires_in\":0,\"token_type\":\"Bearer\",\"not-before-policy\":0,\"scope\":\"profile mano:ovi email\"}";
	@Mock
	private SubscriptionRepository subscriptionRepo;

	private String baseUrl;

	public static MockWebServer mockBackEnd;

	@BeforeAll
	static void setUp() throws IOException {
		mockBackEnd = new MockWebServer();
		mockBackEnd.start();
	}

	@AfterAll
	static void tearDown() throws IOException {
		mockBackEnd.shutdown();
	}

	@BeforeEach
	void initialize() {
		baseUrl = String.format("http://localhost:%s/", mockBackEnd.getPort());
	}

	@Test
	void testName() throws Exception {
		final SubscriptionNotificationService sns = new SubscriptionNotificationService(subscriptionRepo);
		final MonitoringDataSlim latest = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", 123D, null);
		final MonitoringDataSlim old = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", 456D, null);
		final MetricChange metricChange = new MetricChange(latest, old);
		final MonSubscription subscription = new MonSubscription();
		final AuthParamOauth2 authParamOauth2 = new AuthParamOauth2();
		authParamOauth2.setClientId("aa");
		authParamOauth2.setClientSecret("aa");
		authParamOauth2.setGrantType(OAuth2GrantType.CLIENT_CREDENTIAL);
		authParamOauth2.setO2Password("aa");
		authParamOauth2.setO2Username("aa");
		authParamOauth2.setTokenEndpoint(baseUrl + "token/");
		subscription.setAuthParamOauth2(authParamOauth2);
		subscription.setCallBackUrl(baseUrl);
		subscription.setId(id);
		subscription.setKey(id.toString());
		subscription.hashCode();
		subscription.equals(null);
		subscription.equals(authParamOauth2);
		subscription.equals(subscription);
		subscription.toString();
		//
		when(subscriptionRepo.findByKey("key2")).thenReturn(List.of(subscription));
		//
		mockBackEnd.enqueue(new MockResponse()
				.setBody(JSON_TOKEN)
				.addHeader("Content-Type", "application/json"));
		mockBackEnd.enqueue(new MockResponse()
				.setBody("")
				.addHeader("Content-Type", "application/json"));
		sns.onNotification(metricChange);
		final RecordedRequest recordedRequest = mockBackEnd.takeRequest();
		assertEquals("POST", recordedRequest.getMethod());
		assertEquals("/token/", recordedRequest.getPath());
		final RecordedRequest recordedRequest2 = mockBackEnd.takeRequest();
		assertEquals("POST", recordedRequest2.getMethod());
		assertEquals("/", recordedRequest2.getPath());
	}

	@SuppressWarnings("static-method")
	@Test
	void testSubscription() {
		final MonSubscription subscription = new MonSubscription();
		final AuthParamOauth2 authParamOauth2 = new AuthParamOauth2();
		subscription.setAuthParamOauth2(authParamOauth2);
		final MonSubscription subscription2 = new MonSubscription();
		final AuthParamOauth2 authParamOauth3 = new AuthParamOauth2();
		subscription2.setAuthParamOauth2(authParamOauth3);
		assertEquals(subscription, subscription2);
	}

	@Test
	void testSubscriptionNoAuth() {
		final SubscriptionNotificationService sns = new SubscriptionNotificationService(subscriptionRepo);
		final MonitoringDataSlim latest = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", 123D, null);
		final MonitoringDataSlim old = new TestMonitoringDataSlim(OffsetDateTime.now(), "masterJobId2", "key2", "res", 456D, null);
		final MetricChange metricChange = new MetricChange(latest, old);
		final MonSubscription subscription = new MonSubscription();
		subscription.setCallBackUrl(baseUrl);
		subscription.setId(id);
		subscription.setKey(id.toString());
		subscription.hashCode();
		//
		when(subscriptionRepo.findByKey("key2")).thenReturn(List.of(subscription));
		mockBackEnd.enqueue(new MockResponse()
				.setBody("")
				.addHeader("Content-Type", "application/json"));
		sns.onNotification(metricChange);
		assertTrue(true);
	}
}
