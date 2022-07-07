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
package com.ubiqube.parser.tosca.objects.tosca.nodes.nfv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Node;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import java.lang.String;
import java.util.List;
import javax.validation.Valid;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.Forwarding;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * node definition of NFP position
 */
public class NfpPosition extends Root {
	/**
	 * Identifies a rule to apply to forward traffic to CP or SAP instances corresponding to the referenced NfpPositionElement(s).
	 */
	@Valid
	@JsonProperty("forwarding_behaviour")
	private String forwardingBehaviour;

	/**
	 * Caps.
	 */
	private Forwarding forwarding;

	@Occurence({ "1", "UNBOUNDED" })
	@Node("tosca.nodes.nfv.NfpPositionElement")
	@Capability("tosca.capabilities.nfv.Forwarding")
	@Relationship("tosca.relationships.nfv.ForwardTo")
	@JsonProperty("element")
	private List<String> elementReq;

	public String getForwardingBehaviour() {
		return this.forwardingBehaviour;
	}

	public void setForwardingBehaviour(final String forwardingBehaviour) {
		this.forwardingBehaviour = forwardingBehaviour;
	}

	public Forwarding getForwarding() {
		return this.forwarding;
	}

	public void setForwarding(final Forwarding forwarding) {
		this.forwarding = forwarding;
	}

	public List<String> getElementReq() {
		return this.elementReq;
	}

	public void setElementReq(final List<String> elementReq) {
		this.elementReq = elementReq;
	}
}
