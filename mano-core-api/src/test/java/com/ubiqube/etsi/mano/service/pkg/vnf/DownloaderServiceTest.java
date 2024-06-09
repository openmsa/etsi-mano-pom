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
package com.ubiqube.etsi.mano.service.pkg.vnf;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.ExternalArtifactsAccessConfig;
import com.ubiqube.etsi.mano.dao.mano.pkg.ExternalArtifactsAccessConfigArtifact;
import com.ubiqube.etsi.mano.dao.mano.pkg.ParamsBasicCredentials;
import com.ubiqube.etsi.mano.dao.mano.pkg.ParamsOauth2ClientCredentials;
import com.ubiqube.etsi.mano.dao.mano.repo.ToscaRepository;
import com.ubiqube.etsi.mano.dao.mano.vim.Checksum;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.auth.model.AuthType;
import com.ubiqube.etsi.mano.service.vim.VimException;

@ExtendWith(MockitoExtension.class)
@WireMockTest
class DownloaderServiceTest {
	@Mock
	private VnfPackageRepository vnfPackageRepository;

	@Test
	void testBasic() {
		final DownloaderService srv = new DownloaderService(vnfPackageRepository);
		final VnfPackage vnfPkg = new VnfPackage();
		srv.doDownload(List.of(), vnfPkg);
		assertTrue(true);
	}

	@Test
	void testOk() {
		final DownloaderService srv = new DownloaderService(vnfPackageRepository);
		final SoftwareImage sw01 = new SoftwareImage();
		sw01.setImagePath("http://nexus.ubiqube.com/repository/helm-local/index.yaml");
		sw01.setChecksum(new Checksum());
		final VnfPackage vnfPkg = new VnfPackage();
		final ExternalArtifactsAccessConfig eaac = new ExternalArtifactsAccessConfig();
		eaac.setArtifact(List.of());
		vnfPkg.setExternalArtifactsAccessConfig(eaac);
		srv.doDownload(List.of(sw01), vnfPkg);
		assertTrue(true);
	}

	@Test
	void testNotFound() throws Exception {
		final DownloaderService srv = new DownloaderService(vnfPackageRepository);
		final SoftwareImage sw01 = new SoftwareImage();
		sw01.setImagePath("http://nexus.ubiqube.com/repository/local-helm-bad/index.yaml");
		sw01.setChecksum(new Checksum());
		final VnfPackage vnfPkg = new VnfPackage();
		final ExternalArtifactsAccessConfig eaac = new ExternalArtifactsAccessConfig();
		eaac.setArtifact(List.of());
		vnfPkg.setExternalArtifactsAccessConfig(eaac);
		final List<SoftwareImage> lst = List.of(sw01);
		assertThrows(VimException.class, () -> srv.doDownload(lst, vnfPkg));
	}

	@Test
	void testExternalCredentialsBasic(final WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		final String url = buildUrl(wmRuntimeInfo);
		stubFor((get(urlPathMatching("/repository/helm-local/index.yaml")).willReturn(aResponse()
				.withStatus(200)
				.withBody("test"))));
		final DownloaderService srv = new DownloaderService(vnfPackageRepository);
		final SoftwareImage sw01 = new SoftwareImage();
		sw01.setImagePath(url);
		sw01.setChecksum(new Checksum());
		final VnfPackage vnfPkg = new VnfPackage();
		final ExternalArtifactsAccessConfig eaac = new ExternalArtifactsAccessConfig();
		final ExternalArtifactsAccessConfigArtifact eaaca = new ExternalArtifactsAccessConfigArtifact();
		eaaca.setArtifactUri(url);
		eaaca.setAuthType(AuthType.BASIC);
		final ParamsBasicCredentials params = new ParamsBasicCredentials();
		params.setUsername("user");
		params.setPassword("passwd");
		eaaca.setParamsBasicCredentials(params);
		eaac.setArtifact(List.of(eaaca));
		vnfPkg.setExternalArtifactsAccessConfig(eaac);
		srv.doDownload(List.of(sw01), vnfPkg);
		assertTrue(true);
	}

