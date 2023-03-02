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
package com.ubiqube.etsi.mano.service.event.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.rest.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.rest.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.rest.model.AuthType;
import com.ubiqube.etsi.mano.service.rest.model.AuthentificationInformations;

@SuppressWarnings("static-method")
class AuthentificationInformationsTest {

	@Test
	void testBuilder() throws Exception {
		final AuthentificationInformations res = AuthentificationInformations.builder()
				.authParamBasic(null)
				.authTlsCert(null)
				.authType(null)
				.build();
		assertNotNull(res);
	}

	@Test
	void testBuilderToString() throws Exception {
		final String res = AuthentificationInformations.builder()
				.authParamBasic(null)
				.authTlsCert(null)
				.authType(null)
				.toString();
		assertNotNull(res);
	}

	@Test
	void testSetterGetter() {
		final AuthentificationInformations res = new AuthentificationInformations();
		final AuthParamBasic basic = new AuthParamBasic();
		res.setAuthParamBasic(basic);
		final AuthParamOauth2 oAuth2 = new AuthParamOauth2();
		res.setAuthParamOauth2(oAuth2);
		res.setAuthTlsCert("");
		res.setAuthType(List.of(AuthType.BASIC, AuthType.fromValue("TLS_CERT")));

		assertNotNull(res.getAuthParamBasic());
		assertNotNull(res.getAuthParamOauth2());
		assertNotNull(res.getAuthTlsCert());
		assertNotNull(res.getAuthType());
	}
}
