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
package com.ubiqube.etsi.mano.service.git;

import java.net.MalformedURLException;
import java.net.URI;

import org.junit.jupiter.api.Test;

class GitVersionExtractorTest {

	@SuppressWarnings("static-method")
	@Test
	void test() throws MalformedURLException {
		final GitVersionExtractor srv = new GitVersionExtractor();
		final GitVersion res = srv.extract(URI.create("file:src/test/resources/git.properties").toURL());
		System.out.println(res.toString());
	}

}
