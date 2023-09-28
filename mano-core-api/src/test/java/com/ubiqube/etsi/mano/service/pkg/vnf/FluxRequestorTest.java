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
package com.ubiqube.etsi.mano.service.pkg.vnf;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.ClientAuthorizationException;

import com.ubiqube.etsi.mano.dao.mano.pkg.ParamsOauth2ClientCredentials;
import com.ubiqube.etsi.mano.dao.mano.pkg.UploadUriParameters;
import com.ubiqube.etsi.mano.service.auth.model.AuthType;

@SuppressWarnings("static-method")
class FluxRequestorTest {

	@Test
	void testNoAuth() throws Exception {
		final UploadUriParameters params = new UploadUriParameters();
		params.setAddressInformation(URI.create("http://nexus.ubiqube.com/repository/local-helm/index.yaml"));
		final FluxRequestor fr = new FluxRequestor(params);
		final InputStream is = fr.getInputStream();
		assertNotNull(is);
		is.close();
		fr.close();
	}

	@Test
	void testBasic() throws Exception {
		final UploadUriParameters params = new UploadUriParameters();
		params.setAddressInformation(URI.create("http://nexus.ubiqube.com/repository/local-helm/index.yaml"));
		params.setAuthType(AuthType.BASIC);
		params.setUsername("user");
		params.setPassword("pass");
		final FluxRequestor fr = new FluxRequestor(params);
		assertThrows(IllegalStateException.class, () -> fr.getInputStream());
	}

	@Test
	void testOAuth2() throws Exception {
		final UploadUriParameters params = new UploadUriParameters();
		params.setAddressInformation(URI.create("http://nexus.ubiqube.com/repository/local-helm/index.yaml"));
		params.setAuthType(AuthType.OAUTH2_CLIENT_CREDENTIALS);
		final ParamsOauth2ClientCredentials paramOAuth2 = new ParamsOauth2ClientCredentials();
		paramOAuth2.setClientId("clientId");
		paramOAuth2.setClientPassword("pass");
		paramOAuth2.setTokenEndpoint(URI.create("http://mano-auth/auth/realms/mano-realm/protocol/openid-connect/token"));
		params.setParamsOauth2ClientCredentials(paramOAuth2);
		final FluxRequestor fr = new FluxRequestor(params);
		assertThrows(ClientAuthorizationException.class, () -> fr.getInputStream());
		fr.close();
	}

	@Test
	void testCloseNoInputStream() throws Exception {
		final UploadUriParameters params = new UploadUriParameters();
		params.setAddressInformation(URI.create("http://nexus.ubiqube.com/repository/local-helm/index.yaml"));
		final FluxRequestor fr = new FluxRequestor(params);
		fr.close();
		assertTrue(true);
	}

}
