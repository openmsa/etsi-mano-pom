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
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Node;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;

/**
 * Describes a connection point to allocate one or a set of virtual IP addresses
 */
public class VipCp extends Cp {
	/**
	 * Indicates whether the VIP address shall be different from the addresses
	 * allocated to all associated VduCp instances or shall be the same as one of
	 * them.
	 */
	@Valid
	@NotNull
	@JsonProperty("dedicated_ip_address")
	private Boolean dedicatedIpAddress = true;

	/**
	 * Indicates the function the virtual IP address is used for: high availability
	 * or load balancing. When used for high availability, only one of the internal
	 * VDU CP instances or VNF external CP instances that share the virtual IP is
	 * bound to the VIP address at a time. When used for load balancing purposes all
	 * CP instances that share the virtual IP are bound to it.
	 */
	@Valid
	@NotNull
	@JsonProperty("vip_function")
	private String vipFunction;

	@Occurence({ "1", "1" })
	@Capability("tosca.capabilities.nfv.VirtualLinkable")
	@Relationship("tosca.relationships.nfv.VipVirtualLinksTo")
	@JsonProperty("virtual_link")
	private String virtualLinkReq;

	@Occurence({ "1", "UNBOUNDED" })
	@Node("tosca.nodes.nfv.VduCp")
	@Capability("tosca.capabilities.Node")
	@Relationship("tosca.relationships.DependsOn")
	@JsonProperty("target")
	private List<String> targetReq;

	@NotNull
	public Boolean getDedicatedIpAddress() {
		return this.dedicatedIpAddress;
	}

	public void setDedicatedIpAddress(@NotNull final Boolean dedicatedIpAddress) {
		this.dedicatedIpAddress = dedicatedIpAddress;
	}

	@NotNull
	public String getVipFunction() {
		return this.vipFunction;
	}

	public void setVipFunction(@NotNull final String vipFunction) {
		this.vipFunction = vipFunction;
	}

	public String getVirtualLinkReq() {
		return this.virtualLinkReq;
	}

	public void setVirtualLinkReq(final String virtualLinkReq) {
		this.virtualLinkReq = virtualLinkReq;
	}

	public List<String> getTargetReq() {
		return this.targetReq;
	}

	public void setTargetReq(final List<String> targetReq) {
		this.targetReq = targetReq;
	}
}
