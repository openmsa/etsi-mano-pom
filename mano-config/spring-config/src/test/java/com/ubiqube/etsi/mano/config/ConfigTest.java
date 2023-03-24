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
package com.ubiqube.etsi.mano.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import com.ubiqube.etsi.mano.config.ubi.UbiqubeSourceLocator;

@ExtendWith(MockitoExtension.class)
class ConfigTest {
	@Mock
	private Environment env;

	@Test
	void testNotAPath() throws Exception {
		final UbiqubeSourceLocator dsl = new UbiqubeSourceLocator();
		when(env.getProperty("mano.config.folder", File.class)).thenReturn(new File("src:-('é&"));
		when(env.getProperty("mano.config.type", String.class)).thenReturn("applicationBAD");
		final PropertySource<?> res = dsl.locate(env);
		assertTrue(true);
		assertNull(res.getProperty("a key"));
	}

	@Test
	void testNameBadFolder() throws Exception {
		final UbiqubeSourceLocator dsl = new UbiqubeSourceLocator();
		when(env.getProperty("mano.config.folder", File.class)).thenReturn(new File("src/test/resources/applicationBAD"));
		when(env.getProperty("mano.config.type", String.class)).thenReturn("applicationBAD");
		final PropertySource<?> res = dsl.locate(env);
		assertTrue(true);
		assertNull(res.getProperty("a key"));
	}

	@Test
	void testNameBadBundle() throws Exception {
		final UbiqubeSourceLocator dsl = new UbiqubeSourceLocator();
		when(env.getProperty("mano.config.folder", File.class)).thenReturn(new File("src/test/resources/"));
		when(env.getProperty("mano.config.type", String.class)).thenReturn("applicationBAD");
		dsl.locate(env);
		assertTrue(true);
	}

	@Test
	void testName3() throws Exception {
		final UbiqubeSourceLocator dsl = new UbiqubeSourceLocator();
		when(env.getProperty("mano.config.folder", File.class)).thenReturn(new File("src/test/resources/"));
		when(env.getProperty("mano.config.type", String.class)).thenReturn("applicationBAD");
		dsl.locate(env);
		assertTrue(true);
	}

	@Test
	void testNameOk() throws Exception {
		final UbiqubeSourceLocator dsl = new UbiqubeSourceLocator();
		when(env.getProperty("mano.config.folder", File.class)).thenReturn(new File("src/test/resources/secrets"));
		when(env.getProperty("mano.config.type", String.class)).thenReturn("vnfm");
		final PropertySource<?> res = dsl.locate(env);
		assertTrue(true);
		final boolean keys = res.containsProperty("badNAme");
		assertFalse(keys);
		Object val = res.getProperty("badKey");
		assertNull(val);
		val = res.getProperty("mano.test");
		assertEquals("good", val);
		res.hashCode();
	}

	@Test
	void testNfvo() throws Exception {
		final UbiqubeSourceLocator dsl = new UbiqubeSourceLocator();
		when(env.getProperty("mano.config.folder", File.class)).thenReturn(new File("src/test/resources/secrets"));
		when(env.getProperty("mano.config.type", String.class)).thenReturn("nfvo");
		final PropertySource<?> res = dsl.locate(env);
		assertTrue(true);
		final boolean keys = res.containsProperty("badNAme");
		assertFalse(keys);
		Object val = res.getProperty("badKey");
		assertNull(val);
		val = res.getProperty("mano.test");
		assertEquals("nfvo", val);
		res.hashCode();
	}
}
