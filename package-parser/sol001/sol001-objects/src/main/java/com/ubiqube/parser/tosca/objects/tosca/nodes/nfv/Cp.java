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
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.CpProtocolData;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * Provides information regarding the purpose of the connection point
 */
public class Cp extends Root {
	/**
	 * Provides information on the addresses to be assigned to the connection
	 * point(s) instantiated from this Connection Point Descriptor
	 */
	@Valid
	@JsonProperty("protocol")
	private List<CpProtocolData> protocol;

	/**
	 * Provides information about whether the CP instantiated from this Cp is in
	 * Trunk mode (802.1Q or other), When operating in "trunk mode", the Cp is
	 * capable of carrying traffic for several VLANs. Absence of this property
	 * implies that trunkMode is not configured for the Cp i.e. It is equivalent to
	 * boolean value "false".
	 */
	@Valid
	@JsonProperty("trunk_mode")
	private Boolean trunkMode;

	/**
	 * Identifies which protocol the connection point uses for connectivity purposes
	 */
	@Valid
	@NotNull
	@JsonProperty("layer_protocols")
	private List<String> layerProtocols;

	/**
	 * Identifies the role of the port in the context of the traffic flow patterns
	 * in the VNF or parent NS
	 */
	@Valid
	@JsonProperty("role")
	private String role;

	/**
	 * Provides human-readable information on the purpose of the connection point
	 */
	@Valid
	@JsonProperty("description")
	private String description;

	public List<CpProtocolData> getProtocol() {
		return this.protocol;
	}

	public void setProtocol(final List<CpProtocolData> protocol) {
		this.protocol = protocol;
	}

	public Boolean getTrunkMode() {
		return this.trunkMode;
	}

	public void setTrunkMode(final Boolean trunkMode) {
		this.trunkMode = trunkMode;
	}

	@NotNull
	public List<String> getLayerProtocols() {
		return this.layerProtocols;
	}

	public void setLayerProtocols(@NotNull final List<String> layerProtocols) {
		this.layerProtocols = layerProtocols;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
