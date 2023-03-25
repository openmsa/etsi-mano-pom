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
package com.ubiqube.etsi.mano.auth.cert.common;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bouncycastle.asn1.x500.X500Name;
import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.auth.AuthException;

class CertUtilsTest {

	@Test
	void test() {
		final X500Name name = new X500Name("C=US,ST=Isere,L=Grenoble,O=TestUnit,CN=test,emailAddress=abc@test.com");
		CertUtils.createUserDetails(name);
		assertTrue(true);
	}

	@Test
	void testMultipleCn() {
		final X500Name name = new X500Name("C=US,ST=Isere,L=Grenoble,O=TestUnit,CN=test,CN=t2,emailAddress=abc@test.com");
		assertThrows(AuthException.class, () -> CertUtils.createUserDetails(name));
	}
}
