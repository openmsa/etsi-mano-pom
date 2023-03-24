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
package com.ubiqube.etsi.mano.config.docker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

public class DockerSecretLocator implements PropertySourceLocator {
	private static final Logger LOG = LoggerFactory.getLogger(DockerSecretLocator.class);
	private static final String DEFAULT_BOND_PATH = "/run/secrets";

	@Override
	public PropertySource<?> locate(final Environment environment) {
		final String bindPath = getBindPath(environment);
		LOG.debug("Using path: {}", bindPath);
		final List<Path> files = getFiles(bindPath);
		final Map<String, Object> secrets = files.stream().collect(Collectors.toMap(x -> x.getFileName().toString(), this::getContent));
		return new DockerSecretPropertySource(secrets);
	}

	private String getContent(final Path x) {
		try {
			return Files.readString(x);
		} catch (final IOException e) {
			LOG.error("Unable to read " + x, e);
			return null;
		}
	}

	private static List<Path> getFiles(final String bindPath) {
		final Path root = Paths.get(bindPath);
		if (!Files.isDirectory(root)) {
			LOG.warn("{} is not a directory.", bindPath);
			return List.of();
		}
		try (Stream<Path> p = Files.list(root)) {
			return p.toList();
		} catch (final IOException e) {
			LOG.error("", e);
			return List.of();
		}

	}

	private static String getBindPath(final Environment environment) {
		return environment.getProperty("mano.config.docker.bind-path", DEFAULT_BOND_PATH);
	}

}
