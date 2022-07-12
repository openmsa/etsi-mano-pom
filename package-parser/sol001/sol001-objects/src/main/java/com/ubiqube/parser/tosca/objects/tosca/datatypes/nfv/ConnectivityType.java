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
 * describes additional connectivity information of a virtualLink
 */
public class ConnectivityType extends Root {
	/**
	 * Identifies the protocol a virtualLink gives access to (ethernet, mpls, odu2,
	 * ipv4, ipv6, pseudo-wire).The top layer protocol of the virtualLink protocol
	 * stack shall always be provided. The lower layer protocols may be included
	 * when there are specific requirements on these layers.
	 */
	@Valid
	@NotNull
	@JsonProperty("layer_protocols")
	private List<String> layerProtocols;

	/**
	 * Identifies the flow pattern of the connectivity
	 */
	@Valid
	@JsonProperty("flow_pattern")
	private String flowPattern;

	@NotNull
	public List<String> getLayerProtocols() {
		return this.layerProtocols;
	}

	public void setLayerProtocols(@NotNull final List<String> layerProtocols) {
		this.layerProtocols = layerProtocols;
	}

	public String getFlowPattern() {
		return this.flowPattern;
	}

	public void setFlowPattern(final String flowPattern) {
		this.flowPattern = flowPattern;
	}
}
