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
package com.ubiqube.etsi.mano.autth.oauth2.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

@ExtendWith(MockitoExtension.class)
class KeycloakRealmRoleConverterTest {
	@Mock
	private Jwt jwt;

	@Test
	void test() {
		final KeycloakRealmRoleConverter conv = new KeycloakRealmRoleConverter();
		final Map<String, List<String>> realmAccess = Map.of("roles", List.of("MyRole"));
		final Map<String, Object> map = Map.of("realm_access", realmAccess);
		when(jwt.getClaims()).thenReturn(map);
		final Collection<GrantedAuthority> res = conv.convert(jwt);
		assertNotNull(res);
		assertEquals(1, res.size());
		final GrantedAuthority n = res.iterator().next();
		assertEquals("ROLE_MYROLE", n.getAuthority());
	}

}
