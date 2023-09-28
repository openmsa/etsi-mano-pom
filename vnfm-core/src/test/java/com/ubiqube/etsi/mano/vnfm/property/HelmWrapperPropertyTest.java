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
package com.ubiqube.etsi.mano.vnfm.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.auth.model.OAuth2GrantType;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;
import nl.jqno.equalsverifier.Warning;

class HelmWrapperPropertyTest {

	@Test
	void test() {
		final HelmWrapperProperty obj = new HelmWrapperProperty();
		obj.toString();
		obj.hashCode();
		final EqualsVerifierReport rep = EqualsVerifier
				.simple()
				.forClass(obj.getClass())
				.suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT, Warning.SURROGATE_KEY)
				.report();
		obj.setUrl(URI.create("http://url/"));
		assertEquals(URI.create("http://url/"), obj.getUrl());
		final OAuth2 oauth2 = new OAuth2();
		obj.setOauth2(oauth2);
		assertEquals(oauth2, obj.getOauth2());
	}

	@Test
	void testOAuth2() {
		final OAuth2 oauth2 = new OAuth2();
		oauth2.setClientId("cli");
		assertEquals("cli", oauth2.getClientId());
		oauth2.setClientSecret("sec");
		assertEquals("sec", oauth2.getClientSecret());
		oauth2.setGrantType(OAuth2GrantType.CLIENT_CREDENTIAL);
		assertEquals(OAuth2GrantType.CLIENT_CREDENTIAL, oauth2.getGrantType());
		oauth2.setScope(List.of());
		assertNotNull(oauth2.getScope());
		oauth2.setTokenEndpoint(URI.create("http://tok/"));
		assertEquals(URI.create("http://tok/"), oauth2.getTokenEndpoint());
		oauth2.toString();
		final EqualsVerifierReport rep = EqualsVerifier
				.simple()
				.forClass(oauth2.getClass())
				.suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT, Warning.SURROGATE_KEY)
				.report();
	}
}
