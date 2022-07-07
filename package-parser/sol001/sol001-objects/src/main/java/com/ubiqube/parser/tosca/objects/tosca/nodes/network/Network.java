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
package com.ubiqube.parser.tosca.objects.tosca.nodes.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import javax.validation.Valid;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.network.Linkable;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

public class Network extends Root {
	@Valid
	@JsonProperty("physical_network")
	private String physicalNetwork;

	@Valid
	@JsonProperty("dhcp_enabled")
	private Boolean dhcpEnabled = true;

	@Valid
	@JsonProperty("segmentation_id")
	private String segmentationId;

	@Valid
	@JsonProperty("network_id")
	private String networkId;

	@Valid
	@JsonProperty("ip_version")
	private Integer ipVersion = 4;

	@Valid
	@JsonProperty("start_ip")
	private String startIp;

	@Valid
	@JsonProperty("network_name")
	private String networkName;

	@Valid
	@JsonProperty("cidr")
	private String cidr;

	@Valid
	@JsonProperty("gateway_ip")
	private String gatewayIp;

	@Valid
	@JsonProperty("network_type")
	private String networkType;

	@Valid
	@JsonProperty("end_ip")
	private String endIp;

	/**
	 * Caps.
	 */
	private Linkable link;

	public String getPhysicalNetwork() {
		return this.physicalNetwork;
	}

	public void setPhysicalNetwork(final String physicalNetwork) {
		this.physicalNetwork = physicalNetwork;
	}

	public Boolean getDhcpEnabled() {
		return this.dhcpEnabled;
	}

	public void setDhcpEnabled(final Boolean dhcpEnabled) {
		this.dhcpEnabled = dhcpEnabled;
	}

	public String getSegmentationId() {
		return this.segmentationId;
	}

	public void setSegmentationId(final String segmentationId) {
		this.segmentationId = segmentationId;
	}

	public String getNetworkId() {
		return this.networkId;
	}

	public void setNetworkId(final String networkId) {
		this.networkId = networkId;
	}

	public Integer getIpVersion() {
		return this.ipVersion;
	}

	public void setIpVersion(final Integer ipVersion) {
		this.ipVersion = ipVersion;
	}

	public String getStartIp() {
		return this.startIp;
	}

	public void setStartIp(final String startIp) {
		this.startIp = startIp;
	}

	public String getNetworkName() {
		return this.networkName;
	}

	public void setNetworkName(final String networkName) {
		this.networkName = networkName;
	}

	public String getCidr() {
		return this.cidr;
	}

	public void setCidr(final String cidr) {
		this.cidr = cidr;
	}

	public String getGatewayIp() {
		return this.gatewayIp;
	}

	public void setGatewayIp(final String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}

	public String getNetworkType() {
		return this.networkType;
	}

	public void setNetworkType(final String networkType) {
		this.networkType = networkType;
	}

	public String getEndIp() {
		return this.endIp;
	}

	public void setEndIp(final String endIp) {
		this.endIp = endIp;
	}

	public Linkable getLink() {
		return this.link;
	}

	public void setLink(final Linkable link) {
		this.link = link;
	}
}
