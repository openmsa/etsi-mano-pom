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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * information that corresponds to the authority component of a URI as specified
 * in IETF RFC 3986 [8]
 */
public class UriAuthority extends Root {
	/**
	 * user_info field of the authority component of a URI
	 */
	@Valid
	@JsonProperty("user_info")
	private String userInfo;

	/**
	 * port field of the authority component of a URI
	 */
	@Valid
	@JsonProperty("port")
	private String port;

	/**
	 * host field of the authority component of a URI
	 */
	@Valid
	@JsonProperty("host")
	private String host;

	public String getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(final String userInfo) {
		this.userInfo = userInfo;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(final String port) {
		this.port = port;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(final String host) {
		this.host = host;
	}
}
