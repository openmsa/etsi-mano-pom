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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GitPropertyManagerTest {
	@Mock
	private GitVersionExtractor gitVersionExtractor;

	private GitPropertyManager createService() {
		return new GitPropertyManager(gitVersionExtractor);
	}

	@Test
	void testGetAllProperties() {
		final GitPropertyManager srv = createService();
		final List<GitVersion> res = srv.getAllProperties();
		assertNotNull(res);
	}

	@Test
	void testGetLocalProperties() {
		final GitPropertyManager srv = createService();
		final GitVersion res = srv.getLocalPRoperty();
		assertNull(res);
	}
}
