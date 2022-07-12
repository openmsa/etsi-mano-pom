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

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Node;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Credential;
import com.ubiqube.parser.tosca.scalar.Version;

public class SoftwareComponent extends Root {
	@Valid
	@JsonProperty("admin_credential")
	private Credential adminCredential;

	@Valid
	@JsonProperty("component_version")
	private Version componentVersion;

	@Node("tosca.nodes.Compute")
	@Capability("tosca.capabilities.Compute")
	@Relationship("tosca.relationships.HostedOn")
	@JsonProperty("host")
	private String hostReq;

	public Credential getAdminCredential() {
		return this.adminCredential;
	}

	public void setAdminCredential(final Credential adminCredential) {
		this.adminCredential = adminCredential;
	}

	public Version getComponentVersion() {
		return this.componentVersion;
	}

	public void setComponentVersion(final Version componentVersion) {
		this.componentVersion = componentVersion;
	}

	public String getHostReq() {
		return this.hostReq;
	}

	public void setHostReq(final String hostReq) {
		this.hostReq = hostReq;
	}
}
