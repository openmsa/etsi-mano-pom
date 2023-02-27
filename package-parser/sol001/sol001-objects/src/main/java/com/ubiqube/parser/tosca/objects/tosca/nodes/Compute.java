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
package com.ubiqube.parser.tosca.objects.tosca.nodes;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Node;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.OperatingSystem;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.Scalable;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.endpoint.Admin;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.network.Bindable;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.network.NetworkInfo;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.network.PortInfo;

public class Compute extends com.ubiqube.parser.tosca.objects.tosca.nodes.Abstract.Compute {
	@Valid
	@NotNull
	@JsonProperty("private_address")
	private String privateAddress;

	@Valid
	@NotNull
	@JsonProperty("public_address")
	private String publicAddress;

	@Valid
	@NotNull
	@JsonProperty("networks")
	private Map<String, NetworkInfo> networks;

	@Valid
	@NotNull
	@JsonProperty("ports")
	private Map<String, PortInfo> ports;

	/**
	 * Caps.
	 */
	private com.ubiqube.parser.tosca.objects.tosca.capabilities.Compute host;

	/**
	 * Caps.
	 */
	private Admin endpoint;

	/**
	 * Caps.
	 */
	private OperatingSystem os;

	/**
	 * Caps.
	 */
	private Scalable scalable;

	/**
	 * Caps.
	 */
	private Bindable binding;

	@Occurence({ "0", "UNBOUNDED" })
	@Node("tosca.nodes.Storage.BlockStorage")
	@Capability("tosca.capabilities.Attachment")
	@Relationship("tosca.relationships.AttachesTo")
	@JsonProperty("local_storage")
	private List<String> localStorageReq;

	@NotNull
	public String getPrivateAddress() {
		return this.privateAddress;
	}

	public void setPrivateAddress(@NotNull final String privateAddress) {
		this.privateAddress = privateAddress;
	}

	@NotNull
	public String getPublicAddress() {
		return this.publicAddress;
	}

	public void setPublicAddress(@NotNull final String publicAddress) {
		this.publicAddress = publicAddress;
	}

	@NotNull
	public Map<String, NetworkInfo> getNetworks() {
		return this.networks;
	}

	public void setNetworks(@NotNull final Map<String, NetworkInfo> networks) {
		this.networks = networks;
	}

	@NotNull
	public Map<String, PortInfo> getPorts() {
		return this.ports;
	}

	public void setPorts(@NotNull final Map<String, PortInfo> ports) {
		this.ports = ports;
	}

	@Override
	public com.ubiqube.parser.tosca.objects.tosca.capabilities.Compute getHost() {
		return this.host;
	}

	@Override
	public void setHost(final com.ubiqube.parser.tosca.objects.tosca.capabilities.Compute host) {
		this.host = host;
	}

	public Admin getEndpoint() {
		return this.endpoint;
	}

	public void setEndpoint(final Admin endpoint) {
		this.endpoint = endpoint;
	}

	public OperatingSystem getOs() {
		return this.os;
	}

	public void setOs(final OperatingSystem os) {
		this.os = os;
	}

	public Scalable getScalable() {
		return this.scalable;
	}

	public void setScalable(final Scalable scalable) {
		this.scalable = scalable;
	}

	public Bindable getBinding() {
		return this.binding;
	}

	public void setBinding(final Bindable binding) {
		this.binding = binding;
	}

	public List<String> getLocalStorageReq() {
		return this.localStorageReq;
	}

	public void setLocalStorageReq(final List<String> localStorageReq) {
		this.localStorageReq = localStorageReq;
	}
}
