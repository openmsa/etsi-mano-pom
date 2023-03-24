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

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

public class UbiqubeSourceLocator implements PropertySourceLocator {

	private static final Logger LOG = LoggerFactory.getLogger(UbiqubeSourceLocator.class);

	private static final File ROOT = new File(System.getProperty("user.home"), ".mano");

	@SuppressWarnings("resource")
	@Override
	public PropertySource<?> locate(final Environment environment) {
		File path = Optional.ofNullable(environment.getProperty("mano.config.folder", File.class)).orElse(ROOT);
		final Locale locale = Optional.ofNullable(environment.getProperty("mano.config.type", String.class))
				.map(Locale::new)
				.orElse(Locale.getDefault());
		final ClassLoader loader = new URLClassLoader(getUrls(path));
		try {
			final ResourceBundle rb = ResourceBundle.getBundle("configuration", locale, loader);
			return new UbiqubePropertySource("ubiqube", rb);
		} catch (RuntimeException e) {
			LOG.trace("", e);
			LOG.warn("Configuration {} not loaded.", path);
			return new PropertySource<>("dummy") {
				public Object getProperty(final String nameIn) {
					return null;
				}
			};
		}
	}

	private static URL[] getUrls(File path) {
		try {
			final URI uri = path.toURI();
			return new URL[] { uri.toURL() };
		} catch (final Exception e) {
			LOG.warn("", e);
			return new URL[0];
		}
	}
}
