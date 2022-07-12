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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.Forwarding;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * node definition of NfpPositionElement
 */
public class NfpPositionElement extends Root {
	/**
	 * Caps.
	 */
	private Forwarding forwarding;

	@Occurence({ "1", "2" })
	@Capability("tosca.capabilities.nfv.Forwarding")
	@Relationship("tosca.relationships.nfv.ForwardTo")
	@JsonProperty("profile_element")
	private List<String> profileElementReq;

	public Forwarding getForwarding() {
		return this.forwarding;
	}

	public void setForwarding(final Forwarding forwarding) {
		this.forwarding = forwarding;
	}

	public List<String> getProfileElementReq() {
		return this.profileElementReq;
	}

	public void setProfileElementReq(final List<String> profileElementReq) {
		this.profileElementReq = profileElementReq;
	}
}
