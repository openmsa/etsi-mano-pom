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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * information used to build a URI that complies with IETF RFC 3986 [8].
 */
public class UriComponents extends Root {
	/**
	 * path component of a URI.
	 */
	@Valid
	@JsonProperty("path")
	private String path;

	/**
	 * fragment component of a URI.
	 */
	@Valid
	@JsonProperty("fragment")
	private String fragment;

	/**
	 * scheme component of a URI.
	 */
	@Valid
	@NotNull
	@JsonProperty("scheme")
	private String scheme;

	/**
	 * Authority component of a URI
	 */
	@Valid
	@JsonProperty("authority")
	private UriAuthority authority;

	/**
	 * query component of a URI.
	 */
	@Valid
	@JsonProperty("query")
	private String query;

	public String getPath() {
		return this.path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public String getFragment() {
		return this.fragment;
	}

	public void setFragment(final String fragment) {
		this.fragment = fragment;
	}

	@NotNull
	public String getScheme() {
		return this.scheme;
	}

	public void setScheme(@NotNull final String scheme) {
		this.scheme = scheme;
	}

	public UriAuthority getAuthority() {
		return this.authority;
	}

	public void setAuthority(final UriAuthority authority) {
		this.authority = authority;
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(final String query) {
		this.query = query;
	}
}
