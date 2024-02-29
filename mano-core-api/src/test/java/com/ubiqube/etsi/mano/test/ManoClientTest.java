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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.mapper.OffsetDateTimeToDateConverter;
import com.ubiqube.etsi.mano.mapper.OrikaFilterMapper;
import com.ubiqube.etsi.mano.mapper.UuidConverter;
import com.ubiqube.etsi.mano.nfvo.v261.services.VnfmGateway261;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.NfvoFactory;
import com.ubiqube.etsi.mano.service.VnfmFactory;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.auth.model.OAuth2GrantType;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.OrikaSystemProperties;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.generator.EclipseJdtCompilerStrategy;

@SuppressWarnings("static-method")
class ManoClientTest {

	private static final Logger LOG = LoggerFactory.getLogger(ManoClientTest.class);

	private DefaultMapperFactory mapperFactory;

	final ObjectProvider<VnfmFactory> vnfmFactory = new ObjectProvider<>() {

		@Override
		public VnfmFactory getObject() throws BeansException {
			return null;
		}

		@Override
		public VnfmFactory getObject(final Object... args) throws BeansException {
			return null;
		}

		@Override
		public VnfmFactory getIfAvailable() throws BeansException {
			return null;
		}

		@Override
		public VnfmFactory getIfUnique() throws BeansException {
			return null;
		}
	};
	final ObjectProvider<NfvoFactory> nfvoFactory = new ObjectProvider<>() {

		@Override
		public NfvoFactory getObject() throws BeansException {
			return null;
		}

		@Override
		public NfvoFactory getObject(final Object... args) throws BeansException {
			return null;
		}

		@Override
		public NfvoFactory getIfAvailable() throws BeansException {
			return null;
		}

		@Override
		public NfvoFactory getIfUnique() throws BeansException {
			return null;
		}
	};

	private static AuthParamOauth2 getNfvoAuth() {
		return AuthParamOauth2.builder()
				.clientId("mano-nfvo")
				.clientSecret("ed9aeb6d-3ea5-4392-bb22-835603cf3dfc")
				.grantType(OAuth2GrantType.CLIENT_CREDENTIAL)
				.tokenEndpoint(URI.create("http://mano-auth/auth/realms/mano-realm/protocol/openid-connect/token"))
				.build();
	}

	@Test
	void dummy() {
		assertTrue(true);
	}

