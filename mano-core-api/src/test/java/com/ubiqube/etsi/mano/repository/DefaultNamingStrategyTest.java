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
package com.ubiqube.etsi.mano.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.config.properties.ManoRepositoryProperties;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;

@SuppressWarnings("static-method")
class DefaultNamingStrategyTest {

	@Test
	void testRoot() {
		final ManoRepositoryProperties props = new ManoRepositoryProperties();
		props.setPhysRoot("/tmp/test/");
		final DefaultNamingStrategy dms = new DefaultNamingStrategy(props);
		final Path res = dms.getRoot();
		assertTrue(res.toString().startsWith(buildPath("/tmp/test/")));
	}

	@Test
	void testClassUuidFilename() {
		final ManoRepositoryProperties props = new ManoRepositoryProperties();
		props.setPhysRoot("/tmp/test/");
		final DefaultNamingStrategy dms = new DefaultNamingStrategy(props);
		final Path res = dms.getRoot(NsdPackage.class, UUID.randomUUID(), "filename");
		assertTrue(res.toString().startsWith(buildPath("/tmp/test/")));
	}

	@Test
	void testClassUuid() {
		final ManoRepositoryProperties props = new ManoRepositoryProperties();
		props.setPhysRoot("/tmp/test/");
		final DefaultNamingStrategy dms = new DefaultNamingStrategy(props);
		final Path res = dms.getRoot(NsdPackage.class, UUID.randomUUID());
		assertTrue(res.toString().startsWith(buildPath("/tmp/test/")));
	}

	@Test
	void testClass() {
		final ManoRepositoryProperties props = new ManoRepositoryProperties();
		props.setPhysRoot("/tmp/test/");
		final DefaultNamingStrategy dms = new DefaultNamingStrategy(props);
		final Path res = dms.getRoot(NsdPackage.class);
		assertTrue(res.toString().startsWith(buildPath("/tmp/test/")));
	}

	private static String buildPath(final String segment) {
		return Paths.get(segment).toString();
	}
}
