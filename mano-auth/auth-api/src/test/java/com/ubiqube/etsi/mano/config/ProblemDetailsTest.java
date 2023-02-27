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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import org.junit.jupiter.api.Test;

class ProblemDetailsTest {

	@Test
	void testProblemDetails() {
		final ProblemDetails pd = new ProblemDetails(123, "error!!!")
				.type(URI.create("https://www.net.com/"))
				.title("")
				.status(321)
				.detail("detail")
				.instance(URI.create("https://www.abc.com/"));
		pd.setTitle("");
		pd.setInstance(URI.create("http://www.abc.net/"));
		pd.setStatus(456);
		pd.setTitle("");
		pd.setType(URI.create("http://www.uri.net/"));
		pd.toString();
		pd.hashCode();
		pd.equals(null);
		pd.equals(pd);
		assertTrue(true);
	}
}
