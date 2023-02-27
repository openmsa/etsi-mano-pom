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
package com.ubiqube.parser.tosca.objects.tosca.policies.nfv;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.policies.Placement;

/**
 * The NsAffinityRule describes the affinity rules applicable for the defined
 * targetstosca.nodes.nfv.VNFtosca.nodes.nfv.NsVirtualLinktosca.nodes.nfv.NStosca.groups.nfv.NsPlacementGroup
 */
public class NsAffinityRule extends Placement {
	/**
	 * Specifies the scope of the local affinity rule.
	 */
	@Valid
	@NotNull
	@JsonProperty("scope")
	private String scope;

	@Valid
	private List<String> targets;

	@NotNull
	public String getScope() {
		return this.scope;
	}

	public void setScope(@NotNull final String scope) {
		this.scope = scope;
	}

	@Override
	public List<String> getTargets() {
		return this.targets;
	}

	@Override
	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
