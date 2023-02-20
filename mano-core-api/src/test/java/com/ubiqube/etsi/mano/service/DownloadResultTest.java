/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
	}

	@Test
	void testEqual001() {
		final DownloadResult dr = new DownloadResult("".getBytes(), "sha256".getBytes(), "sha512".getBytes(), 123L);
		assertTrue(dr.equals(dr));
	}

	@Test
	void testEqual002() {
		final DownloadResult dr = new DownloadResult("".getBytes(), "sha256".getBytes(), "sha512".getBytes(), 123L);
		assertFalse(dr.equals(null));
	}

	@Test
	void testEqual003() {
		final DownloadResult dr = new DownloadResult("".getBytes(), "sha256".getBytes(), "sha512".getBytes(), 123L);
		assertFalse(dr.equals(""));
	}

	@Test
	void testEqual004() {
		final DownloadResult dr = new DownloadResult("".getBytes(), "sha256".getBytes(), "sha512".getBytes(), 123L);
		final DownloadResult dr2 = new DownloadResult("".getBytes(), "sha256".getBytes(), "sha512".getBytes(), 123L);
		assertTrue(dr.equals(dr2));
	}
}
