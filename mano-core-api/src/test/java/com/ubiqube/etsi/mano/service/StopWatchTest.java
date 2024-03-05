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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("static-method")
class StopWatchTest {

	private static final Logger LOG = LoggerFactory.getLogger(StopWatchTest.class);

	@Test
	void testName() throws Exception {
		final StopWatch sw = new StopWatch(LOG);
		sw.start("");
		sw.log();
		sw.stop();
		assertTrue(true);
	}

	@Test
	void test2() throws Exception {
		final StopWatch sw = StopWatch.create(LOG);
		sw.start("a");
		sw.log();
		sw.start("b");
		sw.log();
		sw.stop();
		sw.log();
		sw.stop();
		assertTrue(true);
	}
}
