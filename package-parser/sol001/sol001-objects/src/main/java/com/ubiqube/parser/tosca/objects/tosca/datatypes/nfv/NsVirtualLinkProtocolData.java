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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes one protocol layer and associated protocol data for a given virtual
 * link used in a specific NS deployment flavour
 */
public class NsVirtualLinkProtocolData extends Root {
	/**
	 * Identifies one of the protocols a virtualLink gives access to (ethernet,
	 * mpls, odu2, ipv4, ipv6, pseudo-wire) as specified by the connectivity_type
	 * property.
	 */
	@Valid
	@NotNull
	@JsonProperty("associated_layer_protocol")
	private String associatedLayerProtocol;

	/**
	 * Specifies the L2 protocol data for a virtual link. Shall be present when the
	 * associatedLayerProtocol attribute indicates a L2 protocol and shall be absent
	 * otherwise.
	 */
	@Valid
	@JsonProperty("l2_protocol_data")
	private NsL2ProtocolData l2ProtocolData;

	/**
	 * Specifies the L3 protocol data for this virtual link. Shall be present when
	 * the associatedLayerProtocol attribute indicates a L3 protocol and shall be
	 * absent otherwise.
	 */
	@Valid
	@JsonProperty("l3_protocol_data")
	private NsL3ProtocolData l3ProtocolData;

	@NotNull
	public String getAssociatedLayerProtocol() {
		return this.associatedLayerProtocol;
	}

	public void setAssociatedLayerProtocol(@NotNull final String associatedLayerProtocol) {
		this.associatedLayerProtocol = associatedLayerProtocol;
	}

	public NsL2ProtocolData getL2ProtocolData() {
		return this.l2ProtocolData;
	}

	public void setL2ProtocolData(final NsL2ProtocolData l2ProtocolData) {
		this.l2ProtocolData = l2ProtocolData;
	}

	public NsL3ProtocolData getL3ProtocolData() {
		return this.l3ProtocolData;
	}

	public void setL3ProtocolData(final NsL3ProtocolData l3ProtocolData) {
		this.l3ProtocolData = l3ProtocolData;
	}
}
