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
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * The VnfToLevelMapping type is a policy type representing the number of VNF
 * instances to be deployed at each NS level, as defined in ETSI GS NFV-IFA 014
 * [2]tosca.nodes.nfv.VNF
 */
public class VnfToLevelMapping extends Root {
	/**
	 * Represents the scaling aspect to which this policy applies
	 */
	@Valid
	@NotNull
	@JsonProperty("aspect")
	private String aspect;

	/**
	 * Number of VNF instances to be deployed for each NS level.
	 */
	@Valid
	@NotNull
	@JsonProperty("number_of_instances")
	@Size(min = 1)
	private Map<String, Integer> numberOfInstances;

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
	public Map<String, Integer> getNumberOfInstances() {
		return this.numberOfInstances;
	}

	public void setNumberOfInstances(@NotNull final Map<String, Integer> numberOfInstances) {
		this.numberOfInstances = numberOfInstances;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
