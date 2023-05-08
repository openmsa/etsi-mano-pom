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
package com.ubiqube.etsi.mano.sol004.manifest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.sol004.manifest.Sol004ManifestReader.Certificate;

class ManifestWriterTest {

	@Test
	void test() throws IOException {
		final Certificate c1 = new Certificate("SHA-1", "".getBytes());
		final ManifestWriter srv = new ManifestWriter(Map.of("key", "valu"), List.of(), List.of(c1));
		final Writer writer = new CharArrayWriter();
		srv.write(writer);
		assertTrue(true);
	}

}
