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
package com.ubiqube.parser.tosca.objects.tosca.capabilities.endpoint;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.Endpoint;

public class Public extends Endpoint {
	/**
	 * indicates that the public address should be allocated from a pool of floating
	 * IPs that are associated with the network.
	 */
	@Valid
	@NotNull
	@JsonProperty("floating")
	private Boolean floating = false;

	@Valid
	@NotNull
	@JsonProperty("network_name")
	private String networkName = "PUBLIC";

	/**
	 * The optional name to register with DNS
	 */
	@Valid
	@JsonProperty("dns_name")
	private String dnsName;

	@NotNull
	public Boolean getFloating() {
		return this.floating;
	}

	public void setFloating(@NotNull final Boolean floating) {
		this.floating = floating;
	}

	@Override
	@NotNull
	public String getNetworkName() {
		return this.networkName;
	}

	@Override
	public void setNetworkName(@NotNull final String networkName) {
		this.networkName = networkName;
	}

	public String getDnsName() {
		return this.dnsName;
	}

	public void setDnsName(final String dnsName) {
		this.dnsName = dnsName;
	}
}
