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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Provides information about Layer 3 level addressing scheme and parameters
 * applicable to a CP
 */
public class L3AddressData extends Root {
	/**
	 * Method of IP address assignment in case the IP configuration is not provided
	 * using the NFV MANO interfaces towards the VNFM. Description of the valid
	 * values (1) dynamic the VNF gets an IP address dynamically from the NFVI
	 * (i.e., using DHCP), (2) vnf_pkg, an IP address defined by the VNF provider is
	 * assigned by means included as part of the VNF package (e.g., LCM script); (3)
	 * external, an IP address is provided by an external management entity (such as
	 * EM) directly towards the VNF.Shall be present in case the
	 * ip_address_assignment property is set to False and shall be absent otherwise.
	 */
	@Valid
	@JsonProperty("ip_address_assignment_subtype")
	private Boolean ipAddressAssignmentSubtype;

	/**
	 * Minimum number of IP addresses to be assigned
	 */
	@Valid
	@JsonProperty("number_of_ip_address")
	@DecimalMin("0")
	private Integer numberOfIpAddress;

	/**
	 * Fixed IP addresses to be assigned to the internal CP instance. This property
	 * enables the VNF provider to define fixed IP addresses for internal CP
	 * instances to be assigned by the VNFM or the NFVO. This property is only
	 * permitted for Cpds without external connectivity, i.e. connectivity outside
	 * the VNF. If present, it shall be compatible with the values of the
	 * L3ProtocolData of the VnfVirtualLink referred to by the Cp, if L3ProtocolData
	 * is included in the VnfVirtualLink
	 */
	@Valid
	@JsonProperty("fixed_ip_address")
	private List<String> fixedIpAddress;

	/**
	 * Specify which mode is used for the IP address assignment. If it is set to
	 * True, IP configuration information shall be provided for the VNF by a
	 * management entity using the NFV MANO interfaces towards the VNFM. If it is
	 * set to False, the value of the ip_address_assignment_subtype property defines
	 * the method of IP address assignment. Shall be present if the fixed_ip_address
	 * property is not present and should be absent otherwise. See note
	 */
	@Valid
	@NotNull
	@JsonProperty("ip_address_assignment")
	private Boolean ipAddressAssignment;

	/**
	 * Defines address type. The address type should be aligned with the address
	 * type supported by the layer_protocols properties of the connetion point
	 */
	@Valid
	@JsonProperty("ip_address_type")
	private String ipAddressType;

	/**
	 * Specifies if the floating IP scheme is activated on the Connection Point or
	 * not
	 */
	@Valid
	@NotNull
	@JsonProperty("floating_ip_activated")
	private Boolean floatingIpActivated;

	public Boolean getIpAddressAssignmentSubtype() {
		return this.ipAddressAssignmentSubtype;
	}

	public void setIpAddressAssignmentSubtype(final Boolean ipAddressAssignmentSubtype) {
		this.ipAddressAssignmentSubtype = ipAddressAssignmentSubtype;
	}

	public Integer getNumberOfIpAddress() {
		return this.numberOfIpAddress;
	}

	public void setNumberOfIpAddress(final Integer numberOfIpAddress) {
		this.numberOfIpAddress = numberOfIpAddress;
	}

	public List<String> getFixedIpAddress() {
		return this.fixedIpAddress;
	}

	public void setFixedIpAddress(final List<String> fixedIpAddress) {
		this.fixedIpAddress = fixedIpAddress;
	}

	@NotNull
	public Boolean getIpAddressAssignment() {
		return this.ipAddressAssignment;
	}

	public void setIpAddressAssignment(@NotNull final Boolean ipAddressAssignment) {
		this.ipAddressAssignment = ipAddressAssignment;
	}

	public String getIpAddressType() {
		return this.ipAddressType;
	}

	public void setIpAddressType(final String ipAddressType) {
		this.ipAddressType = ipAddressType;
	}

	@NotNull
	public Boolean getFloatingIpActivated() {
		return this.floatingIpActivated;
	}

	public void setFloatingIpActivated(@NotNull final Boolean floatingIpActivated) {
		this.floatingIpActivated = floatingIpActivated;
	}
}
