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

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes L2 protocol data for a given virtual link used in a specific NS
 * deployment flavour.
 */
public class NsL2ProtocolData extends Root {
	/**
	 * Specifies a specific virtualised network segment, which depends on the
	 * network type. For e.g., VLAN ID for VLAN network type and tunnel ID for
	 * GRE/VXLAN network types
	 */
	@Valid
	@JsonProperty("segmentation_id")
	private String segmentationId;

	/**
	 * Identifies the network name associated with this L2 protocol.
	 */
	@Valid
	@JsonProperty("name")
	private String name;

	/**
	 * Specifies whether to support VLAN transparency for this L2 protocol or not.
	 */
	@Valid
	@JsonProperty("vlan_transparent")
	private Boolean vlanTransparent = false;

	/**
	 * Specifies the network type for this L2 protocol. The value may be overridden
	 * at run-time.
	 */
	@Valid
	@JsonProperty("network_type")
	private String networkType;

	/**
	 * Specifies the maximum transmission unit (MTU) value for this L2 protocol.
	 */
	@Valid
	@JsonProperty("mtu")
	@DecimalMin("0")
	private Integer mtu;

	public String getSegmentationId() {
		return this.segmentationId;
	}

	public void setSegmentationId(final String segmentationId) {
		this.segmentationId = segmentationId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Boolean getVlanTransparent() {
		return this.vlanTransparent;
	}

	public void setVlanTransparent(final Boolean vlanTransparent) {
		this.vlanTransparent = vlanTransparent;
	}

	public String getNetworkType() {
		return this.networkType;
	}

	public void setNetworkType(final String networkType) {
		this.networkType = networkType;
	}

	public Integer getMtu() {
		return this.mtu;
	}

	public void setMtu(final Integer mtu) {
		this.mtu = mtu;
	}
}
