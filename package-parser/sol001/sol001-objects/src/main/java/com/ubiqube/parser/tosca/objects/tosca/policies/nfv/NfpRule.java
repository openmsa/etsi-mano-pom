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
package com.ubiqube.parser.tosca.objects.tosca.policies.nfv;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.Mask;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;
import com.ubiqube.parser.tosca.scalar.Range;

/**
 * policy definition of NfpRuletosca.nodes.nfv.NFP
 */
public class NfpRule extends Root {
	/**
	 * Indicates a VLAN identifier in an IEEE 802.1Q-2014 tag [14]. Multiple tags
	 * can be included for QinQ stacking.
	 */
	@Valid
	@JsonProperty("vlan_tag")
	private List<String> vlanTag;

	/**
	 * Indicates the L4 protocol, For IPv4 [15] this corresponds to the field called
	 * "Protocol" to identify the next level protocol. For IPv6 [16] this
	 * corresponds to the field is called the "Next Header" field. Permitted values:
	 * Any keyword defined in the IANA [17] protocol registry.
	 */
	@Valid
	@JsonProperty("protocol")
	private String protocol;

	/**
	 * For IPv4 [15] a string of "0" and "1" digits that corresponds to the 6-bit
	 * Differentiated Services Code Point (DSCP) field of the IP header. For IPv6
	 * [16] a string of "0" and "1" digits that corresponds to the 6 differentiated
	 * services bits of the traffic class header field.
	 */
	@Valid
	@JsonProperty("dscp")
	private String dscp;

	/**
	 * Indicates a range of source ports.
	 */
	@Valid
	@JsonProperty("source_port_range")
	@Min(0)
	@Max(65535)
	private Range sourcePortRange;

	/**
	 * Indicates a range of destination ports.
	 */
	@Valid
	@JsonProperty("destination_port_range")
	@Min(0)
	@Max(65535)
	private Range destinationPortRange;

	/**
	 * Indicates the source IP address range in CIDR format.
	 */
	@Valid
	@JsonProperty("source_ip_address_prefix")
	private String sourceIpAddressPrefix;

	/**
	 * Indicates a destination Mac address.
	 */
	@Valid
	@JsonProperty("ether_destination_address")
	private String etherDestinationAddress;

	/**
	 * Indicates the protocol carried over the Ethernet layer.
	 */
	@Valid
	@JsonProperty("ether_type")
	private String etherType;

	/**
	 * Indicates the destination IP address range in CIDR format.
	 */
	@Valid
	@JsonProperty("destination_ip_address_prefix")
	private String destinationIpAddressPrefix;

	/**
	 * Indicates a source Mac address.
	 */
	@Valid
	@JsonProperty("ether_source_address")
	private String etherSourceAddress;

	/**
	 * Indicates values of specific bits in a frame.
	 */
	@Valid
	@JsonProperty("extended_criteria")
	private List<Mask> extendedCriteria;

	@Valid
	private List<String> targets;

	public List<String> getVlanTag() {
		return this.vlanTag;
	}

	public void setVlanTag(final List<String> vlanTag) {
		this.vlanTag = vlanTag;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}

	public String getDscp() {
		return this.dscp;
	}

	public void setDscp(final String dscp) {
		this.dscp = dscp;
	}

	public Range getSourcePortRange() {
		return this.sourcePortRange;
	}

	public void setSourcePortRange(final Range sourcePortRange) {
		this.sourcePortRange = sourcePortRange;
	}

	public Range getDestinationPortRange() {
		return this.destinationPortRange;
	}

	public void setDestinationPortRange(final Range destinationPortRange) {
		this.destinationPortRange = destinationPortRange;
	}

	public String getSourceIpAddressPrefix() {
		return this.sourceIpAddressPrefix;
	}

	public void setSourceIpAddressPrefix(final String sourceIpAddressPrefix) {
		this.sourceIpAddressPrefix = sourceIpAddressPrefix;
	}

	public String getEtherDestinationAddress() {
		return this.etherDestinationAddress;
	}

	public void setEtherDestinationAddress(final String etherDestinationAddress) {
		this.etherDestinationAddress = etherDestinationAddress;
	}

	public String getEtherType() {
		return this.etherType;
	}

	public void setEtherType(final String etherType) {
		this.etherType = etherType;
	}

	public String getDestinationIpAddressPrefix() {
		return this.destinationIpAddressPrefix;
	}

	public void setDestinationIpAddressPrefix(final String destinationIpAddressPrefix) {
		this.destinationIpAddressPrefix = destinationIpAddressPrefix;
	}

	public String getEtherSourceAddress() {
		return this.etherSourceAddress;
	}

	public void setEtherSourceAddress(final String etherSourceAddress) {
		this.etherSourceAddress = etherSourceAddress;
	}

	public List<Mask> getExtendedCriteria() {
		return this.extendedCriteria;
	}

	public void setExtendedCriteria(final List<Mask> extendedCriteria) {
		this.extendedCriteria = extendedCriteria;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
