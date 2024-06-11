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
package com.ubiqube.etsi.mano.service.mapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.dao.mano.ai.KubernetesV1Auth;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.auth.model.AuthType;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.auth.model.OAuth2GrantType;

import uk.co.jemos.podam.api.PodamFactoryImpl;

class ConnectionMappingTest {
	ConnectionMapping mapper = Mappers.getMapper(ConnectionMapping.class);
	private final PodamFactoryImpl podam;

	public ConnectionMappingTest() {
		this.podam = new PodamFactoryImpl();
	}

	@Test
	void testInterfaceInfo() {
		final InterfaceInfo ai = podam.manufacturePojo(InterfaceInfo.class);
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testInterfaceInfoNull() {
		final Map<String, String> res = mapper.map((InterfaceInfo) null);
		assertNotNull(res);
	}

	@Test
	void testAccessInfo() {
		final KeystoneAuthV3 ai = podam.manufacturePojo(KeystoneAuthV3.class);
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testAccessInfoNull() {
		final Map<String, String> res = mapper.map((InterfaceInfo) null);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceInfo001() {
		final Map<String, String> map = Map.of();
		assertThrows(GenericException.class, () -> mapper.mapToInterfaceInfo("BAD", map));
	}

	@Test
	void testMapToInterfaceInfo002() {
		final Map<String, String> map = Map.of();
		final InterfaceInfo res = mapper.mapToInterfaceInfo("ETSINFV.OPENSTACK_KEYSTONE.V_3", map);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceInfo003() {
		final InterfaceInfo res = mapper.mapToInterfaceInfo("BAD", null);
		assertNull(res);
	}

	@Test
	void testMapToInterfaceInfo004() {
		final Map<String, String> map = Map.of();
		final InterfaceInfo res = mapper.mapToInterfaceInfo("UBINFV.CISM.V_1", map);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceInfo005() {
		final Map<String, String> map = Map.of();
		final InterfaceInfo res = mapper.mapToInterfaceInfo("PAAS", map);
		assertNotNull(res);
	}

	@Test
	void testMapToAccessInfo001() {
		final Map<String, String> map = Map.of();
		final AccessInfo res = mapper.mapToAccessInfo("PAAS", map);
		assertNotNull(res);
	}

	@Test
	void testMapToAccessInfo002() {
		final AccessInfo res = mapper.mapToAccessInfo("PAAS", null);
		assertNull(res);
	}

	@Test
	void testMapToAccessInfo003() {
		final Map<String, String> map = Map.of();
		final AccessInfo res = mapper.mapToAccessInfo("ETSINFV.OPENSTACK_KEYSTONE.V_3", map);
		assertNotNull(res);
	}

	@Test
	void testMapToAccessInfo004() {
		final Map<String, String> map = Map.of();
		final AccessInfo res = mapper.mapToAccessInfo("UBINFV.CISM.V_1", map);
		assertNotNull(res);
	}

	@Test
	void testMapToAccessInfo005() {
		final Map<String, String> map = Map.of();
		assertThrows(GenericException.class, () -> mapper.mapToAccessInfo("BAD", map));
	}

	@Test
	void testMap001() {
		final Map<String, String> res = mapper.map((AccessInfo) null);
		assertNotNull(res);
	}

	@Test
	void testMap002() {
		final AccessInfo ai = new AccessInfo();
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testMap003() {
		final AccessInfo ai = new KeystoneAuthV3();
		ai.setId(UUID.randomUUID());
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testMap004() {
		final AccessInfo ai = new KubernetesV1Auth();
		ai.setId(UUID.randomUUID());
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testMap005() {
		final Map<String, String> res = mapper.map((KeystoneAuthV3) null);
		assertNotNull(res);
	}

	@Test
	void testMapToVimType001() {
		final ConnectionInformation value = new ConnectionInformation();
		final String res = mapper.mapToVimType(value);
		assertNotNull(res);
	}

	@Test
	void testMapToVimType002() {
		final ConnectionInformation value = new ConnectionInformation();
		final AuthentificationInformations auth = new AuthentificationInformations();
		auth.setAuthType(List.of(AuthType.BASIC));
		value.setAuthentification(auth);
		final String res = mapper.mapToVimType(value);
		assertNotNull(res);
	}

	@Test
	void testMapToVimType003() {
		final String res = mapper.mapToVimType(null);
		assertNull(res);
	}

	@Test
	void testMapToMap001() {
		final Map<String, String> res = mapper.mapToMap(null);
		assertNotNull(res);
	}

	@Test
	void testMapToMap002() {
		final AuthentificationInformations ai = new AuthentificationInformations();
		ai.setAuthType(List.of(AuthType.BASIC));
		final AuthParamBasic basic = new AuthParamBasic();
		ai.setAuthParamBasic(basic);
		final Map<String, String> res = mapper.mapToMap(ai);
		assertNotNull(res);
	}

	@Test
	void testMapToMap003() {
		final AuthentificationInformations ai = new AuthentificationInformations();
		ai.setAuthType(List.of(AuthType.OAUTH2_CLIENT_CREDENTIALS));
		final AuthParamOauth2 auth = new AuthParamOauth2();
		ai.setAuthParamOauth2(auth);
		final Map<String, String> res = mapper.mapToMap(ai);
		assertNotNull(res);
	}

	@Test
	void testMapToMap004() {
		final AuthentificationInformations ai = new AuthentificationInformations();
		ai.setAuthType(List.of(AuthType.OAUTH2_CLIENT_CREDENTIALS));
		final AuthParamOauth2 auth = new AuthParamOauth2();
		auth.setGrantType(OAuth2GrantType.CLIENT_CREDENTIAL);
		ai.setAuthParamOauth2(auth);
		final Map<String, String> res = mapper.mapToMap(ai);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceMap001() {
		final Map<String, String> res = mapper.mapToInterfaceMap(null);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceMap002() {
		final ConnectionInformation ci = new ConnectionInformation();
		final Map<String, String> res = mapper.mapToInterfaceMap(ci);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceMap003() {
		final ConnectionInformation ci = new ConnectionInformation();
		ci.setIgnoreSsl(true);
		ci.setUrl(URI.create("http://localhost/"));
		final Map<String, String> res = mapper.mapToInterfaceMap(ci);
		assertNotNull(res);
	}
}
