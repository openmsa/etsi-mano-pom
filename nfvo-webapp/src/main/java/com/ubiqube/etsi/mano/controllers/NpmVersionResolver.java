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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

@Controller
public class NpmVersionResolver {

	private static final Logger logger = LoggerFactory.getLogger(NpmVersionResolver.class);

	private static final Set<String> ALERTS = new HashSet<>();

	private static final String PROPERTIES_ROOT = "META-INF/maven/";
	private static final String RESOURCE_ROOT = "META-INF/resources/webjars/";
	private static final String NPM = "org.webjars.npm/";
	private static final String PLAIN = "org.webjars/";
	private static final String POM_PROPERTIES = "/pom.properties";
	private static final String PACKAGE_JSON = "/package.json";

	@Nonnull
	private final String contextPath;

	public NpmVersionResolver(@Value("${server.servlet.contextPath:}") final String contextPath) {
		this.contextPath = contextPath;
	}

	@GetMapping("/npm/{webjar}")
	public ResponseEntity<Void> module(@PathVariable final String webjar) {
		String path = findWebJarResourcePath(webjar, "/");
		if (path == null) {
			path = findUnpkgPath(webjar, "");
			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(path)).build();
		}
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(contextPath + "/webjars/" + path)).build();
	}

	@GetMapping("/npm/{webjar}/{*remainder}")
	public ResponseEntity<Void> remainder(@PathVariable final String webjarIn, @PathVariable final String remainderIn) {
		String webjar = webjarIn;
		String remainder = remainderIn;
		if (webjarIn.startsWith("@")) {
			final int index = remainderIn.indexOf("/", 1);
			final String path = index < 0 ? remainderIn.substring(1) : remainderIn.substring(1, index);
			webjar = webjarIn.substring(1) + "__" + path;
			if ((index < 0) || (index == (remainderIn.length() - 1))) {
				return module(webjar);
			}
			remainder = remainderIn.substring(index);
		}
		String path = findWebJarResourcePath(webjar, remainder);
		if (path == null) {
			if (version(webjar) != null) {
				return ResponseEntity.notFound().build();
			}
			path = findUnpkgPath(webjar, remainder);
			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(path)).build();
		}
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(contextPath + "/webjars/" + path)).build();
	}

	private static String findUnpkgPath(final String webjarIn, final String remainderIn) {
		String remainder = remainderIn;
		String webjar = webjarIn;
		if (!StringUtils.hasText(remainder)) {
			remainder = "";
		} else if (!remainder.startsWith("/")) {
			remainder = "/" + remainder;
		}
		if (webjar.contains("__")) {
			webjar = "@" + webjar.replace("__", "/");
		}
		final String local = findLocalPath(webjar + remainder);
		if (local != null) {
			//
		}
		if (logger.isInfoEnabled() && !ALERTS.contains(webjar)) {
			ALERTS.add(webjar);
			logger.info("Resolving webjar to unpkg.com: {}", webjar);
		}
		return "https://unpkg.com/" + webjar + remainder;
	}

	private static @Nullable String findLocalPath(final String path) {
		final File module = new File("node_modules", path);
		if (module.exists() && module.isDirectory()) {
			return "/" + module.getPath();
		}
		return null;
	}

	@Nullable
	protected static String findWebJarResourcePath(final String webjar, final String path) {
		if (!webjar.isEmpty()) {
			final String version = version(webjar);
			if (version != null) {
				final String partialPath = path(webjar, version, path);
				if (partialPath != null) {
					return webjar + "/" + version + partialPath;
				}
			}
		}
		return null;
	}

	private static @Nullable String path(final String webjar, final String version, final String path) {
		if (path.equals("/")) {
			return module(webjar, version, path);
		}
		if (path.equals("/main.js")) {
			final String module = module(webjar, version, path);
			if (module != null) {
				return module;
			}
		}
		if (new ClassPathResource(RESOURCE_ROOT + webjar + "/" + version + path).isReadable()) {
			return path;
		}
		return null;
	}

	private static @Nullable String module(final String webjar, final String version, final String path) {
		final Resource resource = new ClassPathResource(RESOURCE_ROOT + webjar + "/" + version + PACKAGE_JSON);
		if (resource.isReadable()) {
			try {
				final JsonParser parser = JsonParserFactory.getJsonParser();
				final Map<String, Object> map = parser
						.parseMap(StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8));
				if (!path.equals("/main.js") && map.containsKey("module")) {
					return "/" + (String) map.get("module");
				}
				if (!map.containsKey("main") && map.containsKey("jspm")) {
					final String stem = resolve(map, "jspm.directories.lib", "dist");
					final String main = resolve(map, "jspm.main", "index.js");
					return "/" + stem + "/" + main + (main.endsWith(".js") ? "" : ".js");
				}
				return "/" + (String) map.get("main");
			} catch (final IOException e) {
				logger.debug("", e);
			}
		}
		return null;
	}

	private static String resolve(final Map<String, Object> map, final String path, final String defaultValue) {
		Map<String, Object> sub = map;
		final String[] elements = StringUtils.delimitedListToStringArray(path, ".");
		for (int i = 0; i < (elements.length - 1); i++) {
			@SuppressWarnings("unchecked")
			final Map<String, Object> tmp = (Map<String, Object>) sub.get(elements[i]);
			sub = tmp;
			if (sub == null) {
				return defaultValue;
			}
		}
		return (String) sub.getOrDefault(elements[elements.length - 1], defaultValue);
	}

	private static @Nullable String version(final String webjar) {
		Resource resource = new ClassPathResource(PROPERTIES_ROOT + NPM + webjar + POM_PROPERTIES);
		if (!resource.isReadable()) {
			resource = new ClassPathResource(PROPERTIES_ROOT + PLAIN + webjar + POM_PROPERTIES);
		}
		if (resource.isReadable()) {
			Properties properties;
			try {
				properties = PropertiesLoaderUtils.loadProperties(resource);
				return properties.getProperty("version");
			} catch (final IOException e) {
				logger.debug("", e);
			}
		}
		return null;
	}
}
