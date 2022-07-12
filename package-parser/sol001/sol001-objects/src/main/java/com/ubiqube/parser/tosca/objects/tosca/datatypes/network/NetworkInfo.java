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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.network;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

public class NetworkInfo extends Root {
	@Valid
	@NotNull
	@JsonProperty("network_id")
	private String networkId;

	@Valid
	@NotNull
	@JsonProperty("addresses")
	private List<String> addresses;

	@Valid
	@NotNull
	@JsonProperty("network_name")
	private String networkName;

	@NotNull
	public String getNetworkId() {
		return this.networkId;
	}

	public void setNetworkId(@NotNull final String networkId) {
		this.networkId = networkId;
	}

	@NotNull
	public List<String> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(@NotNull final List<String> addresses) {
		this.addresses = addresses;
	}

	@NotNull
	public String getNetworkName() {
		return this.networkName;
	}

	public void setNetworkName(@NotNull final String networkName) {
		this.networkName = networkName;
	}
}
