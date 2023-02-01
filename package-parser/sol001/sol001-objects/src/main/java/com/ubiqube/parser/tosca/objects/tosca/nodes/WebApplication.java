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
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Node;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.Endpoint;

public class WebApplication extends Root {
	@Valid
	@NotNull
	@JsonProperty("context_root")
	private String contextRoot;

	/**
	 * Caps.
	 */
	private Endpoint appEndpoint;

	@Node("tosca.nodes.WebServer")
	@Capability("tosca.capabilities.Compute")
	@Relationship("tosca.relationships.HostedOn")
	@JsonProperty("host")
	private String hostReq;

	@NotNull
	public String getContextRoot() {
		return this.contextRoot;
	}

	public void setContextRoot(@NotNull final String contextRoot) {
		this.contextRoot = contextRoot;
	}

	public Endpoint getAppEndpoint() {
		return this.appEndpoint;
	}

	public void setAppEndpoint(final Endpoint appEndpoint) {
		this.appEndpoint = appEndpoint;
	}

	public String getHostReq() {
		return this.hostReq;
	}

	public void setHostReq(final String hostReq) {
		this.hostReq = hostReq;
	}
}
