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
package com.ubiqube.etsi.mano.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Olivier Vignaud
 *
 */
@SuppressWarnings("static-method")
class GlobalConfiguratonTest {

	@Test
	void testBeanWalker() {
		final GlobalConfiguraton srv = new GlobalConfiguraton();
		final var res = srv.beanWalker();
		assertNotNull(res);
	}

	@Test
	void testBufferedImageHttpMessageConverter() {
		final GlobalConfiguraton srv = new GlobalConfiguraton();
		final var res = srv.bufferedImageHttpMessageConverter();
		assertNotNull(res);
	}

	@Test
	void testConditionService() {
		final GlobalConfiguraton srv = new GlobalConfiguraton();
		final var res = srv.conditionService();
		assertNotNull(res);
	}

	@Test
	void testJsonWalker() {
		final GlobalConfiguraton srv = new GlobalConfiguraton();
		final var res = srv.jsonWalker(null);
		assertNotNull(res);
	}

	@Test
	void testSpelwriter() {
		final GlobalConfiguraton srv = new GlobalConfiguraton();
		final var res = srv.spelWriter(null);
		assertNotNull(res);
	}
}
