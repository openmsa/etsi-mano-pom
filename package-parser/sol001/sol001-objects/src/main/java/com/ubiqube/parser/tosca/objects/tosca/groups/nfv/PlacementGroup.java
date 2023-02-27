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
package com.ubiqube.parser.tosca.objects.tosca.groups.nfv;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Members;
import com.ubiqube.parser.tosca.objects.tosca.groups.Root;

/**
 * PlacementGroup is used for describing the affinity or anti-affinity
 * relationship applicable between the virtualization containers to be created
 * based on different VDUs, or between internal VLs to be created based on
 * different VnfVirtualLinkDesc(s) or between the workloads being deployed based
 * on different Mciops
 */
public class PlacementGroup extends Root {
	/**
	 * Human readable description of the group
	 */
	@Valid
	@NotNull
	@JsonProperty("description")
	private String description;

	@Valid
	@Members("tosca.nodes.nfv.Vdu.Compute")
	@Members("tosca.nodes.nfv.Vdu.OsContainerDeployableUnit")
	@Members("tosca.nodes.nfv.VnfVirtualLink")
	@Members("tosca.nodes.nfv.Mciop")
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
