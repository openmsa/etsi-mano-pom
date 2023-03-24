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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import com.ubiqube.etsi.mano.config.docker.DockerSecretLocator;
import com.ubiqube.etsi.mano.config.docker.DockerSecretPropertySource;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("static-method")
class DockerTest {
	@Mock
	private Environment env;

	@Test
	void testName() throws Exception {
		final DockerSecretPropertySource dps = new DockerSecretPropertySource(Map.of("test", ""));
		final String[] names = dps.getPropertyNames();
		assertEquals(1, names.length);
		assertEquals("test", names[0]);

		final Object r1 = dps.getProperty("");
		assertNull(r1);

		final Object r2 = dps.getProperty("test");
		assertNotNull(r2);
		assertEquals("", r2);
		dps.hashCode();
		dps.equals(null);
		dps.equals(" ");
		final DockerSecretPropertySource dps2 = new DockerSecretPropertySource(Map.of("test", ""));
		assertTrue(dps.equals(dps2));
		assertTrue(dps.equals(dps));
	}

	@Test
	void testName2() throws Exception {
		final DockerSecretLocator dsl = new DockerSecretLocator();
		when(env.getProperty(anyString(), anyString())).thenReturn("src/test/resources/badfolder");
		dsl.locate(env);
		assertTrue(true);
	}

	@Test
	void testName3() throws Exception {
		final DockerSecretLocator dsl = new DockerSecretLocator();
		when(env.getProperty(anyString(), anyString())).thenReturn("src/test/resources/secrets");
		dsl.locate(env);
		assertTrue(true);
	}

}
