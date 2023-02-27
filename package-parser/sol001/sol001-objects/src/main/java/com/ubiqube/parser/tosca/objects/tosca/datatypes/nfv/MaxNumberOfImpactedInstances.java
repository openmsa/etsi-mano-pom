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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Specifies the maximum number of instances of a given Vdu.Compute node or
 * VnfVirtualLink node that may be impacted simultaneously without impacting the
 * functionality of the group of a given size.
 */
public class MaxNumberOfImpactedInstances extends Root {
	/**
	 * Determines the size of the group for which the
	 * max_number_of_impacted_instances is specified. If not present the size is not
	 * limited.
	 */
	@Valid
	@JsonProperty("group_size")
	private Integer groupSize;

	/**
	 * The maximum number of instances that can be impacted simultaneously within
	 * the group of the specified size.
	 */
	@Valid
	@NotNull
	@JsonProperty("max_number_of_impacted_instances")
	private Integer maxNumberOfImpactedInstances;

	public Integer getGroupSize() {
		return this.groupSize;
	}

	public void setGroupSize(final Integer groupSize) {
		this.groupSize = groupSize;
	}

	@NotNull
	public Integer getMaxNumberOfImpactedInstances() {
		return this.maxNumberOfImpactedInstances;
	}

	public void setMaxNumberOfImpactedInstances(@NotNull final Integer maxNumberOfImpactedInstances) {
		this.maxNumberOfImpactedInstances = maxNumberOfImpactedInstances;
	}
}
