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
package com.ubiqube.parser.tosca.objects.tosca.nodes;

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.Compute;

public class DBMS extends SoftwareComponent {
	/**
	 * The port the DBMS service will listen to for data and requests.
	 */
	@Valid
	@JsonProperty("port")
	private Integer port;

	/**
	 * the optional root password for the DBMS service
	 */
	@Valid
	@JsonProperty("root_password")
	private String rootPassword;

	/**
	 * Caps.
	 */
	private Compute host;

	public Integer getPort() {
		return this.port;
	}

	public void setPort(final Integer port) {
		this.port = port;
	}

	public String getRootPassword() {
		return this.rootPassword;
	}

	public void setRootPassword(final String rootPassword) {
		this.rootPassword = rootPassword;
	}

	public Compute getHost() {
		return this.host;
	}

	public void setHost(final Compute host) {
		this.host = host;
	}
}
