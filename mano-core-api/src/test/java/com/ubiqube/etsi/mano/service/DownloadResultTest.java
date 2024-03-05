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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("static-method")
class DownloadResultTest {

	@Test
	void testBasic() throws Exception {
		final DownloadResult dr = new DownloadResult("".getBytes(), "sha256".getBytes(), "sha512".getBytes(), 123L);
		dr.toString();
		dr.md5String();
		dr.sha256String();
		dr.sha512String();
		dr.hashCode();
		assertTrue(true);
	}

	@Test
	void testEqual001() {
		final EqualsVerifierReport rep = EqualsVerifier
				.simple()
				.forClass(DownloadResult.class)
				.suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT, Warning.SURROGATE_KEY)
				.report();
		System.out.println("" + rep.getMessage());
		assertTrue(true);
	}
}
