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
package com.ubiqube.etsi.mano.repository.phys;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.RepositoryException;

@SuppressWarnings("static-method")
class LowPhysTest {

	@Test
	void test() throws IOException {
		final LowPhys lp = new LowPhys();
		lp.mkdir("/tmp/low-phys");
		// Assert directory exist
		assertTrue(lp.exist("/tmp/low-phys"));
		final byte[] content = "test".getBytes();
		lp.add("/tmp/low-phys/filea", content);
		final ManoResource mr = lp.get("/tmp/low-phys/filea");
		assertEquals("/tmp/low-phys/filea", mr.getFileName());
		final InputStream is = mr.getInputStream();
		final byte[] br = is.readAllBytes();
		assertArrayEquals("test".getBytes(), br);
		final InputStream is2 = new ByteArrayInputStream("azert".getBytes());
		lp.add("/tmp/low-phys/fileb", is2);
		final List<String> res = lp.find("/tmp/low-phys", "a");
		assertEquals(1, res.size());
		assertFalse(lp.isDirectory("/tmp/low-phys/filea"));
		lp.delete("/tmp/low-phys/filea");
		lp.get("/tmp/low-phys/fileb", 0, 1L);
		assertThrows(RepositoryException.class, () -> lp.delete("/tmp/low-phys/bad-dir"));
		assertFalse(lp.exist("/tmp/low-phys/filea"));
		lp.delete("/tmp/low-phys/");
	}

	@Test
	void testName() throws Exception {
		final LowPhys lp = new LowPhys();
		final InputStream is = Mockito.mock(InputStream.class);
		lp.mkdir("/tmp/low-phys");
		when(is.transferTo(any())).thenThrow(IOException.class);
		assertThrows(RepositoryException.class, () -> lp.add("/tmp/low-phys/filec", is));
	}
}
