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
package com.ubiqube.etsi.mano.service.mon.poller.gnocchi;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.data.Metric;
import com.ubiqube.etsi.mano.service.mon.data.MonConnInformation;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
@WireMockTest
class GnocchiPollerListenerTest {
	@Mock
	private JmsTemplate jmsTemplate;
	@Mock
	private ConfigurableApplicationContext configurableCtx;
	@Mock
	private ConfigurableListableBeanFactory beanFactory;

	@Test
	void test(final WireMockRuntimeInfo wri) {
		stubFor(post(urlPathMatching("/auth/tokens")).willReturn(aResponse()
				.withStatus(200)
				.withBody(getFile(wri, "/auth.json"))));

		final GnocchiPollerListener srv = new GnocchiPollerListener(jmsTemplate, configurableCtx);
		final BatchPollingJob bpj = new BatchPollingJob();
		bpj.setId(UUID.randomUUID());
		bpj.setMetrics(List.of());
		bpj.setResourceId("res");
		final MonConnInformation conn = new MonConnInformation();
		final KeystoneAuthV3 ai = KeystoneAuthV3.builder()
				.projectId("proj")
				.build();
		conn.setAccessInfo(ai);
		final InterfaceInfo ii = new InterfaceInfo();
		ii.setEndpoint(wri.getHttpBaseUrl());
		conn.setInterfaceInfo(ii);
		bpj.setConnection(conn);
		//
		when(configurableCtx.getBeanFactory()).thenReturn(beanFactory);
		when(beanFactory.resolveEmbeddedValue(anyString())).thenReturn("queue");
		srv.onEvent(bpj);
		assertTrue(true);
	}

	@Test
	void test2(final WireMockRuntimeInfo wri) {
		stubFor(post(urlPathMatching("/auth/tokens")).willReturn(aResponse()
				.withStatus(200)
				.withBody(getFile(wri, "/auth.json"))));
		stubFor(get(urlPathMatching("/v1/resource/instance/res")).willReturn(aResponse()
				.withStatus(200)
				.withBody(getFile(wri, "/resorce.json"))));

		final GnocchiPollerListener srv = new GnocchiPollerListener(jmsTemplate, configurableCtx);
		final BatchPollingJob bpj = new BatchPollingJob();
		bpj.setId(UUID.randomUUID());
		final Metric m = new Metric();
		m.setMetricName("cpu");
		bpj.setMetrics(List.of(m));
		bpj.setResourceId("res");
		final MonConnInformation conn = new MonConnInformation();
		final KeystoneAuthV3 ai = KeystoneAuthV3.builder()
				.projectId("proj")
				.build();
		conn.setAccessInfo(ai);
		final InterfaceInfo ii = new InterfaceInfo();
		ii.setEndpoint(wri.getHttpBaseUrl());
		conn.setInterfaceInfo(ii);
		bpj.setConnection(conn);
		//
		when(configurableCtx.getBeanFactory()).thenReturn(beanFactory);
		when(beanFactory.resolveEmbeddedValue(anyString())).thenReturn("queue");
		srv.onEvent(bpj);
		assertTrue(true);
	}

	@Test
	void test3(final WireMockRuntimeInfo wri) {
		stubFor(post(urlPathMatching("/auth/tokens")).willReturn(aResponse()
				.withStatus(200)
				.withBody(getFile(wri, "/auth.json"))));
		stubFor(get(urlPathMatching("/v1/resource/instance/res")).willReturn(aResponse()
				.withStatus(200)
				.withBody(getFile(wri, "/resorce.json"))));
		stubFor(get(urlPathMatching("/v1/metric/2dd17e3d-253e-4133-8ce3-a69d4e614e02/measures")).willReturn(aResponse()
				.withStatus(200)
				.withBody(getFile(wri, "/metric.json"))));
		final GnocchiPollerListener srv = new GnocchiPollerListener(jmsTemplate, configurableCtx);
		final BatchPollingJob bpj = new BatchPollingJob();
		bpj.setId(UUID.randomUUID());
		final Metric m = new Metric();
		m.setMetricName("cpu");
		bpj.setMetrics(List.of(m));
		bpj.setResourceId("res");
		final MonConnInformation conn = new MonConnInformation();
		final KeystoneAuthV3 ai = KeystoneAuthV3.builder()
				.projectId("proj")
				.build();
		conn.setAccessInfo(ai);
		final InterfaceInfo ii = new InterfaceInfo();
		ii.setEndpoint(wri.getHttpBaseUrl());
		conn.setInterfaceInfo(ii);
		bpj.setConnection(conn);
		//
		when(configurableCtx.getBeanFactory()).thenReturn(beanFactory);
		when(beanFactory.resolveEmbeddedValue(anyString())).thenReturn("queue");
		srv.onEvent(bpj);
		assertTrue(true);
	}

	public static String getFile(final WireMockRuntimeInfo wri, final String fileName) {
		try (InputStream is = wri.getClass().getResourceAsStream(fileName);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			is.transferTo(baos);
			return new String(baos.toByteArray()).replace("${URL}", wri.getHttpBaseUrl());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