	void testName() throws URISyntaxException {
		setupOrika();
		final HttpGateway httpGateway = new VnfmGateway261(vnfmFactory, nfvoFactory, mapperFactory.getMapperFacade());
		final AuthParamOauth2 authParamOath2 = getNfvoAuth();
		final Servers server = Servers.builder()
				.url(URI.create("http://localhost:8100/ubi-etsi-mano/sol005"))
				.version("2.6.1")
				.tlsCert("-----BEGIN CERTIFICATE-----\n"
						+ "MIIEPTCCAyWgAwIBAgIIFNYoTdjPHuIwDQYJKoZIhvcNAQELBQAwgY0xCzAJBgNV\n"
						+ "BAYTAkZSMQ4wDAYDVQQIEwVJU0VSRTERMA8GA1UEBxMIR1JFTk9CTEUxHTAbBgNV\n"
						+ "BAoTFHdlYi5tYW5vLnViaXF1YmUuY29tMR0wGwYDVQQLExR3ZWIubWFuby51Ymlx\n"
						+ "dWJlLmNvbTEdMBsGA1UEAxMUd2ViLm1hbm8udWJpcXViZS5jb20wHhcNMjExMDIy\n"
						+ "MDg0MzAwWhcNMjIxMDIyMDg0MzAwWjCBhzELMAkGA1UEBhMCRlIxDjAMBgNVBAgT\n"
						+ "BUlTRVJFMREwDwYDVQQHEwhHUkVOT0JMRTEbMBkGA1UECgwSKi5tYW5vLnViaXF1\n"
						+ "YmUuY29tMRswGQYDVQQLDBIqLm1hbm8udWJpcXViZS5jb20xGzAZBgNVBAMMEiou\n"
						+ "bWFuby51YmlxdWJlLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\n"
						+ "ANHy4SnXHsQUzpi0cgPPWKk0j5OdVigNTtULCrSrP/yyO47r9DWFWlkHfGGAK6G4\n"
						+ "LcEcvg8cZfcJFy9g0qS0R1QtpF0lPw9IFo4s5UBUjp6me8MZTLpxchQ1zviKJ2TM\n"
						+ "yk1P1tfRbFqaFTHNVyXZ7QCWl9gd5nb8ASrlPGIlkRiERYBXoFaTByoX/q9dj1i6\n"
						+ "LqNOm1VY+aGPqj2ON9X7U5A03YZBz5C0A30hcrEuDxlrdeFtwFxosyC7XmZQKiim\n"
						+ "Yl/eVz0vgezCS4oJQ1vPKZOkAMefWVYepgUgJi3ZXxsMytcDHXZIB7Q2iI1oNqNw\n"
						+ "zHwAF7xxYYPmIFIA9qM/bUcCAwEAAaOBpDCBoTAMBgNVHRMBAf8EAjAAMB0GA1Ud\n"
						+ "DgQWBBSkwiPmKrKXWEKjeoz+vGe5cO3G5zALBgNVHQ8EBAMCA+gwEwYDVR0lBAww\n"
						+ "CgYIKwYBBQUHAwEwHQYDVR0RBBYwFIISKi5tYW5vLnViaXF1YmUuY29tMBEGCWCG\n"
						+ "SAGG+EIBAQQEAwIGQDAeBglghkgBhvhCAQ0EERYPeGNhIGNlcnRpZmljYXRlMA0G\n"
						+ "CSqGSIb3DQEBCwUAA4IBAQAfxsM5XAfCBV4sDByJbYdNWx52kkzbbo79a3dE4nhi\n"
						+ "D+VvnB0TVDxXITSZ4pVbG/f+RxQ1rek4VWfCdpG66fqLSr/6sg5gefsPAISy0eJh\n"
						+ "lzvWCaqcR+7GHyk2I9ymjnt6zeaI6EmL3CfcIKr0Mv543K8b6wZgGfpNhjhXpEvV\n"
						+ "pAHGGQWQpTLABcrVLitDrBj+8amyLzqoMCs29CgUkDjYTnKQQ9iuyz1jG0ajPdwn\n"
						+ "5tdEDcBOGj90Xl2MnuMwH4T0QYWN/njW1h+i7rHbEeAfJ+Rr2rxerJFf33HSC1RX\n"
						+ "3bN4KgYJvPQwSU2zGpm6wW3po1jHZnmUFRUEp+H3aA5j\r\n"
						+ "-----END CERTIFICATE-----")
				.authentification(
						AuthentificationInformations.builder()
								.authParamOauth2(authParamOath2)
								.build())
				.build();
		final FluxRest fr = new FluxRest(server);
		fr.get(new URI("https://web.mano.ubiqube.com/"), Servers.class, "2.6.1"); // need to set "10.31.1.245 web.mano.ubiqube.com" in /etc/hosts
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateway, server, new FluxRest(server));
		final MapperFacade mapper = mapperFactory.getMapperFacade();
		final ManoClient mc = new ManoClient(mapper, serverAdapter);
		mc.vnfPackage().list();
		final Map<String, String> userDefinedData = Map.of();
		final VnfPackage pkg = mc.vnfPackage().create(userDefinedData);
		final UUID id = pkg.getId();
		assertNotNull(id);
		final Path path = null;
		mc.vnfPackage(id).onboard(path, "application/zip");
		mc.vnfPackage(id).delete();
	}

	void nsTest() {
		setupOrika();
		final HttpGateway httpGateway = new VnfmGateway261(vnfmFactory, nfvoFactory, mapperFactory.getMapperFacade());
		final AuthParamOauth2 authParamOath2 = getNfvoAuth();
		final Servers server = Servers.builder()
				.url(URI.create("http://localhost:8100/ubi-etsi-mano/sol005"))
				.version("2.6.1")
				.authentification(
						AuthentificationInformations.builder()
								.authParamOauth2(authParamOath2)
								.build())
				.build();
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateway, server, new FluxRest(server));
		final MapperFacade mapper = mapperFactory.getMapperFacade();
		final ManoClient mc = new ManoClient(mapper, serverAdapter);
		//
		final NsdPackage nsd = mc.nsPackage().create(Map.of());
		final UUID nsdId = nsd.getId();
		assertNotNull(nsdId);
		final Path path = Path.of("/home/olivier/workspace/workspace17.1.1/ubi-etsi-mano/package-parser/demo/ubi-nsd-empty-tosca.csar");
		mc.nsPackage(nsdId).onboard(path, "application/zip");
		mc.nsPackage(nsdId).waitForOnboarding();
	}

	void testLcmOpOccs() {
		setupOrika();
		final HttpGateway httpGateway = new VnfmGateway261(vnfmFactory, nfvoFactory, mapperFactory.getMapperFacade());
		final AuthParamOauth2 authParamOath2 = getNfvoAuth();
		final Servers server = Servers.builder()
				.url(URI.create("http://localhost:8888/ubi-etsi-mano/sol003"))
				.version("2.7.1")
				.authentification(
						AuthentificationInformations.builder()
								.authParamOauth2(authParamOath2)
								.build())
				.build();
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateway, server, new FluxRest(server));
		final MapperFacade mapper = mapperFactory.getMapperFacade();
		final ManoClient mc = new ManoClient(mapper, serverAdapter);
		//
		final UUID id = UUID.fromString("51d2048d-2e9f-4b82-8991-7e52a2fbccca");
		final VnfBlueprint obj = mc.vnfLcmOpOccs(id)
				.find();
		assertNotNull(obj.getOperationStatus());
	}

	private void setupOrika() {
		System.setProperty(OrikaSystemProperties.COMPILER_STRATEGY, EclipseJdtCompilerStrategy.class.getName());
		System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES, "true");
		System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES_TO_PATH, "/tmp/orika-test");
		mapperFactory = new DefaultMapperFactory.Builder().compilerStrategy(new EclipseJdtCompilerStrategy()).build();
		final ConverterFactory converterFactory = mapperFactory.getConverterFactory();
		converterFactory.registerConverter("filterConverter", new OrikaFilterMapper());
		converterFactory.registerConverter(new UuidConverter());
		converterFactory.registerConverter(new OffsetDateTimeToDateConverter());
	}

	void testVnfdArtifacts() throws Exception {
		setupOrika();
		final HttpGateway httpGateway = new VnfmGateway261(vnfmFactory, nfvoFactory, mapperFactory.getMapperFacade());
		final AuthParamOauth2 authParamOath2 = getNfvoAuth();
		final Servers server = Servers.builder()
				.url(URI.create("http://10.31.1.29:8100/ubi-etsi-mano/sol003"))
				// .version("2.7.1")
				.authentification(
						AuthentificationInformations.builder()
								.authParamOauth2(authParamOath2)
								.build())
				.build();
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateway, server, new FluxRest(server));
		final MapperFacade mapper = mapperFactory.getMapperFacade();
		final ManoClient mc = new ManoClient(mapper, serverAdapter);
		//
		final UUID id = UUID.fromString("5af09567-fc5f-4be9-b372-0cc431ad5c03");
		final Consumer<InputStream> tgt = is -> {
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				is.transferTo(bos);
			} catch (final IOException e) {
				throw new GenericException(e);
			}
			LOG.info("Finish to copy.");
		};
		mc.vnfPackage().onboarded(id).artifacts(tgt, "Artifacts/Images/lbimage");
	}

	/**
	 * Make sure all other certificates works normally.
	 *
	 * @throws URISyntaxException
	 */
	@Test
	void testGitHttps() throws URISyntaxException {
		final AuthParamOauth2 authParamOath2 = getNfvoAuth();
		final Servers server = Servers.builder()
				.url(URI.create("https://github.com/"))
				.authentification(
						AuthentificationInformations.builder()
								.authParamOauth2(authParamOath2)
								.build())
				.build();
		final FluxRest fr = new FluxRest(server);
		fr.get(new URI("https://google.com/"), String.class, "2.6.1");
		assertTrue(true);
	}

	@Test
	void testDummy() throws Exception {
		assertTrue(true);
	}
}
