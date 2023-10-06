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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.git;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.GenericException;

@Service
public class GitPropertyManager {

	private final GitVersionExtractor gitVersionExtractor;

	public GitPropertyManager(final GitVersionExtractor gitVersionExtractor) {
		this.gitVersionExtractor = gitVersionExtractor;
	}

	public GitVersion getLocalPRoperty() {
		try (InputStream is = this.getClass().getResourceAsStream("/git.properties")) {
			return gitVersionExtractor.extract(is);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	public List<GitVersion> getAllProperties() {
		final List<GitVersion> ret = new ArrayList<>();
		final PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
		try {
			final Resource[] res = pmrpr.getResources("classpath*:git.properties");
			for (final Resource resource : res) {
				final GitVersion r = gitVersionExtractor.extract(resource.getURL());
				ret.add(r);
			}
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		return ret;
	}

}
