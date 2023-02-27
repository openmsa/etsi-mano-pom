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
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VduLevel;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * The VduScalingAspectDeltas type is a policy type representing the Vdu.Compute
 * detail of an aspect deltas used for horizontal scaling, as defined in ETSI GS
 * NFV-IFA 011
 * [1]tosca.nodes.nfv.Vdu.Computetosca.nodes.nfv.Vdu.OsContainerDeployableUnit
 */
public class VduScalingAspectDeltas extends Root {
	/**
	 * Represents the scaling aspect to which this policy applies
	 */
	@Valid
	@NotNull
	@JsonProperty("aspect")
	private String aspect;

	/**
	 * Describes the Vdu.Compute scaling deltas to be applied for every scaling
	 * steps of a particular aspect.
	 */
	@Valid
	@NotNull
	@JsonProperty("deltas")
	@Size(min = 1)
	private Map<String, VduLevel> deltas;

	@Valid
	private List<String> targets;

	@NotNull
	public String getAspect() {
		return this.aspect;
	}

	public void setAspect(@NotNull final String aspect) {
		this.aspect = aspect;
	}

	@NotNull
	public Map<String, VduLevel> getDeltas() {
		return this.deltas;
	}

	public void setDeltas(@NotNull final Map<String, VduLevel> deltas) {
		this.deltas = deltas;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
