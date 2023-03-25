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
package com.ubiqube.etsi.mano.config.ubi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.Test;

class SpringFactoriesTest {

	@Test
	void testName() throws Exception {
		final InputStream is = getClass().getResourceAsStream("/META-INF/spring.factories");
		final Properties props = new Properties();
		props.load(is);
		final Object str = props.get("org.springframework.cloud.bootstrap.BootstrapConfiguration");
		System.out.println("'" + str + "'");
		assertEquals(str, UbiqubeSourceLocator.class.getName());
	}
}
