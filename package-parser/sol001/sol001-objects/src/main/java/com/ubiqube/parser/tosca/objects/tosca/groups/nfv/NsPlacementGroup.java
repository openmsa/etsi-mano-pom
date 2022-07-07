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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Members;
import java.lang.String;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.groups.Root;

/**
 * NsPlacementGroup is used for describing the affinity or anti-affinity relationship applicable between VNF instances created using different VNFDs, the Virtual Link instances created using different VLDs or the nested NS instances created using different NSDs when used in a NSD.
 */
public class NsPlacementGroup extends Root {
	/**
	 * Human readable description of the group
	 */
	@Valid
	@NotNull
	@JsonProperty("description")
	private String description;

	@Valid
	@Members("tosca.nodes.nfv.VNF")
	@Members("tosca.nodes.nfv.NsVirtualLink")
	@Members("tosca.nodes.nfv.NS")
	private List<String> members;

	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(@NotNull final String description) {
		this.description = description;
	}

	public List<String> getMembers() {
		return this.members;
	}

	public void setMembers(final List<String> members) {
		this.members = members;
	}
}
