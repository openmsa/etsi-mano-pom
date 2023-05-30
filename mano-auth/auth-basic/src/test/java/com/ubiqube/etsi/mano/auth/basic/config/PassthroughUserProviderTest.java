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
package com.ubiqube.etsi.mano.auth.basic.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * @author Olivier Vignaud
 *
 */
@SuppressWarnings("static-method")
class PassthroughUserProviderTest {

	@Test
	void test() {
		final PassthroughUserProvider srv = new PassthroughUserProvider();
		srv.additionalAuthenticationChecks(null, null);
		assertTrue(true);
	}

	@Test
	void testRetreiveUser() {
		final PassthroughUserProvider srv = new PassthroughUserProvider();
		final UsernamePasswordAuthenticationToken auth = Mockito.mock(UsernamePasswordAuthenticationToken.class);
		when(auth.getCredentials()).thenReturn("pass");
		srv.retrieveUser("user", auth);
		assertTrue(true);
	}
}
