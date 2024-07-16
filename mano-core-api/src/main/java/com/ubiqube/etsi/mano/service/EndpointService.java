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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ubiqube.etsi.mano.utils.Version;

import jakarta.annotation.Nullable;

@Lazy
@Service
public class EndpointService {
	private static final Logger LOG = LoggerFactory.getLogger(EndpointService.class);
	private static Pattern p = Pattern.compile("/.*/(?<part>\\w+)/v\\d");
	private final ApplicationContext applicationContext;
	public static final Set<String> FRAGMENTS = Set.of("/vrqan/", "/vnfpkgm/", "/grant/", "/vnfpm/", "/vnflcm/", "/vnfind/", "/vnffm/", "/vrgan/", "/nsd/", "/nsfm/", "/nslcm/", "/nspm/", "/vnfconfig/", "/vnfsnapshotpkgm/", "/nsiun/");
	private static final MultiValueMap<String, Endpoint> dedupe = new LinkedMultiValueMap<>();

	public EndpointService(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		// extractVersions
	}

	public void extractVersions() {
		final String[] beans = applicationContext.getBeanNamesForAnnotation(Controller.class);
		final List<String> list = Arrays.asList(beans);
		final Map<String, Endpoint> res = new HashMap<>();
		list.stream().forEach(x -> {
			LOG.trace("Reading: {}", x);
			if (isSkippable(x)) {
				return;
			}
			final Object obj = applicationContext.getBean(x);
			final RequestMapping req = AnnotationUtils.findAnnotation(obj.getClass(), RequestMapping.class);
			if (haveUsableRequest(req) && (req != null)) {
				final String part = extractPart(req.value()[0]);
				final List<Version> version = getVersion(req.headers());
				if (null == part) {
					LOG.warn("Ignoring controller: {}", x);
				} else {
					final List<HttpMethod> lst = extractMethod(obj.getClass());
					version.forEach(y -> res.put(part + y, new Endpoint(part, y, x, lst)));
				}
			}
		});
		res.forEach((key, value) -> dedupe.add(value.part, value));
	}

	private List<HttpMethod> extractMethod(final Class<? extends Object> clazz) {
		final List<HttpMethod> ret = new ArrayList<>();
		final Method[] meths = clazz.getMethods();
		for (final Method method : meths) {
			final Set<RequestMapping> res = AnnotatedElementUtils.findAllMergedAnnotations(method, RequestMapping.class);
			if (!res.isEmpty()) {
				final RequestMapping req = res.iterator().next();
				ret.add(new HttpMethod(req.method()[0].name(), safeGetAArray(req.value())));
			}
		}
		return ret;
	}

	@Nullable
	private static String safeGetAArray(final @Nullable String[] val) {
		if ((null == val) || (val.length == 0)) {
			return null;
		}
		return val[0];
	}

	@SuppressWarnings("static-method")
	public MultiValueMap<String, Endpoint> getEndpoints() {
		if (dedupe.isEmpty()) {
			extractVersions();
		}
		return dedupe;
	}

	private static @Nullable String extractPart(final String string) {
		final Matcher m = p.matcher(string);
		if (!m.matches()) {
			return null;
		}
		return m.group("part");
	}

	private static boolean haveUsableRequest(final @Nullable RequestMapping req) {
		return (null != req) && (req.headers() != null) && (req.value().length > 0);
	}

	private static boolean isSkippable(final String versionName) {
		return "nfvoApiVersion".equals(versionName) || "vnfmApiVersion".equals(versionName) || "swaggerWelcome".equals(versionName);
	}

	private static List<Version> getVersion(final String[] headers) {
		final List<Version> res = new ArrayList<>();
		if (headers.length == 0) {
			return res;
		}
		for (final String string : headers) {
			if (string.startsWith("Version=")) {
				final String ver = string.substring("version=".length());
				res.add(new Version(ver));
			}
		}
		return res;
	}

	record Endpoint(String part, Version versoin, Object bean, List<HttpMethod> lst) {
		//
	}

	record HttpMethod(String action, @Nullable String payh) {
		//
	}
}
