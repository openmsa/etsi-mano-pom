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
package com.ubiqube.etsi.mano.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 *
 * @author olivier
 *
 */
class MimeTypeTest {

	@Test
	void testZip() {
		final String res = MimeType.findMatch("PKZIP".getBytes());
		assertEquals("application/zip", res);
	}

	// bytes.length > 2
	@Test
	void testZip2() {
		final String res = MimeType.findMatch("PK".getBytes());
		assertEquals("application/octet-stream", res);
	}

	@Test
	void testZip3() {
		final String res = MimeType.findMatch("POS".getBytes());
		assertEquals("application/octet-stream", res);
	}

	@Test
	void testJson() {
		final String res = MimeType.findMatch("{}".getBytes());
		assertEquals("application/json", res);
	}

	@Test
	void testJson2() {
		final String res = MimeType.findMatch("{".getBytes());
		assertEquals("application/octet-stream", res);
	}

	@Test
	void testAny() {
		final String res = MimeType.findMatch("Blah".getBytes());
		assertEquals("application/octet-stream", res);
	}
}
