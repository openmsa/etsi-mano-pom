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
package com.ubiqube.etsi.mano.uri;

import java.net.URI;

import org.springframework.hateoas.server.core.UriMapping;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author olivier
 *
 */
public class ManoUriComponentsBuilder {

	static final String REQUEST_ATTRIBUTES_MISSING = "Could not find current request via RequestContextHolder. Is this being called from a Spring MVC handler?";
	private static final String CACHE_KEY = ManoUriComponentsBuilder.class.getName() + "#BUILDER_CACHE";

	private ManoUriComponentsBuilder() {
		// Nothing.
	}

	/**
	 * Returns a {@link UriComponentsBuilder} obtained from the current servlet
	 * mapping with scheme tweaked in case the request contains an
	 * {@code X-Forwarded-Ssl} header. If no {@link RequestContextHolder} exists
	 * (you're outside a Spring Web call), fall back to relative URIs.
	 *
	 * @return The uri component.
	 */
	public static UriComponentsBuilder getBuilder() {

		if (RequestContextHolder.getRequestAttributes() == null) {
			return UriComponentsBuilder.fromPath("/");
		}

		final URI baseUri = getCachedBaseUri();

		return baseUri != null //
				? UriComponentsBuilder.fromUri(baseUri) //
				: cacheBaseUri(ServletUriComponentsBuilder.fromCurrentServletMapping());
	}

	public static UriComponents getComponents() {
		return getBuilder().build();
	}

	private static UriComponentsBuilder cacheBaseUri(final UriComponentsBuilder builder) {

		final URI uri = builder.build().toUri();

		getRequestAttributes().setAttribute(CACHE_KEY, uri, RequestAttributes.SCOPE_REQUEST);

		return builder;
	}

	private static RequestAttributes getRequestAttributes() {

		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

		if (requestAttributes == null) {
			throw new IllegalStateException("Could not look up RequestAttributes!");
		}

		Assert.state(requestAttributes != null, REQUEST_ATTRIBUTES_MISSING);
		Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);

		return requestAttributes;
	}

	@Nullable
	private static URI getCachedBaseUri() {
		return (URI) getRequestAttributes().getAttribute(CACHE_KEY, RequestAttributes.SCOPE_REQUEST);
	}

	public static UriComponentsBuilder forMapping(final UriMapping mapping) {
		return getBuilder().path(mapping.getMapping());
	}
}
