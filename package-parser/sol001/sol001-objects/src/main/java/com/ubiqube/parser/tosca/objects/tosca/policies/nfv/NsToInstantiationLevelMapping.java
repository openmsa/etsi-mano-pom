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
 * The NsToInstantiationLevelMapping type is a policy type representing the
 * number of NS instances of a nested NS to be deployed at each NS instantiation
 * level of the composite NS, as defined in ETSI GS NFV-IFA 014
 * [2]tosca.nodes.nfv.NS
 */
public class NsToInstantiationLevelMapping extends Root {
	/**
	 * Number of NS instances of a nested NS to be deployed for each NS
	 * instantiation level of the composite NS.
	 */
	@Valid
	@NotNull
	@JsonProperty("number_of_instances")
	@Size(min = 1)
	private Map<String, Integer> numberOfInstances;

	@Valid
	private List<String> targets;

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
