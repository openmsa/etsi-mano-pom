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
package com.ubiqube.etsi.mano.service.event.images;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.event.HelmUploader;

import reactor.core.publisher.Mono;

@WireMockTest
@SuppressWarnings("static-method")
class HelmUploaderTest {

	@Test
	void dummyTest() {
		assertTrue(true);
	}

	@ParameterizedTest
	@ValueSource(strings = { "/mariadb-7.3.14.tgz", "/mariadb-7.3.14.tar", "/mariadb-7.3.14.tar.gz", "/mariadb-7.3.14.zip" })
	void testName(final String param, final WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		stubFor((put(urlPathMatching(param)).willReturn(aResponse().withStatus(200))));
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("test".getBytes());
		final ManoResource mr = new ByteArrayResource(bos.toByteArray(), param);
		final ConnectionInformation ci = createConnection(wmRuntimeInfo);
		HelmUploader.uploadFile(mr, ci, param.substring(1));
		assertTrue(true);
	}

	/**
	 * Test what append when IOException is thrown, but test will never finish.
	 *
	 * @param wmRuntimeInfo
	 * @throws Exception
	 */
	void testNameFail(final WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		stubFor((put(urlPathMatching("/mariadb-7.3.14.bad")).willReturn(aResponse().withStatus(200))));
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("test".getBytes());
		final ManoResource mr = Mockito.mock(ManoResource.class);
		final InputStream is = Mockito.mock(InputStream.class);
		when(mr.getInputStream()).thenReturn(is);
		doThrow(IOException.class).when(is).close();
		final ConnectionInformation ci = createConnection(wmRuntimeInfo);
		HelmUploader.uploadFile(mr, ci, "mariadb-7.3.14.b");
		assertTrue(true);
	}

	private ConnectionInformation createConnection(final WireMockRuntimeInfo wmRuntimeInfo) {
		return ConnectionInformation.builder()
				.authentification(AuthentificationInformations.builder()
						.authParamBasic(AuthParamBasic.builder()
								.userName("ovi")
								.password("ubiqube")
								.build())
						.build())
				.connType(ConnectionType.HELM)
				.url(URI.create(wmRuntimeInfo.getHttpBaseUrl()))
				.build();
	}

	void testName_002() throws Exception {
		final FileInputStream fis = new FileInputStream("/home/olivier/workspace/workspace17.1.1/ubi-etsi-mano/package-parser/demo/vnf-cnf/Artifacts/Scripts/mariadb-7.3.14.tgz");
		final Builder wcb = WebClient.builder();
		final WebClient webClient = wcb.build();
		final Mono<Object> httpStatusMono = webClient.put()
				.uri("http://localhost:8080/repository/local-helm/mariadb-7.3.14.tgz")
				.contentType(MediaType.parseMediaType("application/tar+gzip"))
				.body(BodyInserters.fromResource(new InputStreamResource(fis)))
				.exchangeToMono(response -> {
					if (HttpStatus.OK.equals(response.statusCode())) {
						return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
					}
					throw new GenericException("Error uploading file");
				});
		httpStatusMono.block();
	}
}
