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
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.VirtualLinkable;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

public class Forwarding extends Root {
	/**
	 * Caps.
	 */
	private VirtualLinkable virtualLinkable;

	/**
	 * Caps.
	 */
	private Forwarding forwarding;

	@Capability("tosca.capabilities.nfv.VirtualLinkable")
	@Relationship("tosca.relationships.nfv.VirtualLinksTo")
	@JsonProperty("virtual_link")
	private String virtualLinkReq;

	public VirtualLinkable getVirtualLinkable() {
		return this.virtualLinkable;
	}

	public void setVirtualLinkable(final VirtualLinkable virtualLinkable) {
		this.virtualLinkable = virtualLinkable;
	}

	public Forwarding getForwarding() {
		return this.forwarding;
	}

	public void setForwarding(final Forwarding forwarding) {
		this.forwarding = forwarding;
	}

	public String getVirtualLinkReq() {
		return this.virtualLinkReq;
	}

	public void setVirtualLinkReq(final String virtualLinkReq) {
		this.virtualLinkReq = virtualLinkReq;
	}
}