	@Test
	void testExternalCredentialsOauth2(final WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		final String url = buildUrl(wmRuntimeInfo);
		stubFor((get(urlPathMatching("/repository/helm-local/index.yaml")).willReturn(aResponse()
				.withStatus(200)
				.withBody("test"))));
		oAuth2Stub();

		final DownloaderService srv = new DownloaderService(vnfPackageRepository);
		final SoftwareImage sw01 = new SoftwareImage();
		sw01.setImagePath(url);
		sw01.setChecksum(new Checksum());
		final VnfPackage vnfPkg = new VnfPackage();
		final ExternalArtifactsAccessConfig eaac = new ExternalArtifactsAccessConfig();
		final ExternalArtifactsAccessConfigArtifact eaaca = new ExternalArtifactsAccessConfigArtifact();
		eaaca.setArtifactUri(url);
		eaaca.setAuthType(AuthType.OAUTH2_CLIENT_CREDENTIALS);
		final ParamsOauth2ClientCredentials params = new ParamsOauth2ClientCredentials();
		params.setTokenEndpoint(URI.create(wmRuntimeInfo.getHttpBaseUrl() + "/auth/realms/mano-realm/protocol/openid-connect/token"));
		params.setClientId("clientId");
		params.setClientPassword("password");
		eaaca.setParamsOauth2ClientCredentials(params);
		eaac.setArtifact(List.of(eaaca));
		vnfPkg.setExternalArtifactsAccessConfig(eaac);
		srv.doDownload(List.of(sw01), vnfPkg);
		assertTrue(true);
	}

	private void oAuth2Stub() {
		stubFor(post(urlPathMatching("/auth/realms/mano-realm/protocol/openid-connect/token")).willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("""
						{
						        "access_token":"eyJhbGciOiJSUzI1",
						        "expires_in":3000,
						        "refresh_expires_in":0,
						        "token_type":"Bearer",
						        "not-before-policy":0,
						        "scope":"profile mano:ovi email"
						}
						""")));
	}

	@Test
	void testRepositoryBasic(final WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		final String url = buildUrl(wmRuntimeInfo);
		stubFor((get(urlPathMatching("/repository/helm-local/index.yaml")).willReturn(aResponse()
				.withStatus(200)
				.withBody("test"))));
		final DownloaderService srv = new DownloaderService(vnfPackageRepository);
		final SoftwareImage sw01 = new SoftwareImage();
		sw01.setImagePath(url);
		sw01.setRepository("myRepo");
		sw01.setChecksum(new Checksum());
		final VnfPackage vnfPkg = new VnfPackage();
		final ExternalArtifactsAccessConfig eaac = new ExternalArtifactsAccessConfig();
		eaac.setArtifact(List.of());
		vnfPkg.setExternalArtifactsAccessConfig(eaac);
		final ToscaRepository repo = new ToscaRepository();
		repo.setName("myRepo");
		repo.setProtocol("http");
		repo.setUrl(wmRuntimeInfo.getHttpBaseUrl());
		repo.setUsername("anon");
		repo.setToken("passwd");
		repo.setTokenType("basic_auth");
		vnfPkg.setRepositories(Set.of(repo));
		srv.doDownload(List.of(sw01), vnfPkg);
		assertTrue(true);
	}

	@Test
	void testRepositoryOauth2(final WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		final String url = buildUrl(wmRuntimeInfo);
		stubFor((get(urlPathMatching("/repository/helm-local/index.yaml")).willReturn(aResponse()
				.withStatus(200)
				.withBody("test"))));
		oAuth2Stub();
		final DownloaderService srv = new DownloaderService(vnfPackageRepository);
		final SoftwareImage sw01 = new SoftwareImage();
		sw01.setImagePath(url);
		sw01.setRepository("myRepo");
		sw01.setChecksum(new Checksum());
		final VnfPackage vnfPkg = new VnfPackage();
		final ExternalArtifactsAccessConfig eaac = new ExternalArtifactsAccessConfig();
		eaac.setArtifact(List.of());
		vnfPkg.setExternalArtifactsAccessConfig(eaac);
		final ToscaRepository repo = new ToscaRepository();
		repo.setName("myRepo");
		repo.setProtocol("oauth2");
		repo.setUrl(wmRuntimeInfo.getHttpBaseUrl());
		repo.setUsername("anon");
		repo.setToken("passwd");
		repo.setTokenType("bearer");
		repo.setUrl(wmRuntimeInfo.getHttpBaseUrl() + "/auth/realms/mano-realm/protocol/openid-connect/token");
		vnfPkg.setRepositories(Set.of(repo));
		srv.doDownload(List.of(sw01), vnfPkg);
		assertTrue(true);
	}

	private static String buildUrl(final WireMockRuntimeInfo wmRuntimeInfo) {
		return "%s/repository/helm-local/index.yaml".formatted(wmRuntimeInfo.getHttpBaseUrl());
	}
}
