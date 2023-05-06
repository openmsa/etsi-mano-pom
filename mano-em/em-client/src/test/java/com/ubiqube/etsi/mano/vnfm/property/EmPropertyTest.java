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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.vnfm.property;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.rest.model.OAuth2GrantType;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;
import nl.jqno.equalsverifier.Warning;

class EmPropertyTest {

	@Test
	void test() {
		final EmProperty em = new EmProperty();
		final OAuth2 oauth2 = new OAuth2();
		oauth2.setClientId("clientId");
		oauth2.setClientSecret("secret");
		oauth2.setGrantType(OAuth2GrantType.CLIENT_CREDENTIAL);
		oauth2.setScope(List.of());
		oauth2.setTokenEndpoint("endp");
		assertNotNull(oauth2.getClientId());
		assertNotNull(oauth2.getClientSecret());
		assertNotNull(oauth2.getGrantType());
		assertNotNull(oauth2.getScope());
		assertNotNull(oauth2.getTokenEndpoint());
		em.setOauth2(oauth2);
		em.setUrl("url");
		em.setVersion("1.2.3");
		em.toString();
		em.hashCode();
		final EqualsVerifierReport rep = EqualsVerifier
				.simple()
				.forClass(em.getClass())
				.suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT, Warning.SURROGATE_KEY)
				.report();
	}

}
