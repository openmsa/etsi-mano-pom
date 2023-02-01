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

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes L3 protocol data for a given virtual link used in a specific VNF
 * deployment flavour.
 */
public class L3ProtocolData extends Root {
	/**
	 * Indicates whether DHCP (Dynamic Host Configuration Protocol) is enabled or
	 * disabled for this L3 protocol. The value may be overridden at run-time.
	 */
	@Valid
	@JsonProperty("dhcp_enabled")
	private Boolean dhcpEnabled;

	/**
	 * Specifies the allocation pools with start and end IP addresses for this L3
	 * protocol. The value may be overridden at run-time.
	 */
	@Valid
	@JsonProperty("ip_allocation_pools")
	private List<IpAllocationPool> ipAllocationPools;

	/**
	 * Specifies IPv6 address mode. May be present when the value of the ipVersion
	 * attribute is "ipv6" and shall be absent otherwise. The value may be
	 * overridden at run-time.
	 */
	@Valid
	@JsonProperty("ipv6_address_mode")
	private String ipv6AddressMode;

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

	/**
	 * Specifies the gateway IP address for this L3 protocol. The value may be
	 * overridden at run-time.
	 */
	@Valid
	@JsonProperty("gateway_ip")
	private String gatewayIp;

	public Boolean getDhcpEnabled() {
		return this.dhcpEnabled;
	}

	public void setDhcpEnabled(final Boolean dhcpEnabled) {
		this.dhcpEnabled = dhcpEnabled;
	}

	public List<IpAllocationPool> getIpAllocationPools() {
		return this.ipAllocationPools;
	}

	public void setIpAllocationPools(final List<IpAllocationPool> ipAllocationPools) {
		this.ipAllocationPools = ipAllocationPools;
	}

	public String getIpv6AddressMode() {
		return this.ipv6AddressMode;
	}

	public void setIpv6AddressMode(final String ipv6AddressMode) {
		this.ipv6AddressMode = ipv6AddressMode;
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

	public String getGatewayIp() {
		return this.gatewayIp;
	}

	public void setGatewayIp(final String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}
}
