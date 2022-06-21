/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class VersionTest {

	@Test
	void testName() throws Exception {
		final List<Version> versions = List.of(new Version("3.5.1"), new Version("2.6.1"), new Version("3.3.1"), new Version("2.7.1"), new Version("2.8.1"), new Version("2.6.5"));
		final List<Version> sorted = versions.stream().sorted().toList();
		assertEquals("2.6.1", sorted.get(0).toString());
	}
}
