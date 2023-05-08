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
package com.ubiqube.etsi.mano.tosca;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.sol004.Sol004Exception;

class ResolverTest {

	@Test
	void test() {
		final Resolver r = new Resolver(new File("src/test/resources"));
		final String cnt = r.getContent("file:src/test/resources/key.pem");
		assertNotNull(cnt);
	}

	@Test
	void testUsingClasspath() {
		final Resolver r = new Resolver(new File("src/test/resources"));
		final String cnt = r.getContent("key.pem");
		assertNotNull(cnt);
	}

	@Test
	void testBad() {
		final Resolver r = new Resolver(new File("src/test/resources"));
		assertThrows(Sol004Exception.class, () -> r.getContent("key.pem.bad"));
	}
}
