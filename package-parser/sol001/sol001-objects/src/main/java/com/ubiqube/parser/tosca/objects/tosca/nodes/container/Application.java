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
package com.ubiqube.parser.tosca.objects.tosca.nodes.container;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Node;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

public class Application extends Root {
	@Node("tosca.nodes.Container.Runtime")
	@Capability("tosca.capabilities.Compute")
	@Relationship("tosca.relationships.HostedOn")
	@JsonProperty("host")
	private String hostReq;

	@Capability("tosca.capabilities.Storage")
	@JsonProperty("storage")
	private String storageReq;

	@Capability("tosca.capabilities.Endpoint")
	@JsonProperty("network")
	private String networkReq;

	public String getHostReq() {
		return this.hostReq;
	}

	public void setHostReq(final String hostReq) {
		this.hostReq = hostReq;
	}

	public String getStorageReq() {
		return this.storageReq;
	}

	public void setStorageReq(final String storageReq) {
		this.storageReq = storageReq;
	}

	public String getNetworkReq() {
		return this.networkReq;
	}

	public void setNetworkReq(final String networkReq) {
		this.networkReq = networkReq;
	}
}
