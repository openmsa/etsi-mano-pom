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
package com.ubiqube.etsi.mano.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;

class MetaStreamResourceTest {

	@Test
	void test() throws IOException {
		final ManoResource res = new ByteArrayResource(new byte[0], "filename");
		final MetaStreamResource srv = new MetaStreamResource(res);
		srv.contentLength();
		assertThrows(FileNotFoundException.class, () -> srv.createRelative(null));
		srv.equals(srv);
		srv.equals(null);
		srv.exists();
		srv.getContentAsByteArray();
		srv.getContentAsString(Charset.defaultCharset());
		srv.getDescription();
		assertThrows(FileNotFoundException.class, () -> srv.getFile());
		srv.getFilename();
		srv.getInputStream();
		assertThrows(FileNotFoundException.class, () -> srv.getURI());
		assertThrows(FileNotFoundException.class, () -> srv.getURL());
		srv.isOpen();
		srv.hashCode();
		assertTrue(true);
	}

}
