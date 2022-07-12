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
package com.ubiqube.parser.tosca.objects.tosca.groups.nfv;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Members;
import com.ubiqube.parser.tosca.objects.tosca.groups.Root;

/**
 * the VNFFG group type describes a topology of the NS or a portion of the NS,
 * and optionally forwarding rules, applicable to the traffic conveyed over this
 * topology
 */
public class VNFFG extends Root {
	/**
	 * Human readable description of the group
	 */
	@Valid
	@NotNull
	@JsonProperty("description")
	private String description;

	@Valid
	@Members("tosca.nodes.nfv.NFP")
	@Members("tosca.nodes.nfv.VNF")
	@Members("tosca.nodes.nfv.PNF")
	@Members("tosca.nodes.nfv.NS")
	@Members("tosca.nodes.nfv.NsVirtualLink")
	@Members("tosca.nodes.nfv.NfpPositionElement")
	private List<String> members;

	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(@NotNull final String description) {
		this.description = description;
	}

	@Override
	public List<String> getMembers() {
		return this.members;
	}

	@Override
	public void setMembers(final List<String> members) {
		this.members = members;
	}
}
