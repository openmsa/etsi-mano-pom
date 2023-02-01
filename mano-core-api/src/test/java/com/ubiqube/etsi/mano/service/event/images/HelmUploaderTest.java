/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.service.event.images;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.service.event.HelmUploader;
import com.ubiqube.etsi.mano.service.event.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.event.model.AuthentificationInformations;

import reactor.core.publisher.Mono;

@SuppressWarnings("static-method")
class HelmUploaderTest {

	@Test
	void dummyTest() {
		assertTrue(true);
	}

	void testName() throws Exception {
		final FileInputStream fis = new FileInputStream("/home/olivier/workspace/workspace17.1.1/ubi-etsi-mano/package-parser/demo/vnf-cnf/Artifacts/Scripts/mariadb-7.3.14.tgz");
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		fis.transferTo(bos);
		final ManoResource mr = new ByteArrayResource(bos.toByteArray(), "mariadb-7.3.14.tgz");
		final ConnectionInformation ci = ConnectionInformation.builder()
				.authentification(AuthentificationInformations.builder()
						.authParamBasic(AuthParamBasic.builder()
								.userName("ovi")
								.password("ubiqube")
								.build())
						.build())
				.connType(ConnectionType.HELM)
				.url("http://localhost:8080/repository/local-helm/")
				.build();
		HelmUploader.uploadFile(mr, ci, "mariadb-7.3.14.tgz");
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
