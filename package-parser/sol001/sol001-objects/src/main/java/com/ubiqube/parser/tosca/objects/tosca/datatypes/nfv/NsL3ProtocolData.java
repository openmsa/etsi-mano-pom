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

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes L3 protocol data for a given virtual link used in a specific NS
 * deployment flavour.
 */
public class NsL3ProtocolData extends Root {
	/**
	 * Specifies the allocation pools with start and end IP addresses for this L3
	 * protocol. The value may be overridden at run-time.
	 */
	@Valid
	@JsonProperty("ip_allocation_pools")
	private List<NsIpAllocationPool> ipAllocationPools;

	/**
	 * Specifies IP version of this L3 protocol. The value of the ip_version
	 * property shall be consistent with the value of the layer_protocol in the
	 * connectivity_type property of the virtual link node.
	 */
	@Valid
	@NotNull
	@JsonProperty("ip_version")
	private String ipVersion;

	/**
	 * Identifies the network name associated with this L3 protocol.
	 */
	@Valid
	@JsonProperty("name")
	private String name;

	/**
	 * Specifies the CIDR (Classless Inter-Domain Routing) of this L3 protocol. The
	 * value may be overridden at run-time.
	 */
	@Valid
	@NotNull
	@JsonProperty("cidr")
	private String cidr;

	public List<NsIpAllocationPool> getIpAllocationPools() {
		return this.ipAllocationPools;
	}

	public void setIpAllocationPools(final List<NsIpAllocationPool> ipAllocationPools) {
		this.ipAllocationPools = ipAllocationPools;
	}

	@NotNull
	public String getIpVersion() {
		return this.ipVersion;
	}

	public void setIpVersion(@NotNull final String ipVersion) {
		this.ipVersion = ipVersion;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public String getCidr() {
		return this.cidr;
	}

	public void setCidr(@NotNull final String cidr) {
		this.cidr = cidr;
	}
}
