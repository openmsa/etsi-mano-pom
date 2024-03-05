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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class NsDefaultRegistryHandlerTest {

	@Test
	void testName() throws Exception {
		final NsDefaultRegistryHandler srv = new NsDefaultRegistryHandler();
		assertEquals(false, srv.isProcessable(null));
	}

	@Test
	void testProviderName() throws Exception {
		final NsDefaultRegistryHandler srv = new NsDefaultRegistryHandler();
		assertNotNull(srv.getProviderName());
	}

	@Test
	void testGetInstanceReader() throws Exception {
		final NsDefaultRegistryHandler srv = new NsDefaultRegistryHandler();
		assertNotNull(srv.getNewReaderInstance(null, null));
	}

	@Test
	void testFileSystem() throws Exception {
		final NsDefaultRegistryHandler srv = new NsDefaultRegistryHandler();
		assertNull(srv.getFileSystem(null));
	}
}
