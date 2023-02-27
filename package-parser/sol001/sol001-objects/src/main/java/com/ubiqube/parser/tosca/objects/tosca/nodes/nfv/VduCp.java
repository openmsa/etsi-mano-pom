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
package com.ubiqube.parser.tosca.objects.tosca.nodes.nfv;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.TrunkBindable;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VirtualNetworkInterfaceRequirements;

/**
 * describes network connectivity between a VNFC instance based on this VDU and
 * an internal VL
 */
public class VduCp extends Cp {
	/**
	 * Specifies requirements on a virtual network interface realising the CPs
	 * instantiated from this CPD
	 */
	@Valid
	@JsonProperty("virtual_network_interface_requirements")
	private List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements;

	/**
	 * Bitrate requirement in bit per second on this connection point
	 */
	@Valid
	@JsonProperty("bitrate_requirement")
	@DecimalMin(value = "0", inclusive = true)
	private Integer bitrateRequirement;

	/**
	 * Describes the type of the virtual network interface realizing the CPs
	 * instantiated from this CPD
	 */
	@Valid
	@JsonProperty("vnic_type")
	private String vnicType;

	/**
	 * The order of the NIC on the compute instance (e.g.eth2)
	 */
	@Valid
	@JsonProperty("order")
	@DecimalMin(value = "0", inclusive = true)
	private Integer order;

	/**
	 * Caps.
	 */
	private TrunkBindable trunkBinding;

	@Occurence({ "0", "1" })
	@Capability("tosca.capabilities.nfv.VirtualBindable")
	@Relationship("tosca.relationships.nfv.VirtualBindsTo")
	@JsonProperty("virtual_binding")
	private String virtualBindingReq;

	@Occurence({ "1", "1" })
	@Capability("tosca.capabilities.nfv.VirtualLinkable")
	@Relationship("tosca.relationships.nfv.VirtualLinksTo")
	@JsonProperty("virtual_link")
	private String virtualLinkReq;

	public List<VirtualNetworkInterfaceRequirements> getVirtualNetworkInterfaceRequirements() {
		return this.virtualNetworkInterfaceRequirements;
	}

	public void setVirtualNetworkInterfaceRequirements(
			final List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements) {
		this.virtualNetworkInterfaceRequirements = virtualNetworkInterfaceRequirements;
	}

	public Integer getBitrateRequirement() {
		return this.bitrateRequirement;
	}

	public void setBitrateRequirement(final Integer bitrateRequirement) {
		this.bitrateRequirement = bitrateRequirement;
	}

	public String getVnicType() {
		return this.vnicType;
	}

	public void setVnicType(final String vnicType) {
		this.vnicType = vnicType;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(final Integer order) {
		this.order = order;
	}

	public TrunkBindable getTrunkBinding() {
		return this.trunkBinding;
	}

	public void setTrunkBinding(final TrunkBindable trunkBinding) {
		this.trunkBinding = trunkBinding;
	}

	public String getVirtualBindingReq() {
		return this.virtualBindingReq;
	}

	public void setVirtualBindingReq(final String virtualBindingReq) {
		this.virtualBindingReq = virtualBindingReq;
	}

	public String getVirtualLinkReq() {
		return this.virtualLinkReq;
	}

	public void setVirtualLinkReq(final String virtualLinkReq) {
		this.virtualLinkReq = virtualLinkReq;
	}
}
